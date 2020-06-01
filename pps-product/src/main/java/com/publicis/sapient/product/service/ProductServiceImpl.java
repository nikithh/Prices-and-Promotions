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

package com.publicis.sapient.product.service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.publicis.sapient.product.exception.ActivePromotionInDateRange;
import com.publicis.sapient.product.exception.EffectivePriceAlreadyDefined;
import com.publicis.sapient.product.exception.PageNumberOutofBounds;
import com.publicis.sapient.product.exception.PriceAtClusterLevelNotDefined;
import com.publicis.sapient.product.exception.PriceAtZoneLevelNotDefined;
import com.publicis.sapient.product.exception.ProductAlreadyAssociatedToCluster;
import com.publicis.sapient.product.exception.ProductAlreadyAssociatedToZone;
import com.publicis.sapient.product.exception.ProductAlreadyExists;
import com.publicis.sapient.product.exception.ProductDoesntExist;
import com.publicis.sapient.product.exception.ProductPriceBelowMinimumSellingPrice;
import com.publicis.sapient.product.exception.ProductPriceIsAlreadyCancelled;
import com.publicis.sapient.product.exception.ProductPriceIsAlreadyFixed;
import com.publicis.sapient.product.exception.PromotionDoesntExists;
import com.publicis.sapient.product.exception.QuantityInsufficient;
import com.publicis.sapient.product.exception.WithdrawDateAfterAppliedDate;
import com.publicis.sapient.product.handler.Constants;
import com.publicis.sapient.product.handler.ProductHandler;
import com.publicis.sapient.product.model.AssignProduct;
import com.publicis.sapient.product.model.Cluster;
import com.publicis.sapient.product.model.EffectivePrice;
import com.publicis.sapient.product.model.Product;
import com.publicis.sapient.product.model.ProductCategory;
import com.publicis.sapient.product.model.Promotion;
import com.publicis.sapient.product.model.PromotionStatus;
import com.publicis.sapient.product.model.Store;
import com.publicis.sapient.product.repository.ProductRepository;

import net.bytebuddy.asm.Advice.Return;
import net.minidev.json.JSONObject;

