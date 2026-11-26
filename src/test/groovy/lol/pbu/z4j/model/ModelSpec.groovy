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
package lol.pbu.z4j.model

import io.micronaut.context.ApplicationContext
import io.micronaut.serde.ObjectMapper
import net.datafaker.Faker
import spock.lang.Shared
import spock.lang.Specification

abstract class ModelSpec extends Specification {

    private static final ApplicationContext SHARED_CONTEXT = createContext()
    private static final ObjectMapper SHARED_MAPPER = SHARED_CONTEXT.getBean(ObjectMapper.class)

    @Shared
    ObjectMapper objectMapper = SHARED_MAPPER

    @Shared
    Faker faker = new Faker()

    private static ApplicationContext createContext() {
        ApplicationContext ctx = ApplicationContext.builder().deduceEnvironment(false).start()
        Runtime.runtime.addShutdownHook(new Thread({ ctx.close() }))
        return ctx
    }
}
