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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import com.publicis.sapient.group.model.Group;
import com.publicis.sapient.group.resource.GroupResource;
import com.publicis.sapient.group.service.GroupServiceImpl;

/**
 *
 * @author mnanjan
 *
 */

@SpringBootTest
class GroupApplicationTests {

    @InjectMocks
    GroupResource groupController;

    @Mock
    GroupServiceImpl groupServiceImpl;

    @Test
    void getAllGroupsTest() {
        final List<Group> groups = new ArrayList<Group>();
        final List<Integer> productList1 = new ArrayList<Integer>();
        productList1.add(11);
        productList1.add(12);
        final Group group1 = new Group(1, "Group1", productList1);

        final List<Integer> productList2 = new ArrayList<Integer>();
        productList2.add(11);
        productList2.add(12);
        final Group group2 = new Group(1, "Group2", productList2);

        final List<Integer> productList3 = new ArrayList<Integer>();
        productList3.add(11);
        productList3.add(12);
        final Group group3 = new Group(1, "Group3", productList3);

        groups.add(group1);
        groups.add(group2);
        groups.add(group3);

        when(this.groupServiceImpl.getAllGroups()).thenReturn(groups);
        assertEquals(3, groups.size());

        final ResponseEntity response = this.groupController.getAllGroups();
        verify(this.groupServiceImpl).getAllGroups();
    }

    @Test
    void insertGroupTest() {

        final Group group = new Group();
        group.setGroupName("Group1");

        final List<Integer> products = new ArrayList();
        products.add(1);
        products.add(2);
        products.add(3);
        group.setProductIds(products);

        when(this.groupServiceImpl.insertGroup(group)).thenReturn(group);
        final ResponseEntity<Group> r = this.groupController.createGroup(group);
        verify(this.groupServiceImpl).insertGroup(group);

    }

    @Test
    void addProductsToGroupTest() {

        final Group group = new Group();
        group.setGroupId(4);
        group.setGroupName("Group4");
        final List<Integer> productList = new ArrayList<Integer>();
        productList.add(11);
        productList.add(12);
        when(this.groupServiceImpl.addToProductList("Group4", 13)).thenReturn(true);

        final ResponseEntity<Boolean> ex = this.groupController.addToProductList("Group4", 13);
        assertNotNull(ex);
        verify(this.groupServiceImpl).addToProductList("Group4", 13);

    }

    @Test
    void getAllGroupNamesTest() {
        final List<String> groupNames = new ArrayList<String>();
        groupNames.add("Group1");
        groupNames.add("Group2");
        groupNames.add("Group3");
        when(this.groupServiceImpl.getAllGroupNames()).thenReturn(groupNames);
        assertEquals(3, groupNames.size());
        final ResponseEntity response = this.groupController.getAllGroupNames();
        verify(this.groupServiceImpl).getAllGroupNames();
    }

}
