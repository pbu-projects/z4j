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
package lol.pbu.z4j.client;

/**
 * Shared constraints for string path parameters so callers cannot inject slashes, dots, queries, fragments, or whitespace.
 */
public final class PathValidation {

    public static final int MAX_PATH_SEGMENT_LENGTH = 256;
    public static final String SAFE_SEGMENT_PATTERN = "^(?!\\.\\.?$)(?!.*\\.\\.)[^/?#\\\\\\s]+$";

    private PathValidation() {
    }
}
