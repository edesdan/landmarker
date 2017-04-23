
package com.androidexperiments.landmarker.wikipedia.entity.geosearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Limits {

    @SerializedName("extracts")
    @Expose
    private Integer extracts;

    public Integer getExtracts() {
        return extracts;
    }

    public void setExtracts(Integer extracts) {
        this.extracts = extracts;
    }

}
