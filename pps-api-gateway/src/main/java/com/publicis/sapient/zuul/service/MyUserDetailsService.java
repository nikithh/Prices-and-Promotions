package com.publicis.sapient.zuul.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.publicis.sapient.zuul.exceptions.VendorNotVerifiedException;

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
 * @author godkarte
 * @since March 2020
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private VendorProxy vendorProxy;

    public int state = 0;

    public int getState() {
        return this.state;
    }

    public void setState(final int state) {
        this.state = state;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        if (this.state == 1) {
            final String password = this.vendorProxy.getVendorCredentials(username).getBody();
            if (password.equals("NA")) {
                throw new VendorNotVerifiedException("Email not verified! Please check your registered email to verify.");
            }
            return new User(username, password, new ArrayList<>());
        } else if (this.state == 2) {
            return new User(username, this.vendorProxy.getRetailersByUsername(username).getBody(), new ArrayList<>());
        } else {
            return new User(username, this.vendorProxy.getAdminByUsername(username).getBody(), new ArrayList<>());
        }

    }

}
