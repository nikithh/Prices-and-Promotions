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

package com.publicis.sapient.group.config;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author mnanjan
 * @see Redis Cache Error Handler
 * 
 */

public class CustomCacheErrorHandler implements CacheErrorHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomCacheErrorHandler.class.getClass());

	@Override
	public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
		LOGGER.info("Cache " + cache.getName() + " is down to search for key : " + key +  "with exception "+exception);
	}

	@Override
	public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
		LOGGER.info("Cache " + cache.getName() + "is down to put for key :" + key + "with exception " + exception);	
	}

	@Override
	public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
		LOGGER.info("Cache " + cache.getName() + "is down to evict for key :" + key + "with exception " + exception);	
	}

	@Override
	public void handleCacheClearError(RuntimeException exception, Cache cache) {
		LOGGER.info("Cache " + cache.getName() + "is down to clear"+ exception);
	}

}