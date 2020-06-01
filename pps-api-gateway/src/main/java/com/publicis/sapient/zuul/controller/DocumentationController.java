package com.publicis.sapient.zuul.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Component
@Primary
@EnableAutoConfiguration
public class DocumentationController implements SwaggerResourcesProvider {
	
	@Override
	public List<SwaggerResource> get() {
		
		final String uriPrefix = "/v2/api-docs";


    	final String vendorRetailerURL = "/vendor-retailer-management"+uriPrefix;
    	final String apiGatewayURL = uriPrefix;
    	final String productURL = "/product-management"+uriPrefix;
    	final String locationURL = "/location-management"+uriPrefix;
    	final String groupURL =  "/group-management"+uriPrefix;

		
		List<SwaggerResource> resources = new ArrayList<>();
		resources.add(swaggerResource("vendor-retailer-management", vendorRetailerURL, "2.0"));
		resources.add(swaggerResource("api-gateway", apiGatewayURL, "2.0"));
		resources.add(swaggerResource("product-management", productURL, "2.0"));
		resources.add(swaggerResource("location-management", locationURL, "2.0"));
		resources.add(swaggerResource("group-management", groupURL, "2.0"));
	
		return resources;
	}

	private SwaggerResource swaggerResource(String name, String location, String version) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion(version);
		return swaggerResource;
	}

}