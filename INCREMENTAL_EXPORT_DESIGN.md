# Incremental Export Architecture & Design Specification

**Status**: Approved for Implementation  
**Target Release**: `0.3.0`  
**Tracking Issue**: [#151 Add support for the Incremental Export endpoints](https://github.com/pbu-projects/z4j/issues/151)  
**Future Improvement Issues**:
- [#158 Support sideloading in Incremental Exports via composite envelope / batch streaming](https://github.com/pbu-projects/z4j/issues/158)
- [#159 Provide safe blocking Stream bridge for Incremental Exports with event loop protection](https://github.com/pbu-projects/z4j/issues/159)
- [#160 Add cursor checkpointing and failure recovery hooks to Incremental Export streams](https://github.com/pbu-projects/z4j/issues/160)

---

## 1. Overview & Motivation

Zendesk's Search API (`/api/v2/search/export`) caps results at 1,000 records, shares the interactive search quota, and is rate-limited to 100 requests/minute. The **Incremental Export API** (`/api/v2/incremental/...`) provides a dedicated rate limit bucket and pages up to 1,000 records per call, making it the primary mechanism for bulk ingestion and change-data-capture.

### Key API Differences in Zendesk
1. **Cursor-Based Exports**: Available for **Tickets**, **Users**, and **Custom Object Records**. Paginates via opaque `cursor` / `after_cursor` tokens until `end_of_stream: true`. Deterministic, no duplicate-timestamp issues.
2. **Time-Based Exports**: Available for **Organizations**, **Ticket Events**, and **Ticket Metric Events**. (Note: Organizations do *not* support cursor exports). Paginates via `start_time` / `end_time` or `next_page` URL. Can produce duplicate records across adjacent page boundaries when records share timestamps.

---

## 2. Core Architecture

The implementation follows a decoupled three-tier architecture:

```
┌────────────────────────────────────────────────────────┐
│ Tier 3: Fluent Reactive Service & Typed Builders       │
│ - IncrementalService (@Singleton)                      │
│ - CursorIncrementalBuilder<T> / TimeIncrementalBuilder<T>│
│ - Reactive Paging Engine (.expand / boundary dedupe)   │
└───────────────────────────┬────────────────────────────┘
                            │ Calls Mono<...>
┌───────────────────────────▼────────────────────────────┐
│ Tier 2: Declarative HTTP Client                        │
│ - @Client("zendesk") IncrementalClient                 │
│ - @Retryable with Micronaut HTTP annotations           │
└───────────────────────────┬────────────────────────────┘
                            │ Deserializes via
┌───────────────────────────▼────────────────────────────┐
│ Tier 1: Serde Data Transfer Objects                    │
│ - @Serdeable IncrementalTicketCursorResponse           │
│ - @Serdeable IncrementalUserCursorResponse             │
│ - @Serdeable IncrementalOrganizationTimeResponse       │
│ - @Serdeable IncrementalTicketEventTimeResponse        │
└────────────────────────────────────────────────────────┘
```

---

## 3. Component Details & Decisions

### A. Declarative Client (`IncrementalClient`)
Defined under `lol.pbu.z4j.client.IncrementalClient` with `@Client("zendesk")`:

```java
@Retryable
@Client("zendesk")
public interface IncrementalClient {

    @Get("/api/v2/incremental/tickets/cursor.json")
    Mono<IncrementalTicketCursorResponse> exportTickets(
        @QueryValue("start_time") @Nullable Long startTime,
        @QueryValue("cursor") @Nullable String cursor,
        @QueryValue("per_page") @Nullable @Max(1000) Integer perPage
    );

    @Get("/api/v2/incremental/users/cursor.json")
    Mono<IncrementalUserCursorResponse> exportUsers(
        @QueryValue("start_time") @Nullable Long startTime,
        @QueryValue("cursor") @Nullable String cursor,
        @QueryValue("per_page") @Nullable @Max(1000) Integer perPage
    );

    @Get("/api/v2/incremental/organizations.json")
    Mono<IncrementalOrganizationTimeResponse> exportOrganizations(
        @QueryValue("start_time") @NotNull Long startTime,
        @QueryValue("per_page") @Nullable @Max(1000) Integer perPage
    );

    @Get("/api/v2/incremental/ticket_events.json")
    Mono<IncrementalTicketEventTimeResponse> exportTicketEvents(
        @QueryValue("start_time") @NotNull Long startTime
    );
}
```

### B. High-Level Service (`IncrementalService`)
Provides entry points returning typed builders:
* `incrementalService.tickets()` $\to$ `CursorIncrementalBuilder<Ticket>`
* `incrementalService.users()` $\to$ `CursorIncrementalBuilder<User>`
* `incrementalService.organizations()` $\to$ `TimeIncrementalBuilder<Organization>`
* `incrementalService.ticketEvents()` $\to$ `TimeIncrementalBuilder<TicketEvent>`

### C. Typed Builders
1. **`CursorIncrementalBuilder<T>`**:
   * `.since(Instant start)`: Sets initial export timestamp. Validates that `startTime` is at least 70 seconds in the past (`Instant.now().minusSeconds(70)`) to account for client/server clock drift and network latency.
   * `.after(String cursor)`: Resumes from a previously persisted cursor (mutually exclusive with `.since()`).
   * `.perPage(int size)`: Custom page size ($\le 1,000$).
   * `.delayBetweenPages(Duration delay)`: Configurable pacing between page requests (non-blocking via `Mono.delay`).
   * `.flux()`: Terminal operation returning `Flux<T>`.
   * `.fetchPage()`: Terminal operation returning `Mono<IncrementalPage<T>>` for single-page fetch.

2. **`TimeIncrementalBuilder<T>`**:
   * `.since(Instant start)`: Sets initial start time (enforcing the 70s cushion).
   * `.perPage(int size)`: Custom page size ($\le 1,000$).
   * `.delayBetweenPages(Duration delay)`: Pacing delay.
   * `.flux()`: Terminal operation returning `Flux<T>` with subscription-scoped sliding-window boundary deduplication.
   * `.fetchPage()`: Terminal operation returning `Mono<IncrementalTimePage<T>>`.

### D. Time-Based Boundary Deduplication (Thread-Safe & Bounded)
To prevent Out-Of-Memory errors from unbounded `.distinct()` and prevent state-leaking across subscribers:
* Deduplication state is scoped **per subscription** using `Flux.defer()`.
* Uses a bounded `LinkedHashSet` capped at 1,000 IDs to deduplicate records that share the timestamp boundary between page $N$ and $N+1$:
```java
public Flux<T> flux() {
    return Flux.defer(() -> {
        Set<Long> recentIds = Collections.newSetFromMap(new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Long, Boolean> eldest) {
                return size() > 1000;
            }
        });
        return executePagingLoop().filter(record -> recentIds.add(record.getId()));
    });
}
```

### E. Time-Based Same-Second Loop Protection
In time-based exports where $>1,000$ records occur in the same epoch second, `page.getEndTime()` equals `lastStartTime`. The engine inspects `page.getNextPage()` URL or verifies forward timestamp progress to avoid infinite cycles on identical timestamps.

### F. Rate Limiting & Pacing Mechanics
1. **Proactive Pacing**: Callers can configure `.delayBetweenPages(Duration.ofSeconds(6))` for courteous 10 RPM pacing.
2. **Page 1 Fast-Path**: The delay is applied strictly to subsequent `.expand()` calls using `Mono.delay(delayBetweenPages)`. The initial request fires immediately without a penalty delay.
3. **Filter Safety Net**: Requests pass through `RateLimitFilter`. HTTP 429 responses are caught, reading `Retry-After`, and retrying up to 5 times via non-blocking `Mono.delay()`.
4. **Tracking**: Response headers (`zendesk-ratelimit-*`, `X-Rate-Limit-*`) are recorded in `RateLimitTracker`.

---

## 4. Live Sandbox Testing Strategy

In accordance with `AGENTS.md` and repository standards, tests run against live Zendesk sandbox environments (`Z4jSpec`):

1. **Role Context Matrix**:
   * `adminCtx`: Full execution and streaming assertions.
   * `agentCtx`: Expect `403 FORBIDDEN` (`HttpClientResponseException`).
   * `userCtx`: Expect `403 FORBIDDEN`.
   * `badTokenCtx` / `badUrlCtx`: Expect `HttpClientException`.
2. **Micro-Paging (`per_page=1` or `2`)**:
   Enables comprehensive multi-page traversal tests even when the sandbox ticket population is small.
3. **Eventual Consistency & Self-Healing Fixtures**:
   If test tickets must be seeded, they are created with UUID entropy. Polling with timeout (or querying historical windows) accounts for Zendesk's 1–2 minute indexing latency. Cleanups execute defensively in `cleanupSpec`.
