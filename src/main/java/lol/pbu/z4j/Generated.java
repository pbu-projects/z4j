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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker annotation used to identify generated code within the {@code z4j} library.
 * <p>
 * Classes, methods, and constructors annotated with this annotation are excluded
 * from code coverage analysis (e.g. by JaCoCo) based on the {@code Generated} naming convention.
 * </p>
 *
 * @author Jonathan-Zollinger
 * @since 0.2.3
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.PACKAGE,
        ElementType.TYPE,
        ElementType.ANNOTATION_TYPE,
        ElementType.METHOD,
        ElementType.CONSTRUCTOR,
        ElementType.FIELD,
        ElementType.LOCAL_VARIABLE,
        ElementType.PARAMETER
})
public @interface Generated {

    /**
     * The name of the generator or tool that generated the code.
     *
     * @return name of the code generator
     */
    String[] value() default {};

    /**
     * A date or timestamp string when the code was generated.
     *
     * @return generation timestamp
     */
    String date() default "";

    /**
     * Additional comments about the generation.
     *
     * @return comments
     */
    String comments() default "";
}
