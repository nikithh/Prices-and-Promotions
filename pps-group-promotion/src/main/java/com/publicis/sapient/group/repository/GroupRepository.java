/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.publicis.sapient.group.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.publicis.sapient.group.model.Group;

/**
 *
 * @author mnanjan
 * @since March 2020
 * @see Repository for Group collection
 */

public interface GroupRepository extends MongoRepository<Group, Integer> {

    /**
     * @see Custom query to get group by groupName
     * @param groupName
     * @return Group
     */
    @Query(value = "{'groupName':?0}")
    public Group getGroupByName(String groupName);

}
