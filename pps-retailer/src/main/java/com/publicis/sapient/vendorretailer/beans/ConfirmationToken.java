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
package com.publicis.sapient.vendorretailer.beans;

import java.util.Date;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author godkarte
 */

@Document(collection = "confirmationToken")
public class ConfirmationToken {

    public String getConfirmationToken() {
        return this.confirmationToken;
    }

    public void setConfirmationToken(final String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Vendor getVendor() {
        return this.vendor;
    }

    public void setVendor(final Vendor vendor) {
        this.vendor = vendor;
    }

    @Id
    private String confirmationToken;

    private Date createdDate;

    private Vendor vendor;

    public ConfirmationToken(final Vendor vendor) {
        this.vendor = vendor;
        this.createdDate = new Date();
        this.confirmationToken = UUID.randomUUID().toString();
    }

}
