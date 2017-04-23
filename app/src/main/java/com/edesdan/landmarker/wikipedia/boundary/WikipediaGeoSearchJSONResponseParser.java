package com.edesdan.landmarker.wikipedia.boundary;

import android.util.Log;

import com.edesdan.landmarker.wikipedia.control.WikipediaReaderException;
import com.edesdan.landmarker.wikipedia.entity.WikipediaPlace;
import com.edesdan.landmarker.wikipedia.entity.geosearch.Page;
import com.edesdan.landmarker.wikipedia.entity.geosearch.WikipediaGeoSearchResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class WikipediaGeoSearchJSONResponseParser implements WikipediaGeoSearchResponseReader {

    private static final String TAG = WikipediaGeoSearchJSONResponseParser.class.getSimpleName();

    private final Gson gson = new Gson();

    @Override
    public List<WikipediaPlace> readWikipediaPlaces(InputStream data) throws WikipediaReaderException {
        Log.d(TAG, "readWikipediaPlaces()");

        List<WikipediaPlace> wikipediaPlaces = null;

        final BufferedReader reader = new BufferedReader(new InputStreamReader(data));

        WikipediaGeoSearchResponse response = gson.fromJson(reader, WikipediaGeoSearchResponse.class);

        List<Page> pages = null;

        if (response != null) {
            if (response.getQuery() != null) {
                pages = response.getQuery().getPages();
            }
        }

        if (pages != null) {
            wikipediaPlaces = new ArrayList<>();

            for (Page p : pages) {
                wikipediaPlaces.add(new WikipediaPlace(p));
            }

        }

        return wikipediaPlaces;

    }


}
