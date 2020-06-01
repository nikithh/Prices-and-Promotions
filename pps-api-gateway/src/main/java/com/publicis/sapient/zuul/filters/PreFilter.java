package com.publicis.sapient.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class PreFilter extends ZuulFilter {
	
	private static Logger LOGGER = LoggerFactory.getLogger(PreFilter.class.getClass());
	
	@Override
	public Object run() throws ZuulException {
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		LOGGER.info("<<<< Request -> {} request URI -> {}", request, request.getRequestURI());
		try {
			if (request.getContentLength() > 0) {
				LOGGER.info("<<<< Request Body :: {}", request.getReader());
            }
        } catch (Exception e) {
        	LOGGER.error("Error parsing request ", e);
            //throw e;
        }
		
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public String filterType() {
		return "pre";
	}
	
}
