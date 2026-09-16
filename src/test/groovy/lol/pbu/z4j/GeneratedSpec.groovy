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
package lol.pbu.z4j

import lol.pbu.z4j.model.Follower
import lol.pbu.z4j.model.TicketCustomField
import spock.lang.Specification

import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

class GeneratedSpec extends Specification {

    def "Generated annotation is retained at runtime and targets relevant elements"() {
        given:
        def annotationClass = Generated

        expect:
        annotationClass.isAnnotation()

        and: "retention policy is RUNTIME so it is preserved in class bytecode and visible to reflection"
        def retention = annotationClass.getAnnotation(Retention)
        retention != null
        retention.value() == RetentionPolicy.RUNTIME

        and: "documented annotation is present"
        annotationClass.isAnnotationPresent(Documented)

        and: "target includes TYPE, METHOD, CONSTRUCTOR, FIELD"
        def target = annotationClass.getAnnotation(Target)
        target != null
        target.value().toList().containsAll([
                ElementType.TYPE,
                ElementType.METHOD,
                ElementType.CONSTRUCTOR,
                ElementType.FIELD
        ])
    }

    def "models are annotated with Generated"() {
        expect:
        Follower.isAnnotationPresent(Generated)
        TicketCustomField.isAnnotationPresent(Generated)
        TicketCustomField.Text.isAnnotationPresent(Generated)
    }

    @Generated
    static class SampleAnnotatedClass {}

    def "default annotation values are empty"() {
        when:
        def generated = SampleAnnotatedClass.getAnnotation(Generated)

        then:
        generated != null
        generated.value() == new String[0]
        generated.date() == ""
        generated.comments() == ""
    }
}
