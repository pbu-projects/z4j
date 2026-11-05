/*
 * Copyright 2026 Peanut Butter Unicorn, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lol.pbu.z4j;

import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lol.pbu.z4j.client.IncrementalClient;
import lol.pbu.z4j.model.IncrementalCursorPaginationResponse;
import lol.pbu.z4j.model.IncrementalTimePaginationResponse;
import lol.pbu.z4j.model.Organization;
import lol.pbu.z4j.model.Ticket;
import lol.pbu.z4j.model.TicketEvent;
import lol.pbu.z4j.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service for high-level incremental export streaming and fetching.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.7
 */
@Singleton
public class IncrementalService {

    private static final Logger log = LoggerFactory.getLogger(IncrementalService.class);

    @Inject
    IncrementalClient incrementalClient;

    public CursorIncrementalBuilder<Ticket> tickets() {
        return new CursorIncrementalBuilder<>(ResourceType.TICKET, incrementalClient);
    }

    public CursorIncrementalBuilder<User> users() {
        return new CursorIncrementalBuilder<>(ResourceType.USER, incrementalClient);
    }

    public TimeIncrementalBuilder<Organization> organizations() {
        return new TimeIncrementalBuilder<>(ResourceType.ORGANIZATION, incrementalClient);
    }

    public TimeIncrementalBuilder<TicketEvent> ticketEvents() {
        return new TimeIncrementalBuilder<>(ResourceType.TICKET_EVENT, incrementalClient);
    }

    public enum ResourceType {
        TICKET, USER, ORGANIZATION, TICKET_EVENT
    }

    @Serdeable
    @RecordBuilder
    @Generated
    public record IncrementalPage<T>(
            List<T> results,
            String cursor,
            String afterCursor,
            String beforeCursor,
            Boolean endOfStream,
            Integer count
    ) implements IncrementalServiceIncrementalPageBuilder.With<T> {
        public IncrementalPage() {
            this(null, null, null, null, null, null);
        }

        public static <T> IncrementalServiceIncrementalPageBuilder<T> builder() {
            return IncrementalServiceIncrementalPageBuilder.builder();
        }

        public List<T> getResults() {
            return results;
        }

        public String getCursor() {
            return cursor;
        }

        public String getAfterCursor() {
            return afterCursor;
        }

        public String getBeforeCursor() {
            return beforeCursor;
        }

        public Boolean getEndOfStream() {
            return endOfStream;
        }

        public Integer getCount() {
            return count;
        }
    }

    @Serdeable
    @RecordBuilder
    @Generated
    public record IncrementalTimePage<T>(
            List<T> results,
            Long endTime,
            String nextPage,
            Boolean endOfStream,
            Integer count
    ) implements IncrementalServiceIncrementalTimePageBuilder.With<T> {
        public IncrementalTimePage() {
            this(null, null, null, null, null);
        }

        public static <T> IncrementalServiceIncrementalTimePageBuilder<T> builder() {
            return IncrementalServiceIncrementalTimePageBuilder.builder();
        }

        public List<T> getResults() {
            return results;
        }

        public Long getEndTime() {
            return endTime;
        }

        public String getNextPage() {
            return nextPage;
        }

        public Boolean getEndOfStream() {
            return endOfStream;
        }

        public Integer getCount() {
            return count;
        }
    }

    public static class CursorIncrementalBuilder<T> {
        private final ResourceType resourceType;
        private final IncrementalClient client;
        private Long startTime;
        private String cursor;
        private Integer perPage;
        private Duration delayBetweenPages;

        public CursorIncrementalBuilder(ResourceType resourceType, IncrementalClient client) {
            this.resourceType = resourceType;
            this.client = client;
        }

        public CursorIncrementalBuilder<T> since(Instant start) {
            if (start != null) {
                Instant threshold = Instant.now().minusSeconds(70);
                if (start.isAfter(threshold)) {
                    throw new IllegalArgumentException("Start time must be at least 70 seconds in the past to account for clock drift.");
                }
                this.startTime = start.getEpochSecond();
            }
            return this;
        }

