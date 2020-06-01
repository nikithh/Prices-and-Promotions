package com.publicis.sapient.vendorretailer;

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
 * @author gvsrini
 * @author preagraw
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Collation.CaseFirst;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.publicis.sapient.vendorretailer.beans.Admin;
import com.publicis.sapient.vendorretailer.beans.Retailer;
import com.publicis.sapient.vendorretailer.beans.Vendor;
import com.publicis.sapient.vendorretailer.controller.VendorRetailerController;
import com.publicis.sapient.vendorretailer.service.VendorRetailerServiceImpl;

@SpringBootTest
class VendorRetailerApplicationTests {

    @InjectMocks
    VendorRetailerController vendorRetailerController;

    @Mock
    VendorRetailerServiceImpl vendorRetailerServiceImpl;

    /**
     * @see Test case got get all vendors
     */
    @Test
    void getAllVendorsTest() {
        final List<Vendor> list = new ArrayList<Vendor>();
        final List<String> l = new ArrayList<String>();
        l.add("alocohol");
        l.add("baby");
        final List<String> ll = new ArrayList<String>();
        l.add("baby");

        final Vendor v1 = new Vendor(101, "gv@ps.com", "ABC", "pqr", "qwe", l);
        final Vendor v2 = new Vendor(102, "gvs@ps.com", "DEF", "stu", "asd", ll);
        final Vendor v3 = new Vendor(103, "gvsn@ps.com", "GHI", "vwx", "zxc", l);

        list.add(v1);
        list.add(v2);
        list.add(v3);

        when(this.vendorRetailerServiceImpl.getAllVendors()).thenReturn(list);
        final ResponseEntity<List<Vendor>> vendorlist = this.vendorRetailerController.getAllVendors();
        verify(this.vendorRetailerServiceImpl).getAllVendors();
    }

    // /**
    // * @see Test case for inserting a vendor
    // */
    // @Test
    // void insertVendorTest() {
    // final Vendor vendor = new Vendor();
    //
    // final List<String> l = new ArrayList<String>();
    // l.add("alocohol");
    // l.add("baby");
    // vendor.setVendorId(111);
    // vendor.setEmail("preets@ps.com");
    // vendor.setCompanyName("ps");
    // vendor.setCompanyType("liquor");
    // vendor.setPassword("agrawal");
    // vendor.setProductSold(l);
    // List<Vendor> vendorList=new ArrayList<Vendor>();
    // when(this.vendorRetailerServiceImpl.createVendor(vendor)).thenReturn(vendor);
    // when(this.vendorRepository.findByEmailIdIgnoreCase(vendor.getEmail())).thenReturn(vendorList);
    // when(this.confirmationTokenRepository.save()).thenReturn(vendor);
    // final ResponseEntity<Vendor> ex = this.vendorRetailerController.createVendor(vendor);
    // verify(this.vendorRetailerServiceImpl).createVendor(vendor);
    // }
    //
    // /**
    // * @see Test case for inserting vendor without giving vendorId
    // */
    //
    // @Test
    // void insertVendorNullTest() {
    // final Vendor vendor = new Vendor();
    // final List<String> l = new ArrayList<String>();
    // l.add("alcohol");
    // l.add("baby");
    //
    // vendor.setEmail("preets@ps.com");
    // vendor.setCompanyName("ps");
    // vendor.setCompanyType("liquor");
    // vendor.setPassword("agrawal");
    // vendor.setProductSold(l);
    // vendor.setEnabled(false);
    //
    // when(this.vendorRetailerServiceImpl.createVendor(vendor)).thenReturn(vendor);
    // final ResponseEntity<Vendor> ex = this.vendorRetailerController.createVendor(vendor);
    // verify(this.vendorRetailerServiceImpl).createVendor(vendor);
    // }

    /**
     * @see Test {@link CaseFirst} for returning password given companyName
     */
    @Test
    void getPasswordTest() {

        final List<String> l = new ArrayList<String>();
        l.add("alocohol");
        l.add("baby");
        final Vendor v1 = new Vendor(101, "gv@ps.com", "ABC", "pqr", "qwe", l);
        final String vn = v1.getCompanyName();
        final String pwd = v1.getPassword();
        this.vendorRetailerServiceImpl.getVendor(vn);
        when(this.vendorRetailerServiceImpl.getVendor("ABC")).thenReturn("qwe");
        verify(this.vendorRetailerServiceImpl).getVendor(vn);
    }

    /**
     * @see Test {@link CaseFirst} for returning password given userName
     */
    @Test
    void getVendorCredentialsTest() {

        final List<String> l = new ArrayList<String>();
        l.add("alocohol");
        l.add("baby");
        final Vendor v1 = new Vendor(101, "gv@ps.com", "ABC", "pqr", "qwe", l);
        final String vn = v1.getCompanyName();
        final String pwd = v1.getPassword();
        this.vendorRetailerServiceImpl.getVendor(vn);
        when(this.vendorRetailerServiceImpl.getVendor("ABC")).thenReturn("qwe");
        verify(this.vendorRetailerServiceImpl).getVendor(vn);
    }

    @Test
    void getRetailersByUsernameTest() {
        final List<Retailer> list = new ArrayList<Retailer>();

        final Retailer retailer1 = new Retailer("srini@ps.com", "srini123");
        final Retailer retailer2 = new Retailer("sanumash@ps.com", "sanumash987");
        final Retailer retailer3 = new Retailer("mayank@ps.com", "mayank6987");

        list.add(retailer1);
        list.add(retailer2);
        list.add(retailer3);

        when(this.vendorRetailerServiceImpl.getRetailersByUsername(list.get(0).getUsername()))
                .thenReturn(list.get(0).getPassword());

        final String retailer = this.vendorRetailerController.getRetailersByUsername(list.get(0).getUsername());

        verify(this.vendorRetailerServiceImpl, times(1)).getRetailersByUsername(list.get(0).getUsername());

        assertEquals(this.vendorRetailerServiceImpl.getRetailersByUsername(list.get(0).getUsername()), retailer);

    }

    @Test
    void createAdminTest() {
        final Admin admin = new Admin();
        // 1, "user1", "user1"
        admin.setAdminId(1);
        admin.setPassword("user1");
        admin.setUserName("user1");
        when(this.vendorRetailerServiceImpl.createAdmin(admin)).thenReturn(admin);
        final ResponseEntity<Admin> admin2 = this.vendorRetailerController.createAdmin(admin);
        assertEquals(new ResponseEntity<>(this.vendorRetailerServiceImpl.createAdmin(admin), HttpStatus.CREATED), admin2);

    }

    @Test
    void getAdminCredentialsTest() {
        when(this.vendorRetailerServiceImpl.getAdmin("admin1")).thenReturn("admin1");
        final String adminPassword = this.vendorRetailerController.getAdminCredentials("admin1");
        assertEquals(this.vendorRetailerServiceImpl.getAdmin("admin1"), adminPassword);
    }

}
