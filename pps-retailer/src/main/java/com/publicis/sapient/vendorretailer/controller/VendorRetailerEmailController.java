package com.publicis.sapient.vendorretailer.controller;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.publicis.sapient.vendorretailer.beans.Admin;
import com.publicis.sapient.vendorretailer.beans.ConfirmationToken;
import com.publicis.sapient.vendorretailer.beans.Vendor;
import com.publicis.sapient.vendorretailer.exception.EmailAlreadyRegisteredException;
import com.publicis.sapient.vendorretailer.repository.ConfirmationTokenRepository;
import com.publicis.sapient.vendorretailer.repository.VendorRepository;
import com.publicis.sapient.vendorretailer.service.VendorRetailerServiceImpl;

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
@RestController
public class VendorRetailerEmailController {

    private static Logger LOGGER = LoggerFactory.getLogger(VendorRetailerEmailController.class.getClass());

    @Autowired
    private VendorRetailerServiceImpl vendorRetailerServiceImpl;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    /**
     * @see inserting vendor
     * @param vendor
     * @return Vendor
     */
    @PostMapping("/vendor")
    public ResponseEntity<String> createVendor(@RequestBody @Valid final Vendor vendor) {
        final List<Vendor> vendorList = this.vendorRepository.findByEmailIdIgnoreCase(vendor.getEmail());
        if (vendorList.size() > 0) {
            throw new EmailAlreadyRegisteredException("Sorry,Account already exists with this email");
        }
        final Vendor finalVendor = this.vendorRetailerServiceImpl.createVendor(vendor);
        final ConfirmationToken confirmationToken = new ConfirmationToken(vendor);
        this.confirmationTokenRepository.save(confirmationToken);
        final String message =
                "Hello " + vendor.getCompanyName() + ", Thank you for choosing Price and Promotions Application."
                        + " To verify your account, please click here : "
                        + "https://compute.price-and-promotions.com/vendor-retailer-management/confirm-account?token="
                        + confirmationToken.getConfirmationToken();
        return new ResponseEntity<>(message, HttpStatus.CREATED);

    }

   

    /**
     * @see confirmation of vendor account
     * @return String
     */
    @RequestMapping(value = "/confirm-account")
    public String confirmVendorAccount(@RequestParam("token") final String confirmationToken) {
        final ConfirmationToken token = this.confirmationTokenRepository.getConfirmationTokenByToken(confirmationToken);
        String finalMessage = "";
        if (token != null) {
            final List<Vendor> vendor = this.vendorRepository.findByEmailIdIgnoreCase(token.getVendor().getEmail());
            final Vendor finalVendor = vendor.get(0);
            finalVendor.setEnabled(true);
            this.vendorRepository.save(finalVendor);
            finalMessage = "Thank you for verifying your account, Now you can login into the application";
        } else {
            finalMessage = "Invalid token";
        }

        return finalMessage;
    }

}
