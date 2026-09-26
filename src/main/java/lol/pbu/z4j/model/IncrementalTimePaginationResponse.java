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
package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.experimental.Accessors;
import lol.pbu.z4j.Generated;

import java.util.List;

/**
 * Base class for time-based incremental export responses.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Data
@Accessors(chain = true)
@Serdeable
@Generated
public abstract class IncrementalTimePaginationResponse<T> {
    @Nullable
    @JsonProperty("end_time")
    private Long endTime;

    @Nullable
    @JsonProperty("next_page")
    private String nextPage;

    @Nullable
    @JsonProperty("end_of_stream")
    private Boolean endOfStream;

    @Nullable
    @JsonProperty("count")
    private Integer count;

    @JsonIgnore
    public abstract List<T> getResults();
}
