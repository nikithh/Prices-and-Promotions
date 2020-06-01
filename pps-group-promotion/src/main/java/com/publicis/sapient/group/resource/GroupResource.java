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

package com.publicis.sapient.group.resource;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.publicis.sapient.group.model.Group;
import com.publicis.sapient.group.service.GroupServiceImpl;

import brave.sampler.Sampler;

/**
 *
 * @author mnanjan
 * @since March 2020
 * @see Controller for group service
 */

@RestController
public class GroupResource {
	
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}

    @Autowired
    public GroupServiceImpl groupServiceImpl;

    /**
     * @see Controller to list all Groups
     * @return ResponseEntity<List<Group>>
     *
     */
    @GetMapping("/groups")
    public ResponseEntity<List<Group>> getAllGroups() {
        return new ResponseEntity<>(this.groupServiceImpl.getAllGroups(), HttpStatus.OK);
    }

    /**
     * @see Controller to list all Groups names
     * @return ResponseEntity<List<Group>>
     *
     */
    @GetMapping("/groupNames")
    public ResponseEntity<List<String>> getAllGroupNames() {
        return new ResponseEntity<>(this.groupServiceImpl.getAllGroupNames(), HttpStatus.OK);
    }

    /**
     * @see Controller to insert a group
     * @param group
     * @return ResponseEntity<Group>
     */
    @PostMapping("/group")
    public ResponseEntity<Group> createGroup(@RequestBody @Valid final Group group) {
        return new ResponseEntity<>(this.groupServiceImpl.insertGroup(group), HttpStatus.CREATED);
    }

    /**
     * @see Controller to add product to a group
     * @param groupName, productId
     * @return ResponseEntity<Boolean>
     */
    @PutMapping("/groups/{groupName}/{productId}")
    public ResponseEntity<Boolean> addToProductList(@PathVariable final String groupName,
            @PathVariable final Integer productId) {
        return new ResponseEntity<>(this.groupServiceImpl.addToProductList(groupName, productId), HttpStatus.OK);

    }

}
