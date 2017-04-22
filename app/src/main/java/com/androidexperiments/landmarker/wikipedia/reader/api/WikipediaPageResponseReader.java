package com.androidexperiments.landmarker.wikipedia.reader.api;

import com.androidexperiments.landmarker.wikipedia.client.boundary.WikipediaPlaceInfo;

import java.io.InputStream;


/**
 * Created by danieleds on 07/12/2015.
 */
public interface WikipediaPageResponseReader {

    /**
     * Read a page content from an input stream.
     *
     * @param in
     * @return a WikipediaPlaceInfo .
     */
    WikipediaPlaceInfo readPlaceInfo(InputStream in) throws WikipediaReaderException;
}
