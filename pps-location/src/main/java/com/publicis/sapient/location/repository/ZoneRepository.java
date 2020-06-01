package com.publicis.sapient.location.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.publicis.sapient.location.model.Zone;

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
 * @author sursuhas
 * @since March 2020
 * @see Repository for Zone collection
 */

public interface ZoneRepository extends MongoRepository<Zone, Integer> {

    /**
     * @see Custom query to get list of zone names
     * @return List<String>
     */
    @Query(value = "{}", fields = "{'zoneName':1}")
    public List<String> getZoneNames();

    /**
     * @see Custom query to get zone by zoneName
     * @param zoneName
     * @return Zone
     */
    @Query(value = "{'zoneName':?0}")
    public Zone getZoneByName(String zoneName);

    @Query(value = "{'zoneName':{$regex:?0}}")
    public List<Zone> getZoneNamesRegex(String zoneName);

    @Query(value = "{'cluster.clusterName':{$regex:?0}}", fields = "{'zoneName':1,'cluster.clusterName':1,'_id':0}")
    public List<Zone> getClusterNamesRegex(String clusterName);

}
