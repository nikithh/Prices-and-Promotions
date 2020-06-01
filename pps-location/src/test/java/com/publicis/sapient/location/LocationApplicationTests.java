package com.publicis.sapient.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.publicis.sapient.location.model.Address;
import com.publicis.sapient.location.model.Cluster;
import com.publicis.sapient.location.model.Store;
import com.publicis.sapient.location.model.Zone;
import com.publicis.sapient.location.resource.LocationResource;
import com.publicis.sapient.location.service.LocationServiceImpl;

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
 *
 * @author sursuhas
 * @author gvsrini
 *
 */

@SpringBootTest
class LocationApplicationTests {

    @InjectMocks
    LocationResource locationController;

    @Mock
    LocationServiceImpl locationServiceImpl;

    @Test
    void getAllZonesTest() {
        final List<Zone> zones = new ArrayList<Zone>();
        final List<Store> s = new ArrayList<Store>();
        final List<Store> ss = new ArrayList<Store>();
        final List<Cluster> c = new ArrayList<Cluster>();
        final List<Cluster> cc = new ArrayList<Cluster>();

        final Address a1 = new Address("marathalli", "blr", 123212);
        final Address a2 = new Address("matikere", "blr", 560054);

        final Address a3 = new Address("BEL", "blr", 123213);
        final Address a4 = new Address("HSR", "blr", 560056);

        final Store s1 = new Store(111, "abc", a1);
        final Store s2 = new Store(222, "def", a2);
        s.add(s1);
        s.add(s2);

        final Store s3 = new Store(333, "ghi", a3);
        final Store s4 = new Store(444, "jkl", a4);
        ss.add(s3);
        ss.add(s4);

        final Cluster c1 = new Cluster(s, 11, "Karnataka", 5.6);
        final Cluster c2 = new Cluster(s, 22, "Andhra", 6.7);
        c.add(c1);
        c.add(c2);

        final Cluster c3 = new Cluster(ss, 33, "Kerala", 5.6);
        final Cluster c4 = new Cluster(ss, 44, "Chennai", 6.7);
        cc.add(c3);
        cc.add(c4);

        final Zone z1 = new Zone(c, 1, "Europe", 11.6);
        final Zone z2 = new Zone(cc, 2, "India", 15.6);

        zones.add(z1);
        zones.add(z2);
        when(this.locationServiceImpl.getAllZones()).thenReturn(zones);

        final List<Zone> zoneList = this.locationController.getAllZones();

        assertEquals(2, zoneList.size());
        verify(this.locationServiceImpl).getAllZones();
    }

    @Test
    void insertZoneTest() {

        final Zone zone = new Zone();
        zone.setZoneName("ind");
        zone.setLiquorPricePerUnit(12.1);

        when(this.locationServiceImpl.insertZone(zone)).thenReturn(zone);
        final Zone z = this.locationController.createZone(zone);
        verify(this.locationServiceImpl).insertZone(zone);
    }

    @Test
    void updateZoneTest() {

        final Zone zone = new Zone();
        zone.setZoneName("nikith");
        when(this.locationServiceImpl.updateZone(zone)).thenReturn(zone);

        final Zone ex = this.locationController.updateZone(zone);
        assertNotNull(ex);

        assertEquals("nikith", ex.getZoneName());

    }

    @Test
    void insertClusterTest() {
        final List<Store> store = new ArrayList<Store>();
        final List<Cluster> cluster = new ArrayList<Cluster>();

        final Address add = new Address();
        add.setStreetName("J.P Nagar");
        add.setCity("Bangalore");
        add.setPin(560078);

        final Store s1 = new Store(1, "Nike", add);
        final Store s2 = new Store(2, "Jones", add);
        store.add(s1);
        store.add(s2);

        final Cluster c1 = new Cluster(store, 11, "Karnataka", 5.6);
        final Cluster c2 = new Cluster(store, 22, "Andhra", 6.7);
        cluster.add(c1);
        cluster.add(c2);

        final Zone z1 = new Zone(cluster, 1, "Europe", 11.6);
        final Zone z2 = new Zone(cluster, 2, "Europe", 9.6);

        final String zoneName = "Europe";

        final Cluster c = new Cluster();
        c.setClusterId(33);
        c.setClusterName("Tamil Nadu");
        c.setTaxRate(7.54);
        c.setStore(store);

        when(this.locationServiceImpl.insertCluster(c1, zoneName)).thenReturn(z1);

        final Zone zo = this.locationController.insertCluster(c1, zoneName);

        assertNotNull(zo);
        assertEquals("Europe", zo.getZoneName());
        verify(this.locationServiceImpl).insertCluster(c1, zoneName);
    }

