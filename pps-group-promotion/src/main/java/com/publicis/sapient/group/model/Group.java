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

package com.publicis.sapient.group.model;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;

/**
 *
 * @author mnanjan
 * @since March 2020
 * @see Bean for Group
 */

public class Group implements Serializable{

    @Id
    private Integer groupId;
    @NotNull
    @Size(min = 4, message = "groupName's length should be greater than 3")
    private String groupName;
    private List<Integer> productIds;

    public Integer getGroupId() {
        return this.groupId;
    }

    public void setGroupId(final Integer groupId) {
        this.groupId = groupId;
    }

    public List<Integer> getProductIds() {
        return this.productIds;
    }

    public void setProductIds(final List<Integer> productIds) {
        this.productIds = productIds;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    public Group(final Integer groupId,
            final String groupName,
            final List<Integer> productIds) {
        super();
        this.groupId = groupId;
        this.groupName = groupName;
        this.productIds = productIds;
    }

    @Override
    public String toString() {
        return "group [groupId=" + this.groupId + ", groupName=" + this.groupName + ", productIds=" + this.productIds + "]";
    }

    public Group() {

    }

}
