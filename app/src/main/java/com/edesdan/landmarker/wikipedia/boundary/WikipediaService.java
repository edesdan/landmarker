package com.edesdan.landmarker.wikipedia.boundary;

import com.edesdan.landmarker.wikipedia.control.WikipediaConnectionException;
import com.edesdan.landmarker.wikipedia.entity.WikipediaPlace;

import java.util.List;
import java.util.Locale;


/**
 * Created by danieleds on 04/12/2015.
 * This is the main entry point for the wikipedia APIs.
 * All the methods here are intended to communicate with the wikipedia web service (remote endpoint).
 * The documentation for using this api is at http://www.mediawiki.org/wiki/API.
 * The specific documentation for the English Wikipedia(the mediawiki api can be called on all Wikimedia sites, so not just Wikipedia itself, but also Wikimedia Commons etc..) is at http://en.wikipedia.org/w/api.php.
 * The Wikipedia API makes it possible to interact with Wikipedia/Mediawiki through a webservice instead of the normal browserbased web interface.
 */
public interface WikipediaService {

    String ENCODING = "UTF-8";

    String ENDPOINT_URL = "https://" + Locale.getDefault().getLanguage() + ".wikipedia.org/w/api.php";

    /**
     * Retrive a list of Wikipedia places that are around the coordinates passed as parameters.
     *
     * @param lat latitude of location
     * @param lng longitude of location
     * @return a list of places (@see WikipediaPlace )
     */
    List<WikipediaPlace> getPlacesNearBy(double lat, double lng) throws WikipediaConnectionException;


}