    @Test
    void getZoneNamesTest() {

        final List<Zone> zones = new ArrayList<Zone>();
        final List<Store> s = new ArrayList<Store>();
        final List<Store> ss = new ArrayList<Store>();
        final List<Cluster> c = new ArrayList<Cluster>();
        final List<Cluster> cc = new ArrayList<Cluster>();

        final Address a1 = new Address("marathalli", "blr", 123212);
        final Address a2 = new Address("matikere", "blr", 560054);

        final Address a3 = new Address("BEL", "blr", 123213);
        final Address a4 = new Address("HSR", "blr", 560056);

        final Store s1 = new Store(111, "abc", a1);
        final Store s2 = new Store(222, "def", a2);
        s.add(s1);
        s.add(s2);

        final Store s3 = new Store(333, "ghi", a3);
        final Store s4 = new Store(444, "jkl", a4);
        ss.add(s3);
        ss.add(s4);

        final Cluster c1 = new Cluster(s, 11, "Karnataka", 5.6);
        final Cluster c2 = new Cluster(s, 22, "Andhra", 6.7);
        c.add(c1);
        c.add(c2);

        final Cluster c3 = new Cluster(ss, 33, "Kerala", 5.6);
        final Cluster c4 = new Cluster(ss, 44, "Chennai", 6.7);
        cc.add(c3);
        cc.add(c4);

        final Zone z1 = new Zone(c, 1, "Europe", 11.6);
        final Zone z2 = new Zone(cc, 2, "India", 15.6);

        zones.add(z1);
        zones.add(z2);

        final List<String> listofZoneNames = new ArrayList<String>();

        for (final Zone zn : zones) {
            listofZoneNames.add(zn.getZoneName());

        }
        when(this.locationServiceImpl.getZoneNames()).thenReturn(listofZoneNames);

        // test
        final List<String> zoneList = this.locationController.getZoneNames();

        assertEquals(2, zoneList.size());
        verify(this.locationServiceImpl).getZoneNames();
    }

    @Test
    void insertStoreTest() {
        final List<Store> store = new ArrayList<Store>();
        final List<Cluster> cluster = new ArrayList<Cluster>();

        final Address add = new Address();
        add.setStreetName("Jayanagar");
        add.setCity("Bangalore");
        add.setPin(560066);

        final Store s1 = new Store(1, "Levis", add);
        final Store s2 = new Store(2, "Fossil", add);
        store.add(s1);
        store.add(s2);

        final Cluster c1 = new Cluster(store, 11, "Karnataka", 5.6);
        final Cluster c2 = new Cluster(store, 22, "Andhra", 6.7);
        cluster.add(c1);
        cluster.add(c2);

        final Zone z1 = new Zone(cluster, 1, "Europe", 11.6);
        final Zone z2 = new Zone(cluster, 2, "Europe", 9.6);

        final String zoneName = "Europe";
        final String clusterName = "Karnataka";

        final Store s = new Store();
        s.setStoreId(3);
        s.setStoreName("Casio");
        s.setAddress(add);

        when(this.locationServiceImpl.insertStore(s, zoneName, clusterName)).thenReturn(z1);

        final Zone zo = this.locationController.insertStore(s, zoneName, clusterName);

        assertNotNull(zo);
        assertEquals("Europe", zo.getZoneName());
        verify(this.locationServiceImpl).insertStore(s, zoneName, clusterName);
    }

    @Test
    void getZoneByNameTest() {
        final List<Cluster> cluster = new ArrayList<Cluster>();
        final Zone z1 = new Zone(cluster, 1, "Brazil", 9.6);

        final String zoneName = "Brazil";

        when(this.locationServiceImpl.getZoneByName(zoneName)).thenReturn(z1);

        final Zone zo = this.locationController.getZoneByName(zoneName);

        assertNotNull(zo);
        assertEquals("Brazil", zo.getZoneName());
        verify(this.locationServiceImpl).getZoneByName(zoneName);
    }

