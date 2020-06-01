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

package com.publicis.sapient.group.service;

import java.util.List;
import com.publicis.sapient.group.model.Group;

/**
 *
 * @author prejayak
 *
 */

public interface GroupService {

    public Group insertGroup(Group group);

    public List<Group> getAllGroups();

    public List<String> getAllGroupNames();

    public Boolean addToProductList(String groupName,
            Integer productId);

    public void deleteGroup(final String groupName);
}
