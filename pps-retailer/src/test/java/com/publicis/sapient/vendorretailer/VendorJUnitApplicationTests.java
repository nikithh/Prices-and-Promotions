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

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.publicis.sapient.vendorretailer.beans.Admin;
import com.publicis.sapient.vendorretailer.beans.Vendor;
import com.publicis.sapient.vendorretailer.controller.VendorRetailerController;
import com.publicis.sapient.vendorretailer.exception.VendorAlreadyExists;
import com.publicis.sapient.vendorretailer.service.VendorRetailerServiceImpl;

/**
 * @author sidshriv3
 * @since April 2020
 * @see Tests for all functionalities in Product Service
 */

@SpringBootTest
class VendorJUnitApplicationTests {

    @InjectMocks
    VendorRetailerController controller;

    @Autowired
    VendorRetailerServiceImpl serviceImpl;

    @Test
    void createVendorTest2() {
        final String message = null;
        final Vendor vendor1 = new Vendor();
        vendor1.setEmail("xyz@abc.com");
        vendor1.setCompanyName("BigCompany");
        vendor1.setCompanyType("AwesomeType");
        vendor1.setPassword("2343dsf3czfasd2156afas23ccas");
        vendor1.setProductSold(null);
        vendor1.setEnabled(true);
        final Vendor vendor2 = this.serviceImpl.createVendor(vendor1);
        final Vendor vendor3 = new Vendor();
        vendor3.setEmail("xyz2@abc.com");
        vendor3.setCompanyName("BigCompany2");
        vendor3.setCompanyType("AwesomeType");
        vendor3.setPassword("2343dsf3czfasd2156afas23ccas");
        vendor3.setProductSold(null);
        vendor3.setEnabled(true);
        final Vendor vendor4 = this.serviceImpl.createVendor(vendor3);
        assertEquals(vendor4, vendor3);
        this.serviceImpl.deleteVendor(vendor1.getCompanyName());
        this.serviceImpl.deleteVendor(vendor3.getCompanyName());
    }

    @Test
    void createVendorTest() {
        String message = null;
        final Vendor vendor1 = new Vendor();
        vendor1.setEmail("xyz@abc.com");
        vendor1.setCompanyName("BigCompany");
        vendor1.setCompanyType("AwesomeType");
        vendor1.setPassword("2343dsf3czfasd2156afas23ccas");
        this.serviceImpl.createVendor(vendor1);
        final Vendor vendor2 = new Vendor();
        vendor2.setEmail("qwerty@abc.com");
        vendor2.setCompanyName("BigCompany");
        vendor2.setCompanyType("LessAwesomeType");
        vendor2.setPassword("yef3dsf3czfasdwerva4as23ccas");
        try {
            this.serviceImpl.createVendor(vendor2);
        } catch (final VendorAlreadyExists e) {
            message = e.getMessage();
        } finally {
            assertEquals(message, "Vendor with the name BigCompany already exists");
            this.serviceImpl.deleteVendor(vendor1.getCompanyName());
        }
    }

    @Test
    void deleteVendorTest() {
        final int initVal = this.serviceImpl.getAllVendors().size();
        final Vendor vendor1 = new Vendor();
        vendor1.setEmail("xyz@abc.com");
        vendor1.setCompanyName("BigCompany");
        vendor1.setCompanyType("AwesomeType");
        vendor1.setPassword("2343dsf3czfasd2156afas23ccas");
        this.serviceImpl.createVendor(vendor1);
        this.serviceImpl.deleteVendor(vendor1.getCompanyName());
        final int finalVal = this.serviceImpl.getAllVendors().size();
        assertEquals(initVal, finalVal);
    }

    @Test
    void getVendorTest2() {
        final Vendor vendor1 = new Vendor();
        vendor1.setEmail("xyz@abc.com");
        vendor1.setCompanyName("BigCompany");
        vendor1.setCompanyType("AwesomeType");
        vendor1.setPassword("2343dsf3czfasd2156afas23ccas");
        vendor1.setProductSold(null);
        vendor1.setEnabled(false);
        this.serviceImpl.createVendor(vendor1);
        final String password = this.serviceImpl.getVendor("BigCompany");
        assertEquals("NA", password);
        this.serviceImpl.deleteVendor(vendor1.getCompanyName());
    }

    @Test
    void getVendorTest() {
        final Vendor vendor1 = new Vendor();
        vendor1.setEmail("xyz@abc.com");
        vendor1.setCompanyName("BigCompany");
        vendor1.setCompanyType("AwesomeType");
        vendor1.setPassword("2343dsf3czfasd2156afas23ccas");
        vendor1.setProductSold(null);
        vendor1.setEnabled(true);
        this.serviceImpl.createVendor(vendor1);
        final String password = this.serviceImpl.getVendor("BigCompany");
        assertEquals("2343dsf3czfasd2156afas23ccas", password);
        this.serviceImpl.deleteVendor(vendor1.getCompanyName());
    }

    @Test
    void createAdminTest() {
        final Admin admin = new Admin();
        admin.setUserName("admin1234");
        admin.setPassword("admin1234");
        final Admin admin2 = this.serviceImpl.createAdmin(admin);
        assertEquals(admin2, admin);
        this.serviceImpl.deleteAdmin("admin1234");
    }

    @Test
    void createAdminTest2() {
        final Admin admin = new Admin();
        final Admin admin2 = new Admin();
        admin.setUserName("admin1234");
        admin.setPassword("admin1234");
        admin2.setUserName("admin12345");
        admin2.setPassword("admin12345");
        final Admin admin3 = this.serviceImpl.createAdmin(admin);
        final Admin admin4 = this.serviceImpl.createAdmin(admin2);
        assertEquals(admin3, admin);
        this.serviceImpl.deleteAdmin("admin1234");
        this.serviceImpl.deleteAdmin("admin12345");
    }

    @Test
    void createAdminTest3() {
        final Admin admin = new Admin();
        final Admin admin2 = new Admin();
        admin.setUserName("admin1234");
        admin.setPassword("admin1234");
        admin2.setUserName("admin1234");
        admin2.setPassword("admin1234");
        final Admin admin3 = this.serviceImpl.createAdmin(admin);
        String message = null;
        try {
            this.serviceImpl.createAdmin(admin2);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            message = e.getMessage();
        } finally {
            assertEquals(message, "Admin with the username " + admin.getUserName() + " already exists");
            this.serviceImpl.deleteAdmin("admin1234");
        }

    }

    @Test
    void getAdminTest() {
        final Admin admin = new Admin();
        admin.setUserName("admin123456");
        admin.setPassword("admin123456");
        this.serviceImpl.createAdmin(admin);
        final String adminPassword = this.serviceImpl.getAdmin("admin123456");
        assertEquals(adminPassword, admin.getPassword());
        this.serviceImpl.deleteAdmin("admin123456");
    }

    @Test
    void getAdminTest2() {
        final Admin admin = new Admin();
        admin.setUserName("admin123456");
        admin.setPassword("admin123456");
        String adminPassword;
        String message = null;
        try {
            adminPassword = this.serviceImpl.getAdmin("admin123456");
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            message = e.getMessage();
        } finally {
            assertEquals(message, "User does not exist");

        }

    }

}