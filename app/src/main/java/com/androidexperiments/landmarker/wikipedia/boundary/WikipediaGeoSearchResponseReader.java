package com.androidexperiments.landmarker.wikipedia.boundary;

import com.androidexperiments.landmarker.wikipedia.control.WikipediaReaderException;
import com.androidexperiments.landmarker.wikipedia.entity.WikipediaPlace;

import java.io.InputStream;
import java.util.List;

/**
 * Created by danieleds on 14/12/2015.
 */
public interface WikipediaGeoSearchResponseReader {

    /**
     * Read data from an input stream and fill a List with WikipediaPlace objects.
     *
     * @param in input stream object from which to read data.
     * @return a list of wikipedia places, always not null but can be empty.
     */
    List<WikipediaPlace> readWikipediaPlaces(InputStream in) throws WikipediaReaderException;

}
