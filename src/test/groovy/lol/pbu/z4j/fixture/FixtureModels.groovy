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
package lol.pbu.z4j.fixture

import groovy.transform.Canonical
import groovy.transform.CompileStatic

@CompileStatic
@Canonical
class TicketItem {
    String subject
    String comment
    String updateComment
}

@CompileStatic
@Canonical
class TicketFixtures {
    List<TicketItem> ticketData = []
}

@CompileStatic
@Canonical
class CategoryItem {
    String userType
    String categoryName
    String description
}

@CompileStatic
@Canonical
class SortOptionItem {
    String sortBy
    String sortOrder
}

@CompileStatic
@Canonical
class CategoryFixtures {
    List<CategoryItem> categories = []
    List<CategoryItem> deleteCategories = []
    List<SortOptionItem> sortOptions = []
}

@CompileStatic
@Canonical
class UserSegmentItem {
    String userType
    String segmentName
    String updatedName
}

@CompileStatic
@Canonical
class UserSegmentFixtures {
    List<UserSegmentItem> userSegments = []
}

@CompileStatic
@Canonical
class ArticleFixtures {
    List<SortOptionItem> articleQueries = []
}
