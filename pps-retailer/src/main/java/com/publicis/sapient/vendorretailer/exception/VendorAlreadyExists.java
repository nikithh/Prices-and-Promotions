package com.publicis.sapient.vendorretailer.exception;

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
 * @author preagrav
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author godkarte
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VendorAlreadyExists extends RuntimeException {

    /**
     * @see Exception thrown when the vendor already exists
     * @param message
     */
    public VendorAlreadyExists(final String message) {
        super(message);
    }
}