@EnableScheduling
@Component
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private ProductHandler productHandler;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    /**
     * @see Method scheduled to be called everyday at 12:01AM to check for effectivePrice(Scheduled methods dont accept any
     *      argument, or have a return type)
     * @param
     * @return void
     */
    @Async("threadPoolTaskExecutor")
    @Scheduled(cron = "0 1 0 * * ?") // Scheduled to execute at 00:01 Everyday.
    public void checkEffectivePriceCron() {
        final Date currentDate = new Date();
        Double temp;
        final List<Product> products = this.productRepository.findAll();
        for (final Product p : products) {
            if ((null != p.getEffectivePriceObj())) {
                if ((p.getIsPriceFixed().equals(Boolean.FALSE)) && (p.getEffectivePriceObj().getFlag().equals(Boolean.FALSE))
                        && ((p.getEffectivePriceObj().getStartDate()).before(currentDate)
                                || (p.getEffectivePriceObj().getStartDate()).equals(currentDate))
                        && (p.getEffectivePriceObj().getEndDate().after(currentDate))) {
                    // temp variable is used to swap the value of base price and effective price
                    temp = p.getProductBasePrice();
                    p.setProductBasePrice(p.getEffectivePriceObj().getEffectivePrice());
                    final EffectivePrice effectivePriceObj = p.getEffectivePriceObj();
                    effectivePriceObj.setEffectivePrice(temp);
                    p.setEffectivePriceObj(effectivePriceObj);
                    // Flag is set to indicate that the swap has occured
                    p.getEffectivePriceObj().setFlag(true);
                } else if ((p.getIsPriceFixed().equals(Boolean.TRUE))
                        && (p.getEffectivePriceObj().getFlag().equals(Boolean.TRUE))) {
                    temp = p.getProductBasePrice();
                    p.setProductBasePrice(p.getEffectivePriceObj().getEffectivePrice());
                    p.getEffectivePriceObj().setEffectivePrice(temp);
                    p.getEffectivePriceObj().setFlag(false);
                } else if ((p.getEffectivePriceObj().getFlag().equals(Boolean.TRUE))
                        && p.getEffectivePriceObj().getEndDate().before(currentDate)) {
                    p.setProductBasePrice(p.getEffectivePriceObj().getEffectivePrice());
                    p.setEffectivePriceObj(null);
                } else if ((p.getEffectivePriceObj().getFlag().equals(Boolean.FALSE))
                        && p.getEffectivePriceObj().getEndDate().before(currentDate)) {
                    p.setEffectivePriceObj(null);
                }
                // Due to change in base price, update of promotion selling price is necessary
                this.productRepository.save(this.productHandler.updatePromotionSellingPrices(p));
            }
        }
    }

    /**
     * @see Service to Insert a Product
     * @param product
     * @return Product
     * @throws IOException
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<Product> insertProduct(final Product product,
            final MultipartFile[] files) {
        final List<Product> products = this.productRepository.findAll();
        final IntSummaryStatistics maxIdStats = products.stream().collect(Collectors.summarizingInt(Product::getProductId));
        final Integer maxId = maxIdStats.getMax();
        final Boolean productExists = products.stream().anyMatch(p -> p.getProductName().equals(product.getProductName()));
        if (Boolean.TRUE.equals(productExists)) {
            throw new ProductAlreadyExists("Product with name " + product.getProductName() + " already exists");
        }
        this.productHandler.setImagePath(product, files);
        return CompletableFuture.completedFuture(
                this.productRepository.insert(this.productHandler.setParametersToProduct(product, products, maxId)));
    }

    /**
     * @see Service to Get a Product by Name
     * @param productName
     * @return Product
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "get-product", key = "#productName", unless = "#result==null")
    public CompletableFuture<Product> getProductByName(final String productName) {
        return CompletableFuture.completedFuture(this.productRepository.getProductByName(productName));
    }

    /**
     * @see Service to Get all Products
     * @return List<Product>
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "products")
    public CompletableFuture<List<Product>> getAllProducts() {
        return CompletableFuture.completedFuture(this.productRepository.findAll());
    }

    /**
     * @see Service to Get all Product Names
     * @return List<String>
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "product-names")
    public CompletableFuture<List<String>> getAllProductNames() {
        final Function<Product, String> nameFunction = (final Product product) -> product.getProductName();
        final List<Product> products = this.productRepository.findAll();
        final List<String> productNames = new ArrayList<>();
        products.stream().filter(product -> productNames.add(nameFunction.apply(product))).collect(Collectors.toList());
        return CompletableFuture.completedFuture(productNames);
    }

    /**
     * @see Service to Get all Categories
     * @return List<String>
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "get-product-categories", unless = "#result==null")
    public CompletableFuture<List<String>> getAllCategories() {
        final Function<ProductCategory, String> convertToString =
                (final ProductCategory productCategory) -> productCategory.toString();

        final List<String> finalList = new ArrayList<>();
        final List<ProductCategory> categories = Arrays.asList(ProductCategory.values());
        categories.stream().filter(pc -> finalList.add(convertToString.apply(pc))).collect(Collectors.toList());
        return CompletableFuture.completedFuture(finalList);
    }

    /**
     * @see Service to Get all Zone Names for a Product
     * @param productName
     * @return List<String>
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "get-zonenames", key = "#productName", unless = "#result==null")
    public CompletableFuture<List<String>> getAllZoneNamesInProduct(final String productName) {
        final Function<AssignProduct, String> nameFunction = (final AssignProduct assignProduct) -> assignProduct.getZoneName();
        final Product product = this.productRepository.getProductByName(productName);
        final List<String> zoneNames = new ArrayList<>();
        if (null != product) {
            final List<AssignProduct> assignProducts = product.getAssignProduct();
            assignProducts.stream().filter(assignProduct -> zoneNames.add(nameFunction.apply(assignProduct)))
                    .collect(Collectors.toList());
        }
        return CompletableFuture.completedFuture(zoneNames);
    }

    /**
     * @see Service to Get all Cluster Names in a Zone
     * @param productName
     * @param zoneName
     * @return List<String>
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "get-clusternames", key = "{#productName, #zoneName}", unless = "#result==null")
    public CompletableFuture<List<String>> getAllClusterNamesInZone(final String productName,
            final String zoneName) {
        final Product product = this.productRepository.getProductByName(productName);
        final List<AssignProduct> assignProducts = product.getAssignProduct();
        final Optional<AssignProduct> assignProduct =
                assignProducts.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny();
        if (assignProduct.isPresent()) {
            final Function<Cluster, String> nameFunction = (final Cluster cluster) -> cluster.getClusterName();
            final List<Cluster> clusters = assignProduct.get().getCluster();
            final List<String> clusterNames = new ArrayList<>();
            clusters.stream().filter(c -> clusterNames.add(nameFunction.apply(c))).collect(Collectors.toList());
            return CompletableFuture.completedFuture(clusterNames);
        }
        return CompletableFuture.completedFuture(new ArrayList<>());
    }

    /**
     * @see Service to Get all Products by Category
     * @param productCategory
     * @return List<JSONObject>
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "get-products", key = "#productCategory", unless = "#result==null")
    public CompletableFuture<List<JSONObject>> getProductsByCategory(final ProductCategory productCategory) {
        final List<Product> products = this.productRepository.getProductsByCategory(productCategory);
        return CompletableFuture.completedFuture(products.stream()
                .map(p -> new JSONObject().appendField(Constants.getProductNameString(), p.getProductName())
                        .appendField(Constants.getProductBasePriceString(), p.getProductBasePrice())
                        .appendField(Constants.getCompanyNameString(), p.getCompanyName())
                        .appendField(Constants.getRemainingQuantityString(), p.getRemainingQuantity()))
                .collect(Collectors.toList()));

    }

    /**
     * @see get indexed product list based on vendor name
     * @param vendorName
     * @param pageNo
     * @param rowsPerPage
     * @return List<JSONObject>
     */
    @Async("threadPoolTaskExecutor")
	@Override
	public CompletableFuture<List<JSONObject>> getProductsOfVendor(final String vendorName, final Integer pageNo,
			final Integer rowsPerPage) {
		final List<Product> products = this.productRepository.getProductsByVendorName(vendorName);
		Collections.reverse(products);
		final Integer start = (pageNo - 1) * rowsPerPage;
		Integer end = (pageNo * rowsPerPage) - 1;
		List<Product> productSubList;
		if (start > products.size()) {
			throw new PageNumberOutofBounds("Page number out of bounds");
		} else if (end >= products.size()) {
			end = products.size() - 1;
		}
		productSubList = products.subList(start, end + 1);

		final Function<Product, JSONObject> generateJSON = (final Product product) -> new JSONObject()
				.appendField(Constants.getProductNameString(), product.getProductName())
				.appendField(Constants.getProductCategory(), product.getProductCategory())
				.appendField(Constants.getProductBasePriceString(), product.getProductBasePrice())
				.appendField(Constants.getRemainingQuantityString(), product.getRemainingQuantity())
				.appendField(Constants.getProductUom(), product.getUom())
				.appendField(Constants.getProductImage(), product.getProductImagePath());

		final List<JSONObject> productdetails = new ArrayList<>();
		productSubList.stream().filter(p -> productdetails.add(generateJSON.apply(p))).collect(Collectors.toList());
		return CompletableFuture.completedFuture(productdetails);
	}

    /**
     * @see get product count based on vendor name
     * @param vendorName
     * @return Integer
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<Integer> countOfVendorProducts(final String vendorName) {
        final List<Product> products = this.productRepository.getProductsByVendorName(vendorName);
        return CompletableFuture.completedFuture(products.size());
    }

    /**
     * @see Service to Delete a Product
     * @param productName
     * @return
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public void deleteProduct(final String productName) {
        this.productRepository.delete(this.productRepository.getProductByName(productName));
    }

    /**
     * @see Service to Update a Product
     * @param product
     * @return Product
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<Product> updateProduct(final Product product) {
        final Product product1 = this.productRepository.getProductByName(product.getProductName());
        product.setProductId(product1.getProductId());
        product.setProductName(product1.getProductName());
        return CompletableFuture.completedFuture(this.productRepository.save(product));
    }

    /**
     * @see Service to Check if Price for a Zone exists
     * @param productName
     * @param zoneName
     * @return Boolean
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<Boolean> checkIfZonePriceExists(final String productName,
            final String zoneName) {
        final Product finalProduct = this.productRepository.getProductByName(productName);
        final List<AssignProduct> zones = finalProduct.getAssignProduct();
        for (final AssignProduct p : zones) {
            if (p.getZoneName().contentEquals(zoneName) && (p.getPrice() != Constants.getIntegerZero())) { // zone story
                                                                                                           // needs
                // to
                // ensure this is set to
                return CompletableFuture.completedFuture(true);
            }
        }
        return CompletableFuture.completedFuture(false);
    }

    /**
     * @see Service to Assign a Product to a Zone and clusters
     * @param productName
     * @param productDetails
     * @return
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<Boolean> assignProductToZoneAndCluster(final String productName,
            final AssignProduct productDetails) {
        final int remainingQuantity = this.productRepository.getProductByName(productName).getRemainingQuantity();
        if (this.productHandler.quantityCheck(productDetails, remainingQuantity)) {
            final JSONObject zoneDetails =
                    new JSONObject().appendField(Constants.getQuantityAssignedString(), productDetails.getQuantityAssigned())
                            .appendField(Constants.getProfitPercentageString(), productDetails.getProfitPercentage());
            this.assignProductToZone(productName, productDetails.getZoneName(), zoneDetails);
            final List<Cluster> clusterDets = productDetails.getCluster();
            for (final Cluster cluster : clusterDets) {
                final JSONObject clusterDetails =
                        new JSONObject().appendField(Constants.getQuantityAssignedString(), cluster.getQuantityAssigned())
                                .appendField(Constants.getProfitPercentageString(), cluster.getProfitPercentage());
                this.assignProductToCluster(productName, productDetails.getZoneName(), cluster.getClusterName(),
                        clusterDetails);
            }
            return CompletableFuture.completedFuture(true);
        } else {
            throw new QuantityInsufficient(Constants.getQuantityInsufficientString());
        }
    }

    /**
     * @see Service to Assign a Product to a Zone
     * @param productName
     * @param zoneName
     * @param productDetails
     * @return
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<Boolean> assignProductToZone(final String productName,
            final String zoneName,
            final JSONObject productDetails) {
        // Fetch the product by name
        final Product finalProduct = this.productRepository.getProductByName(productName);
        // Retrieve the list of AssignProduct from Product object
        final List<AssignProduct> zones = finalProduct.getAssignProduct();
        // If sum of all quantities
        // Check if AssignProduct with a given ZoneName already exists
        AssignProduct finalZone = zones.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny().orElse(null);
        // Create a AssignProduct object with the given Zone name
        if (finalZone == null) {
            finalZone = this.productHandler.setParametersToAssignProduct(zoneName);
            zones.add(finalZone);
        } else {
            if (finalZone.getPrice() != Constants.getDoubleZero()) {
                throw new ProductAlreadyAssociatedToZone(Constants.getAlreadyAssignedZone());
            }
        }
        // Set profit for AssignProduct object
        final Double finalProfitPercentage =
                Double.parseDouble(productDetails.getAsString(Constants.getProfitPercentageString()));
        // Set Quantity assigned for AssignProduct object
        final Integer finalQuantityAssigned =
                Integer.parseInt(productDetails.getAsString(Constants.getQuantityAssignedString()));
        // Check if Remaining Quantity is greater than Quantity to be assigned
        this.productHandler.setQuantityAssignedToZone(finalQuantityAssigned, finalProduct, finalZone, finalProfitPercentage,
                false);
        // If the Product is Alcohol check for Minimum Selling Price
        this.productHandler.setMinimumSellingPrice(finalProduct, finalZone, finalProfitPercentage, zoneName);
        this.productRepository.save(finalProduct);
        return CompletableFuture.completedFuture(true);
    }

    /**
     * @see Service to Assign a Product to a Cluster
     * @param productName
     * @param zoneName
     * @param clusterName
     * @param productDetails
     * @return
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<Boolean> assignProductToCluster(final String productName,
            final String zoneName,
            final String clusterName,
            final JSONObject productDetails) {
        // Fetch the product by name
        final Product finalProduct = this.productRepository.getProductByName(productName);
        // Retrieve the list of AssignProduct from Product object
        final List<AssignProduct> zones = finalProduct.getAssignProduct();
        // Retrieve the AssignProduct object associated with the Zone name
        AssignProduct finalZone = zones.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny().orElse(null);
        if (null == finalZone) {
            // If finalZone is null, Create a new AssignProduct object
            finalZone = new AssignProduct(zoneName, Constants.getIntegerZero(), Constants.getDoubleZero(),
                    Constants.getDoubleZero(), Constants.getDoubleZero(), new ArrayList<>(), new ArrayList<>());
            // Minimum Price check if the product is alcohol
            if (finalProduct.getProductCategory().equals(ProductCategory.ALCOHOL_PROD)) {
                final Double finalMinimumSP = this.productHandler.calculateMinimumSP(zoneName, finalProduct);
                finalZone.setMinimumSP(finalMinimumSP);
            }
            zones.add(finalZone);
        }
        // Retrieve the list of clusters
        final List<Cluster> clusters = finalZone.getCluster();
        // Search for the Cluster object with a given Cluster name
        Cluster finalCluster = clusters.stream().filter(c -> c.getClusterName().equals(clusterName)).findAny().orElse(null);
        if (null == finalCluster) {
            // If null create a new Cluster object
            finalCluster = new Cluster(clusterName, Constants.getIntegerZero(), Constants.getDoubleZero(),
                    Constants.getDoubleZero(), new ArrayList<>(), new ArrayList<>());
            clusters.add(finalCluster);
        } else {
            throw new ProductAlreadyAssociatedToCluster(Constants.getAlreadyAssignedCluster());
        }
        final Double finalProfitPercentage =
                Double.parseDouble(productDetails.getAsString(Constants.getProfitPercentageString()));
        // Set quantity assigned
        final Integer finalQuantityAssigned =
                Integer.parseInt(productDetails.getAsString(Constants.getQuantityAssignedString()));
        // Calculate Cluster level price
        final Double clusterLevelPrice =
                this.productHandler.calculatePrice(finalProduct.getProductBasePrice(), finalProfitPercentage);
        // If the Product is alcohol, check for Minimum Selling Price
        if (finalProduct.getProductCategory().equals(ProductCategory.ALCOHOL_PROD)
                && (clusterLevelPrice < finalZone.getMinimumSP())) {
            throw new ProductPriceBelowMinimumSellingPrice(Constants.getBelowMspErrMsg());
        }
        // Check if Remaining Quantity is greater that FinalQuantity to be assigned
        this.productHandler.setQuantityAssignedToCluster(finalQuantityAssigned, finalProduct, finalCluster,
                finalProfitPercentage, false);
        finalCluster.setPrice(clusterLevelPrice);
        this.productRepository.save(finalProduct);
        return CompletableFuture.completedFuture(true);
    }

    /**
     * @see Service to Add a Promotion to a Zone for a Product
     * @param productName
     * @param zoneName
     * @param promotion
     * @return JSONObject
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<JSONObject> addPromotionInZoneToProduct(final String productName,
            final String zoneName,
            final Promotion promotion) {
        // Fetch the product by name
        final Product product = this.productRepository.getProductByName(productName);
        // Retrieve the list of AssignProduct from Product object
        final List<AssignProduct> assignProducts = product.getAssignProduct();
        // Retrieve the AssignProduct object associated with the Zone name
        final Optional<AssignProduct> assignProduct =
                assignProducts.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny();
        if (assignProduct.isPresent()) {
            // Increment number of promotion by 1
            product.setNoOfPromotions(product.getNoOfPromotions() + 1);
            final AssignProduct ap = assignProduct.get();
            // Promotions applied array
            final List<Promotion> promotions = ap.getPromotions();
            // Set selling price
            final Double promotionSellingPrice =
                    this.productHandler.calculatePrice(ap.getPrice(), promotion.getPromotionPercentage());
            // If the Product is alcohol check for Minimum Selling price
            if (((promotionSellingPrice > Constants.getDoubleZero())
                    && product.getProductCategory().equals(ProductCategory.BABY_PROD))
                    || this.productHandler.checkFailConditionForAlcohol(promotionSellingPrice, product, ap)
                            .equals(Boolean.TRUE)) {
                // Set PromotionsApplied array to AssignProduct object
                this.productHandler.checkAndSetPromotions(promotion, promotions, product, promotionSellingPrice);
                this.productRepository.save(product);
                return CompletableFuture.completedFuture(
                        new JSONObject().appendField(Constants.getStatusString(), Constants.getAddedSuccessfullyString())
                                .appendField(Constants.getSellingPriceString(), promotionSellingPrice));
            } else {
                this.productHandler.setParametersToPromotions(promotions, promotion, product, Constants.getDoubleZero(), false,
                        promotion.getAddedBy(), PromotionStatus.APPROVED);
                this.productRepository.save(product);
            }
        }
        return CompletableFuture
                .completedFuture(new JSONObject().appendField(Constants.getStatusString(), Constants.getAddedFailedString())
                        .appendField(Constants.getSellingPriceString(), Constants.getDoubleZero()));
    }

    /**
     * @see Service to Add a Promotion to a Cluster for a Product
     * @param productName
     * @param zoneName
     * @param clusterName
     * @param promotion
     * @return JSONObject
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<JSONObject> addPromotionInClusterToProduct(final String productName,
            final String zoneName,
            final String clusterName,
            final Promotion promotion) {
        // Fetch the product by name
        final Product product = this.productRepository.getProductByName(productName);
        // Retrieve the list of AssignProduct from Product object
        final List<AssignProduct> assignProducts = product.getAssignProduct();
        // Retrieve the AssignProduct object associated with the Zone name
        final Optional<AssignProduct> assignProduct =
                assignProducts.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny();
        if (assignProduct.isPresent()) {
            // Assignproduct object
            final AssignProduct ap = assignProduct.get();
            // Retrieve cluster array from AssignProduct object
            final List<Cluster> clusters = ap.getCluster();
            // Retrieve the cluster object with given cluster name
            final Optional<Cluster> cluster = clusters.stream().filter(c -> c.getClusterName().equals(clusterName)).findAny();
            if (cluster.isPresent()) {
                // Increment number of promotion by 1
                product.setNoOfPromotions(product.getNoOfPromotions() + 1);
                final Cluster clu = cluster.get();
                // PromotionApplied array
                final List<Promotion> promotions = clu.getPromotions();
                // Set selling price
                final Double promotionSellingPrice =
                        this.productHandler.calculatePrice(clu.getPrice(), promotion.getPromotionPercentage());
                // If the Product is alcohol check for Minimum Selling price
                if ((this.productHandler.checkFailConditionForBaby(product, promotionSellingPrice) || this.productHandler
                        .checkFailConditionForAlcohol(promotionSellingPrice, product, ap).equals(Boolean.TRUE))) {
                    this.productHandler.checkAndSetPromotions(promotion, promotions, product, promotionSellingPrice);
                    this.productRepository.save(product);
                    return CompletableFuture.completedFuture(
                            new JSONObject().appendField(Constants.getStatusString(), Constants.getAddedSuccessfullyString())
                                    .appendField(Constants.getSellingPriceString(), promotionSellingPrice));
                } else {
                    this.productHandler.setParametersToPromotions(promotions, promotion, product, Constants.getDoubleZero(),
                            false, promotion.getAddedBy(), PromotionStatus.APPROVED);
                    this.productRepository.save(product);
                }
            }
        }
        return CompletableFuture
                .completedFuture(new JSONObject().appendField(Constants.getStatusString(), Constants.getAddedFailedString())
                        .appendField(Constants.getSellingPriceString(), Constants.getDoubleZero()));
    }

    /**
     * @see Service to get promotion status in zone on a particular date
     * @param productName
     * @param zoneName
     * @param checkDate
     * @return JSONObject
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<JSONObject> promotionStatusInZone(final String productName,
            final String zoneName,
            final JSONObject checkDate) {
        final boolean dflag = true;
        final boolean pflag = true;
        final Date appliedDate = Date.from(Instant.parse(checkDate.getAsString(Constants.getAppliedDateString())));
        final Product product = this.productRepository.getProductByName(productName);
        JSONObject jsonObj =
                new JSONObject().appendField("promotionAlreadyApplied", 0).appendField("promotionHasBeenAppliedLast72hours", 0);
        if (product.getNoOfPromotions() == 0) {
            return CompletableFuture.completedFuture(jsonObj);
        }
        final List<AssignProduct> assignProducts = product.getAssignProduct();
        // Get Zone with name zoneName
        final AssignProduct zone = assignProducts.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny().get();
        if (null != zone) {
            final List<Promotion> promotionsZone = zone.getPromotions();
            jsonObj = this.productHandler.promotionStatus(promotionsZone, pflag, dflag, appliedDate, jsonObj);
        }
        return CompletableFuture.completedFuture(jsonObj);
    }

    /**
     * @see Service to get promotion status in cluster on a particular date
     * @param productName
     * @param zoneName
     * @param clusterName
     * @param checkDate
     * @return JSONObject
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<JSONObject> promotionStatusInCluster(final String productName,
            final String zoneName,
            final String clusterName,
            final JSONObject checkDate) {
        final boolean dflag = true;
        final boolean pflag = true;
        final Date appliedDate = Date.from(Instant.parse(checkDate.getAsString(Constants.getAppliedDateString())));
        final Product product = this.productRepository.getProductByName(productName);
        JSONObject jsonObj =
                new JSONObject().appendField("promotionAlreadyApplied", 0).appendField("promotionHasBeenAppliedLast72hours", 0);
        if (product.getNoOfPromotions() == 0) {
            return CompletableFuture.completedFuture(jsonObj);
        }
        final List<AssignProduct> zones = product.getAssignProduct();
        // Get zone by zoneName
        final AssignProduct finalZone = zones.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny().orElse(null);
        if (null != finalZone) {
            final List<Cluster> clusters = finalZone.getCluster();
            // Get cluster by clusterName
            final Cluster finalCluster =
                    clusters.stream().filter(c -> c.getClusterName().equals(clusterName)).findAny().orElse(null);
            if (null != finalCluster) {
                final List<Promotion> promotions = finalCluster.getPromotions();
                jsonObj = this.productHandler.promotionStatus(promotions, pflag, dflag, appliedDate, jsonObj);
            }
        }
        return CompletableFuture.completedFuture(jsonObj);
    }

    /**
     * @see Get all Products in a Date range for a Cluster
     * @param startDate
     * @param endDate
     * @return List<JSONObject>
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "get-products-by-DateRange-zone", key = "{#startDate, #endDate}", unless = "#result==null")
    public CompletableFuture<List<JSONObject>> getAllProductsIntheDateRangeInZone(final Date startDate,
            final Date endDate) {
        final List<Product> products = this.productRepository.findAllProductsIntheDateRangeZone(startDate, endDate);
        return CompletableFuture.completedFuture(this.productHandler.getRangeOutputZone(products, startDate, endDate));
    }

    /**
     * @see Get all Products in a Date range for a Cluster
     * @param startDate
     * @param endDate
     * @return List<JSONObject>
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "get-products-by-DateRange-cluster", key = "{#startDate, #endDate}", unless = "#result==null")
    public CompletableFuture<List<JSONObject>> getAllProductsIntheDateRangeInCluster(final Date startDate,
            final Date endDate) {
        final List<Product> products = this.productRepository.findAllProductsIntheDateRangeCluster(startDate, endDate);
        return CompletableFuture.completedFuture(this.productHandler.getRangeOutputCluster(products, startDate, endDate));
    }

    /**
     * @see Get all Products in a Date range for a Zone (Future)
     * @param startDate
     * @param endDate
     * @param currentDate
     * @return List<JSONObject>
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "get-products-by-futureDateRange-zone", key = "{#startDate, #endDate, #currentDate}",
            unless = "#result==null")
    public CompletableFuture<List<JSONObject>> getAllProductsIntheDateRangeFutureInZone(final Date startDate,
            final Date endDate,
            final Date currentDate) {
        final List<Product> products =
                this.productRepository.findAllProductsIntheDateRangeFutureZone(startDate, endDate, currentDate);
        return CompletableFuture.completedFuture(this.productHandler.getRangeOutputZone(products, startDate, endDate));
    }

    /**
     * @see Get all Products in a Date range for a Cluster (Future)
     * @param startDate
     * @param endDate
     * @param currentDate
     * @return List<JSONObject>
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "get-products-by-futureDateRange-cluster", key = "{#startDate, #endDate, #currentDate}",
            unless = "#result==null")
    public CompletableFuture<List<JSONObject>> getAllProductsIntheDateRangeFutureInCluster(final Date startDate,
            final Date endDate,
            final Date currentDate) {
        final List<Product> products =
                this.productRepository.findAllProductsIntheDateRangeFutureCluster(startDate, endDate, currentDate);
        return CompletableFuture.completedFuture(this.productHandler.getRangeOutputCluster(products, startDate, endDate));
    }

    /**
     * @see Cancel all Promotions in Zone for a Product
     * @param productName
     * @param zoneName
     * @param cancelledDate
     * @return Boolean
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<Boolean> cancelPromotionInZone(final String productName,
            final String zoneName,
            final Date cancelledDate) {
        // Fetch product by productName
        final Product finalProduct = this.productRepository.getProductByName(productName);
        // Proceed if product exists
        if (null != finalProduct) {
            // Array of all Zones associated with this product
            final List<AssignProduct> assignProductArry = finalProduct.getAssignProduct();
            // Get Zone with name zoneName
            final AssignProduct assignProductArrayZoneName =
                    assignProductArry.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny().get();
            // If the Zone exists, cancel all the promotions and set cancelledDate
            if (null != assignProductArrayZoneName) {
                final List<Promotion> promotionList = assignProductArrayZoneName.getPromotions();
                if (promotionList.isEmpty()) {
                    throw new PromotionDoesntExists("Sorry, promotions doesnt exist!");
                } else {
                    promotionList.forEach(promotion -> {
                        promotion.setIsActivated(false);
                        promotion.setCancelledDate(cancelledDate);
                    });
                    this.productRepository.save(finalProduct);
                    return CompletableFuture.completedFuture(true);
                }
            }
        }
        // When product doesn't exists
        return CompletableFuture.completedFuture(false);
    }

    /**
     * @see with draw a particular Promotion in Zone for a Product
     * @param productName
     * @param zoneName
     * @param cancelledDate
     * @param promotionId
     * @return void
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<Boolean> withdrawPromotionInZone(final String productName,
            final String zoneName,
            final Integer promotionId,
            final Date withDrawnDate) {
        // Fetch product by name
        final Product product = this.productRepository.getProductByName(productName);
        final List<AssignProduct> zones = product.getAssignProduct();
        // Get Zone from zone list
        final AssignProduct finalZone = zones.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny().get();
        // Fetch all promotions of that zone
        final List<Promotion> promotions = finalZone.getPromotions();
        // Throw exception for empty promotion list
        if (promotions.isEmpty()) {
            throw new PromotionDoesntExists("sorry no promotions");
        }
        // Search promotion by promotionId
        final Promotion finalPromotion =
                promotions.stream().filter(p -> p.getPromotionId().equals(promotionId)).findAny().get();
        // If the conditions meet, set Attributes
        if (finalPromotion.getStartDate().after(withDrawnDate)) {
            finalPromotion.setWithDrawnDate(withDrawnDate);
            finalPromotion.setIsActivated(false);
            this.productRepository.save(product);
            return CompletableFuture.completedFuture(true);
        } else {
            throw new WithdrawDateAfterAppliedDate("Withdraw date is after applied date");
        }
    }

    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<Boolean> cancelPromotionInCluster(final String productName,
            final String zoneName,
            final String clusterName,
            final Date cancelledDate) {
        return CompletableFuture.completedFuture(null);
    }

    /**
     * @see with draw a particular Promotion in Cluster for a Product
     * @param productName
     * @param zoneName
     * @param clusterName
     * @param cancelledDate
     * @param promotionId
     * @return void
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<Boolean> withdrawPromotionInCluster(final String productName,
            final String zoneName,
            final String clusterName,
            final Integer promotionId,
            final Date withDrawnDate) {
        // Fetch product by productName
        final Product finalProduct = this.productRepository.getProductByName(productName);
        final List<AssignProduct> zones = finalProduct.getAssignProduct();
        // Get Zone by zoneName
        final Optional<AssignProduct> finalZoneOptional =
                zones.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny();
        // Proceed if Zone exists
        if (finalZoneOptional.isPresent()) {
            final AssignProduct finalZone = finalZoneOptional.get();
            final List<Cluster> clusters = finalZone.getCluster();
            // Get cluster by clusterName
            final Optional<Cluster> finalClusterOptional =
                    clusters.stream().filter(c -> c.getClusterName().equals(clusterName)).findAny();
            // Proceed if Cluster exists
            if (finalClusterOptional.isPresent()) {
                final Cluster finalCluster = finalClusterOptional.get();
                // Get promotion list
                final List<Promotion> promotions = finalCluster.getPromotions();
                if (promotions.isEmpty()) {
                    throw new PromotionDoesntExists("sorry no promotions");
                }
                // Find promotion by promotionId
                final Promotion finalPromotion =
                        promotions.stream().filter(p -> p.getPromotionId().equals(promotionId)).findAny().get();
                // If conditions meet, set Attributes
                if (finalPromotion.getStartDate().after(withDrawnDate)) {
                    finalPromotion.setWithDrawnDate(withDrawnDate);
                    finalPromotion.setIsActivated(false);
                    this.productRepository.save(finalProduct);
                    return CompletableFuture.completedFuture(true);
                } else {
                    throw new WithdrawDateAfterAppliedDate("Withdraw date is after applied date");
                }
            }
        }
        return CompletableFuture.completedFuture(false);
    }

    /**
     * @see assign a product to a store
     * @param productName
     * @param zoneName
     * @param clusterName
     * @param storeName
     * @return void
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<Boolean> addProductsToStore(final String zoneName,
            final String clusterName,
            final String storeName,
            final List<JSONObject> productList) {
        // Doing operations for all the products in the list
        for (final JSONObject product : productList) {
            // Get productName from JSONObject
            final String productName = product.getAsString(Constants.getProductNameString());
            // Fetch product by productName
            final Product finalProduct = this.productRepository.getProductByName(productName);
            final List<AssignProduct> zones = finalProduct.getAssignProduct();
            // Get zone by zoneName
            AssignProduct finalZone = zones.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny().orElse(null);
            // Initialize if it's null
            if (null == finalZone) {
                finalZone = new AssignProduct(zoneName, Constants.getIntegerZero(), Constants.getDoubleZero(),
                        Constants.getDoubleZero(), Constants.getDoubleZero(), new ArrayList<>(), new ArrayList<>());
                zones.add(finalZone);
            }
            final List<Cluster> clusters = finalZone.getCluster();
            // Get cluster by clusterName
            Cluster finalCluster = clusters.stream().filter(c -> c.getClusterName().equals(clusterName)).findAny().orElse(null);
            // Initialize if it's null
            if (null == finalCluster) {
                finalCluster = new Cluster(clusterName, Constants.getIntegerZero(), Constants.getDoubleZero(),
                        Constants.getDoubleZero(), new ArrayList<>(), new ArrayList<>());
                clusters.add(finalCluster);
            }
            final List<Store> stores = finalCluster.getStore();
            // Get store by storeName
            Store finalStore = stores.stream().filter(c -> c.getStoreName().equals(storeName)).findAny().orElse(null);
            // Initialize if it's null
            if (null == finalStore) {
                finalStore = new Store(storeName, Constants.getIntegerZero(), Constants.getDoubleZero());
                stores.add(finalStore);
            }
            // Set parameters if Remaining quantity is more than assigned
            if (Integer.parseInt(product.getAsString(Constants.getQuantityAssignedString())) < finalProduct
                    .getRemainingQuantity()) {
                final int newQuantity = finalStore.getQuantityAssigned()
                        + Integer.parseInt(product.getAsString(Constants.getQuantityAssignedString()));
                finalStore.setQuantityAssigned(newQuantity);
                finalProduct.setRemainingQuantity(finalProduct.getRemainingQuantity()
                        - Integer.parseInt(product.getAsString(Constants.getQuantityAssignedString())));
            } else {
                throw new QuantityInsufficient(Constants.getQuantityInsufficientString());
            }
            this.productRepository.save(finalProduct);
        }
        return CompletableFuture.completedFuture(true);
    }

    /**
     * @see get all promotions in a Zone
     * @param productName
     * @param zoneName
     * @return List<Promotion>
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "get-promotions", key = "{#productName, #zoneName}", unless = "#result==null")
    public CompletableFuture<List<Promotion>> getAllPromotionsInZone(final String productName,
            final String zoneName) {
        // Fetch product by productName
        final Product product = this.productRepository.getProductByName(productName);
        final List<AssignProduct> assignProducts = product.getAssignProduct();
        // Get zone by zoneName
        final Optional<AssignProduct> assignProductOptional =
                assignProducts.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny();
        // If zone object exists then return all promotions of zone, else return empty
        // list.
        if (assignProductOptional.isPresent()) {
            final AssignProduct assignProduct = assignProductOptional.get();
            if (assignProduct.getPromotions().isEmpty()) {
                throw new PromotionDoesntExists("Sorry promotions doesnt exist!");
            }
            return CompletableFuture.completedFuture(assignProduct.getPromotions());
        }
        return CompletableFuture.completedFuture(new ArrayList<>());
    }

    /**
     * @see get all promotions in a cluster
     * @param productName
     * @param zoneName
     * @param clusterName
     * @return List<Promotion>
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "get-promotions", key = "{#productName, #zoneName, #clusterName}", unless = "#result==null")
    public CompletableFuture<List<Promotion>> getAllPromotionsInCluster(final String productName,
            final String zoneName,
            final String clusterName) {
        // Fetch product by productName
        final Product product = this.productRepository.getProductByName(productName);
        final List<AssignProduct> assignProducts = product.getAssignProduct();
        // Get zone by zoneName
        final Optional<AssignProduct> assignProductOptional =
                assignProducts.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny();
        if (assignProductOptional.isPresent()) {
            final AssignProduct assignProduct = assignProductOptional.get();
            final List<Cluster> clusters = assignProduct.getCluster();
            // Get cluster bu clusterName
            final Optional<Cluster> clusterOptional =
                    clusters.stream().filter(c -> c.getClusterName().equals(clusterName)).findAny();
            // If cluster object exists then return all promotions of cluster, else return
            // empty list.
            if (clusterOptional.isPresent()) {
                final Cluster cluster = clusterOptional.get();
                if (cluster.getPromotions().isEmpty()) {
                    throw new PromotionDoesntExists("sorry promotions doesnt exist!");
                }
                return CompletableFuture.completedFuture(cluster.getPromotions());
            }
        }
        return CompletableFuture.completedFuture(new ArrayList<>());
    }

    /**
     * @see set effective price object
     * @param productName
     * @param startDate
     * @param startDate
     * @param effectivePercent
     * @return CompletableFuture<Double>
     */
    @Override
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Double> setEffectivePrice(final String productName,
            final Date startDate,
            final Date endDate,
            final double effectivePercent) {
        final Product product = this.productRepository.getProductByName(productName);
        final EffectivePrice effectivePriceObj = product.getEffectivePriceObj();

        if (null != effectivePriceObj) {
            throw new EffectivePriceAlreadyDefined("Effective price is already defined for this product");
        } else {
            if (effectivePercent <= -100) {
                throw new ArithmeticException("The Effective Percent should be greater than -100");
            }
            if (this.productHandler.activePromotionInDateRange(startDate, endDate, product).equals(Boolean.TRUE)) {
                throw new ActivePromotionInDateRange("Sorry cannot change price of product in given date range");
            }
            final EffectivePrice effectivePriceObj2 = new EffectivePrice();
            final double effectivePrice = this.productHandler.calculatePrice(product.getProductBasePrice(), effectivePercent);
            effectivePriceObj2.setEffectivePercentage(effectivePercent);
            effectivePriceObj2.setEffectivePrice(effectivePrice);
            effectivePriceObj2.setStartDate(startDate);
            effectivePriceObj2.setEndDate(endDate);
            effectivePriceObj2.setFlag(false);
            product.setEffectivePriceObj(effectivePriceObj2);
            this.productRepository.save(product);
            return CompletableFuture.completedFuture(effectivePrice);
        }

    }

    /**
     * @see get all products whose effectiveprice is not in effect
     * @return CompletableFuture<Double>
     */
    @Override
    @Async("threadPoolTaskExecutor")
    @Cacheable(value = "productsEffectivePriceNotInEffect")
    public CompletableFuture<List<JSONObject>> getAllProductsEffectivePriceNotInEffect() {
        final List<Product> products = this.productRepository.findAll();
        final Date currentDate = new Date();
        final List<Product> productsWithEffecivePriceDefined =
                products.stream().filter(obj -> (obj.getEffectivePriceObj() != null)).collect(Collectors.toList());
        final List<Product> productWithEffectivePriceNotInEffect = productsWithEffecivePriceDefined.stream()
                .filter(predicate -> predicate.getEffectivePriceObj().getStartDate().after(currentDate))
                .collect(Collectors.toList());

        if (null != productWithEffectivePriceNotInEffect) {
            return CompletableFuture.completedFuture(productWithEffectivePriceNotInEffect.stream()
                    .map(p -> this.productHandler.constructProductEffectiveObject(p)).collect(Collectors.toList()));
        } else {
            throw new ProductDoesntExist("Product Not Found");
        }
    }

    /**
     * @see get all products whose effectiveprice is in effect
     * @return CompletableFuture<Double>
     */
    @Override
    @Async("threadPoolTaskExecutor")
    @Cacheable(value = "productsEffectivePriceInEffect")
    public CompletableFuture<List<JSONObject>> getAllProductsEffectivePriceInEffect() {
        final List<Product> products = this.productRepository.findAll();
        final Date currentDate = new Date();
        final List<Product> productsWithEffecivePriceDefined =
                products.stream().filter(obj -> (obj.getEffectivePriceObj() != null)).collect(Collectors.toList());
        final List<Product> productWithEffectivePriceNotInEffect = productsWithEffecivePriceDefined.stream()
                .filter(predicate -> (this.productHandler.checkIfEffectivePriceInEffect(predicate, currentDate)))
                .collect(Collectors.toList());

        if (null != productWithEffectivePriceNotInEffect) {
            return CompletableFuture.completedFuture(productWithEffectivePriceNotInEffect.stream()
                    .map(p -> this.productHandler.constructProductEffectiveObject(p)).collect(Collectors.toList()));
        } else {
            throw new ProductDoesntExist("Product Not Found");
        }
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Boolean> cancelWithdrawEffectivePrice(final String productName) {
        // Fetch product by productName
        final Product product = this.productRepository.getProductByName(productName);
        // Proceed if product exists
        if (null != product) {
            final EffectivePrice effectivePriceObj = product.getEffectivePriceObj();
            if (null != effectivePriceObj) {
                product.setEffectivePriceObj(null);
                this.productRepository.save(product);
            }
            return CompletableFuture.completedFuture(true);
        }
        return CompletableFuture.completedFuture(false);

    }

    /**
     * @see update product (quantity,base price)
     * @param productName
     * @param productDetails
     * @return void
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public void updateProductByVendor(final String productName,
            final JSONObject productDetails) {
        final Product product = this.productRepository.getProductByName(productName);
        final Integer newQuantity = Integer.parseInt(productDetails.getAsString(Constants.getNewQuantityString()));
        final Double finalBasePrice = Double.parseDouble(productDetails.getAsString(Constants.getNewBasePriceString()));
        final Integer finalInitialQuantity = product.getInitialQuantity() + newQuantity;
        final Integer finalRemainingQuantity = product.getRemainingQuantity() + newQuantity;
        product.setInitialQuantity(finalInitialQuantity);
        product.setRemainingQuantity(finalRemainingQuantity);
        product.setProductBasePrice(finalBasePrice);
        this.productRepository.save(this.productHandler.updatePromotionSellingPrices(product));
    }

    /**
     * @see check if the promotion is running currently
     * @param productName
     * @return Boolean
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "check-promotion", key = "{#productName}", unless = "#result==null")
    public CompletableFuture<Boolean> checkPromotion(final String productName) {
        final Date currentDate = new Date();
        final Product product = this.productRepository.getProductByName(productName);
        final List<AssignProduct> zones = product.getAssignProduct();
        for (final AssignProduct ap : zones) {
            final List<Promotion> promotionsZone = ap.getPromotions();
            for (final Promotion p : promotionsZone) {
                // Check in date range and isActivated is true
                if (this.productHandler.checkIfPromotionRunning(p, currentDate, product)) {
                    return CompletableFuture.completedFuture(true);
                }
            }
            final List<Cluster> clusters = ap.getCluster();
            for (final Cluster c : clusters) {
                final List<Promotion> promotionsCluster = c.getPromotions();
                for (final Promotion p : promotionsCluster) {
                    // Check in date range and isActivated is true
                    if (this.productHandler.checkIfPromotionRunning(p, currentDate, product)) {
                        return CompletableFuture.completedFuture(true);
                    }
                }
            }
        }
        return CompletableFuture.completedFuture(false);
    }

    /**
     * @see Get minimum base price allowed(for alcohol product)
     * @param productName
     * @return Double
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "minimum-baseprice", key = "{#productName}", unless = "#result==null")
    public CompletableFuture<Double> getMinimumBasePrice(final String productName) {
        final Product product = this.productRepository.getProductByName(productName);
        if (product.getProductCategory().equals(ProductCategory.ALCOHOL_PROD)) {
            return CompletableFuture.completedFuture(this.productHandler.calculateMinimumBasePriceAllowed(product));
        } else {
            return CompletableFuture.completedFuture(0.0);
        }
    }

    /**
     * @see to update product, sell at fixed price
     * @param productName
     * @return Boolean
     */
    @Override
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Boolean> sellAtFixedPrice(final String productName) {
        final Product product = this.productRepository.getProductByName(productName);
        if (product.getIsPriceFixed().equals(Boolean.TRUE)) {
            throw new ProductPriceIsAlreadyFixed("This product already has a fixed price");
        } else {
            product.setIsPriceFixed(true);
            this.productRepository.save(product);
            return CompletableFuture.completedFuture(true);
        }
    }

    /**
     * @see to update product, cancel at fixed price
     * @param productName
     * @return Boolean
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<Boolean> cancelFixedPrice(final String productName) {
        final Product product = this.productRepository.getProductByName(productName);
        if (product.getIsPriceFixed().equals(Boolean.TRUE)) {
            product.setIsPriceFixed(false);
            this.productRepository.save(product);
            return CompletableFuture.completedFuture(true);

        } else {
            throw new ProductPriceIsAlreadyCancelled("This product's fixed price has already been cancelled");
        }

    }

    /**
     * @see Get List of Non alcoholic product names
     * @return List<String>
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "non-alcoholic")
    public CompletableFuture<List<String>> getNonAlcoholProductNameList() {
        final Function<Product, String> nameFunction = (final Product product) -> product.getProductName();
        final List<Product> products = this.productRepository.getNonAlcoholProductByName(ProductCategory.ALCOHOL_PROD);
        final List<String> productNames = new ArrayList<>();
        products.stream().filter(product -> productNames.add(nameFunction.apply(product))).collect(Collectors.toList());
        return CompletableFuture.completedFuture(productNames);
    }

    /**
     * @see Get specific product details based on product name
     * @param productName
     * @return JSONObject
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "specific-product-details", key = "{#productName}", unless = "#result==null")
    public CompletableFuture<JSONObject> getProductDetailsByName(@NotNull final String productName) {
        final Product product = this.productRepository.getProductByName(productName);
        if ((null == product.getEffectivePriceObj()) || !product.getEffectivePriceObj().getFlag().equals(Boolean.TRUE)) {
            return CompletableFuture.completedFuture(this.productHandler.constructProductDetails(product,
                    product.getProductBasePrice(), product.getProductBasePrice()));
        }
        if (product.getEffectivePriceObj().getFlag().equals(Boolean.TRUE)) {
            return CompletableFuture.completedFuture(this.productHandler.constructProductDetails(product,
                    product.getEffectivePriceObj().getEffectivePrice(), product.getProductBasePrice()));
        }
        return CompletableFuture.completedFuture(null);
    }

    /**
     * @see function contains calculations for parameters required by DashBoard on the application
     * @return JSONObject
     */

    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "dashboard-details")
    public CompletableFuture<JSONObject> getDetails() {
        final RestTemplate restTemplate = new RestTemplate();
        final ServiceInstance serviceInstance = this.loadBalancerClient.choose("LOCATION-MANAGEMENT");
        final String resourceUrl = serviceInstance.getUri() + "/zones/names";
        final List<String> restTemplateZone = restTemplate.getForObject(resourceUrl, List.class);
        final int noOfZones = restTemplateZone.size();
        // final String

        final RestTemplate restTemplate2 = new RestTemplate();
        final String resourceUrl2 = serviceInstance.getUri() + "/cluster-map";
        final Map<Object, Object> restTemplateZone2 = restTemplate2.getForObject(resourceUrl2, Map.class);
        final int noOfClusters = restTemplateZone2.size();

        final ServiceInstance serviceInstance2 = this.loadBalancerClient.choose("VENDOR-RETAILER-MANAGEMENT");
        final String resourceUrl3 = serviceInstance2.getUri() + "/vendors";
        final List<JSONObject> restTemplateZone3 = restTemplate.getForObject(resourceUrl3, List.class);
        final int noOfVendors = restTemplateZone3.size();

        final List<Product> products = this.productRepository.findAll();
        final int noOfProducts = products.size();

        final HashMap<String, Integer> noOfAlcoholProductsPerZone = new HashMap<>();
        final HashMap<String, Integer> noOfBabyProductsPerZone = new HashMap<>();
        final HashMap<String, Integer> noOfActivePromotionPerZone = new HashMap<>();

        for (int i = 0; i < noOfZones; i++) {
            noOfAlcoholProductsPerZone.put(restTemplateZone.get(i), 0);
            noOfBabyProductsPerZone.put(restTemplateZone.get(i), 0);
            noOfActivePromotionPerZone.put(restTemplateZone.get(i), 0);
        }

        int noOfBabyProducts = 0;
        int noOfAlcoholProducts = 0;

        final Date currentDate = new Date();

        for (int i = 0; i < noOfProducts; i++) {
            final ProductCategory productCategory = products.get(i).getProductCategory();
            if (productCategory.equals(ProductCategory.ALCOHOL_PROD)) {
                noOfAlcoholProducts++;
            } else {
                noOfBabyProducts++;
            }
            final List<AssignProduct> listOfAssignProduct = products.get(i).getAssignProduct();
            for (final AssignProduct element : listOfAssignProduct) {

                final List<Cluster> clusters = element.getCluster();
                final String zoneName = element.getZoneName();

                for (final Cluster cluster : clusters) {
                    final List<Promotion> promotions2 = cluster.getPromotions();
                    for (final Promotion promotion : promotions2) {
                        if (this.productHandler.checkIfPromotionRunning2(promotion, currentDate)) {
                            noOfActivePromotionPerZone.put(zoneName, noOfActivePromotionPerZone.get(zoneName) + 1);
                        }
                    }

                }

                final List<Promotion> promotions = element.getPromotions();
                for (final Promotion promotion : promotions) {
                    if (this.productHandler.checkIfPromotionRunning2(promotion, currentDate)) {
                        noOfActivePromotionPerZone.put(zoneName, noOfActivePromotionPerZone.get(zoneName) + 1);
                    }
                }
                // map.put(key, map.get(key) + 1); // BABY_PROD,ALCOHOL_PROD
                if (productCategory.equals(ProductCategory.ALCOHOL_PROD)) {
                    noOfAlcoholProductsPerZone.put(zoneName, noOfAlcoholProductsPerZone.get(zoneName) + 1);
                } else {
                    noOfBabyProductsPerZone.put(zoneName, noOfBabyProductsPerZone.get(zoneName) + 1);
                }

            }
        }
        // Map<String, Integer> map = new HashMap<String, Integer>();
        final List<String> zoneNames = new ArrayList<>(noOfAlcoholProductsPerZone.keySet());
        final List<Integer> alcoholInZone = new ArrayList<>(noOfAlcoholProductsPerZone.values());
        final List<Integer> babyInZone = new ArrayList<>(noOfBabyProductsPerZone.values());
        final List<Integer> activePromotionInZone = new ArrayList<>(noOfActivePromotionPerZone.values());
        return CompletableFuture.completedFuture(new JSONObject().appendField("NoOfZones", noOfZones)
                .appendField("NoOfProducts", noOfProducts).appendField("NoOfVendors", noOfVendors)
                .appendField("ZoneNames", zoneNames).appendField("noOfAlcoholProductsPerZone", alcoholInZone)
                .appendField("noOfBabyProductsPerZone", babyInZone).appendField("NoOfClusters", noOfClusters)
                .appendField("totalNoOfBabyProducts", noOfBabyProducts)
                .appendField("totalNoOfAlcoholProducts", noOfAlcoholProducts)
                .appendField("totalNoOfActivePromotions", activePromotionInZone));

    }

    /**
     * @see Return specific product details required by vendor
     * @param productName
     * @return JSONObject
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "vendor-product-details", key = "{#productName}", unless = "#result==null")
    public CompletableFuture<JSONObject> getProductDetailsForVendor(final String productName) {
        final Product product = this.productRepository.getProductByName(productName);
        return CompletableFuture
                .completedFuture(new JSONObject().appendField(Constants.getProductNameString(), product.getProductName())
                        .appendField(Constants.getProductCategory(), product.getProductCategory())
                        .appendField(Constants.getProductBasePriceString(), product.getProductBasePrice())
                        .appendField(Constants.getInitialQuantity(), product.getInitialQuantity())
                        .appendField(Constants.getProductUom(), product.getUom())
                        .appendField(Constants.getProductImage(), product.getProductImagePath())
                        .appendField(Constants.getProductDescription(), product.getProductDescription()));

    }

    /**
     * @see check if the price of a product is assigned for a particular zone or not
     * @param productName
     * @param zoneName
     * @return Double
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "get-price-at-zone", key = "{#productName,#zoneName}", unless = "#result==null")
    public CompletableFuture<Double> getPriceAtZoneLevel(final String productName,
            final String zoneName) {
        final Product product = this.productRepository.getProductByName(productName);
        final List<AssignProduct> zones = product.getAssignProduct();
        final AssignProduct finalZone = zones.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny().orElse(null);
        if (null == finalZone) {
            throw new PriceAtZoneLevelNotDefined("Product is not assigned to this zone");
        } else if (finalZone.getPrice() == 0) {
            throw new PriceAtZoneLevelNotDefined("Product is not assigned to this zone, but to cluster");
        }
        return CompletableFuture.completedFuture(finalZone.getPrice());
    }

    /**
     * @see check if the price of a product is assigned for a particular cluster or not
     * @param productName
     * @param zoneName
     * @param clusterName
     * @return Double
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "get-price-at-cluster", key = "{#productName,#zoneName,#clusterName}", unless = "#result==null")
    public CompletableFuture<Double> getPriceAtClusterLevel(final String productName,
            final String zoneName,
            final String clusterName) {
        final Product product = this.productRepository.getProductByName(productName);
        final List<AssignProduct> zones = product.getAssignProduct();
        final AssignProduct finalZone = zones.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny().orElse(null);
        if (null == finalZone) {
            throw new PriceAtClusterLevelNotDefined("Product is not assigned to the cluster");
        }
        final List<Cluster> clusters = finalZone.getCluster();
        // Search for the Cluster object with a given Cluster name
        final Cluster finalCluster =
                clusters.stream().filter(c -> c.getClusterName().equals(clusterName)).findAny().orElse(null);
        if (null == finalCluster) {
            throw new PriceAtClusterLevelNotDefined("Product is not assigned to the cluster");
        }
        return CompletableFuture.completedFuture(finalCluster.getPrice());
    }

    /**
     * @see get product list based on vendor name
     * @param vendorName
     * @return List<String>
     */
    @Async("threadPoolTaskExecutor")
    @Override
    @Cacheable(value = "get-product-names-of-vendor", key = "{#vendorName}", unless = "#result==null")
    public CompletableFuture<List<String>> getProductNamesOfVendor(final String vendorName) {
        final List<Product> products = this.productRepository.getProductsByVendorName(vendorName);
        final Function<Product, String> nameFunction = (final Product product) -> product.getProductName();
        final List<String> productNames = new ArrayList<>();
        products.stream().filter(p -> productNames.add(nameFunction.apply(p))).collect(Collectors.toList());
        return CompletableFuture.completedFuture(productNames);
    }

    /**
     * @see get promotions at zone and cluster level
     * @param productName
     * @return JSONObject
     */
    @Override
    @Async("threadPoolTaskExecutor")
    @Cacheable(value = "get-promotion-at-zone-cluster", key = "{#productName}", unless = "#result==null")
    public CompletableFuture<JSONObject> getPromotionAtZoneCluster(@NotNull final String productName) {
        final Product product = this.productRepository.getProductByName(productName);
        final List<JSONObject> promotion = new ArrayList<>();
        final List<AssignProduct> assignProduct = product.getAssignProduct();
        for (final AssignProduct ap : assignProduct) {
            final List<Promotion> promotionsZone = ap.getPromotions();
            for (final Promotion promotionInZone : promotionsZone) {
                if (promotionInZone.getStatus().equals(PromotionStatus.PENDING)) {
                    promotion.add(
                            this.productHandler.constructPromotionPending(promotionInZone, ap.getZoneName(), ap.getPrice()));
                }
            }
            final List<Cluster> clusters = ap.getCluster();
            for (final Cluster c : clusters) {
                final List<Promotion> promotionsCluster = c.getPromotions();
                for (final Promotion promotionInCluster : promotionsCluster) {
                    if (promotionInCluster.getStatus().equals(PromotionStatus.PENDING)) {
                        promotion.add(this.productHandler.constructPromotionPending(promotionInCluster,
                                ap.getZoneName().concat("/").concat(c.getClusterName()), c.getPrice()));
                    }
                }
            }
        }
        return CompletableFuture
                .completedFuture(new JSONObject().appendField(Constants.getProductNameString(), product.getProductName())
                        .appendField(Constants.getPendingPromotion(), promotion));
    }

    /**
     * @see to approve product
     * @param productName
     * @return Boolean
     */
    @Override
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Boolean> approveProduct(@NotNull final String productName,
            @NotNull final Integer promotionId,
            @NotNull final PromotionStatus status) {
        final Product product = this.productRepository.getProductByName(productName);
        final List<AssignProduct> assignProduct = product.getAssignProduct();
        for (final AssignProduct ap : assignProduct) {
            final List<Promotion> promotionsZone = ap.getPromotions();
            final Optional<Promotion> finalPromotionInZone =
                    promotionsZone.stream().filter(p -> p.getPromotionId().equals(promotionId)).findAny();
            if (finalPromotionInZone.isPresent()) {
                final Promotion promotionInZone = finalPromotionInZone.get();
                this.productHandler.setStatus(promotionInZone, status);
            } else {
                final List<Cluster> clusters = ap.getCluster();
                for (final Cluster c : clusters) {
                    final List<Promotion> promotionsCluster = c.getPromotions();
                    final Optional<Promotion> finalPromotionInCluster =
                            promotionsCluster.stream().filter(p -> p.getPromotionId().equals(promotionId)).findAny();
                    if (finalPromotionInCluster.isPresent()) {
                        final Promotion promotionInCluster = finalPromotionInCluster.get();
                        this.productHandler.setStatus(promotionInCluster, status);
                    }
                }
            }
        }
        this.productRepository.save(product);
        return CompletableFuture.completedFuture(true);
    }

    /**
     * @see get product Quantity in a zone
     * @param productName
     * @param zoneName
     * @return Integer
     */
    @Override
    @Async("threadPoolTaskExecutor")
    @Cacheable(value = "get-quanity-in-zone", key = "{#productName,#zoneName}", unless = "#result==null")
    public CompletableFuture<Integer> getQuantityInZone(final String productName,
            final String zoneName) {
        final Product finalProduct = this.productRepository.getProductByName(productName);
        final List<AssignProduct> zones = finalProduct.getAssignProduct();
        final AssignProduct finalZone = zones.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny().orElse(null);
        if (null != finalZone) {
            return CompletableFuture.completedFuture(finalZone.getQuantityAssigned());
        }
        return CompletableFuture.completedFuture(0);
    }

    /**
     * @see get product Quantity in a cluster
     * @param productName
     * @param zoneName
     * @param clusterName
     * @return Integer
     */
    @Override
    @Async("threadPoolTaskExecutor")
    @Cacheable(value = "get-quanity-in-cluster", key = "{#productName,#zoneName}", unless = "#result==null")
    public CompletableFuture<Integer> getQuantityInCluster(final String productName,
            final String zoneName,
            final String clusterName) {
        final Product finalProduct = this.productRepository.getProductByName(productName);
        final List<AssignProduct> zones = finalProduct.getAssignProduct();
        final AssignProduct finalZone = zones.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny().orElse(null);
        if (null != finalZone) {
            final List<Cluster> clusters = finalZone.getCluster();
            final Cluster finalCluster =
                    clusters.stream().filter(c -> c.getClusterName().equals(clusterName)).findAny().orElse(null);
            if (null != finalCluster) {
                return CompletableFuture.completedFuture(finalCluster.getQuantityAssigned());
            }
        }
        return CompletableFuture.completedFuture(0);
    }

    /**
     * @see update product Quantity in a zone
     * @param productName
     * @param productDetails
     * @return Boolean
     */
    @Override
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Boolean> updateQuantityInZone(final String productName,
            final JSONObject productDetails) {
        final Product finalProduct = this.productRepository.getProductByName(productName);
        final String zoneName = productDetails.getAsString(Constants.getZoneNameString());
        final Integer finalQuantityAssigned =
                Integer.parseInt(productDetails.getAsString(Constants.getQuantityAssignedString()));
        final List<AssignProduct> zones = finalProduct.getAssignProduct();
        final AssignProduct finalZone = zones.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny().orElse(null);
        if (finalZone != null) {
            this.productHandler.setQuantityAssignedToZone(finalQuantityAssigned, finalProduct, finalZone, 0.0, true);
            this.productRepository.save(finalProduct);
            return CompletableFuture.completedFuture(true);
        }
        return CompletableFuture.completedFuture(false);
    }

    /**
     * @see update product Quantity in a cluster
     * @param productName
     * @param productDetails
     * @return Boolean
     */
    @Override
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Boolean> updateQuantityInCluster(final String productName,
            final JSONObject productDetails) {
        final Product finalProduct = this.productRepository.getProductByName(productName);
        final String zoneName = productDetails.getAsString(Constants.getZoneNameString());
        final String clusterName = productDetails.getAsString(Constants.getClusterNameString());
        final Integer finalQuantityAssigned =
                Integer.parseInt(productDetails.getAsString(Constants.getQuantityAssignedString()));
        final List<AssignProduct> zones = finalProduct.getAssignProduct();
        final AssignProduct finalZone = zones.stream().filter(ap -> ap.getZoneName().equals(zoneName)).findAny().orElse(null);
        if (null != finalZone) {
            final List<Cluster> clusters = finalZone.getCluster();
            final Cluster finalCluster =
                    clusters.stream().filter(c -> c.getClusterName().equals(clusterName)).findAny().orElse(null);
            if (null != finalCluster) {
                this.productHandler.setQuantityAssignedToCluster(finalQuantityAssigned, finalProduct, finalCluster, 0.0, true);
                this.productRepository.save(finalProduct);
                return CompletableFuture.completedFuture(true);
            }
        }
        return CompletableFuture.completedFuture(false);
    }

    /**
     * @see function to throw exceptions based on message passed
     * @param message
     * @return void
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public void throwFallBackErrorMessage(final String message) {
        if (message.equals(Constants.getAlreadyAssignedZone())) {
            throw new ProductAlreadyAssociatedToZone(Constants.getAlreadyAssignedZone());
        } else if (message.equals(Constants.getQuantityInsufficientString())) {
            throw new QuantityInsufficient(Constants.getQuantityInsufficientString());
        } else if (message.equals(Constants.getAlreadyAssignedCluster())) {
            throw new ProductAlreadyAssociatedToCluster(Constants.getAlreadyAssignedCluster());
        } else if (message.equals(Constants.getBelowMspErrMsg())) {
            throw new ProductPriceBelowMinimumSellingPrice(Constants.getBelowMspErrMsg());
        } else {
            throw new RuntimeException(message);
        }
    }

}