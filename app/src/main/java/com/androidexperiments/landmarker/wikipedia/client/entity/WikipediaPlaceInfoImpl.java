package com.androidexperiments.landmarker.wikipedia.client.entity;


import com.androidexperiments.landmarker.wikipedia.client.boundary.WikipediaPlaceInfo;

/**
 * Created by daniele on 11/06/16.
 */
public class WikipediaPlaceInfoImpl implements WikipediaPlaceInfo {

    private String description;

    private String pageUriString;

    private String pageId;


    /**
     * Get the html text extracted from the Wikipedia page.
     *
     * @return
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Get the pageUriString of the Wikipedia page
     *
     * @return
     */
    @Override
    public String getPageUriString() {
        return pageUriString;
    }

    /**
     * Get page id
     *
     * @return
     */
    @Override
    public String getPageId() {
        return pageId;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public void setPageUriString(String pageUriString) {
        this.pageUriString = pageUriString;
    }

    @Override
    public String toString() {
        return "WikipediaPlaceInfoImpl{" +
                "description='" + description + '\'' +
                ", pageUriString='" + pageUriString + '\'' +
                ", pageId='" + pageId + '\'' +
                '}';
    }
}
