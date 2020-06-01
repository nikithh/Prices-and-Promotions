package com.publicis.sapient.product.model;

import java.io.Serializable;
import java.util.Date;

public class FilterOnDate implements Serializable{

    private Date startDate;
    private Date endDate;
    private Date currentDate;
    private String clusterName;
    private String zoneName;

    public FilterOnDate() {
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

    public Date getCurrentDate() {
        return this.currentDate;
    }

    public void setCurrentDate(final Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getClusterName() {
        return this.clusterName;
    }

    public void setClusterName(final String clusterName) {
        this.clusterName = clusterName;
    }

    public String getZoneName() {
        return this.zoneName;
    }

    public void setZoneName(final String zoneName) {
        this.zoneName = zoneName;
    }

    @Override
    public String toString() {
        return "FilterOnDate [startDate=" + this.startDate + ", endDate=" + this.endDate + ", currentDate=" + this.currentDate
                + ", clusterName=" + this.clusterName + ", zoneName=" + this.zoneName + "]";
    }

}
