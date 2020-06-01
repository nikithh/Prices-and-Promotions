package com.publicis.sapient.location.model;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;

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
 * @see Bean for Zone
 */

public class Zone {

    @Id
    private int zoneId;
    @NotNull(message = "zone name cant be null")
    @Size(min = 6)
    private String zoneName;

    @NotNull(message = "lppu cant be null")
    @Min(value = 0)
    private Double liquorPricePerUnit;

    List<Cluster> cluster;

    public List<Cluster> getCluster() {
        return this.cluster;
    }

    public void setCluster(final List<Cluster> cluster) {
        this.cluster = cluster;
    }

    public int getZoneId() {
        return this.zoneId;
    }

    public void setZoneId(final int zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return this.zoneName;
    }

    public void setZoneName(final String zoneName) {
        this.zoneName = zoneName;
    }

    public double getLiquorPricePerUnit() {
        return this.liquorPricePerUnit;
    }

    public void setLiquorPricePerUnit(final double liquorPricePerUnit) {
        this.liquorPricePerUnit = liquorPricePerUnit;
    }

    @Override
    public String toString() {
        return "Zone [cluster=" + this.cluster + ", zoneId=" + this.zoneId + ", zoneName=" + this.zoneName
                + ", liquorPricePerUnit=" + this.liquorPricePerUnit + "]";
    }

    public Zone(final List<Cluster> cluster,
            final int zoneId,
            final String zoneName,
            final double liquorPricePerUnit) {
        super();
        this.cluster = cluster;
        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.liquorPricePerUnit = liquorPricePerUnit;
    }

    public Zone() {
    }

}