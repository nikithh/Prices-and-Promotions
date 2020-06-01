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

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import com.publicis.sapient.group.exception.GroupDoesntExist;
import com.publicis.sapient.group.exception.GroupNameAlreadyExists;
import com.publicis.sapient.group.model.Group;
import com.publicis.sapient.group.repository.GroupRepository;

/**
 *
 * @author mnanjan
 * @author prejayak
 * @since March 2020
 * @see Functionalities for group service
 */

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    public GroupRepository groupRepository;

    /**
     * @see Functionality to insert group
     * @param group
     * @return Group
     */
    @Override
    public Group insertGroup(final Group group) {
        final List<Group> groups = this.groupRepository.findAll();
        final IntSummaryStatistics maxIdStats = groups.stream().collect(Collectors.summarizingInt(Group::getGroupId));
        final Integer maxId = maxIdStats.getMax();
        final Boolean groupExists = groups.stream().anyMatch(g -> g.getGroupName().equals(group.getGroupName()));
        if (Boolean.TRUE.equals(groupExists)) {
            throw new GroupNameAlreadyExists("Group Name with the name " + group.getGroupName() + " Exists");
        }
        if (groups.isEmpty()) {
            group.setGroupId(1);
        } else {
            group.setGroupId(maxId + 1);
        }
        group.setProductIds(new ArrayList<Integer>());
        return this.groupRepository.insert(group);
    }

    /**
     * @see Functionality to get all Groups
     * @return List<Group>
     */
    @Override
    @Cacheable(value="groups", unless="#result==null")
    public List<Group> getAllGroups() {
        return this.groupRepository.findAll();
    }

    /**
     * @see Functionality to get all Group names
     * @return List<String>
     */
    @Override
    @Cacheable(value="group-names", unless="#result==null")
    public List<String> getAllGroupNames() {
        final Function<Group, String> nameFunction = (final Group group) -> group.getGroupName();
        final List<Group> groups = this.groupRepository.findAll();
        final List<String> groupNames = new ArrayList<>();
        groups.stream().filter(group -> groupNames.add(nameFunction.apply(group))).collect(Collectors.toList());
        return groupNames;
    }

    /**
     * @see Functionality to add products to group
     * @param groupName, productId
     * @return Boolean
     */
    @Override
    @Caching(put = {@CachePut(value = "add-product-to-group", key = "{#groupName, #productId}", unless="#result==null")})
    public Boolean addToProductList(final String groupName,
            final Integer productId) {
        final Group group = this.groupRepository.getGroupByName(groupName);
        if (group == null) {
            throw new GroupDoesntExist("Group with the name " + groupName + " doesnt exist");
        }
        final List<Integer> productList = group.getProductIds();
        if (group.getProductIds().contains(productId)) {
            return false;
        }
        productList.add(productId);
        group.setProductIds(productList);
        this.groupRepository.save(group);
        return true;
    }

    /**
     * @see Service to Delete a Group
     * @param groupName
     * @return
     */
    @Override
    public void deleteGroup(final String groupName) {
        this.groupRepository.delete(this.groupRepository.getGroupByName(groupName));
    }
}
