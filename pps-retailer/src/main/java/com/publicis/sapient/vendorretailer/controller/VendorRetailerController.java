package com.publicis.sapient.vendorretailer.controller;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.publicis.sapient.vendorretailer.beans.Admin;
import com.publicis.sapient.vendorretailer.beans.Vendor;
import com.publicis.sapient.vendorretailer.service.VendorRetailerServiceImpl;

import brave.sampler.Sampler;

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
 * @author godkarte
 */
@RestController
public class VendorRetailerController {

    private static Logger LOGGER = LoggerFactory.getLogger(VendorRetailerController.class.getClass());

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

    @Autowired
    private VendorRetailerServiceImpl vendorRetailerServiceImpl;

    /**
     * @see inserting vendor
     * @param vendor
     * @return Vendor
     */
    @PostMapping("/admin")
    public ResponseEntity<Admin> createAdmin(@RequestBody @Valid final Admin admin) {
        return new ResponseEntity<>(this.vendorRetailerServiceImpl.createAdmin(admin), HttpStatus.CREATED);
    }

    /**
     * @see getting password given companyName
     * @param companyName
     * @return password
     */

    @GetMapping("/vendor/{companyName}")
    @ResponseStatus(HttpStatus.OK)
    public String getVendorCredentials(@PathVariable final String companyName) {
        LOGGER.info("Fetching the username of the vendor");
        return this.vendorRetailerServiceImpl.getVendor(companyName);
    }

    /**
     * @see getting password given admin username
     * @param companyName
     * @return password
     */

    @GetMapping("/admin/{userName}")
    @ResponseStatus(HttpStatus.OK)
    public String getAdminCredentials(@PathVariable final String userName) {
        LOGGER.info("Fetching the username of the admin");
        return this.vendorRetailerServiceImpl.getAdmin(userName);
    }

    /**
     * @see getting all vendors details
     * @return List<Vendor>
     */
    @GetMapping("/vendors")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Vendor>> getAllVendors() {
        LOGGER.info("Fetching all the vendors");
        return new ResponseEntity<>(this.vendorRetailerServiceImpl.getAllVendors(), HttpStatus.OK);
    }

    /**
     * @see getting all admin details
     * @return List<Admin>
     */
    @GetMapping("/admins")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Admin>> getAllAdmins() {
        LOGGER.info("Fetching all the admins");
        return new ResponseEntity<>(this.vendorRetailerServiceImpl.getAllAdmins(), HttpStatus.OK);
    }

    /**
     * @see get password given username
     * @param username
     * @return password
     */
    @GetMapping("/retailers/{username}")
    public String getRetailersByUsername(@PathVariable final String username) {
        LOGGER.info("Fetching the username of retailer");
        return this.vendorRetailerServiceImpl.getRetailersByUsername(username);
    }

}
