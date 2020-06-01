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

package com.publicis.sapient.vendorretailer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import com.publicis.sapient.vendorretailer.beans.Retailer;
import com.publicis.sapient.vendorretailer.beans.Vendor;
import com.publicis.sapient.vendorretailer.controller.VendorRetailerController;
import com.publicis.sapient.vendorretailer.repository.VendorRepository;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

/**
 *
 * @author prejayak
 * @since March 2020
 * @see Tests for contract testing
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class VendorApiBase {

    @Autowired
    VendorRetailerController vendorController;

    @MockBean
    private VendorRepository vendorRepository;

    @Before
    public void setup() {
        System.out.println("in Contract Testing");
        final Vendor vendor1 = new Vendor(1, "1@ps.com", "1", "baby products", "abc", Arrays.asList("1", "2"), false);
        final Vendor vendor2 = new Vendor(2, "2@ps.com", "2", "liquor products", "abcd", Arrays.asList("1"), false);
        final List<Vendor> vendors = Arrays.asList(vendor1);

        when(this.vendorRepository.findAll()).thenReturn(vendors);
        when(this.vendorRepository.insert(any(Vendor.class))).thenReturn(vendor2);
        when(this.vendorRepository.getVendorByName("1")).thenReturn(vendor1);
        when(this.vendorRepository.getVendorByName("2")).thenReturn(vendor2);

        final Retailer retailer = new Retailer("admin", "admin");

        final StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(this.vendorController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);

    }

}
