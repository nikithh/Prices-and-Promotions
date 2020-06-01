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

package com.publicis.sapient.product.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author sursuhas
 * @since March 2020
 * @see Bean for Store
 */

public class Store {

    @NotNull
    @Size(min = 6)
    private String storeName;
    private Integer quantityAssigned;
    private Double price;

    public Store() {
        // default contructor
    }

    public Store(final String storeName,
            final Integer quantityAssigned,
            final Double price) {
        super();
        this.storeName = storeName;
        this.quantityAssigned = quantityAssigned;
        this.price = price;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public void setStoreName(final String storeName) {
        this.storeName = storeName;
    }

    public Integer getQuantityAssigned() {
        return this.quantityAssigned;
    }

    public void setQuantityAssigned(final Integer quantityAssigned) {
        this.quantityAssigned = quantityAssigned;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Store [storeName=" + this.storeName + ", quantityAssigned=" + this.quantityAssigned + ", price=" + this.price
                + "]";
    }

}
