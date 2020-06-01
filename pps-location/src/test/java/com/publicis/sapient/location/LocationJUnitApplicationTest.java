package com.publicis.sapient.location;

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

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.publicis.sapient.location.model.Address;
import com.publicis.sapient.location.model.Cluster;
import com.publicis.sapient.location.model.Store;
import com.publicis.sapient.location.model.Zone;
import com.publicis.sapient.location.resource.LocationResource;
import com.publicis.sapient.location.service.LocationServiceImpl;

/**
 * @author sidshriv3
 * @author sivrende
 * @since April 2020
 * @see Tests for all functionalities in Location Service
 */

@SpringBootTest
class LocationJunitApplicationTests {

    @InjectMocks
    LocationResource locationResource;

    @Autowired
    LocationServiceImpl locationServiceImpl;

    @Test
    void deleteZoneTest() {
        final int initialValue = this.locationServiceImpl.getAllZones().size();
        final Zone zone = new Zone();
        final List<Cluster> c = new ArrayList<Cluster>();
        final List<Store> s = new ArrayList<Store>();
        final Address a1 = new Address("marathalli", "blr", 123212);
        final Address a2 = new Address("matikere", "blr", 560054);
        final Store s1 = new Store(111, "abc", a1);
        final Store s2 = new Store(222, "def", a2);
        s.add(s1);
        s.add(s2);
        final Cluster c1 = new Cluster(s, 11, "Karnataka", 5.6);
        final Cluster c2 = new Cluster(s, 22, "Andhra", 6.7);
        c.add(c1);
        c.add(c2);
        zone.setCluster(c);
        zone.setZoneId(0);
        zone.setZoneName("Karnataka");
        zone.setLiquorPricePerUnit(100.0);
        this.locationServiceImpl.insertZone(zone);
        this.locationServiceImpl.deleteZone(zone);
        final int finalValue = this.locationServiceImpl.getAllZones().size();
        assertEquals(initialValue, finalValue);
    }

    @Test
    void updateZone() {

        final List<Cluster> clusters = new ArrayList<Cluster>();

        final Zone zone1 = new Zone();
        zone1.setZoneName("Animals");
        zone1.setCluster(clusters);

        final Zone zone2 = new Zone();
        zone2.setCluster(clusters);
        zone2.setZoneName("Animals");

        this.locationServiceImpl.insertZone(zone1);
        this.locationServiceImpl.updateZone(zone2);
        assertEquals(zone2.getZoneId(), zone1.getZoneId());
        this.locationServiceImpl.deleteZone(zone2);
    }

    @Test
    void findClusterByZoneName() {

        final Cluster cluster = new Cluster();
        cluster.setClusterName("Giraffe");
        cluster.setTaxRate(10.0);

        final Zone zone1 = new Zone();
        zone1.setZoneName("Animals");

        this.locationServiceImpl.insertZone(zone1);
        this.locationServiceImpl.insertCluster(cluster, zone1.getZoneName());

        final List<Cluster> clusters = this.locationServiceImpl.findClusterByZoneName(zone1.getZoneName());
        System.out.println(clusters);
        assertEquals(clusters.get(0).getClusterName(), "Giraffe");
        this.locationServiceImpl.deleteZone(zone1);

    }
}
