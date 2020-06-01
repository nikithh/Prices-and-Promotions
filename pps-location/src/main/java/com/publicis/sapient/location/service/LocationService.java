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

package com.publicis.sapient.location.service;

import java.util.List;
import java.util.Map;
import com.publicis.sapient.location.model.Cluster;
import com.publicis.sapient.location.model.Store;
import com.publicis.sapient.location.model.Zone;

/**
 *
 * @author prejayak
 *
 */
public interface LocationService {

    public Zone insertZone(Zone zone);

    public Zone updateZone(Zone zone);

    public List<Zone> getAllZones();

    public List<String> getZoneNames();

    public List<Cluster> findClusterByZoneName(String zoneName);

    public List<String> findClusterNamesByZoneName(String zoneName);

    public Zone insertCluster(Cluster cluster,
            String zoneName);

    public Zone insertStore(Store store,
            String zoneName,
            String clusterName);

    public Zone getZoneByName(String zoneName);

    public Map<Object, Object> getZoneList();

    public Map<Object, Object> getClusterList();

    public List<String> findStoreNames(String zoneName,
            String clusterName);

    public List<String> getClusterNamesByRegex(String clusterName);

    public List<String> getZoneNamesByRegex(String zoneName);

    public void deleteZone(Zone zone);
}
