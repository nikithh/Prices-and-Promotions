package com.publicis.sapient.vendorretailer.service;

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

import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.publicis.sapient.vendorretailer.beans.Admin;
import com.publicis.sapient.vendorretailer.beans.Vendor;
import com.publicis.sapient.vendorretailer.exception.AdminAlreadyExists;
import com.publicis.sapient.vendorretailer.exception.VendorAlreadyExists;
import com.publicis.sapient.vendorretailer.repository.AdminRepository;
import com.publicis.sapient.vendorretailer.repository.VendorRepository;

@Service
public class VendorRetailerServiceImpl implements VendorRetailerService {

    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private AdminRepository adminRepository;

    /**
     * @see inserting a vendor
     * @param vendor
     * @return vendor
     */

    @Override
    public Vendor createVendor(final Vendor vendor) {

        final List<Vendor> vendors = this.vendorRepository.findAll();
        final IntSummaryStatistics maxIdStats = vendors.stream().collect(Collectors.summarizingInt(Vendor::getVendorId));
        final Integer maxId = maxIdStats.getMax();
        final Boolean vendorExists = vendors.stream().anyMatch(v -> v.getCompanyName().equals(vendor.getCompanyName()));
        if (Boolean.TRUE.equals(vendorExists)) {
            throw new VendorAlreadyExists("Vendor with the name " + vendor.getCompanyName() + " already exists");
        }
        if (vendors.isEmpty()) {
            vendor.setVendorId(1);
        } else {
            vendor.setVendorId(maxId + 1);
        }
        return this.vendorRepository.insert(vendor);
    }

    /**
     * @see inserting a admin
     * @param admin
     * @return Admin
     */

    @Override
    public Admin createAdmin(@Valid final Admin admin) {
        final List<Admin> admins = this.adminRepository.findAll();
        final IntSummaryStatistics maxIdStats = admins.stream().collect(Collectors.summarizingInt(Admin::getAdminId));
        final Integer maxId = maxIdStats.getMax();
        final Boolean adminExists = admins.stream().anyMatch(a -> a.getUserName().equals(admin.getUserName()));
        if (Boolean.TRUE.equals(adminExists)) {
            throw new AdminAlreadyExists("Admin with the username " + admin.getUserName() + " already exists");
        }
        if (admins.isEmpty()) {
            admin.setAdminId(1);
        } else {
            admin.setAdminId(maxId + 1);
        }
        return this.adminRepository.insert(admin);
    }

    /**
     * @see get password given companyname
     * @param companyName
     * @return password
     */

    @Override
    public String getVendor(final String companyName) {
        final Vendor vendor = this.vendorRepository.getVendorByName(companyName);
        if (vendor.isEnabled()) {
            return vendor.getPassword();
        } else {
            return "NA";
        }
    }

    /**
     * @see get password given companyname
     * @param companyName
     * @return password
     */

    @Override
    public String getAdmin(final String userName) {
        final Admin admin = this.adminRepository.getAdminByUsername(userName);
        if (null != admin) {
            return admin.getPassword();
        } else {
            throw new AdminAlreadyExists("User does not exist");
        }
    }

    /**
     * @see get all vendors
     * @return List<Vendor>
     */

    @Override
    public List<Vendor> getAllVendors() {
        return this.vendorRepository.findAll();
    }

    /**
     * @see get all admins
     * @return List<Admin>
     */

    @Override
    public List<Admin> getAllAdmins() {
        return this.adminRepository.findAll();
    }

    private static HashMap<String, String> retailers = new HashMap<>();

    static {
        retailers.put("admin", "21232f297a57a5a743894a0e4a801fc3");
    }

    /**
     * @see get password given username
     * @param username
     * @return password
     */

    @Override
    public String getRetailersByUsername(final String username) {
        return retailers.get(username);
    }

    /**
     * @see Service to Delete a Vendor
     * @param companyName
     * @return
     */
    @Override
    public void deleteVendor(final String companyName) {
        this.vendorRepository.delete(this.vendorRepository.getVendorByName(companyName));
    }

    @Override
    public void deleteAdmin(final String userName) {
        this.adminRepository.delete(this.adminRepository.getAdminByUsername(userName));
    }

}
