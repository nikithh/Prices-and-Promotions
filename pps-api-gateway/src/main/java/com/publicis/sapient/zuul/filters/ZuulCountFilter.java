package com.publicis.sapient.zuul.filters;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Endpoint(id = "api-count")
public class ZuulCountFilter extends ZuulFilter {

    private int productCount = 0;
    private int exCount = 0;
    private int locationCount = 0;

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        final RequestContext ctx = RequestContext.getCurrentContext();
        System.out.println(ctx.get("proxy"));
        if ((ctx.get("proxy") != null) && ctx.get("proxy").equals("example-docker")) {
            this.exCount = this.exCount + 1;
        }
        if ((ctx.get("proxy") != null) && ctx.get("proxy").equals("location-management")) {
            this.locationCount += 1;
        }
        if ((ctx.get("proxy") != null) && ctx.get("proxy").equals("product-management")) {
            this.productCount += 1;
        }
        return null;
    }

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @ReadOperation
    public Map<String, Object> countApi() {
        final Map<String, Object> details = new LinkedHashMap<>();
        details.put("exampleCount", this.exCount);
        details.put("productCount", this.productCount);
        details.put("locationCount", this.locationCount);
        return details;
    }
}