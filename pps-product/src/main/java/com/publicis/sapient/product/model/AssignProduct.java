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
 * @author prejayak
 * @since March 2020
 * @see Bean for Assign Product
 */

public class AssignProduct implements Serializable {

    @NotNull
    @Size(min = 6)
    private String zoneName;
    @NotNull
    @Min(1)
    private Integer quantityAssigned;
    private Double price;
    @NotNull
    @Min(0)
    private Double profitPercentage;
    private Double minimumSP;
    private List<Promotion> promotions;
    private List<Cluster> cluster;

    public AssignProduct() {
        // default contructor
    }

    public AssignProduct(@NotNull @Size(min = 6) final String zoneName,
            @NotNull @Min(1) final Integer quantityAssigned,
            final Double price,
            @NotNull @Min(0) final Double profitPercentage,
            final Double minimumSP,
            final List<Promotion> promotions,
            final List<Cluster> cluster) {
        super();
        this.zoneName = zoneName;
        this.quantityAssigned = quantityAssigned;
        this.price = price;
        this.profitPercentage = profitPercentage;
        this.minimumSP = minimumSP;
        this.promotions = promotions;
        this.cluster = cluster;
    }

    public String getZoneName() {
        return this.zoneName;
    }

    public void setZoneName(final String zoneName) {
        this.zoneName = zoneName;
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

    public Double getMinimumSP() {
        return this.minimumSP;
    }

    public void setMinimumSP(final Double minimumSP) {
        this.minimumSP = minimumSP;
    }

    public List<Promotion> getPromotions() {
        return this.promotions;
    }

    public void setPromotions(final List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Cluster> getCluster() {
        return this.cluster;
    }

    public void setCluster(final List<Cluster> cluster) {
        this.cluster = cluster;
    }

    @Override
    public String toString() {
        return "AssignProduct [zoneName=" + this.zoneName + ", quantityAssigned=" + this.quantityAssigned + ", price="
                + this.price + ", profitPercentage=" + this.profitPercentage + ", minimumSP=" + this.minimumSP + ", promotions="
                + this.promotions + ", cluster=" + this.cluster + "]";
    }

}
