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

package com.publicis.sapient.group;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.publicis.sapient.group.exception.GroupDoesntExist;
import com.publicis.sapient.group.model.Group;
import com.publicis.sapient.group.resource.GroupResource;
import com.publicis.sapient.group.service.GroupServiceImpl;

/**
 * @author sidshriv3
 * @since March 2020
 * @see Tests for all functionalities in Product Service
 */

@SpringBootTest
class GroupJUnitApplicationTests {

    @InjectMocks
    GroupResource groupController;

    @Autowired
    GroupServiceImpl groupServiceImpl;

    @Test
    void insertGroupTest() {
        final int initVal = this.groupServiceImpl.getAllGroups().size();
        final Group group1 = new Group();
        group1.setGroupName("Whiskey");
        this.groupServiceImpl.insertGroup(group1);
        final int finalVal = this.groupServiceImpl.getAllGroups().size();
        assertEquals(initVal + 1, finalVal);
        this.groupServiceImpl.deleteGroup(group1.getGroupName());
    }

    @Test
    void deleteGroupTest() {
        final int initVal = this.groupServiceImpl.getAllGroups().size();
        final Group group1 = new Group();
        group1.setGroupName("Whiskey");
        this.groupServiceImpl.insertGroup(group1);
        this.groupServiceImpl.deleteGroup(group1.getGroupName());
        final int finalVal = this.groupServiceImpl.getAllGroups().size();
        assertEquals(initVal, finalVal);
    }

    @Test
    void addToProductList() {
        final Group group1 = new Group();
        group1.setGroupName("Whiskey");
        this.groupServiceImpl.insertGroup(group1);
        this.groupServiceImpl.addToProductList(group1.getGroupName(), 1);
        final List<Group> groups = this.groupServiceImpl.getAllGroups();
        final Group finalGroup = groups.stream().filter(g -> g.getGroupName().equals("Whiskey")).findAny().orElse(null);
        final int initVal = finalGroup.getProductIds().size();
        this.groupServiceImpl.addToProductList(group1.getGroupName(), 2);
        final List<Group> groups1 = this.groupServiceImpl.getAllGroups();
        final Group finalGroup1 = groups1.stream().filter(g -> g.getGroupName().equals("Whiskey")).findAny().orElse(null);
        final int finalVal = finalGroup1.getProductIds().size();
        assertEquals(initVal + 1, finalVal);
        this.groupServiceImpl.deleteGroup(group1.getGroupName());
    }

    @Test
    void addToProductList1() {
        String message = null;
        try {
            this.groupServiceImpl.addToProductList("Elec", 1);
        } catch (final GroupDoesntExist e) {
            message = e.getMessage();
        } finally {
            assertEquals(message, "Group with the name Elec doesnt exist");
        }
    }

}