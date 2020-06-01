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
import java.util.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author prejayak
 *
 */

public class Promotion implements Serializable {

    private Integer promotionId;
    @NotNull
    @Max(value = 0)
    @Min(value = -100)
    private Double promotionPercentage;
    private Double promotionSellingPrice;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @NotNull
    private Date appliedDate;
    private Date cancelledDate;
    private Date withDrawnDate;
    private Boolean isActivated;
    private String addedBy;
    private PromotionStatus status;

    public Promotion() {
        // default contructor
    }

    public String getAddedBy() {
        return this.addedBy;
    }

    public void setAddedBy(final String addedBy) {
        this.addedBy = addedBy;
    }

    public PromotionStatus getStatus() {
        return this.status;
    }

    public void setStatus(final PromotionStatus status) {
        this.status = status;
    }

    public Promotion(@NotNull @Max(0) @Min(-100) final Double promotionPercentage,
            @NotNull final Date startDate,
            @NotNull final Date endDate,
            @NotNull final Date appliedDate,
            @NotNull final String addedBy
            ) {
        super();
        this.promotionPercentage = promotionPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.appliedDate = appliedDate;
        this.addedBy=addedBy;
        
    }

    public Integer getPromotionId() {
        return this.promotionId;
    }

    public void setPromotionId(final Integer promotionId) {
        this.promotionId = promotionId;
    }

    public Double getPromotionPercentage() {
        return this.promotionPercentage;
    }

    public void setPromotionPercentage(final Double promotionPercentage) {
        this.promotionPercentage = promotionPercentage;
    }

    public Double getPromotionSellingPrice() {
        return this.promotionSellingPrice;
    }

    public void setPromotionSellingPrice(final Double promotionSellingPrice) {
        this.promotionSellingPrice = promotionSellingPrice;
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

    public Date getCancelledDate() {
        return this.cancelledDate;
    }

    public void setCancelledDate(final Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public Date getWithDrawnDate() {
        return this.withDrawnDate;
    }

    public void setWithDrawnDate(final Date withDrawnDate) {
        this.withDrawnDate = withDrawnDate;
    }

    public Date getAppliedDate() {
        return this.appliedDate;
    }

    public void setAppliedDate(final Date appliedDate) {
        this.appliedDate = appliedDate;
    }

    public Boolean getIsActivated() {
        return this.isActivated;
    }

    public void setIsActivated(final Boolean isActivated) {
        this.isActivated = isActivated;
    }

	@Override
	public String toString() {
		return "Promotion [promotionId=" + promotionId + ", promotionPercentage=" + promotionPercentage
				+ ", promotionSellingPrice=" + promotionSellingPrice + ", startDate=" + startDate + ", endDate="
				+ endDate + ", appliedDate=" + appliedDate + ", cancelledDate=" + cancelledDate + ", withDrawnDate="
				+ withDrawnDate + ", isActivated=" + isActivated + ", addedBy=" + addedBy + ", status=" + status + "]";
	}

    

}
