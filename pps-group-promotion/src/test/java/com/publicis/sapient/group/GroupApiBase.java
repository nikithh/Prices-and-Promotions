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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import com.publicis.sapient.group.model.Group;
import com.publicis.sapient.group.repository.GroupRepository;
import com.publicis.sapient.group.resource.GroupResource;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

/**
 * @author sanumash
 * @since March 2020
 * @see Tests for contract testing
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class GroupApiBase {

    @Autowired
    GroupResource groupController;

    @MockBean
    private GroupRepository groupRepository;

    @Before
    public void setup() {
        final Group group1 = new Group(1, "Flash Sale", Arrays.asList(1, 2, 3, 4));
        final Group group2 = new Group(2, "Festival Sale", Arrays.asList(5, 6, 7, 8));
        final Group group3 = new Group(3, "Sale", Arrays.asList(1, 2, 3, 4));
        final Group updatedGroup2 = new Group(2, "Festival Sale", Arrays.asList(5, 6, 7, 8, 13));
        final List<Group> groups = Arrays.asList(group1, group2);
        when(this.groupRepository.findAll()).thenReturn(groups);
        when(this.groupRepository.insert(any(Group.class))).thenReturn(group3);
        when(this.groupRepository.getGroupByName(any(String.class))).thenReturn(group2);
        when(this.groupRepository.save(any(Group.class))).thenReturn(updatedGroup2);
        final StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(this.groupController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }

}