        public CursorIncrementalBuilder<T> since(long epochSeconds) {
            return since(Instant.ofEpochSecond(epochSeconds));
        }

        public CursorIncrementalBuilder<T> after(String cursor) {
            this.cursor = cursor;
            return this;
        }

        public CursorIncrementalBuilder<T> perPage(int perPage) {
            if (perPage > 1000) {
                throw new IllegalArgumentException("perPage cannot exceed 1000");
            }
            this.perPage = perPage;
            return this;
        }

        public CursorIncrementalBuilder<T> delayBetweenPages(Duration delay) {
            this.delayBetweenPages = delay;
            return this;
        }

        public Mono<IncrementalPage<T>> fetchPage() {
            return fetchPageForCursor(startTime, cursor, perPage);
        }

        public Flux<T> flux() {
            final String initialCursor = this.cursor;
            final Long initialStartTime = this.startTime;
            final Integer size = this.perPage;
            final Duration delay = this.delayBetweenPages;

            return fetchPageForCursor(initialStartTime, initialCursor, size)
                    .expand(page -> {
                        String nextCursor = page.getCursor() != null ? page.getCursor() : page.getAfterCursor();
                        if (Boolean.TRUE.equals(page.getEndOfStream()) || nextCursor == null || nextCursor.isEmpty()) {
                            return Mono.empty();
                        }
                        Mono<IncrementalPage<T>> nextMono = fetchPageForCursor(null, nextCursor, size);
                        if (delay != null) {
                            return Mono.delay(delay).flatMap(ignored -> nextMono);
                        }
                        return nextMono;
                    })
                    .flatMapIterable(page -> page.getResults() != null ? page.getResults() : Collections.emptyList());
        }

        @SuppressWarnings("unchecked")
        private Mono<IncrementalPage<T>> fetchPageForCursor(Long st, String cur, Integer sz) {
            Mono<? extends IncrementalCursorPaginationResponse<?>> mono;
            if (resourceType == ResourceType.TICKET) {
                mono = client.exportTickets(st, cur, sz);
            } else if (resourceType == ResourceType.USER) {
                mono = client.exportUsers(st, cur, sz);
            } else {
                return Mono.error(new IllegalStateException("Unsupported resource type for cursor export: " + resourceType));
            }

            return mono.map(res -> new IncrementalPage<>(
                    (List<T>) res.results(),
                    res.cursor() != null ? res.cursor() : res.afterCursor(),
                    res.afterCursor(),
                    res.beforeCursor(),
                    res.endOfStream(),
                    res.count()
            ));
        }
    }

    public static class TimeIncrementalBuilder<T> {
        private final ResourceType resourceType;
        private final IncrementalClient client;
        private Long startTime;
        private Integer perPage;
        private Duration delayBetweenPages;

        public TimeIncrementalBuilder(ResourceType resourceType, IncrementalClient client) {
            this.resourceType = resourceType;
            this.client = client;
        }

        public TimeIncrementalBuilder<T> since(Instant start) {
            if (start != null) {
                Instant threshold = Instant.now().minusSeconds(70);
                if (start.isAfter(threshold)) {
                    throw new IllegalArgumentException("Start time must be at least 70 seconds in the past to account for clock drift.");
                }
                this.startTime = start.getEpochSecond();
            }
            return this;
        }

        public TimeIncrementalBuilder<T> since(long epochSeconds) {
            return since(Instant.ofEpochSecond(epochSeconds));
        }

        public TimeIncrementalBuilder<T> perPage(int perPage) {
            if (perPage > 1000) {
                throw new IllegalArgumentException("perPage cannot exceed 1000");
            }
            this.perPage = perPage;
            return this;
        }

