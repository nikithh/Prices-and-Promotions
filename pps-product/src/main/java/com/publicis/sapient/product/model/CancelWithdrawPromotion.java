package com.publicis.sapient.product.model;

import java.util.Date;
import javax.validation.constraints.NotNull;

public class CancelWithdrawPromotion {

    private String zoneName;
    private String clusterName;
    @NotNull
    private Date date;

    public CancelWithdrawPromotion() {
        // default constructor
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

    public Date getDate() {
        return this.date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CancelWithdrawPromotion [zoneName=" + this.zoneName + ", clusterName=" + this.clusterName + ", date="
                + this.date + "]";
    }

}
