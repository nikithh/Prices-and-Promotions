/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Publicis Sapient License;
 * you may not use this file except in compliance with the License.
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.publicis.sapient.zuul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

/**
 * @author godkarte
 * @since March 2020
 */
@Component
public class VendorProxy {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    public HttpEntity<String> getVendorCredentials(@PathVariable("companyName") final String companyName) {
        final RestTemplate restTemplate = new RestTemplate();
        final ServiceInstance serviceInstance = this.loadBalancerClient.choose("VENDOR-RETAILER-MANAGEMENT");
        final String resourceUrl = serviceInstance.getUri() + "/vendor/" + companyName;
        // final String resourceUrl = "http://localhost:"+serviceInstance.getPort()+"/vendor/"+companyName;
        return restTemplate.getForEntity(resourceUrl, String.class);
    }

    public HttpEntity<String> getRetailersByUsername(@PathVariable("username") final String username) {
        final RestTemplate restTemplate = new RestTemplate();
        final ServiceInstance serviceInstance = this.loadBalancerClient.choose("VENDOR-RETAILER-MANAGEMENT");
        final String resourceUrl = serviceInstance.getUri() + "/retailers/" + username;
        // final String resourceUrl = "http://localhost:"+serviceInstance.getPort()+"/retailers/"+username;
        return restTemplate.getForEntity(resourceUrl, String.class);
    };

    public HttpEntity<String> getAdminByUsername(@PathVariable("username") final String username) {
        final RestTemplate restTemplate = new RestTemplate();
        final ServiceInstance serviceInstance = this.loadBalancerClient.choose("VENDOR-RETAILER-MANAGEMENT");
        final String resourceUrl = serviceInstance.getUri() + "/admin/" + username;
        // final String resourceUrl = "http://localhost:"+serviceInstance.getPort()+"/retailers/"+username;
        return restTemplate.getForEntity(resourceUrl, String.class);
    };

}