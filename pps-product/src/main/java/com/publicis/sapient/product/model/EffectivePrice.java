package com.publicis.sapient.product.model;

import java.util.Date;

public class EffectivePrice {

    private Date startDate;
    private Date endDate;
    private Double effectivePercentage;
    private Double effectivePrice;
    private Boolean flag;

    public Boolean getFlag() {
        return this.flag;
    }

    public void setFlag(final Boolean flag) {
        this.flag = flag;
    }

    public EffectivePrice() {
        // default constructor
    }

    public Double getEffectivePrice() {
        return this.effectivePrice;
    }

    public void setEffectivePrice(final Double effectivePrice) {
        this.effectivePrice = effectivePrice;
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

    public Double getEffectivePercentage() {
        return this.effectivePercentage;
    }

    public void setEffectivePercentage(final Double effectivePercentage) {
        this.effectivePercentage = effectivePercentage;
    }

    @Override
    public String toString() {
        return "EffectivePrice [startDate=" + this.startDate + ", endDate=" + this.endDate + ", effectivePercentage="
                + this.effectivePercentage + ", effectivePrice=" + this.effectivePrice + ", flag=" + this.flag + "]";
    }

}
