package com.publicis.sapient.location.resource;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.publicis.sapient.location.model.Cluster;
import com.publicis.sapient.location.model.Store;
import com.publicis.sapient.location.model.Zone;
import com.publicis.sapient.location.service.LocationServiceImpl;

import brave.sampler.Sampler;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
 * @see Resource for location service
 */

@RestController
public class LocationResource {
	
	@Bean  
	public Sampler defaultSampler() {  
		return Sampler.ALWAYS_SAMPLE;  
	}
	
    @Autowired
    private LocationServiceImpl locationServiceImpl;

    /**
     * @see Resource to list all Zones
     * @return List<Zone>
     *
     */
    @ApiOperation(value = "View a list of available zones")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list") })
    @GetMapping("/zones")
    public List<Zone> getAllZones() {
        return this.locationServiceImpl.getAllZones();
    }

    /**
     * @see Resource to insert a Zone
     * @param zone
     * @return Zone
     */
    @ApiOperation(value = "insert a Zone")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully added zone"),
            @ApiResponse(code = 400, message = "Zone name already exists") })
    @PostMapping("/zone")
    @ResponseStatus(HttpStatus.CREATED)
    public Zone createZone(@RequestBody @Valid final Zone zone) {
        return this.locationServiceImpl.insertZone(zone);
    }

    /**
     * @see Resource to update a Zone
     * @param zone
     * @return Zone
     */
    @ApiOperation(value = "update a Zone")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated zone") })
    @PutMapping(path = "/zone", produces = "application/json")
    public Zone updateZone(@RequestBody @Valid final Zone zone) {
        return this.locationServiceImpl.updateZone(zone);
    }

    /**
     * @see Resource to get all zone names
     * @return List<String>
     */
    @ApiOperation(value = "View a list of zone names")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list") })
    @GetMapping("/zones/names")
    public List<String> getZoneNames() {
        return this.locationServiceImpl.getZoneNames();
    }

    /**
     * @see Resource to get Zone object based on zone name
     * @param zoneName
     * @return Zone
     */
    @ApiOperation(value = "View details of zone based on zone name")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved details") })
    @GetMapping("/zones/{zoneName}")
    public Zone getZoneByName(@PathVariable final String zoneName) {
        return this.locationServiceImpl.getZoneByName(zoneName);
    }

    /**
     * @see Resource to get the list of all zone names where cluster name has the regex pattern as clusterName
     * @param clusterName
     * @return
     */
    @ApiOperation(value = "View list of all zone names based on regex provided")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the list") })
    @GetMapping("/clusters/regex/{clusterName}")
    public ResponseEntity<List<String>> getClusterNameByRegex(@PathVariable final String clusterName) {
        return new ResponseEntity<>(this.locationServiceImpl.getClusterNamesByRegex(clusterName), HttpStatus.OK);
    }

    /**
     * @see Resource to get the list of all zone names where zone name has the regex pattern as zoneName
     * @param zoneName
     * @return
     */
    @ApiOperation(value = "View list of all cluster names based on regex provided")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the list") })
    @GetMapping("/zones/regex/{zoneName}")
    public ResponseEntity<List<String>> getZoneNameByRegex(@PathVariable final String zoneName) {
        return new ResponseEntity<>(this.locationServiceImpl.getZoneNamesByRegex(zoneName), HttpStatus.OK);
    }

    /**
     * @see Resource to insert a Cluster
     * @param cluster
     * @param zoneName
     * @return Zone
     */
    @ApiOperation(value = "insert a cluster")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added the cluster"),
            @ApiResponse(code = 400, message = "cluster name already exists") })
    @PutMapping("/{zoneName}/cluster")
    public Zone insertCluster(@RequestBody @Valid final Cluster cluster,
            @PathVariable final String zoneName) {
        return this.locationServiceImpl.insertCluster(cluster, zoneName);
    }

    /**
     * @see Resource to get all Cluster names in a Zone
     * @param zoneName
     * @return List<String>
     */
    @ApiOperation(value = "View list of all cluster names present in a zone")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the list") })
    @GetMapping("/{zoneName}/clusters/names")
    public List<String> getClusterNamesByZoneName(@PathVariable final String zoneName) {
        return this.locationServiceImpl.findClusterNamesByZoneName(zoneName);
    }

    /**
     * @see Resource to insert a Store
     * @param store
     * @param zoneName
     * @param clusterName
     * @return Zone
     */
    @ApiOperation(value = "insert store to a zone and a cluster")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added store to zone and cluster") })
    @PutMapping("/{zoneName}/{clusterName}/store")
    public Zone insertStore(@RequestBody @Valid final Store store,
            @PathVariable final String zoneName,
            @PathVariable final String clusterName) {
        return this.locationServiceImpl.insertStore(store, zoneName, clusterName);
    }

    /**
     * @see Resource to get a list of Zones and the number of Clusters in each Zone
     * @return HashMap<String,Integer>
     */
    @ApiOperation(value = "View list of all zones and the number of clusters associated with it")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the list") })
    @GetMapping("/zone-map")
    public Map<Object, Object> getZoneList() {
        return this.locationServiceImpl.getZoneList();
    }

    /**
     * @see Resource to get a list of Clusters and the number of Stores in each Cluster
     * @return HashMap<String,Integer>
     */
    @ApiOperation(value = "View list of all cluster names and the number of stores associated with it")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the list") })
    @GetMapping("/cluster-map")
    public Map<Object, Object> getClusterList() {
        return this.locationServiceImpl.getClusterList();
    }

    /**
     * @see Resource to get all the store names existing in zone and a cluster
     * @param zoneName
     * @param clusterName
     * @return
     */
    @ApiOperation(value = "View list of all store names associated to the cluster and zone")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the list") })
    @GetMapping("/{zoneName}/{clusterName}/stores/names")
    public List<String> getStoreNames(@PathVariable final String zoneName,
            @PathVariable final String clusterName) {
        return this.locationServiceImpl.findStoreNames(zoneName, clusterName);
    }

    @DeleteMapping("/zone/{zoneName}")
    public ResponseEntity<Void> deleteZone(@PathVariable final String zoneName) {
        this.locationServiceImpl.deleteZone(this.locationServiceImpl.getZoneByName(zoneName));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
