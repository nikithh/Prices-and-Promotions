package com.publicis.sapient.product.handler;

import org.springframework.stereotype.Component;

@Component
public class Constants {

    static final int INTEGER_ZERO = 0;
    static final double DOUBLE_ZERO = 0.0;
    static final String EFFECTIVE_PRICE_STRING = "effectivePrice";
    static final String ZONE_CLUSTER_STRING = "zoneCluster";
    static final String PROFIT_PERCENTAGE_STRING = "profitPercentage";
    static final String PRODUCT_NAME_STRING = "productName";
    static final String QUANTITY_INSUFFICIENT_STRING = "Quantity Insufficient";
    static final String QUANTITY_ASSIGNED_STRING = "quantityAssigned";
    static final String STATUS_STRING = "status";
    static final String LIST_STRING = "list";
    static final String SELLING_PRICE_STRING = "selling price";
    static final String PRODUCT_BASE_PRICE_STRING = "productBasePrice";
    static final String COMPANY_NAME_STRING = "companyName";
    static final String REMAINING_QUANTITY_STRING = "remainingQuantity";
    static final String ZONE_STRING = "zone";
    static final String CLUSTER_STRING = "cluster";
    static final String ADDED_SUCCESSFULLY_STRING = "added successfully";
    static final String ADDED_FAILED_STRING = "could not add promotion";
    static final String START_DATE_STRING = "startDate";
    static final String END_DATE_STRING = "endDate";
    static final String CANCELLED_DATE_STRING = "cancelledDate";
    static final String APPLIED_DATE_STRING = "appliedDate";
    static final String PROMOTION_SP_STRING = "promotionSellingPrice";
    static final String PROMOTION_PERCENTAGE_STRING = "promotionPercentage";
    static final String NEW_QUANTITY_STRING = "newQuantity";
    static final String NEW_BASE_PRICE_STRING = "newBasePrice";
    static final String PRODUCT_ID = "productID";
    static final String PRODUCT_PRICE = "productPrice";
    static final String PRODUCT_CATEGORY = "productCategory";
    static final String PRODUCT_IMAGE = "productImage";
    static final String PRODUCT_UOM = "productUOM";
    static final String INITIAL_QUANTITY = "initialQuantity";
    static final String IS_PROMOTION_RUNNING = "isPromotionRunning";
    static final String MINIMUM_BASE_PRICE = "minimumBasePrice";
    static final String PRODUCT_DESCRIPTION = "productDescription";
    static final String PENDING_PROMOTIONS = "pendingPromotion";
    static final String PROMOTION_ID = "promotionId";
    static final String PRICE_AT_ZONE_LEVEL = "zonePrice";
    static final String PRICE_AT_CLUSTER_LEVEL = "clusterPrice";
    static final String ZONE_NAME_STRING = "zoneName";
    static final String CLUSTER_NAME_STRING = "clusterName";
    static final String BELOW_MSP_ERR_MSG = "Product price is below MSP (Minimum Selling Price)";
    static final String ALREADY_ASSIGNED_ZONE = "Product is already associated with ZONE";
    static final String ALREADY_ASSIGNED_CLUSTER = "Product is already associated with CLUSTER";

    public static String getPendingPromotions() {
        return PENDING_PROMOTIONS;
    }

    public static String getZoneNameString() {
        return ZONE_NAME_STRING;
    }

    public static String getClusterNameString() {
        return CLUSTER_NAME_STRING;
    }

    public static String getPriceAtZoneLevel() {
        return PRICE_AT_ZONE_LEVEL;
    }

    public static String getPriceAtClusterLevel() {
        return PRICE_AT_CLUSTER_LEVEL;
    }

    public static String getProductDescription() {
        return PRODUCT_DESCRIPTION;
    }

    public static String getMinimumBasePrice() {
        return MINIMUM_BASE_PRICE;
    }

    public static String getIsPromotionRunning() {
        return IS_PROMOTION_RUNNING;
    }

    public static String getInitialQuantity() {
        return INITIAL_QUANTITY;
    }

    public static String getProfitPercentageString() {
        return PROFIT_PERCENTAGE_STRING;
    }

    public static String getProductPrice() {
        return PRODUCT_PRICE;
    }

    public static String getProductCategory() {
        return PRODUCT_CATEGORY;
    }

    public static String getProductImage() {
        return PRODUCT_IMAGE;
    }

    public static String getProductUom() {
        return PRODUCT_UOM;
    }

    public static String getNewQuantityString() {
        return NEW_QUANTITY_STRING;
    }

    public static String getNewBasePriceString() {
        return NEW_BASE_PRICE_STRING;
    }

    public static String getProductId() {
        return PRODUCT_ID;
    }

    public static String getPromotionPercentageString() {
        return PROMOTION_PERCENTAGE_STRING;
    }

    public static String getPromotionSpString() {
        return PROMOTION_SP_STRING;
    }

    public static String getCancelledDateString() {
        return CANCELLED_DATE_STRING;
    }

    public static String getAppliedDateString() {
        return APPLIED_DATE_STRING;
    }

    public static String getStartDateString() {
        return START_DATE_STRING;
    }

    public static String getEndDateString() {
        return END_DATE_STRING;
    }

    public static String getSellingPriceString() {
        return SELLING_PRICE_STRING;
    }

    public static String getAddedFailedString() {
        return ADDED_FAILED_STRING;
    }

    public static String getAddedSuccessfullyString() {
        return ADDED_SUCCESSFULLY_STRING;
    }

    public static int getIntegerZero() {
        return INTEGER_ZERO;
    }

    public static double getDoubleZero() {
        return DOUBLE_ZERO;
    }

    public static String getEffectivePriceString() {
        return EFFECTIVE_PRICE_STRING;
    }

    public static String getZoneClusterString() {
        return ZONE_CLUSTER_STRING;
    }

    public static String getProductNameString() {
        return PRODUCT_NAME_STRING;
    }

    public static String getQuantityInsufficientString() {
        return QUANTITY_INSUFFICIENT_STRING;
    }

    public static String getQuantityAssignedString() {
        return QUANTITY_ASSIGNED_STRING;
    }

    public static String getStatusString() {
        return STATUS_STRING;
    }

    public static String getListString() {
        return LIST_STRING;
    }

    public static String getProductBasePriceString() {
        return PRODUCT_BASE_PRICE_STRING;
    }

    public static String getCompanyNameString() {
        return COMPANY_NAME_STRING;
    }

    public static String getZoneString() {
        return ZONE_STRING;
    }

    public static String getClusterString() {
        return CLUSTER_STRING;
    }

    public static String getPendingPromotion() {
        return PENDING_PROMOTIONS;
    }

    public static String getPromotionId() {
        return PROMOTION_ID;
    }

    public static String getRemainingQuantityString() {
        return REMAINING_QUANTITY_STRING;
    }

    public static String getBelowMspErrMsg() {
        return BELOW_MSP_ERR_MSG;
    }

    public static String getAlreadyAssignedZone() {
        return ALREADY_ASSIGNED_ZONE;
    }

    public static String getAlreadyAssignedCluster() {
        return ALREADY_ASSIGNED_CLUSTER;
    }
}
