package com.publicis.sapient.vendorretailer.beans;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

@Document(collection = "vendor")
public class Vendor {

    @Id
    private int vendorId;
    @NotNull
    private String email;
    @NotNull
    @Size(min = 4, message = "company name size should be greater than 3")
    private String companyName;
    @NotNull
    private String companyType;
    @NotNull
    private String password;
    private List<String> productSold;

    private boolean isEnabled;

    public int getVendorId() {
        return this.vendorId;
    }

    public void setVendorId(final int vendorId) {
        this.vendorId = vendorId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return this.companyType;
    }

    public void setCompanyType(final String companyType) {
        this.companyType = companyType;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public List<String> getProductSold() {
        return this.productSold;
    }

    public void setProductSold(final List<String> productSold) {
        this.productSold = productSold;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(final boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Vendor() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Vendor(final int vendorId,
            @NotNull final String email,
            @NotNull @Size(min = 4, message = "company name size should be greater than 3") final String companyName,
            @NotNull final String companyType,
            @NotNull final String password,
            final List<String> productSold,
            final boolean isEnabled) {
        super();
        this.vendorId = vendorId;
        this.email = email;
        this.companyName = companyName;
        this.companyType = companyType;
        this.password = password;
        this.productSold = productSold;
        this.isEnabled = isEnabled;
    }

    public Vendor(final int vendorId,
            @NotNull final String email,
            @NotNull @Size(min = 4, message = "company name size should be greater than 3") final String companyName,
            @NotNull final String companyType,
            @NotNull final String password,
            final List<String> productSold) {
        super();
        this.vendorId = vendorId;
        this.email = email;
        this.companyName = companyName;
        this.companyType = companyType;
        this.password = password;
        this.productSold = productSold;
    }

}
