package com.edesdan.landmarker.wikipedia.entity;

import android.content.SharedPreferences;

/**
 * Created by daniele on 20/02/16.
 */
public class ClientConfig {

    private static final String TAG = ClientConfig.class.getSimpleName();


    private SharedPreferences sharedPref;

    private final String SEARCH_RADIUS = "10000"; //Constant

    private final String RESULT_LIMIT_DEFAULT_VALUE = "20"; // do not change this unless you change also the query


    public ClientConfig(SharedPreferences sharedPref) {
        this.sharedPref = sharedPref;

    }

    public ClientConfig() {
    }

    /**
     * Search radius in meters (10-10000). This parameter is required.
     */
    public String getSearchRadius() {

        return SEARCH_RADIUS;
    }

    /**
     * Maximum number of pages to return. No more than 500 (5000 for bots) allowed.
     *
     * @return
     */
    public String getResultsLimit() {

        return  RESULT_LIMIT_DEFAULT_VALUE;
    }
}
