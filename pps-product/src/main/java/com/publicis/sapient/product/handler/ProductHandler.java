package com.publicis.sapient.product.handler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.threeten.extra.Interval;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.publicis.sapient.product.exception.InvalidFields;
import com.publicis.sapient.product.exception.ProductImagePathDoesntExist;
import com.publicis.sapient.product.exception.ProductPriceBelowMinimumSellingPrice;
import com.publicis.sapient.product.exception.QuantityInsufficient;
import com.publicis.sapient.product.model.AssignProduct;
import com.publicis.sapient.product.model.Cluster;
import com.publicis.sapient.product.model.Product;
import com.publicis.sapient.product.model.ProductCategory;
import com.publicis.sapient.product.model.Promotion;
import com.publicis.sapient.product.model.PromotionStatus;

import net.minidev.json.JSONObject;

@Component
public class ProductHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductHandler.class.getClass());

	@Autowired
	LoadBalancerClient loadBalancerClient;

	@Autowired
	private AmazonS3 amazonS3Client;

	private static final String S3BUCKETSTRING = "https://pps-product-images.s3.amazonaws.com/";

	// Function is used to upload file to bitbucket
	public String uploadFile(final String productName, final MultipartFile multipartFile) {
		final ObjectMetadata objectMetaData = new ObjectMetadata();
		objectMetaData.setContentType(multipartFile.getContentType());
		objectMetaData.setContentLength(multipartFile.getSize());
		try {
			final PutObjectRequest putObjectRequest = new PutObjectRequest("pps-product-images",
					productName + "/" + multipartFile.getOriginalFilename(), multipartFile.getInputStream(),
					objectMetaData).withCannedAcl(CannedAccessControlList.PublicRead);
			this.amazonS3Client.putObject(putObjectRequest);
		} catch (final Exception e) {
			LOGGER.error(e.getMessage());
		}
		return this.amazonS3Client.getUrl("pps-product-images", multipartFile.getOriginalFilename()).toString();
	}

	// Function to calculate price given profit
	public Double calculatePrice(final Double price, final Double percentage) {
		return (price * (percentage / 100)) + price;
	}

	// Function to check if the promotion dates lie in asked date range
	public Boolean checkIfInDateRange(final Date startDate, final Date endDate, final Promotion promotion) {
		return (promotion.getStartDate().after(startDate) || promotion.getStartDate().equals(startDate))
				&& (promotion.getEndDate().before(endDate) || promotion.getEndDate().equals(endDate))
				&& Boolean.TRUE.equals(promotion.getIsActivated());
	}

	// Function to check if promotion has overlapping date with startDate and
	// endDate or not
	public Boolean findOverlappingDate(final Date startDate, final Date endDate, final Promotion promotion) {

		final TimeZone tz = TimeZone.getTimeZone("UTC");
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(tz);

		final Instant startInstant1 = Instant.parse(df.format(startDate));
		final Instant endInstant1 = Instant.parse(df.format(endDate));
		final Instant startInstant2 = Instant.parse(df.format(promotion.getStartDate()));
		final Instant endInstant2 = Instant.parse(df.format(promotion.getEndDate()));

		final Interval intervalA = Interval.of(startInstant1, endInstant1);
		final Interval intervalB = Interval.of(startInstant2, endInstant2);

		if (intervalA.overlaps(intervalB)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	// Function to check if there is an active promotion in date range for a product
	public Boolean activePromotionInDateRange(final Date startDate, final Date endDate, final Product product) {
		final List<AssignProduct> assignProducts = product.getAssignProduct();
		for (final AssignProduct assignProduct : assignProducts) {
			final List<Promotion> promotions = assignProduct.getPromotions();
			for (final Promotion promotion : promotions) {
				if (findOverlappingDate(startDate, endDate, promotion).equals(Boolean.TRUE)) {
					return true;
				}
			}
			final List<Cluster> clusters = assignProduct.getCluster();
			for (final Cluster cluster : clusters) {
				final List<Promotion> clusterPromotions = cluster.getPromotions();
				for (final Promotion promotion : clusterPromotions) {
					if (findOverlappingDate(startDate, endDate, promotion).equals(Boolean.TRUE)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// Function to set parameters of Promotion
	public List<Promotion> setParametersToPromotions(final List<Promotion> promotions, final Promotion promotion,
			final Product product, final Double promotionSellingPrice, final Boolean isActivated, final String addedBy,
			final PromotionStatus status) {
		promotion.setPromotionId(product.getNoOfPromotions());
		promotion.setPromotionSellingPrice(promotionSellingPrice);
		promotion.setIsActivated(isActivated);
		promotion.setAddedBy(addedBy);
		promotion.setStatus(status);
		promotions.add(promotion);
		return promotions;
	}

	// Function to check if the promotion lies within 72h of applied date
	public Boolean checkInterval(final Date appliedDate1, final Date appliedDate2, final Promotion promotion) {
		final TimeZone tz = TimeZone.getTimeZone("UTC");
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(tz);
		final Instant startInstant = Instant.parse(df.format(appliedDate1));
		final Instant endInstant = Instant.parse(df.format(appliedDate2));
		final Duration d = Duration.ofHours(72);
		return !startInstant.plus(d).isBefore(endInstant);

	}

	// Function to check if the product has any promotion that has been applied
	// within 72h
	public boolean checkPromotionWithin72hrs(final Date appliedDate, final Product product) {
		final List<AssignProduct> assignProducts = product.getAssignProduct();
		for (final AssignProduct assignProduct : assignProducts) {
			final List<Promotion> promotions = assignProduct.getPromotions();
			for (final Promotion promotion : promotions) {
				if (this.checkInterval(promotion.getAppliedDate(), appliedDate, promotion).equals(Boolean.TRUE)) {
					return true;
				}
			}
			final List<Cluster> clusters = assignProduct.getCluster();
			for (final Cluster cluster : clusters) {
				final List<Promotion> clusterPromotions = cluster.getPromotions();
				for (final Promotion promotion : clusterPromotions) {
					if (this.checkInterval(promotion.getAppliedDate(), appliedDate, promotion).equals(Boolean.TRUE)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// Function to upload images and set image path in product
	public Product setImagePath(final Product product, final MultipartFile[] files) {
		final List<String> productImagePaths = new ArrayList<>();
		if (files.length == 0) {
			throw new ProductImagePathDoesntExist("No image uploaded");
		} else {
			for (final MultipartFile file : files) {
				productImagePaths.add(S3BUCKETSTRING + product.getProductName() + "/" + file.getOriginalFilename());
				uploadFile(product.getProductName(), file);
			}
		}
		product.setProductImagePath(productImagePaths);
		return product;
	}

	// Function to set Parameters of the product(called from insertProduct)
	public Product setParametersToProduct(final Product product, final List<Product> products, final Integer maxId) {
		if (product.getProductCategory().equals(ProductCategory.ALCOHOL_PROD)) {
			if ((product.getAbv() == null) || (product.getVolume() == null)) {
				throw new InvalidFields("ABV or Volume is not present for alcohol products");
			} else {
				product.setNutritionalFacts(new ArrayList<>());
			}
		} else {
			if (product.getNutritionalFacts() == null) {
				product.setNutritionalFacts(new ArrayList<>());
			}
			product.setAbv(Constants.getDoubleZero());
			product.setVolume(Constants.getDoubleZero());
		}
		if (products.isEmpty()) {
			product.setProductId(1);
		} else {
			product.setProductId(maxId + 1);
		}
		product.setRemainingQuantity(product.getInitialQuantity());
		product.setIsPriceFixed(false);
		product.setNoOfPromotions(Constants.getIntegerZero());
		product.setAssignProduct(new ArrayList<>());
		return product;
	}

	// Initializing AssignProduct object
	public AssignProduct setParametersToAssignProduct(final String zoneName) {
		return new AssignProduct(zoneName, Constants.getIntegerZero(), Constants.getDoubleZero(),
				Constants.getDoubleZero(), Constants.getDoubleZero(), new ArrayList<>(), new ArrayList<>());
	}

	// Calculate and return Minimum Selling Price
	public Double calculateMinimumSP(final String zoneName, final Product product) {
		final RestTemplate restTemplate = new RestTemplate();
		final ServiceInstance serviceInstance = this.loadBalancerClient.choose("LOCATION-MANAGEMENT");
		final String resourceUrl = serviceInstance.getUri() + "/zones/" + zoneName;
		// final String resourceUrl = "http://localhost:9700/zones/" + zoneName;
		// Rest Template call to Location micro-service to get zone object
		final JSONObject restTemplateZone = restTemplate.getForObject(resourceUrl, JSONObject.class);
		// Get liquor price per unit from zone object
		final Double finalLiquorPricePerUnit = Double.parseDouble(restTemplateZone.getAsString("liquorPricePerUnit"));
		final Double finalVolume = product.getVolume();
		final Double finalABV = product.getAbv();
		// Calculate Minimum Selling Price based on BL
		return (finalLiquorPricePerUnit * finalABV * finalVolume);
	}

	// Function to set Minimum selling price
	public AssignProduct setMinimumSellingPrice(final Product product, final AssignProduct assignProduct,
			final Double finalProfitPercentage, final String zoneName) {
		if (product.getProductCategory().equals(ProductCategory.ALCOHOL_PROD)) {
			final Double finalProductBasePrice = product.getProductBasePrice();
			final Double finalMinimumSP = calculateMinimumSP(zoneName, product);
			final Double finalPrice = calculatePrice(finalProductBasePrice, finalProfitPercentage);
			// The final price needs to be more than Minimum Selling price for that zone, if
			// product is of Alcohol Category(as BL states)
			if (finalPrice < finalMinimumSP) {
				assignProduct.setPrice(Constants.getDoubleZero());
				throw new ProductPriceBelowMinimumSellingPrice(Constants.getBelowMspErrMsg());
			} else {
				assignProduct.setPrice(finalPrice);
				assignProduct.setMinimumSP(finalMinimumSP);
			}
		} // No check needed for Baby products, Minimum Selling price is set to 0, and
			// price is set according to calculation
		else if (product.getProductCategory().equals(ProductCategory.BABY_PROD)) {
			final Double finalProductBasePrice = product.getProductBasePrice();
			final Double finalPrice = calculatePrice(finalProductBasePrice, finalProfitPercentage);
			assignProduct.setPrice(finalPrice);
			assignProduct.setMinimumSP(Constants.getDoubleZero());
		}
		return assignProduct;
	}

	// Function returns false when product price becomes less than zero, and also
	// when price is less than MSP for alcohol
	public Boolean checkFailConditionForAlcohol(final Double effectivePrice, final Product product,
			final AssignProduct ap) {
		if (effectivePrice < Constants.getDoubleZero()) {
			return Boolean.FALSE;
		}
		if ((product.getProductCategory().equals(ProductCategory.ALCOHOL_PROD))
				&& (effectivePrice < ap.getMinimumSP())) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;

	}

	// If its a baby product and promotion selling price is positive value, then it
	// returns true
	public boolean checkFailConditionForBaby(final Product product, final Double promotionSellingPrice) {
		return ((promotionSellingPrice >= Constants.getDoubleZero())
				&& product.getProductCategory().equals(ProductCategory.BABY_PROD));

	}

	// Constructs and returns a JSONObject with fields as requirements say
	public JSONObject constructPromotionPrice(final Promotion promotion, final String zoneCluster) {
		return new JSONObject().appendField(Constants.getStartDateString(), promotion.getStartDate())
				.appendField(Constants.getEndDateString(), promotion.getEndDate())
				.appendField(Constants.getPromotionSpString(), promotion.getPromotionSellingPrice())
				.appendField(Constants.getPromotionPercentageString(), promotion.getPromotionPercentage())
				.appendField(Constants.getZoneClusterString(), zoneCluster)
				.appendField(Constants.getCancelledDateString(), promotion.getCancelledDate())
				.appendField(Constants.getAppliedDateString(), promotion.getAppliedDate())
				.appendField(Constants.getPromotionId(), promotion.getPromotionId());
	}

	// Function to construct pending promotion JSONObject
	public JSONObject constructPromotionPending(final Promotion promotion, final String zoneCluster,
			final Double price) {
		return new JSONObject().appendField(Constants.getEndDateString(), promotion.getEndDate())
				.appendField(Constants.getStartDateString(), promotion.getStartDate())
				.appendField(Constants.getAppliedDateString(), promotion.getAppliedDate())
				.appendField(Constants.getPromotionId(), promotion.getPromotionId())
				.appendField(Constants.getPriceAtClusterLevel(), price)
				.appendField(Constants.getZoneClusterString(), zoneCluster);
	}

	// Function to construct product details as per requirement
	public JSONObject constructProductDetails(final Product product, final Double basePrice,
			final Double effectivePrice) {
		return new JSONObject().appendField(Constants.getProductNameString(), product.getProductName())
				.appendField(Constants.getProductPrice(), effectivePrice)
				.appendField(Constants.getProductCategory(), product.getProductCategory())
				.appendField(Constants.getProductImage(), product.getProductImagePath())
				.appendField(Constants.getProductUom(), product.getUom())
				.appendField(Constants.getProductBasePriceString(), basePrice)
				.appendField(Constants.getCompanyNameString(), product.getCompanyName());
	}

	// Creates a List of JSONObject that has promotion details for clusters in the
	// given date range
	public List<JSONObject> getRangeOutputCluster(final List<Product> products, final Date startDate,
			final Date endDate) {
		final List<JSONObject> output = new ArrayList<>();
		for (final Product product : products) {
			final List<JSONObject> list = new ArrayList<>();
			final List<AssignProduct> assignProducts = product.getAssignProduct();
			for (final AssignProduct assignProduct : assignProducts) {
				final List<Cluster> clusters = assignProduct.getCluster();
				for (final Cluster cluster : clusters) {
					final List<Promotion> promotions = cluster.getPromotions();
					for (final Promotion promotion : promotions) {
						// Check to see if promotion lies in the date range
						if (this.checkIfInDateRange(startDate, endDate, promotion).booleanValue()) {
							list.add(constructPromotionPrice(promotion,
									assignProduct.getZoneName() + "/" + cluster.getClusterName()));
						}
					}
				}
			}
			output.add(constructProductObject(product, list));
		}
		return output;
	}

	// Creates a List of JSONObject that has promotion details for zones in the
	// given date range
	public List<JSONObject> getRangeOutputZone(final List<Product> products, final Date startDate, final Date endDate) {
		final List<JSONObject> output = new ArrayList<>();
		for (final Product product : products) {
			final List<JSONObject> list = new ArrayList<>();
			final List<AssignProduct> assignProducts = product.getAssignProduct();
			for (final AssignProduct assignProduct : assignProducts) {
				final List<Promotion> promotions = assignProduct.getPromotions();
				for (final Promotion promotion : promotions) {
					// Check to see if promotion lies in the date range
					if (checkIfInDateRange(startDate, endDate, promotion).booleanValue()) {
						list.add(constructPromotionPrice(promotion, assignProduct.getZoneName()));
					}
				}
			}
			output.add(constructProductObject(product, list));
		}
		return output;
	}

	// Construct and returns a JSONObject with specific fields, according to the
	// requirements
	public JSONObject constructProductObject(final Product product, final List<JSONObject> list) {
		return new JSONObject().appendField("productName", product.getProductName())
				.appendField("vendorPrice", product.getProductBasePrice())
				.appendField("vendorName", product.getCompanyName())
				// .appendField("effectivePrice", product.getEffectivePrice())
				.appendField("initialQty", product.getInitialQuantity())
				.appendField("remainingQty", product.getRemainingQuantity())
				.appendField("category", product.getProductCategory())
				.appendField("image", product.getProductImagePath()).appendField("uom", product.getUom())
				.appendField("description", product.getProductDescription()).appendField("list", list);
	}

	// Function to make and return product details in specific format
	public JSONObject constructProductEffectiveObject(final Product product) {
		return new JSONObject().appendField(Constants.getProductNameString(), product.getProductName())
				.appendField(Constants.getProductId(), product.getProductId())
				.appendField(Constants.getStartDateString(), product.getEffectivePriceObj().getStartDate())
				.appendField(Constants.getEndDateString(), product.getEffectivePriceObj().getEndDate());
	}

	// Returns true if current date lies between start,end date and promotion is
	// active
	public boolean checkIfPromotionRunning(final Promotion p, final Date currentDate, final Product product) {
		return p.getStartDate().before(currentDate) && p.getEndDate().after(currentDate)
				&& p.getIsActivated().equals(Boolean.TRUE) && product.getIsPriceFixed().equals(Boolean.FALSE);
	}

	// Returns true if current date lies between start,end date and promotion is
	// active
	public boolean checkIfPromotionRunning(final Promotion p, final Date currentDate) {
		return p.getStartDate().before(currentDate) && p.getEndDate().after(currentDate)
				&& p.getIsActivated().equals(Boolean.TRUE);
	}

	// Returns true if current date lies between start,end date and promotion is
	// active
	public boolean checkIfPromotionRunning2(final Promotion p, final Date currentDate) {
		return (p.getStartDate().before(currentDate) || p.getStartDate().equals(currentDate))
				&& (p.getEndDate().after(currentDate) || p.getEndDate().equals(currentDate))
				&& (p.getWithDrawnDate() == null) && (p.getCancelledDate() == null);
	}

	public boolean checkIfEffectivePriceInEffect(final Product p, final Date currentDate) {
		return (p.getEffectivePriceObj().getStartDate().before(currentDate)
				|| p.getEffectivePriceObj().getStartDate().equals(currentDate))
				&& (p.getEffectivePriceObj().getEndDate().after(currentDate)
						|| p.getEffectivePriceObj().getEndDate().equals(currentDate));
	}

	// Calculate the value of minimum base price that product can have without
	// violating MinimumSP rules
	public Double calculateMinimumBasePriceAllowed(final Product p) {
		Double minBasePrice = 0.0;
		final List<AssignProduct> zones = p.getAssignProduct();
		if (zones == null) {
			return 0.0;
		}
		for (final AssignProduct ap : zones) {
			Double tempBasePriceZone;
			if (ap.getPrice() != 0.0) {
				tempBasePriceZone = (ap.getMinimumSP() * 100) / (100 + ap.getProfitPercentage());
				if (tempBasePriceZone > minBasePrice) {
					minBasePrice = tempBasePriceZone;
				}
			}
			final List<Cluster> clusters = ap.getCluster();
			for (final Cluster c : clusters) {
				final Double tempBasePriceCluster = (ap.getMinimumSP() * 100) / (100 + c.getProfitPercentage());
				if (tempBasePriceCluster > minBasePrice) {
					minBasePrice = tempBasePriceCluster;
				}
			}
		}
		return minBasePrice;
	}

	// Function to update promotion selling prices,used when the base price changes
	public Product updatePromotionSellingPrices(final Product product) {
		final Double BasePrice = product.getProductBasePrice();
		final List<AssignProduct> zones = product.getAssignProduct();
		for (final AssignProduct ap : zones) {
			final List<Promotion> promotionsInZone = ap.getPromotions();
			ap.setPrice(calculatePrice(BasePrice, ap.getProfitPercentage()));
			for (final Promotion p : promotionsInZone) {
				p.setPromotionSellingPrice(calculatePrice(ap.getPrice(), p.getPromotionPercentage()));
			}
			final List<Cluster> clusters = ap.getCluster();
			for (final Cluster c : clusters) {
				final List<Promotion> promotionsInCluster = c.getPromotions();
				c.setPrice(calculatePrice(BasePrice, c.getProfitPercentage()));
				for (final Promotion p : promotionsInCluster) {
					p.setPromotionSellingPrice(calculatePrice(c.getPrice(), p.getPromotionPercentage()));
				}
			}
		}
		return product;
	}

	// Function to set status of promotion according to status passed
	public Promotion setStatus(final Promotion promotion, final PromotionStatus status) {
		if (status.equals(PromotionStatus.APPROVED)) {
			promotion.setIsActivated(true);
		} else if (status.equals(PromotionStatus.REJECTED)) {
			promotion.setIsActivated(false);
		}
		promotion.setStatus(status);
		return promotion;
	}

	// Function to append fields to JSONObject(which has two fields, one indicates
	// if any promotion has been applied to product within last 72 h,and other
	// indicates if there is any promotion applied to the product or not)
	public JSONObject promotionStatus(final List<Promotion> promotions, boolean pflag, boolean dflag,
			final Date appliedDate, final JSONObject jsonObj) {

		for (final Promotion p : promotions) {
			if (!pflag && !dflag) {
				return jsonObj;
			}
			if (dflag && checkInterval(p.getAppliedDate(), appliedDate, p).equals(Boolean.TRUE)) {
				jsonObj.appendField("promotionHasBeenAppliedLast72hours", 1);
				dflag = false;
			}
			if (pflag && checkIfPromotionRunning(p, appliedDate)) {
				jsonObj.appendField("promotionAlreadyApplied", 1);
				pflag = false;
			}
		}
		return jsonObj;
	}

	// Function to set promotion parameters according to who has applied it
	public void checkAndSetPromotions(final Promotion promotion, final List<Promotion> promotions,
			final Product product, final Double promotionSellingPrice) {
		if (promotion.getAddedBy().equalsIgnoreCase("admin/retailer")) {
			setParametersToPromotions(promotions, promotion, product, promotionSellingPrice, true,
					promotion.getAddedBy(), PromotionStatus.APPROVED);
		} else {
			if (checkPromotionWithin72hrs(promotion.getAppliedDate(), product)) {
				setParametersToPromotions(promotions, promotion, product, promotionSellingPrice, false,
						promotion.getAddedBy(), PromotionStatus.PENDING);
			} else {
				setParametersToPromotions(promotions, promotion, product, promotionSellingPrice, true,
						promotion.getAddedBy(), PromotionStatus.APPROVED);
			}
		}
	}

	// Function to check if the quantity assigned(in bulk) exceeds what's remaining
	// for that product
	public boolean quantityCheck(final AssignProduct productDetails, final int remainingQuantity) {

		int sum = productDetails.getQuantityAssigned();
		final List<Cluster> clusterDets = productDetails.getCluster();
		for (final Cluster cluster : clusterDets) {

			sum += cluster.getQuantityAssigned();
		}
		if (sum <= remainingQuantity) {
			return true;
		}
		return false;

	}

	// Function used to set quantity, update remaining qunaitity, and set profit
	// percentage on zone level depending on flag sent
	public void setQuantityAssignedToZone(final Integer finalQuantityAssigned, final Product finalProduct,
			final AssignProduct finalZone, final Double finalProfitPercentage, final boolean flag) {
		if (finalQuantityAssigned > finalProduct.getRemainingQuantity()) {
			throw new QuantityInsufficient(Constants.getQuantityInsufficientString());
		} else {
			if (flag) {
				finalZone.setQuantityAssigned(finalQuantityAssigned + finalZone.getQuantityAssigned());
				finalProduct.setRemainingQuantity(finalProduct.getRemainingQuantity() - finalQuantityAssigned);
			} else {
				finalZone.setQuantityAssigned(finalQuantityAssigned);
				finalProduct.setRemainingQuantity(finalProduct.getRemainingQuantity() - finalQuantityAssigned);
				finalZone.setProfitPercentage(finalProfitPercentage);
			}
		}
	}

	// Function used to set quantity, update remaining qunaitity, and set profit
	// percentage on cluster level depending on flag sent
	public void setQuantityAssignedToCluster(final Integer finalQuantityAssigned, final Product finalProduct,
			final Cluster finalCluster, final Double finalProfitPercentage, final boolean flag) {
		if (finalQuantityAssigned > finalProduct.getRemainingQuantity()) {
			throw new QuantityInsufficient(Constants.getQuantityInsufficientString());
		} else {
			if (flag) {
				finalCluster.setQuantityAssigned(finalQuantityAssigned + finalCluster.getQuantityAssigned());
				finalProduct.setRemainingQuantity(finalProduct.getRemainingQuantity() - finalQuantityAssigned);
			} else {
				finalCluster.setQuantityAssigned(finalQuantityAssigned);
				finalProduct.setRemainingQuantity(finalProduct.getRemainingQuantity() - finalQuantityAssigned);
				finalCluster.setProfitPercentage(finalProfitPercentage);
			}
		}
	}
}