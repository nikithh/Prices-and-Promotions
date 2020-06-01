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

package com.publicis.sapient.product.model;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * @author prejayak
 * @author sidshriv3
 * @since March 2020
 * @see Bean for Product
 */

public class Product implements Serializable {

    @Id
    private Integer productId;
    @NotNull
    @Indexed
    private String productName;
    @NotNull
    private String productGroup;
    @NotNull
    @Indexed
    private ProductCategory productCategory;
    @NotNull
    private Double productBasePrice;
    @NotNull
    private Integer initialQuantity;
    private Integer remainingQuantity;
    @NotNull
    private String uom;
    @NotNull
    private String companyName;
    @NotNull
    private String productDescription;
    private List<String> productImagePath;
    private Double volume;
    private Double abv;
    private EffectivePrice effectivePriceObj;
    private Integer noOfPromotions;
    private Boolean isPriceFixed;
    private List<String> nutritionalFacts;
    private List<AssignProduct> assignProduct;

    public Product() {
        // default constructor
    }

    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(final Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public String getProductGroup() {
        return this.productGroup;
    }

    public void setProductGroup(final String productGroup) {
        this.productGroup = productGroup;
    }

    public ProductCategory getProductCategory() {
        return this.productCategory;
    }

    public void setProductCategory(final ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Double getProductBasePrice() {
        return this.productBasePrice;
    }

    public void setProductBasePrice(final Double productBasePrice) {
        this.productBasePrice = productBasePrice;
    }

    public Integer getInitialQuantity() {
        return this.initialQuantity;
    }

    public void setInitialQuantity(final Integer initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    public Integer getRemainingQuantity() {
        return this.remainingQuantity;
    }

    public void setRemainingQuantity(final Integer remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public String getUom() {
        return this.uom;
    }

    public void setUom(final String uom) {
        this.uom = uom;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public String getProductDescription() {
        return this.productDescription;
    }

    public void setProductDescription(final String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getVolume() {
        return this.volume;
    }

    public void setVolume(final Double volume) {
        this.volume = volume;
    }

    public Double getAbv() {
        return this.abv;
    }

    public void setAbv(final Double abv) {
        this.abv = abv;
    }

    public EffectivePrice getEffectivePriceObj() {
        return this.effectivePriceObj;
    }

    public void setEffectivePriceObj(final EffectivePrice effectivePriceObj) {
        this.effectivePriceObj = effectivePriceObj;
    }

    public Integer getNoOfPromotions() {
        return this.noOfPromotions;
    }

    public void setNoOfPromotions(final Integer noOfPromotions) {
        this.noOfPromotions = noOfPromotions;
    }

    public List<String> getNutritionalFacts() {
        return this.nutritionalFacts;
    }

    public void setNutritionalFacts(final List<String> nutritionalFacts) {
        this.nutritionalFacts = nutritionalFacts;
    }

    public List<AssignProduct> getAssignProduct() {
        return this.assignProduct;
    }

    public void setAssignProduct(final List<AssignProduct> assignProduct) {
        this.assignProduct = assignProduct;
    }

    public Boolean getIsPriceFixed() {
        return this.isPriceFixed;
    }

    public void setIsPriceFixed(final Boolean isPriceFixed) {
        this.isPriceFixed = isPriceFixed;
    }

    public List<String> getProductImagePath() {
        return this.productImagePath;
    }

    public void setProductImagePath(final List<String> productImagePath) {
        this.productImagePath = productImagePath;
    }

    @Override
    public String toString() {
        return "Product [productId=" + this.productId + ", productName=" + this.productName + ", productGroup="
                + this.productGroup + ", productCategory=" + this.productCategory + ", productBasePrice="
                + this.productBasePrice + ", initialQuantity=" + this.initialQuantity + ", remainingQuantity="
                + this.remainingQuantity + ", uom=" + this.uom + ", companyName=" + this.companyName + ", productDescription="
                + this.productDescription + ", productImagePath=" + this.productImagePath + ", volume=" + this.volume + ", abv="
                + this.abv + ", effectivePriceObj=" + this.effectivePriceObj + ", noOfPromotions=" + this.noOfPromotions
                + ", isPriceFixed=" + this.isPriceFixed + ", nutritionalFacts=" + this.nutritionalFacts + ", assignProduct="
                + this.assignProduct + "]";
    }

}