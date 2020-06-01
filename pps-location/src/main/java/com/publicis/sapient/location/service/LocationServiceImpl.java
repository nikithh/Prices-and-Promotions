package com.publicis.sapient.location.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.publicis.sapient.location.exception.ClusterNameAlreadyExists;
import com.publicis.sapient.location.exception.ZoneNameAlreadyExists;
import com.publicis.sapient.location.model.Cluster;
import com.publicis.sapient.location.model.Store;
import com.publicis.sapient.location.model.Zone;
import com.publicis.sapient.location.repository.ZoneRepository;

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
 * @author sanumash
 * @author gvsrini
 * @author godkarte
 * @author prejayak
 * @since March 2020
 * @see Functionalities for location service
 *
 */

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    ZoneRepository zoneRepository;

    /**
     * @see Functionality to insert a Zone
     * @param zone
     * @return Zone
     */
    @Override
    public Zone insertZone(final Zone zone) {
        final List<Zone> zones = this.zoneRepository.findAll();
        final IntSummaryStatistics maxIdStats = zones.stream().collect(Collectors.summarizingInt(Zone::getZoneId));
        final Integer maxId = maxIdStats.getMax();
        final Boolean zoneExists = zones.stream().anyMatch(z -> z.getZoneName().equals(zone.getZoneName()));
        if (Boolean.TRUE.equals(zoneExists)) {
            throw new ZoneNameAlreadyExists("Zone Name Already Exists");
        }
        if (zone.getCluster() == null) {
            zone.setCluster(new ArrayList<Cluster>());
        }
        if (zones.isEmpty()) {
            zone.setZoneId(1);
        } else {
            zone.setZoneId(maxId + 1);
        }
        return this.zoneRepository.insert(zone);
    }

    /**
     * @see Functionality to update a Zone
     * @param zone
     * @return Zone
     */
    @Override
    public Zone updateZone(final Zone zone) {
        final Zone zoneExist = this.zoneRepository.getZoneByName(zone.getZoneName());
        zone.setZoneId(zoneExist.getZoneId());
        zone.setCluster(zoneExist.getCluster());
        return this.zoneRepository.save(zone);
    }

    /**
     * @see Functionality to list all Zones
     * @return List<Zone>
     */
    @Override
    public List<Zone> getAllZones() {
        return this.zoneRepository.findAll();
    }

    Function<Zone, String> nameFunction = (final Zone zone) -> zone.getZoneName();
    Function<Cluster, String> nameFunctionCluster = (final Cluster cluster) -> cluster.getClusterName();
    Function<Store, String> nameFunctionStore = (final Store store) -> store.getStoreName();

    /**
     * @see Functionality to get all zone names
     * @return List<String>
     */
    @Override
    public List<String> getZoneNames() {
        final List<String> zoneNames = new ArrayList<>();
        final List<Zone> zones = this.getAllZones();
        zones.stream().filter(z -> zoneNames.add(this.nameFunction.apply(z))).collect(Collectors.toList());
        return zoneNames;
    }

    /**
     * @see Funtionality to get zone names based on the regex pattern
     * @return List<String>
     */
    @Override
    public List<String> getZoneNamesByRegex(final String zoneName) {
        final List<String> zoneNames = new ArrayList<>();
        final List<Zone> zones = this.zoneRepository.getZoneNamesRegex(zoneName);
        zones.stream().filter(z -> zoneNames.add(this.nameFunction.apply(z))).collect(Collectors.toList());
        return zoneNames;
    }

    /**
     * @see Functionality to get all Clusters in a Zone
     * @param zoneName
     * @return List<Cluster>
     */
    @Override
    public List<Cluster> findClusterByZoneName(final String zoneName) {
        final Zone zone = this.zoneRepository.getZoneByName(zoneName);
        if (zone == null) {
            throw new ZoneNameAlreadyExists("zone name with the name " + zoneName + " doesn't exist");
        }
        return zone.getCluster();
    }

    /**
     * @see Functionality to get all Cluster names in a Zone
     * @param zoneName
     * @return List<String>
     */
    @Override
    public List<String> findClusterNamesByZoneName(final String zoneName) {
        final Zone zone = this.zoneRepository.getZoneByName(zoneName);
        if (zone == null) {
            return new ArrayList<>();
        }
        final List<Cluster> clusters = zone.getCluster();
        final List<String> clusterNames = new ArrayList<>();
        clusters.stream().filter(cluster -> clusterNames.add(this.nameFunctionCluster.apply(cluster)))
                .collect(Collectors.toList());
        return clusterNames;
    }

    /**
     * @see Functionality to insert a Cluster
     * @param cluster
     * @param zoneName
     * @return Zone
     */
    @Override
    public Zone insertCluster(final Cluster cluster,
            final String zoneName) {
        final Zone zone = this.zoneRepository.getZoneByName(zoneName);
        final List<Cluster> clusterList = zone.getCluster();
        if (cluster.getStore() == null) {
            cluster.setStore(new ArrayList<>());
        }
        final IntSummaryStatistics maxIdStats = clusterList.stream().collect(Collectors.summarizingInt(Cluster::getClusterId));
        final Integer maxId = maxIdStats.getMax();
        final Boolean clusterExists = clusterList.stream().anyMatch(c -> c.getClusterName().equals(cluster.getClusterName()));
        if (Boolean.TRUE.equals(clusterExists)) {
            throw new ClusterNameAlreadyExists("ClusterName already Exists");
        }
        if (clusterList.isEmpty()) {
            cluster.setClusterId(1);
        } else {
            cluster.setClusterId(maxId + 1);
        }
        zone.getCluster().add(cluster);
        return this.zoneRepository.save(zone);
    }

    /**
     * @see Functionality to insert a Store
     * @param store
     * @param zoneName
     * @param clusterName
     * @return Zone
     */
    @Override
    public Zone insertStore(final Store store,
            final String zoneName,
            final String clusterName) {
        final Zone zone = this.zoneRepository.getZoneByName(zoneName);
        final List<Cluster> clusterList = zone.getCluster();
        final List<Cluster> clusters = zone.getCluster();
        final Cluster cluster = clusters.stream().filter(c -> c.getClusterName().equals(clusterName)).findAny().get();
        final List<Store> stores = cluster.getStore();
        final IntSummaryStatistics maxIdStats = stores.stream().collect(Collectors.summarizingInt(Store::getStoreId));
        final Integer maxId = maxIdStats.getMax();
        if (stores.isEmpty()) {
            store.setStoreId(1);
        } else {
            store.setStoreId(maxId + 1);
        }
        clusterList.remove(cluster);
        cluster.getStore().add(store);
        clusterList.add(cluster);
        zone.setCluster(clusterList);
        this.zoneRepository.save(zone);
        return zone;
    }

    /**
     * @see Functionality to get Zone object based on name
     * @param zoneName
     * @return Zone
     */
    @Override
    public Zone getZoneByName(final String zoneName) {
        return this.zoneRepository.getZoneByName(zoneName);
    }

    /**
     * @see Functionality to get a list of Zones and the number of Clusters in each Zone
     * @return HashMap<String, Integer>
     */
    @Override
    public Map<Object, Object> getZoneList() {
        final List<Zone> zones = this.zoneRepository.findAll();
        return zones.stream().collect(Collectors.toMap(zone -> zone.getZoneName(), zone -> zone.getCluster().size()));
    }

    /**
     * @see Functionality to get a list of Clusters and the number of Stores in each Cluster
     * @return HashMap<String, Integer>
     */
    @Override
    public Map<Object, Object> getClusterList() {
        final List<Zone> zones = this.zoneRepository.findAll();
        final Map<Object, Object> clusterList = new HashMap<>();
        for (final Zone zone : zones) {
            final List<Cluster> clusters = zone.getCluster();
            for (final Cluster cluster : clusters) {
                clusterList.put(cluster.getClusterName(), cluster.getStore().size());
            }
        }
        return clusterList;
    }

    /**
     *
     * @param zoneName
     * @param clusterName
     * @return
     */
    @Override
    public List<String> findStoreNames(final String zoneName,
            final String clusterName) {
        final Zone zone = this.zoneRepository.getZoneByName(zoneName);
        final List<Cluster> clusters = zone.getCluster();
        final Cluster cluster = clusters.stream().filter(c -> c.getClusterName().equals(clusterName)).findAny().get();
        final List<Store> stores = cluster.getStore();
        final List<String> storeNames = new ArrayList<>();
        stores.stream().filter(s -> storeNames.add(this.nameFunctionStore.apply(s))).collect(Collectors.toList());
        return storeNames;
    }

    /**
     * @see Funtionality to get cluster names based on the regex pattern
     * @return List<String> where it has zoneName followed by '/' clusterName
     */
    @Override
    public List<String> getClusterNamesByRegex(final String clusterName) {

        final List<Zone> zones = this.zoneRepository.getClusterNamesRegex(clusterName);
        final List<String> zoneClusterList = new ArrayList<>();
        for (final Zone zoneObject : zones) {
            for (final Cluster clusterObject : zoneObject.getCluster()) {
                zoneClusterList.add(zoneObject.getZoneName() + "/" + clusterObject.getClusterName());
            }
        }
        return zoneClusterList;
    }

    @Override
    public void deleteZone(final Zone zone) {
        this.zoneRepository.delete(zone);
    }

}
