package com.publicis.sapient.zuul.configurer;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.publicis.sapient.zuul.service.MyUserDetailsService;

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
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain) throws ServletException, IOException {
        // TODO Auto-generated method stub
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if ((authorizationHeader != null) && authorizationHeader.startsWith("BearerR ")) {
            jwt = authorizationHeader.substring(8);
            username = this.jwtUtil.extractUsername(jwt);
        }
        if ((authorizationHeader != null) && authorizationHeader.startsWith("BearerV ")) {
            jwt = authorizationHeader.substring(8);
            username = this.jwtUtil.extractUsername(jwt);
        }
        if ((authorizationHeader != null) && authorizationHeader.startsWith("BearerA ")) {
            jwt = authorizationHeader.substring(8);
            username = this.jwtUtil.extractUsername(jwt);
        }

        if ((username != null) && (SecurityContextHolder.getContext().getAuthentication() == null)) {
            if (authorizationHeader.startsWith("BearerV ")) {
                this.userDetailsService.setState(1);
            }
            if (authorizationHeader.startsWith("BearerR ")) {
                this.userDetailsService.setState(2);
            }
            if (authorizationHeader.startsWith("BearerA ")) {
                this.userDetailsService.setState(3);
            }
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (this.jwtUtil.validateToken(jwt, userDetails)) {

                final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);

    }

}
