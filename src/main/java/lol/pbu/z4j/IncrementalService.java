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

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lol.pbu.z4j.client.IncrementalClient;
import lol.pbu.z4j.model.*;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * Service for high-level incremental export streaming and fetching.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Slf4j
@Singleton
public class IncrementalService {

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

    @Data
    @Accessors(chain = true)
    public static class IncrementalPage<T> {
        private List<T> results;
        private String cursor;
        private String afterCursor;
        private String beforeCursor;
        private Boolean endOfStream;
        private Integer count;
    }

    @Data
    @Accessors(chain = true)
    public static class IncrementalTimePage<T> {
        private List<T> results;
        private Long endTime;
        private String nextPage;
        private Boolean endOfStream;
        private Integer count;
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

        @SuppressWarnings("unchecked")
        public Mono<IncrementalPage<T>> fetchPage() {
            if (resourceType == ResourceType.TICKET) {
                return client.exportTickets(startTime, cursor, perPage)
                        .map(res -> {
                            IncrementalPage<T> page = new IncrementalPage<>();
                            page.setResults((List<T>) res.getTickets());
                            page.setCursor(res.getCursor() != null ? res.getCursor() : res.getAfterCursor());
                            page.setAfterCursor(res.getAfterCursor());
                            page.setBeforeCursor(res.getBeforeCursor());
                            page.setEndOfStream(res.getEndOfStream());
                            page.setCount(res.getCount());
                            return page;
                        });
            } else if (resourceType == ResourceType.USER) {
                return client.exportUsers(startTime, cursor, perPage)
                        .map(res -> {
                            IncrementalPage<T> page = new IncrementalPage<>();
                            page.setResults((List<T>) res.getUsers());
                            page.setCursor(res.getCursor() != null ? res.getCursor() : res.getAfterCursor());
                            page.setAfterCursor(res.getAfterCursor());
                            page.setBeforeCursor(res.getBeforeCursor());
                            page.setEndOfStream(res.getEndOfStream());
                            page.setCount(res.getCount());
                            return page;
                        });
            }
            return Mono.error(new IllegalStateException("Unsupported resource type for cursor export: " + resourceType));
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
            if (resourceType == ResourceType.TICKET) {
                return client.exportTickets(st, cur, sz)
                        .map(res -> {
                            IncrementalPage<T> page = new IncrementalPage<>();
                            page.setResults((List<T>) res.getTickets());
                            page.setCursor(res.getCursor() != null ? res.getCursor() : res.getAfterCursor());
                            page.setAfterCursor(res.getAfterCursor());
                            page.setBeforeCursor(res.getBeforeCursor());
                            page.setEndOfStream(res.getEndOfStream());
                            page.setCount(res.getCount());
                            return page;
                        });
            } else if (resourceType == ResourceType.USER) {
                return client.exportUsers(st, cur, sz)
                        .map(res -> {
                            IncrementalPage<T> page = new IncrementalPage<>();
                            page.setResults((List<T>) res.getUsers());
                            page.setCursor(res.getCursor() != null ? res.getCursor() : res.getAfterCursor());
                            page.setAfterCursor(res.getAfterCursor());
                            page.setBeforeCursor(res.getBeforeCursor());
                            page.setEndOfStream(res.getEndOfStream());
                            page.setCount(res.getCount());
                            return page;
                        });
            }
            return Mono.error(new IllegalStateException("Unsupported resource type"));
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

        @SuppressWarnings("unchecked")
        public Mono<IncrementalTimePage<T>> fetchPage() {
            if (startTime == null) {
                return Mono.error(new IllegalArgumentException("since() must be specified for time-based incremental exports"));
            }
            if (resourceType == ResourceType.ORGANIZATION) {
                return client.exportOrganizations(startTime, perPage)
                        .map(res -> {
                            IncrementalTimePage<T> page = new IncrementalTimePage<>();
                            page.setResults((List<T>) res.getOrganizations());
                            page.setEndTime(res.getEndTime());
                            page.setNextPage(res.getNextPage());
                            page.setEndOfStream(res.getEndOfStream());
                            page.setCount(res.getCount());
                            return page;
                        });
            } else if (resourceType == ResourceType.TICKET_EVENT) {
                return client.exportTicketEvents(startTime)
                        .map(res -> {
                            IncrementalTimePage<T> page = new IncrementalTimePage<>();
                            page.setResults((List<T>) res.getTicketEvents());
                            page.setEndTime(res.getEndTime());
                            page.setNextPage(res.getNextPage());
                            page.setEndOfStream(res.getEndOfStream());
                            page.setCount(res.getCount());
                            return page;
                        });
            }
            return Mono.error(new IllegalStateException("Unsupported resource type for time export: " + resourceType));
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
                                    return Mono.empty();
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
            if (resourceType == ResourceType.ORGANIZATION) {
                return client.exportOrganizations(st, sz)
                        .map(res -> {
                            IncrementalTimePage<T> page = new IncrementalTimePage<>();
                            page.setResults((List<T>) res.getOrganizations());
                            page.setEndTime(res.getEndTime());
                            page.setNextPage(res.getNextPage());
                            page.setEndOfStream(res.getEndOfStream());
                            page.setCount(res.getCount());
                            return page;
                        });
            } else if (resourceType == ResourceType.TICKET_EVENT) {
                return client.exportTicketEvents(st)
                        .map(res -> {
                            IncrementalTimePage<T> page = new IncrementalTimePage<>();
                            page.setResults((List<T>) res.getTicketEvents());
                            page.setEndTime(res.getEndTime());
                            page.setNextPage(res.getNextPage());
                            page.setEndOfStream(res.getEndOfStream());
                            page.setCount(res.getCount());
                            return page;
                        });
            }
            return Mono.error(new IllegalStateException("Unsupported resource type"));
        }
    }

    private static Object getRecordId(Object record) {
        if (record instanceof Organization) {
            return ((Organization) record).getId();
        } else if (record instanceof TicketEvent) {
            return ((TicketEvent) record).getId();
        } else if (record instanceof Ticket) {
            return ((Ticket) record).getId();
        } else if (record instanceof User) {
            return ((User) record).getId();
        }
        try {
            var method = record.getClass().getMethod("getId");
            return method.invoke(record);
        } catch (Exception e) {
            return null;
        }
    }

    private static Long extractStartTimeFromUrl(String url) {
        if (url == null) {
            return null;
        }
        try {
            int idx = url.indexOf("start_time=");
            if (idx != -1) {
                int ampersandIdx = url.indexOf("&", idx);
                String val = ampersandIdx != -1 ? url.substring(idx + 11, ampersandIdx) : url.substring(idx + 11);
                return Long.parseLong(val);
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
