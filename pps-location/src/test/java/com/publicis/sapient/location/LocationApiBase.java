package com.publicis.sapient.location;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import com.publicis.sapient.location.model.Address;
import com.publicis.sapient.location.model.Cluster;
import com.publicis.sapient.location.model.Store;
import com.publicis.sapient.location.model.Zone;
import com.publicis.sapient.location.repository.ZoneRepository;
import com.publicis.sapient.location.resource.LocationResource;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
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
 * @author prejayak
 * @author sanumash
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class LocationApiBase {

    @Autowired
    LocationResource locationController;

    @MockBean
    private ZoneRepository zoneRepository;

    @Before
    public void setup() {
        final Zone zone = new Zone(null, 4, "England", 15.6);

        final List<Zone> zones = new ArrayList<Zone>();
        final List<Store> s = new ArrayList<Store>();
        final List<Store> ss = new ArrayList<Store>();
        final List<Store> sss = new ArrayList<Store>();

        final List<Cluster> c = new ArrayList<Cluster>();
        final List<Cluster> cc = new ArrayList<Cluster>();
        final List<Cluster> ccc = new ArrayList<Cluster>();

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
        final Store s5 = new Store(12, "Midely", new Address("Randy", "Taiyuan", 680294));

        ss.add(s3);
        ss.add(s4);

        sss.add(s1);
        sss.add(s2);
        sss.add(s5);

        final Cluster c1 = new Cluster(s, 11, "Karnataka", 5.6);
        final Cluster c2 = new Cluster(s, 22, "Andhra", 6.7);
        final Cluster c5 = new Cluster(s, 17, "Gujarat", 5.6);

        c.add(c1);
        c.add(c2);

        final Cluster c3 = new Cluster(ss, 33, "Kerala", 5.6);
        final Cluster c4 = new Cluster(ss, 44, "Chennai", 6.7);
        final Cluster c6 = new Cluster(sss, 44, "Karnataka", 5.6);

        cc.add(c3);
        cc.add(c4);

        final Zone z1 = new Zone(c, 1, "Europe", 11.6);
        final Zone z2 = new Zone(cc, 2, "India", 15.6);
        final Zone z3 = new Zone(cc, 2, "India", 20.6);

        zones.add(z1);
        zones.add(z2);

        when(this.zoneRepository.findById(any(Integer.class))).thenReturn(Optional.of(z2));
        when(this.zoneRepository.insert(any(Zone.class))).thenReturn(zone);
        when(this.zoneRepository.save(any(Zone.class))).thenReturn(z3);
        when(this.zoneRepository.getZoneByName("India")).thenReturn(z2);
        when(this.zoneRepository.findAll()).thenReturn(zones);
        when(this.zoneRepository.getZoneByName("Europe")).thenReturn(z1);
        when(this.zoneRepository.getClusterNamesRegex(any(String.class))).thenReturn(zones);
        when(this.zoneRepository.getZoneNamesRegex(any(String.class))).thenReturn(zones);
        final StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(this.locationController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }

}
