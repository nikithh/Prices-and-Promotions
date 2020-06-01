package com.publicis.sapient.location.model;

import java.util.List;
import javax.validation.constraints.Min;
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
 * @see Bean for Cluster
 */

public class Cluster {

    List<Store> store;
    private int clusterId;
    @NotBlank
    @Size(min = 6)
    private String clusterName;
    @NotNull
    @Min(value = 1)
    private Double taxRate;

    public List<Store> getStore() {
        return this.store;
    }

    public void setStore(final List<Store> store) {
        this.store = store;
    }

    public int getClusterId() {
        return this.clusterId;
    }

    public void setClusterId(final int clusterId) {
        this.clusterId = clusterId;
    }

    public String getClusterName() {
        return this.clusterName;
    }

    public void setClusterName(final String clusterName) {
        this.clusterName = clusterName;
    }

    public double getTaxRate() {
        return this.taxRate;
    }

    public void setTaxRate(final double taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public String toString() {
        return "Cluster [store=" + this.store + ", clusterId=" + this.clusterId + ", clusterName=" + this.clusterName
                + ", taxRate=" + this.taxRate + "]";
    }

    public Cluster(final List<Store> store,
            final int clusterId,
            final String clusterName,
            final double taxRate) {
        super();
        this.store = store;
        this.clusterId = clusterId;
        this.clusterName = clusterName;
        this.taxRate = taxRate;
    }

    public Cluster() {
    }

}
