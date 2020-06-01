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

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author sursuhas
 * @since March 2020
 * @see Bean for Cluster
 */

public class Cluster implements Serializable {

    @NotNull
    @Size(min = 6)
    private String clusterName;
    @NotNull
    @Min(value = 1)
    private Integer quantityAssigned;
    private Double price;
    @NotNull
    @Min(value = 0, message = "profit should be positive")
    private Double profitPercentage;
    private List<Promotion> promotions;
    private List<Store> store;

    public Cluster() {
        // default contructor
    }

    public Cluster(@NotNull @Size(min = 6) final String clusterName,
            @NotNull @Min(1) final Integer quantityAssigned,
            final Double price,
            @NotNull @Min(value = 0, message = "profit should be positive") final Double profitPercentage,
            final List<Promotion> promotions,
            final List<Store> store) {
        super();
        this.clusterName = clusterName;
        this.quantityAssigned = quantityAssigned;
        this.price = price;
        this.profitPercentage = profitPercentage;
        this.promotions = promotions;
        this.store = store;
    }

    public String getClusterName() {
        return this.clusterName;
    }

    public void setClusterName(final String clusterName) {
        this.clusterName = clusterName;
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

    public Double getProfitPercentage() {
        return this.profitPercentage;
    }

    public void setProfitPercentage(final Double profitPercentage) {
        this.profitPercentage = profitPercentage;
    }

    public List<Promotion> getPromotions() {
        return this.promotions;
    }

    public void setPromotions(final List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Store> getStore() {
        return this.store;
    }

    public void setStore(final List<Store> store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "Cluster [clusterName=" + this.clusterName + ", quantityAssigned=" + this.quantityAssigned + ", price="
                + this.price + ", profitPercentage=" + this.profitPercentage + ", promotions=" + this.promotions + ", store="
                + this.store + "]";
    }

}
