package com.publicis.sapient.zuul.controller;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
import com.publicis.sapient.zuul.configurer.AuthenticationRequest;
import com.publicis.sapient.zuul.configurer.AuthenticationResponse;
import com.publicis.sapient.zuul.configurer.JwtUtil;
import com.publicis.sapient.zuul.exceptions.VendorNotVerifiedException;
import com.publicis.sapient.zuul.service.MyUserDetailsService;

/**
 * @author godkarte
 * @since March 2020
 */
@RestController
public class JwtController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(JwtController.class.getClass());
	private JwtMode.JwtModeStat jwtMode;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@RequestMapping(value = "/vendor/authenticate", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "createAuthenticationTokenCustomerFallBack")
	public ResponseEntity<?> createAuthenticationTokenCustomer(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		userDetailsService.state=1;
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		AuthenticationResponse authenticationResponse=new AuthenticationResponse(jwt);
		authenticationResponse.setUserName(authenticationRequest.getUsername());
		return ResponseEntity.ok(authenticationResponse);
	}
	
	public ResponseEntity<?> createAuthenticationTokenCustomerFallBack(AuthenticationRequest authenticationRequest,
            Throwable throwable) throws Exception {
        if (throwable.getMessage() != null) {
            if(throwable.getMessage() == "Email not verified! Please check your registered email to verify.")
                { throw new VendorNotVerifiedException("Email not verified! Please check your registered email to verify."); }
            else { throw new BadCredentialsException(throwable.getMessage()); }
        }
        LOGGER.error("RestTemplateConnectionError: Service Not Available");
        return ResponseEntity.status(HttpStatus.SC_SERVICE_UNAVAILABLE).build();
        //return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.SC_SERVICE_UNAVAILABLE);
    }
	
	@RequestMapping(value = "/retailer/authenticate", method = RequestMethod.POST)
    @HystrixCommand(fallbackMethod = "createAuthenticationTokenRetailerFallBack")
	public ResponseEntity<?> createAuthenticationTokenRetailer(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		userDetailsService.state=2;
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);
		AuthenticationResponse authenticationResponse=new AuthenticationResponse(jwt);
		authenticationResponse.setUserName(authenticationRequest.getUsername());
		return ResponseEntity.ok(authenticationResponse);
	}

	public ResponseEntity<?> createAuthenticationTokenRetailerFallBack(AuthenticationRequest authenticationRequest,
            Throwable throwable) throws Exception {
        if (throwable.getMessage() != null) { throw new BadCredentialsException("Incorrect username or password"); }
        LOGGER.info("<<< RestTemplateConnectionError: Service Not Available");
        return ResponseEntity.status(HttpStatus.SC_SERVICE_UNAVAILABLE).build();
        //return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.SC_SERVICE_UNAVAILABLE);
    }
	
	@RequestMapping(value = "/admin/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationTokenAdmin(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		userDetailsService.state=3;
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);
		AuthenticationResponse authenticationResponse=new AuthenticationResponse(jwt);
		authenticationResponse.setUserName(authenticationRequest.getUsername());
		return ResponseEntity.ok(authenticationResponse);
	}

}
