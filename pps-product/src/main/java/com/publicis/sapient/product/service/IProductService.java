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

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.publicis.sapient.product.model.AssignProduct;
import com.publicis.sapient.product.model.Product;
import com.publicis.sapient.product.model.ProductCategory;
import com.publicis.sapient.product.model.Promotion;
import com.publicis.sapient.product.model.PromotionStatus;
import net.minidev.json.JSONObject;

@Component
public interface IProductService {

    public CompletableFuture<Product> insertProduct(Product product,
            MultipartFile[] files);

    public CompletableFuture<Product> getProductByName(String productName);

    public CompletableFuture<List<Product>> getAllProducts();

    public CompletableFuture<List<String>> getAllProductNames();

    public CompletableFuture<List<String>> getAllCategories();

    public CompletableFuture<List<String>> getAllZoneNamesInProduct(String productName);

    public CompletableFuture<List<String>> getAllClusterNamesInZone(String productName,
            String zoneName);

    public CompletableFuture<List<JSONObject>> getProductsByCategory(ProductCategory productCategory);

    public CompletableFuture<Boolean> addProductsToStore(String zoneName,
            String clusterName,
            String storeName,
            List<JSONObject> productList);

    public CompletableFuture<JSONObject> addPromotionInZoneToProduct(String productName,
            String zoneName,
            Promotion promotion);

    public CompletableFuture<JSONObject> addPromotionInClusterToProduct(String productName,
            String zoneName,
            String clusterName,
            Promotion promotion);

    public CompletableFuture<List<JSONObject>> getAllProductsIntheDateRangeInZone(Date startDate,
            Date endDate);

    public CompletableFuture<List<JSONObject>> getAllProductsIntheDateRangeInCluster(Date startDate,
            Date endDate);

    public CompletableFuture<List<JSONObject>> getAllProductsIntheDateRangeFutureInZone(Date startDate,
            Date endDate,
            Date currentDate);

    public CompletableFuture<List<JSONObject>> getAllProductsIntheDateRangeFutureInCluster(Date startDate,
            Date endDate,
            Date currentDate);

    public CompletableFuture<Boolean> cancelPromotionInZone(String productName,
            String zoneName,
            Date cancelledDate);

    public CompletableFuture<Boolean> withdrawPromotionInZone(String productName,
            String zoneName,
            Integer promotionId,
            Date withDrawnDate);

    public CompletableFuture<Boolean> cancelPromotionInCluster(String productName,
            String zoneName,
            String clusterName,
            Date cancelledDate);

    public CompletableFuture<Boolean> withdrawPromotionInCluster(String productName,
            String zoneName,
            String clusterName,
            Integer promotionId,
            Date withDrawnDate);

    public CompletableFuture<Boolean> assignProductToZoneAndCluster(final String productName,
            final AssignProduct productDetails);

    public CompletableFuture<Boolean> assignProductToZone(final String productName,
            final String zoneName,
            final JSONObject productDetails);

    public CompletableFuture<Boolean> updateQuantityInZone(final String productName,
            final JSONObject productDetails);

    public CompletableFuture<Boolean> checkIfZonePriceExists(String productName,
            String zoneName);

    public CompletableFuture<Boolean> assignProductToCluster(String productName,
            String zoneName,
            String clusterName,
            JSONObject productDetails);

    public CompletableFuture<Boolean> updateQuantityInCluster(String productName,
            JSONObject productDetails);

    public void deleteProduct(String productName);

    public CompletableFuture<Product> updateProduct(Product product);

    public CompletableFuture<List<Promotion>> getAllPromotionsInZone(String productName,
            String zoneName);

    public CompletableFuture<List<Promotion>> getAllPromotionsInCluster(String productName,
            String zoneName,
            String clusterName);

    public CompletableFuture<Double> setEffectivePrice(final String productName,
            final Date startDate,
            final Date endDate,
            final double profitPercent);

    public CompletableFuture<List<JSONObject>> getAllProductsEffectivePriceNotInEffect();

    public CompletableFuture<List<JSONObject>> getAllProductsEffectivePriceInEffect();

    public CompletableFuture<Boolean> cancelWithdrawEffectivePrice(String productName);

    public void updateProductByVendor(String productName,
            JSONObject productDetails);

    public CompletableFuture<Boolean> checkPromotion(String productName);

    public CompletableFuture<Double> getMinimumBasePrice(String productName);

    public CompletableFuture<Boolean> sellAtFixedPrice(String productName);

    public CompletableFuture<Boolean> cancelFixedPrice(String productName);

    public CompletableFuture<List<String>> getNonAlcoholProductNameList();

    public CompletableFuture<JSONObject> getProductDetailsByName(@NotNull String productName);

    public CompletableFuture<JSONObject> getDetails();

    public CompletableFuture<JSONObject> getProductDetailsForVendor(String productName);

    public CompletableFuture<Double> getPriceAtZoneLevel(String productName,
            String zoneName);

    public CompletableFuture<Double> getPriceAtClusterLevel(String productName,
            String zoneName,
            String clusterName);

    public CompletableFuture<List<String>> getProductNamesOfVendor(String vendorName);

    public CompletableFuture<JSONObject> promotionStatusInZone(final String productName,
            final String zoneName,
            final @NotNull JSONObject checkDate);

    public CompletableFuture<JSONObject> promotionStatusInCluster(final String productName,
            final String zoneName,
            final String clusterName,
            final @NotNull JSONObject checkDate);

    public CompletableFuture<JSONObject> getPromotionAtZoneCluster(@NotNull String productName);

    public CompletableFuture<Boolean> approveProduct(@NotNull String productName,
            @NotNull Integer promotionId,
            @NotNull PromotionStatus status);

    public CompletableFuture<List<JSONObject>> getProductsOfVendor(String vendorName,
            Integer pageNo,
            Integer rowsPerPage);

    public CompletableFuture<Integer> getQuantityInZone(String productName,
            String zoneName);

    public CompletableFuture<Integer> getQuantityInCluster(String productName,
            String zoneName,
            String clusterName);

    public void throwFallBackErrorMessage(String message);

    public CompletableFuture<Integer> countOfVendorProducts(String vendorName);
}
