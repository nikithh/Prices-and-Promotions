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

package com.publicis.sapient.product.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.publicis.sapient.product.handler.Constants;
import com.publicis.sapient.product.model.AddPromotion;
import com.publicis.sapient.product.model.AssignProduct;
import com.publicis.sapient.product.model.CancelWithdrawPromotion;
import com.publicis.sapient.product.model.EffectivePrice;
import com.publicis.sapient.product.model.FilterOnDate;
import com.publicis.sapient.product.model.Product;
import com.publicis.sapient.product.model.ProductCategory;
import com.publicis.sapient.product.model.Promotion;
import com.publicis.sapient.product.model.PromotionStatus;
import com.publicis.sapient.product.service.IProductService;
import brave.sampler.Sampler;
import net.minidev.json.JSONObject;

/**
 * @author prejayak
 * @author sursuhas
 * @author godkarte
 * @author sanumash
 * @author sidshriv3
 * @author pawnavee
 * @author shan
 * @since March 2020
 * @see Controller for Product service
 */

@RestController
public class ProductResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductResource.class.getClass());

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

    @Autowired
    private IProductService productService;

    /**
     * @see Controller to Insert a Product
     * @param product
     * @return Product
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    @PostMapping(value = "/product")
    public ResponseEntity<CompletableFuture<Product>> insertProduct(@RequestParam(value = "files") final MultipartFile[] files,
            @RequestParam(value = "product") final String product) throws JsonMappingException, JsonProcessingException {
        LOGGER.info("inserting product");
        final ObjectMapper mapper = new ObjectMapper();
        final Product productObject = mapper.readValue(product, Product.class);
        return new ResponseEntity<>(this.productService.insertProduct(productObject, files), HttpStatus.CREATED);
    }

    /**
     * @see Controller to get a Product by the Product Name
     * @param productName
     * @return Product
     */
    @GetMapping(value = "/product/{productName}")
    public ResponseEntity<CompletableFuture<Product>> getProductByName(@PathVariable @NotNull final String productName) {
        LOGGER.info("get product by product name");
        return new ResponseEntity<>(this.productService.getProductByName(productName), HttpStatus.OK);
    }

    /**
     * @see Controller to get all the Products
     * @return ResponseEntity<List<Product>>
     */
    @GetMapping(value = "/products")
    public ResponseEntity<CompletableFuture<List<Product>>> getAllProducts() {
        LOGGER.info("get all products");
        return new ResponseEntity<>(this.productService.getAllProducts(), HttpStatus.OK);
    }

    /**
     * @see Controller to Get all Product Names
     * @return List<String>
     */
    @GetMapping(value = "/products/names")
    public ResponseEntity<CompletableFuture<List<String>>> getAllProductNames() {
        LOGGER.info("get all product names");
        return new ResponseEntity<>(this.productService.getAllProductNames(), HttpStatus.OK);
    }

    /**
     * @see Controller to Get all Zone Names for a Product
     * @param productName
     * @return List<String>
     */
    @GetMapping(value = "/{productName}/product/zones/names")
    public ResponseEntity<CompletableFuture<List<String>>>
            getAllZoneNamesInProduct(@PathVariable @NotNull final String productName) {
        LOGGER.info("get all zone names the product is associated to");
        return new ResponseEntity<>(this.productService.getAllZoneNamesInProduct(productName), HttpStatus.OK);
    }

    /**
     * @see Controller to Get all Cluster Names in a Zone
     * @param productName
     * @param zoneName
     * @return List<String>
     */
    @GetMapping(value = "/{productName}/{zoneName}/product/clusters/names")
    public ResponseEntity<CompletableFuture<List<String>>> getAllClusterNamesInZone(
            @PathVariable @NotNull final String productName,
            @PathVariable @NotNull final String zoneName) {
        LOGGER.info("get all cluster names a product is associated when given a zone name");
        return new ResponseEntity<>(this.productService.getAllClusterNamesInZone(productName, zoneName), HttpStatus.OK);
    }

    /**
     * @see Controller to get all categories
     * @param
     * @return List<String>
     */
    @GetMapping(value = "/categories")
    public ResponseEntity<CompletableFuture<List<String>>> getAllCategories() {
        LOGGER.info("get All categories");
        return new ResponseEntity<>(this.productService.getAllCategories(), HttpStatus.OK);
    }

    /**
     * @see Controller to get products by category name
     * @param productCategory
     * @return List<JSONObject>
     */
    @GetMapping(value = "/{productCategory}/products")
    public ResponseEntity<CompletableFuture<List<JSONObject>>>
            getProductsByCategory(@PathVariable @NotNull final ProductCategory productCategory) {
        LOGGER.info("get all products by the product category given");
        return new ResponseEntity<>(this.productService.getProductsByCategory(productCategory), HttpStatus.OK);
    }

    /**
     * @see assign a product to a store
     * @param zoneName
     * @param clusterName
     * @param storeName
     * @param productList
     * @return Boolean
     */
    @PutMapping(value = "/{zoneName}/{clusterName}/{storeName}/product")
    public ResponseEntity<CompletableFuture<Boolean>> addProductsToStore(@PathVariable final String zoneName,
            @PathVariable final String clusterName,
            @PathVariable final String storeName,
            @RequestBody final List<JSONObject> productList) {
        return new ResponseEntity<>(this.productService.addProductsToStore(zoneName, clusterName, storeName, productList),
                HttpStatus.OK);
    }

    /**
     * @see add a promotion to a product at zone or cluster level
     * @param productName
     * @param levelOption
     * @param addPromotion
     * @return JSONObject
     */
    @PutMapping(value = "/product/promotion/{productName}/{levelOption}")
    public ResponseEntity<CompletableFuture<JSONObject>> addPromotionToProduct(@PathVariable @NotNull final String productName,
            @PathVariable @NotNull final String levelOption,
            @RequestBody @Valid final AddPromotion addPromotion) {
        final Promotion promotion = new Promotion(addPromotion.getPromotionPercentage(), addPromotion.getStartDate(),
                addPromotion.getEndDate(), addPromotion.getAppliedDate(), addPromotion.getAddedBy());
        if (levelOption.equals(Constants.getZoneString())) {
            LOGGER.info("add promotion to the product in zone level");
            return new ResponseEntity<>(
                    this.productService.addPromotionInZoneToProduct(productName, addPromotion.getZoneName(), promotion),
                    HttpStatus.OK);
        } else if (levelOption.equals(Constants.getClusterString())) {
            LOGGER.info("add promotion to the product in cluster level");
            return new ResponseEntity<>(this.productService.addPromotionInClusterToProduct(productName,
                    addPromotion.getZoneName(), addPromotion.getClusterName(), promotion), HttpStatus.OK);
        }
        return null;
    }

    /**
     * @see get all products having the promotions in the given date range
     * @param levelOption
     * @param filter
     * @return List<JSONObject>
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @GetMapping(value = "/products/{levelOption}/data")
    public CompletableFuture<List<JSONObject>> getProductsByDateRange(@PathVariable @NotNull final String levelOption,
            @RequestParam @NotNull final String filter) throws JsonMappingException, JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final FilterOnDate filterObject = mapper.readValue(filter, FilterOnDate.class);
        if (levelOption.equals(Constants.getZoneString())) {
            if (null == filterObject.getCurrentDate()) {
                LOGGER.info("get products in the date range, zone level");
                return this.productService.getAllProductsIntheDateRangeInZone(filterObject.getStartDate(),
                        filterObject.getEndDate());
            } else {
                LOGGER.info("get products in the future date range, zone level");
                return this.productService.getAllProductsIntheDateRangeFutureInZone(filterObject.getStartDate(),
                        filterObject.getEndDate(), filterObject.getCurrentDate());
            }
        } else if (levelOption.equals(Constants.getClusterString())) {
            if (null == filterObject.getCurrentDate()) {
                LOGGER.info("get products in the date range, cluster level");
                return this.productService.getAllProductsIntheDateRangeInCluster(filterObject.getStartDate(),
                        filterObject.getEndDate());
            } else {
                LOGGER.info("get products in the future date range, cluster level");
                return this.productService.getAllProductsIntheDateRangeFutureInCluster(filterObject.getStartDate(),
                        filterObject.getEndDate(), filterObject.getCurrentDate());
            }
        }
        return CompletableFuture.completedFuture(new ArrayList<>());
    }

    /**
     * @see assign a product to a zone
     * @param productName
     * @param zoneName
     * @param assignProduct
     * @return Boolean
     */
    @PutMapping(value = "/assign/{productName}")
    @HystrixCommand(fallbackMethod = "assignProductPriceInZoneFallBack")
    public ResponseEntity<CompletableFuture<Boolean>> assignProductPriceInZone(@PathVariable @NotNull final String productName,
            @RequestBody @NotNull final AssignProduct assignProduct) {
        LOGGER.info("assigning product to zone");
        return new ResponseEntity<>(this.productService.assignProductToZoneAndCluster(productName, assignProduct),
                HttpStatus.OK);
    }

    /**
     * @see fallback method for assigning product in zone
     * @param productName
     * @param zoneName
     * @param assignProduct
     * @param throwable
     * @return Boolean
     * @throws Exception
     */
    public ResponseEntity<CompletableFuture<Boolean>> assignProductPriceInZoneFallBack(final String productName,
            final AssignProduct assignProduct,
            final Throwable throwable) throws Exception {
        if (null != throwable.getMessage()) {
            this.productService.throwFallBackErrorMessage(throwable.getMessage());
        }
        LOGGER.info("Executing FallBack for assigning ProductPrice In Zone");
        LOGGER.error("RestTemplateConnectionError: Service Not Available");
        return new ResponseEntity<>(CompletableFuture.completedFuture(false), HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * @see assign a product to a cluster
     * @param zoneName
     * @param clusterName
     * @param productName
     * @param productDetails
     * @return Boolean
     */
    @PutMapping(value = "/{productName}/{zoneName}/{clusterName}/products")
    @HystrixCommand(fallbackMethod = "assignProductPriceInClusterFallBack")
    public ResponseEntity<CompletableFuture<Boolean>> assignProductPriceInCluster(@PathVariable @NotNull final String zoneName,
            @PathVariable @NotNull final String clusterName,
            @PathVariable @NotNull final String productName,
            @RequestBody @NotNull final JSONObject productDetails) {
        LOGGER.info("assigning product to cluster");
        return new ResponseEntity<>(
                this.productService.assignProductToCluster(productName, zoneName, clusterName, productDetails), HttpStatus.OK);
    }

    /**
     * @see fallback method for assigning product in cluster
     * @param zoneName
     * @param clusterName
     * @param productName
     * @param productDetails
     * @param throwable
     * @return Boolean
     * @throws Exception
     */
    public ResponseEntity<CompletableFuture<Boolean>> assignProductPriceInClusterFallBack(final String zoneName,
            final String clusterName,
            final String productName,
            final JSONObject productDetails,
            final Throwable throwable) throws Exception {
        if (null != throwable.getMessage()) {
            this.productService.throwFallBackErrorMessage(throwable.getMessage());
        }
        LOGGER.info("Executing FallBack for assigning ProductPrice In Cluster");
        LOGGER.error("RestTemplateConnectionError: Service Not Available");
        return new ResponseEntity<>(CompletableFuture.completedFuture(false), HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * @see Controller to return price from zone level
     * @param productName
     * @param zoneName
     * @return Boolean
     */
    @GetMapping(value = "/product-details/{productName}/{zoneName}")
    public ResponseEntity<CompletableFuture<Boolean>> checkIfZonePriceExists(@PathVariable @NotNull final String productName,
            @PathVariable @NotNull final String zoneName) {
        LOGGER.info("returns price from zone if it exists");
        return new ResponseEntity<>(this.productService.checkIfZonePriceExists(productName, zoneName), HttpStatus.OK);
    }

    /**
     * @see Cancel all Promotions in Zone or a cluster for a Product
     * @param productName
     * @param levelOption
     * @param details
     * @return Boolean
     */
    @PutMapping(value = "/product/promotion/cancel/{productName}/{levelOption}")
    public ResponseEntity<CompletableFuture<Boolean>> cancelPromotion(@PathVariable final String productName,
            @PathVariable final String levelOption,
            @RequestBody @Valid final CancelWithdrawPromotion details) {
        CompletableFuture<Boolean> result = null;
        if (levelOption.equals(Constants.getZoneString())) {
            LOGGER.info("cancelling all the promotions applied to the product in a zone");
            result = this.productService.cancelPromotionInZone(productName, details.getZoneName(), details.getDate());
        } else if (levelOption.equals(Constants.getClusterString())) {
            LOGGER.info("cancelling all the promotions applied to the product in a cluster");
            result = this.productService.cancelPromotionInCluster(productName, details.getZoneName(), details.getClusterName(),
                    details.getDate());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * @see with draw a particular Promotion in Zone or a cluster for a Product
     * @param productName
     * @param levelOption
     * @param promotionId
     * @param details
     * @return Boolean
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PutMapping(value = "/product/promotion/withdraw/{productName}/{levelOption}/{promotionId}")
    public ResponseEntity<CompletableFuture<Boolean>> withdrawNotEffectivePromotions(
            @PathVariable @NotNull final String productName,
            @PathVariable final String levelOption,
            @PathVariable @NotNull final Integer promotionId,
            @RequestBody @Valid final CancelWithdrawPromotion details) throws InterruptedException, ExecutionException {
        Boolean result = false;
        if (levelOption.equals(Constants.getZoneString())) {
            LOGGER.info("withdraw a particular promotion in a zone");
            result = this.productService
                    .withdrawPromotionInZone(productName, details.getZoneName(), promotionId, details.getDate()).get();
        } else if (levelOption.equals(Constants.getClusterString())) {
            LOGGER.info("withdraw a particular promotion in a cluster");
            result = this.productService.withdrawPromotionInCluster(productName, details.getZoneName(),
                    details.getClusterName(), promotionId, details.getDate()).get();
        }
        return new ResponseEntity<>(CompletableFuture.completedFuture(result), HttpStatus.OK);
    }

    /**
     * @see get all promotions in a Zone
     * @param productName
     * @param zoneName
     * @return List<Promotion>
     */
    @GetMapping(value = "/product/promotions/{productName}/{zoneName}")
    public ResponseEntity<CompletableFuture<List<Promotion>>> getPromotionsInZone(@PathVariable final String productName,
            @PathVariable final String zoneName) {
        return new ResponseEntity<>(this.productService.getAllPromotionsInZone(productName, zoneName), HttpStatus.OK);
    }

    /**
     * @see get all promotions in a cluster
     * @param productName
     * @param zoneName
     * @param clusterName
     * @return List<Promotion>
     */
    @GetMapping(value = "/product/promotions/{productName}/{zoneName}/{clusterName}")
    public ResponseEntity<CompletableFuture<List<Promotion>>> getPromotionsInCluster(@PathVariable final String productName,
            @PathVariable final String zoneName,
            @PathVariable final String clusterName) {
        return new ResponseEntity<>(this.productService.getAllPromotionsInCluster(productName, zoneName, clusterName),
                HttpStatus.OK);
    }

    /**
     * @see add effective price for given product
     * @param productName
     * @return Double
     */
    @PutMapping(value = "/product/effectivePrice/{productName}")
    public ResponseEntity<CompletableFuture<Double>> addEffectivePrice(@PathVariable final String productName,
            @RequestBody final EffectivePrice effectivePrice) {
        return new ResponseEntity<>(this.productService.setEffectivePrice(productName, effectivePrice.getStartDate(),
                effectivePrice.getEndDate(), effectivePrice.getEffectivePercentage()), HttpStatus.OK);
    }

    /**
     * @see gets all products whose effective prices are not in effect
     * @return List<JSONObject>
     */
    @GetMapping(value = "/product/effectivePriceNotInEffect")
    public ResponseEntity<CompletableFuture<List<JSONObject>>> effectivePriceNotInEffect() {
        return new ResponseEntity<>(this.productService.getAllProductsEffectivePriceNotInEffect(), HttpStatus.OK);
    }

    /**
     * @see gets all products whose effective prices are in effect
     * @return List<JSONObject>
     */
    @GetMapping(value = "/product/effectivePriceInEffect")
    public ResponseEntity<CompletableFuture<List<JSONObject>>> effectivePriceInEffect() {
        return new ResponseEntity<>(this.productService.getAllProductsEffectivePriceInEffect(), HttpStatus.OK);
    }

    /**
     * @see cancel effective price of given product
     * @param productName
     * @return Boolean
     */
    @PutMapping(value = "/product/effectivePrice/cancel/{productName}")
    public ResponseEntity<CompletableFuture<Boolean>> cancelEffectivePrice(@PathVariable final String productName) {
        CompletableFuture<Boolean> result = null;

        LOGGER.info("cancelling/witdraw  the effective price applied to the product");
        result = this.productService.cancelWithdrawEffectivePrice(productName);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    /**
     * @see update qty,base price of product
     * @param productName
     * @param productDetails(Quantity,base price)
     * @return Void
     */
    @PutMapping(value = "/{productName}/product")
    public ResponseEntity<Void> updateProductByVendor(@PathVariable final String productName,
            @RequestBody final JSONObject productDetails) {
        LOGGER.info("Updating product details(qty,base price)byvendor");
        this.productService.updateProductByVendor(productName, productDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @see check if promotion is running
     * @param productName
     * @return Boolean
     */
    @GetMapping(value = "/{productName}/product/promotion")
    public ResponseEntity<CompletableFuture<Boolean>> checkPromotion(@PathVariable final String productName) {
        CompletableFuture<Boolean> result = null;
        LOGGER.info("checking if a promotion for product is running or not");
        result = this.productService.checkPromotion(productName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * @see get the minimum base price that can be put by vendor
     * @param productName
     * @return Double
     */
    @GetMapping(value = "/{productName}/product/baseprice")
    public ResponseEntity<CompletableFuture<Double>> getMinimumBasePrice(@PathVariable final String productName) {
        return new ResponseEntity<>(this.productService.getMinimumBasePrice(productName), HttpStatus.OK);
    }

    /**
     * @see to update product, sell at fixed price
     * @param productName
     * @return Boolean
     */
    @PutMapping(value = "/{productName}/product/makeFixed")
    public ResponseEntity<CompletableFuture<Boolean>> sellAtFixedPrice(@PathVariable final String productName) {
        return new ResponseEntity<>(this.productService.sellAtFixedPrice(productName), HttpStatus.OK);
    }

    /**
     * @see to update product, cancel fixed price
     * @param productName
     * @return Boolean
     */
    @PutMapping(value = "/{productName}/product/cancelFixed")
    public ResponseEntity<CompletableFuture<Boolean>> cancelFixedPrice(@PathVariable final String productName) {
        return new ResponseEntity<>(this.productService.cancelFixedPrice(productName), HttpStatus.OK);
    }

    /**
     * @see get list of non-alcoholic product names
     * @param
     * @return List<String>
     */
    @GetMapping(value = "/product-list")
    public ResponseEntity<CompletableFuture<List<String>>> getNonAlcoholProductNameList() {
        return new ResponseEntity<>(this.productService.getNonAlcoholProductNameList(), HttpStatus.OK);
    }

    /**
     * @see Controller to get a Product(specific fields) by the Product Name
     * @param productName
     * @return JSONObject
     */
    @GetMapping(value = "/product-details/{productName}")
    public ResponseEntity<CompletableFuture<JSONObject>>
            getProductDetailsByName(@PathVariable @NotNull final String productName) {
        LOGGER.info("get product details by product name");
        return new ResponseEntity<>(this.productService.getProductDetailsByName(productName), HttpStatus.OK);
    }

    @GetMapping(value = "/dashboard")
    public ResponseEntity<CompletableFuture<JSONObject>> getDashBoard() {
        return new ResponseEntity<>(this.productService.getDetails(), HttpStatus.OK);
    }

    /**
     * @see It returns specific details of product along with the boolean value which tells if any promotion is running, and
     *      another field that returns "minimum base price" that vendor can set
     * @param productName
     * @return JSONObject
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping(value = "/{productName}/product/vendor")
    public ResponseEntity<CompletableFuture<JSONObject>> getProductsDetailForVendor(@PathVariable final String productName)
            throws InterruptedException, ExecutionException {
        final JSONObject productDetails = this.productService.getProductDetailsForVendor(productName).get();
        productDetails.appendField(Constants.getIsPromotionRunning(), this.productService.checkPromotion(productName).get())
                .appendField(Constants.getMinimumBasePrice(), this.productService.getMinimumBasePrice(productName).get());
        return new ResponseEntity<>(CompletableFuture.completedFuture(productDetails), HttpStatus.OK);
    }

    /**
     * @see get price at zone level if assigned
     * @param productName
     * @param zoneName
     * @return Double
     */
    @GetMapping(value = "/product/price/{productName}/{zoneName}")
    public ResponseEntity<CompletableFuture<Double>> getPriceAtZoneLevel(@PathVariable final String productName,
            @PathVariable final String zoneName) {
        return new ResponseEntity<>(this.productService.getPriceAtZoneLevel(productName, zoneName), HttpStatus.OK);
    }

    /**
     * @see get price at cluster level if assigned
     * @param productName
     * @param zoneName
     * @param clusterName
     * @return Double
     */
    @GetMapping(value = "/product/price/{productName}/{zoneName}/{clusterName}")
    public ResponseEntity<CompletableFuture<Double>> getPriceAtZoneLevel(@PathVariable final String productName,
            @PathVariable final String zoneName,
            @PathVariable final String clusterName) {
        return new ResponseEntity<>(this.productService.getPriceAtClusterLevel(productName, zoneName, clusterName),
                HttpStatus.OK);
    }

    /**
     * @see get list of products under a vendor
     * @param vendorName
     * @return List<String>
     */
    @GetMapping(value = "/product-list/{vendorName}")
    public ResponseEntity<CompletableFuture<List<String>>> getProductNamesOfVendor(@PathVariable final String vendorName) {
        return new ResponseEntity<>(this.productService.getProductNamesOfVendor(vendorName), HttpStatus.OK);
    }

    /**
     * @see Controller to Get status of promotion in a Zone
     * @param productName
     * @param zoneName
     * @return List<JSONObject>
     */
    @PostMapping(value = "/product/status/{productName}/{zoneName}")
    public ResponseEntity<CompletableFuture<JSONObject>> promotionStatusInZone(@PathVariable final String productName,
            @PathVariable final String zoneName,
            @RequestBody @NotNull final JSONObject checkDate) {
        return new ResponseEntity<>(this.productService.promotionStatusInZone(productName, zoneName, checkDate), HttpStatus.OK);
    }

    /**
     * @see Controller to Get status of promotion in a Cluster
     * @param productName
     * @param zoneName
     * @param clusterName
     * @return List<JSONObject>
     */
    @PostMapping(value = "/product/status/{productName}/{zoneName}/{clusterName}")
    public ResponseEntity<CompletableFuture<JSONObject>> promotionStatusInCluster(@PathVariable final String productName,
            @PathVariable final String zoneName,
            @PathVariable final String clusterName,
            @RequestBody @NotNull final JSONObject checkDate) {
        return new ResponseEntity<>(this.productService.promotionStatusInCluster(productName, zoneName, clusterName, checkDate),
                HttpStatus.OK);
    }

    /**
     * @see to get all promotions in zone/cluster for the productName
     * @param productName
     * @return JSONObject
     */
    @GetMapping(value = "/product/promotion/{productName}")
    public ResponseEntity<CompletableFuture<JSONObject>> getPromotionAtZoneCluster(@PathVariable final String productName) {
        return new ResponseEntity<>(this.productService.getPromotionAtZoneCluster(productName), HttpStatus.OK);
    }

    /**
     * @see to approve or reject the promotion
     * @param productName
     * @param promotionId
     * @param status
     */
    @PutMapping(value = "/product/promotion/{productName}/{promotionId}/{status}")
    public ResponseEntity<CompletableFuture<Boolean>> approveProduct(@PathVariable final String productName,
            @PathVariable final Integer promotionId,
            @PathVariable final PromotionStatus status) {
        return new ResponseEntity<>(this.productService.approveProduct(productName, promotionId, status), HttpStatus.OK);
    }

    /**
     * @see get list of products under a vendor
     * @param vendorName
     * @QueryParam pageNo
     * @QueryParam rowsPerPage
     * @return List<Product>
     */
    @GetMapping(value = "/products/vendor/{vendorName}")
    public ResponseEntity<CompletableFuture<List<JSONObject>>> getProductsOfVendor(@PathVariable final String vendorName,
            @QueryParam(value = "pageNo") final Integer pageNo,
            @QueryParam(value = "rowsPerPage") final Integer rowsPerPage) {
        return new ResponseEntity<>(this.productService.getProductsOfVendor(vendorName, pageNo, rowsPerPage), HttpStatus.OK);
    }

    /**
     * @see get count of products under a vendor
     * @param vendorName
     * @return Integer
     */
    @GetMapping(value = "/products/vendor/{vendorName}/count")
    public ResponseEntity<CompletableFuture<Integer>> countOfVendorProducts(@PathVariable final String vendorName) {
        return new ResponseEntity<>(this.productService.countOfVendorProducts(vendorName), HttpStatus.OK);
    }

    /**
     * @see update quantity to zone or cluster respectively
     * @param levelOption
     * @param productName
     * @param productDetails
     * @return
     */
    @PutMapping(value = "/products/update/{levelOption}/{productName}")
    public ResponseEntity<CompletableFuture<Boolean>> updateQuantity(@PathVariable final String levelOption,
            @PathVariable final String productName,
            @RequestBody final JSONObject productDetails) {
        if (levelOption.contentEquals(Constants.getZoneString())) {
            return new ResponseEntity<>(this.productService.updateQuantityInZone(productName, productDetails), HttpStatus.OK);

        } else if (levelOption.contentEquals(Constants.getClusterString())) {
            return new ResponseEntity<>(this.productService.updateQuantityInCluster(productName, productDetails),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(CompletableFuture.completedFuture(false), HttpStatus.BAD_REQUEST);
    }

    /**
     * @see get quantityAssigned of a product in a zone
     * @param productName
     * @param zoneName
     * @return quantityAssigned
     */
    @GetMapping(value = "/products/{productName}/{zoneName}/quantity")
    public ResponseEntity<CompletableFuture<Integer>> getQuantityInZone(@PathVariable final String productName,
            @PathVariable final String zoneName) {
        return new ResponseEntity<>(this.productService.getQuantityInZone(productName, zoneName), HttpStatus.OK);
    }

    /**
     * @see get quantityAssigned of a product in a cluster
     * @param productName
     * @param zoneName
     * @param clusterName
     * @return quantityAssigned
     */
    @GetMapping(value = "/products/{productName}/{zoneName}/{clusterName}/quantity")
    public ResponseEntity<CompletableFuture<Integer>> getQuantityInZone(@PathVariable final String productName,
            @PathVariable final String zoneName,
            @PathVariable final String clusterName) {
        return new ResponseEntity<>(this.productService.getQuantityInCluster(productName, zoneName, clusterName),
                HttpStatus.OK);
    }
}