    @Test
    void getZoneListTest() {
        final List<Zone> zones = new ArrayList<Zone>();
        final List<Store> s = new ArrayList<Store>();
        final List<Store> ss = new ArrayList<Store>();
        final List<Cluster> c = new ArrayList<Cluster>();
        final List<Cluster> cc = new ArrayList<Cluster>();

        final Address a1 = new Address("marathalli", "blr", 123212);
        final Address a2 = new Address("matikere", "blr", 560054);

        final Address a3 = new Address("BEL", "blr", 123213);
        final Address a4 = new Address("HSR", "blr", 560056);

        final Store s1 = new Store(111, "abc", a1);
        final Store s2 = new Store(222, "def", a2);
        s.add(s1);
        s.add(s2);

        final Store s3 = new Store(333, "ghi", a3);
        final Store s4 = new Store(444, "jkl", a4);
        ss.add(s3);
        ss.add(s4);

        final Cluster c1 = new Cluster(s, 11, "Karnataka", 5.6);
        final Cluster c2 = new Cluster(s, 22, "Andhra", 6.7);
        c.add(c1);
        c.add(c2);

        final Cluster c3 = new Cluster(ss, 33, "Kerala", 5.6);
        final Cluster c4 = new Cluster(ss, 44, "Chennai", 6.7);
        cc.add(c3);
        cc.add(c4);

        final Zone z1 = new Zone(c, 1, "Europe", 11.6);
        final Zone z2 = new Zone(cc, 2, "India", 15.6);

        zones.add(z1);
        zones.add(z2);

        final Map<Object, Object> zoneList = new HashMap<>();

        for (final Zone zone : zones) {
            zoneList.put(zone.getZoneName(), zone.getCluster().size());
        }

        when(this.locationServiceImpl.getZoneList()).thenReturn(zoneList);
        final Map<Object, Object> zList = this.locationController.getZoneList();
        assertEquals(2, zList.size());
        verify(this.locationServiceImpl).getZoneList();
    }

    @Test

    void findClusterNamesByZoneNameTest() {
        final List<Cluster> clus = new ArrayList<Cluster>();
        clus.add(new Cluster(null, 0, "bangalore", 0));
        clus.add(new Cluster(null, 0, "mysore", 0));
        clus.add(new Cluster(null, 0, "dharwad", 0));
        clus.add(new Cluster(null, 0, "hubli", 0));
        clus.add(new Cluster(null, 0, "belgaum", 0));
        final List<String> clusterNames = new ArrayList<String>();
        clusterNames.add("bangalore");
        clusterNames.add("mysore");
        clusterNames.add("dharwad");
        clusterNames.add("hubli");
        clusterNames.add("belgaum");
        final Zone z = new Zone(clus, 0, "karnataka", 0);
        when(this.locationServiceImpl.findClusterNamesByZoneName("karnataka")).thenReturn(clusterNames);
        final List<String> zstring = this.locationController.getClusterNamesByZoneName("karnataka");
        verify(this.locationServiceImpl).findClusterNamesByZoneName("karnataka");

    }

    @Test
    void getClusterList() {

        final List<Zone> zones = new ArrayList<Zone>();
        final List<Store> s = new ArrayList<Store>();
        final List<Store> ss = new ArrayList<Store>();
        final List<Cluster> c = new ArrayList<Cluster>();
        final List<Cluster> cc = new ArrayList<Cluster>();

        final Address a1 = new Address("marathalli", "blr", 123212);
        final Address a2 = new Address("matikere", "blr", 560054);

        final Address a3 = new Address("BEL", "blr", 123213);
        final Address a4 = new Address("HSR", "blr", 560056);

        final Store s1 = new Store(111, "abc", a1);
        final Store s2 = new Store(222, "def", a2);
        s.add(s1);
        s.add(s2);

        final Store s3 = new Store(333, "ghi", a3);
        final Store s4 = new Store(444, "jkl", a4);
        ss.add(s3);
        ss.add(s4);

        final Cluster c1 = new Cluster(s, 11, "Karnataka", 5.6);
        final Cluster c2 = new Cluster(s, 22, "Andhra", 6.7);
        c.add(c1);
        c.add(c2);

        final Cluster c3 = new Cluster(ss, 33, "Kerala", 5.6);
        final Cluster c4 = new Cluster(ss, 44, "Chennai", 6.7);
        cc.add(c3);
        cc.add(c4);

        final Zone z1 = new Zone(c, 1, "Europe", 11.6);
        final Zone z2 = new Zone(cc, 2, "India", 15.6);

        zones.add(z1);
        zones.add(z2);

        final Map<Object, Object> clusterList = new HashMap<>();

        for (final Zone zon : zones) {
            final List<Cluster> clusters = zon.getCluster();
            for (final Cluster cluster : clusters) {
                clusterList.put(cluster.getClusterName(), cluster.getStore().size());
            }
        }
        when(this.locationServiceImpl.getClusterList()).thenReturn(clusterList);

        // test
        final Map<Object, Object> clustList = this.locationController.getClusterList();

        assertEquals(4, clustList.size());
        verify(this.locationServiceImpl).getClusterList();
    }

    @Test
    void deleteZoneTest() {
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
        doNothing().when(this.locationServiceImpl).deleteZone(zone);
        when(this.locationServiceImpl.getZoneByName("Karnataka")).thenReturn(zone);
        this.locationController.deleteZone("Karnataka");
        verify(this.locationServiceImpl).deleteZone(zone);
    }

}
