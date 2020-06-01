package com.publicis.sapient.product.model;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddPromotion implements Serializable{

    private String zoneName;
    private String clusterName;
    @Min(value = -100)
    @Max(value = 0)
    private Double promotionPercentage;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @NotNull
    private Date appliedDate;
    @NotNull
    private String addedBy;
  

    public AddPromotion() {
        // default constructor
    }

    public AddPromotion(final String zoneName,
            final String clusterName,
            @Min(-100) @Max(0) final Double promotionPercentage,
            @NotNull final Date startDate,
            @NotNull final Date endDate,
            @NotNull final Date appliedDate,
            @NotNull final String addedBy) {
        super();
        this.zoneName = zoneName;
        this.clusterName = clusterName;
        this.promotionPercentage = promotionPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.appliedDate = appliedDate;
        this.addedBy=addedBy;
    }

    public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public String getZoneName() {
        return this.zoneName;
    }

    public void setZoneName(final String zoneName) {
        this.zoneName = zoneName;
    }

    public String getClusterName() {
        return this.clusterName;
    }

    public void setClusterName(final String clusterName) {
        this.clusterName = clusterName;
    }

    public Double getPromotionPercentage() {
        return this.promotionPercentage;
    }

    public void setPromotionPercentage(final Double promotionPercentage) {
        this.promotionPercentage = promotionPercentage;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public Date getAppliedDate() {
        return this.appliedDate;
    }

    public void setAppliedDate(final Date appliedDate) {
        this.appliedDate = appliedDate;
    }

	@Override
	public String toString() {
		return "AddPromotion [zoneName=" + zoneName + ", clusterName=" + clusterName + ", promotionPercentage="
				+ promotionPercentage + ", startDate=" + startDate + ", endDate=" + endDate + ", appliedDate="
				+ appliedDate + ", addedBy=" + addedBy + "]";
	}

   

}
