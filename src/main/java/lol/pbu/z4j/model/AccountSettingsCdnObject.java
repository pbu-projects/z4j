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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>CDN settings</p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsCdnObject.JSON_PROPERTY_CDN_PROVIDER,
    AccountSettingsCdnObject.JSON_PROPERTY_FALLBACK_CDN_PROVIDER,
    AccountSettingsCdnObject.JSON_PROPERTY_HOSTS,
})
@Serdeable
public class AccountSettingsCdnObject {

    public static final String JSON_PROPERTY_CDN_PROVIDER = "cdn_provider";
    public static final String JSON_PROPERTY_FALLBACK_CDN_PROVIDER = "fallback_cdn_provider";
    public static final String JSON_PROPERTY_HOSTS = "hosts";

    @Nullable
    @JsonProperty(JSON_PROPERTY_CDN_PROVIDER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String cdnProvider;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FALLBACK_CDN_PROVIDER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String fallbackCdnProvider;

    @Nullable
    @JsonProperty(JSON_PROPERTY_HOSTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid AccountSettingsCdnObjectHostsInner> hosts;

    /**
     * Add an item to the hosts property in a chainable fashion.
     *
     * @return The same instance of AccountSettingsCdnObject for chaining.
     */
    public AccountSettingsCdnObject addHostsItem(AccountSettingsCdnObjectHostsInner hostsItem) {
        if (hosts == null) {
            hosts = new ArrayList<>();
        }
        hosts.add(hostsItem);
        return this;
    }

}