package com.publicis.sapient.vendorretailer.repository;

import java.util.List;

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
 * @author preagraw
 * @author godkarte
 */

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.publicis.sapient.vendorretailer.beans.Vendor;

public interface VendorRepository extends MongoRepository<Vendor, Integer> {

    /**
     * @see custom query to get vendor by companyName
     * @param companyName
     * @return vendor
     */

    @Query(value = "{'companyName':?0}")
    public Vendor getVendorByName(final String companyName);

    @Query(value = "{'email':?0}")
    public List<Vendor> findByEmailIdIgnoreCase(String email);

}
