
package com.androidexperiments.landmarker.wikipedia.entity.geosearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WikipediaGeoSearchResponse {

    @SerializedName("batchcomplete")
    @Expose
    private Boolean batchcomplete;
    @SerializedName("query")
    @Expose
    private Query query;
    @SerializedName("limits")
    @Expose
    private Limits limits;

    public Boolean getBatchcomplete() {
        return batchcomplete;
    }

    public void setBatchcomplete(Boolean batchcomplete) {
        this.batchcomplete = batchcomplete;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Limits getLimits() {
        return limits;
    }

    public void setLimits(Limits limits) {
        this.limits = limits;
    }

}
