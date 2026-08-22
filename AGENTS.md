# Baseline Guidelines & Context for AI Contributors

Welcome to `z4j`! This document establishes baseline context, coding standards, and testing policies for all AI agents and automated tools contributing to this repository.

---

## 1. Project Overview & Architecture

- **Description**: `z4j` is an open-source Java 21 client for the Zendesk API.
- **Tech Stack**:
  - **Language**: Java 21 (GraalVM CE) for main source code, Groovy for Spock 2 test specifications.
  - **Framework**: Micronaut 4 (`io.micronaut.library`).
  - **Build Tool**: Gradle (via `./gradlew` wrapper).
  - **API Definition**: OpenAPI Spec 2.0/3.0 located at [`src/main/resources/z4j.yaml`](file:///home/jimmy/git/pbu/z4j/src/main/resources/z4j.yaml).
- **Code Generation**:
  - Endpoint implementations and model classes rely on OpenAPI Generator driven by `z4j.yaml`.
  - When modifying or extending API coverage, inspect and update `z4j.yaml` first.

---

## 2. Testing Standards & Conventions

> [!IMPORTANT]
> **No Mocking Policy for Sandbox-Testable Functionality**
> - **Do NOT mock** HTTP calls, services, or API endpoints if they can be tested against a live sandbox/test environment (which applies to almost all features in this client).
> - All integration tests must validate real behavior against a live Zendesk instance to ensure actual Zendesk API compliance.
> - Mocking is strictly restricted to isolated unit logic that has no remote dependency or sandbox equivalent.

### A. Test Base Class & Context Hierarchy
- All integration tests MUST extend [`Z4jSpec`](file:///home/jimmy/git/pbu/z4j/src/test/groovy/lol/pbu/z4j/Z4jSpec.groovy).
- `Z4jSpec` manages shared Micronaut `ApplicationContext` instances for different user roles and error cases:
  - `adminCtx`: Authenticated using `Z4J_ADMIN_EMAIL` & `Z4J_TOKEN`
  - `agentCtx`: Authenticated using `Z4J_AGENT_EMAIL`
  - `userCtx`: Authenticated using `Z4J_END_USER_EMAIL`
  - `badTokenCtx`, `badEmailCtx`, `badUrlCtx`: Pre-configured contexts with invalid credentials for negative testing.
- `getCtx()` enforces environment validation (e.g., ensuring `Z4J_URL` is set and throwing `IllegalStateException` if `MICRONAUT_HTTP_SERVICES_ZENDESK_*` is pre-set).
- `cleanupSpec()` automatically stops all running contexts.

### B. Matrix Testing & Groovy Combinations
- Use matrix structures like `clientTestMatrix` (defining maps of `client`, `clientType`, `shouldSucceed`) to test positive and negative paths across user roles in single feature methods.
- Use Groovy's `combinations()` in Spock `where:` blocks to generate Cartesian product permutations (e.g., `[[client, clientType], localeAbbreviation, sortBy, sortOrder].combinations()`).

### C. Dynamic Test Data Generation
- Do NOT hardcode static strings or IDs in tests.
- Use the inherited `@Shared Faker faker` instance from `Z4jSpec` (e.g., `faker.chuckNorris().fact()`, `faker.animal().name()`, `faker.movie().name()`).
- Append UUID entropy (e.g., `UUID.randomUUID().toString()`) when creating unique titles/names to avoid collisions in the live sandbox.

### D. Sandbox Resource Lifecycle & Cleanup
- Any test that creates resources on the live Zendesk sandbox (categories, user segments, tickets, ticket fields) MUST clean them up in Spock's `cleanup:` block.
- Use defensive cleanup (e.g. `try ... catch (NullPointerException ignored)`) so test cleanup does not fail if creation was unsuccessful.

### E. Self-Healing Fixtures & Polling
- If a test requires specific pre-existing sandbox state (e.g., >100 ticket fields for cursor pagination tests), check for required data, programmatically create missing items, and poll with retry delays (`sleep(2000)`) to account for Zendesk's eventual consistency index.

### F. Reactive Blocking & Assertion Discipline
- Client methods return Reactor `Mono<T>`; tests call `.block()` synchronously.
- Use `noExceptionThrown()` for positive assertions.
- Use `thrown(HttpClientException)` or `thrown(HttpClientResponseException)` with HTTP status checks (e.g., `error.getStatus() == FORBIDDEN`) for negative assertions.
- Keep Zendesk backend validation out of scope—focus strictly on client invocation and model deserialization without runtime errors.

### G. BDD Method Naming & Block Annotations
- Use descriptive Spock method names with parameter placeholders (e.g., `"can use CreateCategory as an #userType for the '#localeAbbreviation' locale"`).
- Include narrative string comments on Spock blocks (`given: "..."`, `when: "..."`, `then: "..."`, `cleanup: "..."`).

---

## 3. Development & Workflow Rules

- **Code Style**: Adhere to the Google Java Style Guide.
- **Commit Messages**: Follow [Conventional Commits](file:///home/jimmy/git/pbu/z4j/build.gradle.kts#L20) format (`feat:`, `fix:`, `docs:`, `test:`, `refactor:`, `chore:`).
- **Verification**:
  - Always execute verification using `./gradlew check` or `./gradlew test` before marking work as complete.
  - Fix any failures at the root cause; do not swallow errors or weaken assertions to pass builds.

---

## 4. Expanding this Document

Future contributors (both humans and AI) should update this file whenever new baseline conventions, environment requirements, or architectural decisions are established.
