package com.androidexperiments.landmarker.wikipedia.client.boundary;

/**
 * Created by daniele on 11/06/16.
 */
public interface WikipediaPlaceInfo {

    /**
     * Get the html text extracted from the Wikipedia page.
     *
     * @return
     */
    String getDescription();

    /**
     * Get the url of the Wikipedia page
     *
     * @return
     */
    String getPageUriString();

    /**
     * Get page id
     * @return
     */
    String getPageId();

}
