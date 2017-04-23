
package com.edesdan.landmarker.wikipedia.entity.geosearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coordinate {

    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("primary")
    @Expose
    private String primary;
    @SerializedName("globe")
    @Expose
    private String globe;
    @SerializedName("dist")
    @Expose
    private Double dist;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getGlobe() {
        return globe;
    }

    public void setGlobe(String globe) {
        this.globe = globe;
    }

    public Double getDist() {
        return dist;
    }

    public void setDist(Double dist) {
        this.dist = dist;
    }

}
