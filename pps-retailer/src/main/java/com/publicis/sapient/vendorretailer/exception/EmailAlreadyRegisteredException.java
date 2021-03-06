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
package com.publicis.sapient.vendorretailer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author godkarte
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailAlreadyRegisteredException extends RuntimeException {

    /**
     * @see Exception thrown when the email already exists
     * @param message
     */
    public EmailAlreadyRegisteredException(final String message) {
        super(message);
    }
}