        public TimeIncrementalBuilder<T> delayBetweenPages(Duration delay) {
            this.delayBetweenPages = delay;
            return this;
        }

        public Mono<IncrementalTimePage<T>> fetchPage() {
            if (startTime == null) {
                return Mono.error(new IllegalArgumentException("since() must be specified for time-based incremental exports"));
            }
            return fetchPageAtTime(startTime, perPage);
        }

        public Flux<T> flux() {
            if (startTime == null) {
                return Flux.error(new IllegalArgumentException("since() must be specified for time-based incremental exports"));
            }
            final long initialSt = this.startTime;
            final Integer size = this.perPage;
            final Duration delay = this.delayBetweenPages;

            return Flux.defer(() -> {
                Set<Object> recentIds = Collections.newSetFromMap(new LinkedHashMap<Object, Boolean>() {
                    @Override
                    protected boolean removeEldestEntry(Map.Entry<Object, Boolean> eldest) {
                        return size() > 1000;
                    }
                });

                class State {
                    long currentSt = initialSt;
                }
                State state = new State();

                return fetchPageAtTime(state.currentSt, size)
                        .expand(page -> {
                            if (Boolean.TRUE.equals(page.getEndOfStream()) || page.getResults() == null || page.getResults().isEmpty()) {
                                return Mono.empty();
                            }
                            Long nextTime = page.getEndTime();
                            if (nextTime == null || nextTime <= state.currentSt) {
                                Long extracted = extractStartTimeFromUrl(page.getNextPage());
                                if (extracted != null && extracted > state.currentSt) {
                                    nextTime = extracted;
                                } else {
                                    return Mono.error(new IllegalStateException(
                                            "Incremental export stalled: end_time (" + nextTime
                                                    + ") did not advance past start_time (" + state.currentSt
                                                    + ") and next_page URL was unavailable."));
                                }
                            }
                            state.currentSt = nextTime;
                            Mono<IncrementalTimePage<T>> nextMono = fetchPageAtTime(state.currentSt, size);
                            if (delay != null) {
                                return Mono.delay(delay).flatMap(ignored -> nextMono);
                            }
                            return nextMono;
                        })
                        .flatMapIterable(page -> page.getResults() != null ? page.getResults() : Collections.emptyList())
                        .filter(record -> {
                            Object id = getRecordId(record);
                            if (id == null) {
                                return true;
                            }
                            return recentIds.add(id);
                        });
            });
        }

        @SuppressWarnings("unchecked")
        private Mono<IncrementalTimePage<T>> fetchPageAtTime(long st, Integer sz) {
            Mono<? extends IncrementalTimePaginationResponse<?>> mono;
            if (resourceType == ResourceType.ORGANIZATION) {
                mono = client.exportOrganizations(st, sz);
            } else if (resourceType == ResourceType.TICKET_EVENT) {
                mono = client.exportTicketEvents(st);
            } else {
                return Mono.error(new IllegalStateException("Unsupported resource type for time export: " + resourceType));
            }

            return mono.map(res -> new IncrementalTimePage<>(
                    (List<T>) res.results(),
                    res.endTime(),
                    res.nextPage(),
                    res.endOfStream(),
                    res.count()
            ));
        }
    }

    static Object getRecordId(Object record) {
        if (record instanceof Organization org) {
            return org.id();
        } else if (record instanceof TicketEvent event) {
            return event.id();
        } else if (record instanceof Ticket ticket) {
            return ticket.id();
        } else if (record instanceof User user) {
            return user.id();
        }
        return null;
    }

    static Long extractStartTimeFromUrl(String url) {
        if (url == null || url.isBlank()) {
            return null;
        }
        try {
            URI uri = URI.create(url);
            String query = uri.getRawQuery();
            if (query != null && !query.isEmpty()) {
                for (String param : query.split("&")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2 && "start_time".equals(pair[0])) {
                        return Long.parseLong(pair[1]);
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
