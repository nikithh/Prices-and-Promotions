package com.publicis.sapient.location.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

/**
 * @author sursuhas
 * @since March 2020
 * @see Bean for Store
 */

public class Store {

    private int storeId;
    @NotBlank
    @Size(min = 6)
    private String storeName;
    @NotNull
    @Valid
    Address address;

    public int getStoreId() {
        return this.storeId;
    }

    public void setStoreId(final int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public void setStoreName(final String storeName) {
        this.storeName = storeName;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public Store() {
    }

    @Override
    public String toString() {
        return "Store [storeId=" + this.storeId + ", storeName=" + this.storeName + ", address=" + this.address + "]";
    }

    public Store(final int storeId,
            final String storeName,
            final Address address) {
        super();
        this.storeId = storeId;
        this.storeName = storeName;
        this.address = address;
    }

}
