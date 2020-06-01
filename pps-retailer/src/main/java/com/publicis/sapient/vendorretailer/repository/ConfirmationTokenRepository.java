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
package com.publicis.sapient.vendorretailer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.publicis.sapient.vendorretailer.beans.ConfirmationToken;

public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, Integer> {

    @Query(value = "{'confirmationToken':?0}")
    public ConfirmationToken getConfirmationTokenByToken(String confirmationToken);
}
