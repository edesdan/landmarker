package com.androidexperiments.landmarker.wikipedia.reader.impl;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.androidexperiments.landmarker.wikipedia.client.boundary.WikipediaService;
import com.androidexperiments.landmarker.wikipedia.client.entity.WikipediaPlace;
import com.androidexperiments.landmarker.wikipedia.reader.api.WikipediaGeoSearchResponseReader;
import com.androidexperiments.landmarker.wikipedia.reader.api.WikipediaReaderException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by danieleds on 14/12/2015.
 * {
 * "batchcomplete": "",
 * "query": {
 * "geosearch": [
 * {
 * "pageid": 18618509,
 * "ns": 0,
 * "title": "Wikimedia Foundation",
 * "lat": 37.78697,
 * "lon": -122.399677,
 * "dist": 13.7,
 * "primary": ""
 * },
 * {
 * "pageid": 9292891,
 * "ns": 0,
 * "title": "140 New Montgomery",
 * "lat": 37.787,
 * "lon": -122.4,
 * "dist": 42.3,
 * "primary": ""
 * },
 * {
 * "pageid": 40377676,
 * "ns": 0,
 * "title": "New Montgomery Street",
 * "lat": 37.78729,
 * "lon": -122.40033,
 * "dist": 80.3,
 * "primary": ""
 * }
 * ]
 * }
 * }
 */
public class WikipediaGeoSearchJSONResponseParser implements WikipediaGeoSearchResponseReader {

    private static final String TAG = WikipediaGeoSearchJSONResponseParser.class.getSimpleName();

    private static final List EMPTY_LIST = Collections.emptyList();

    @Override
    public List<WikipediaPlace> readWikipediaPlaces(InputStream in) throws WikipediaReaderException {
        Log.d(TAG, "readWikipediaPlaces()");
        JsonReader reader = null;

        //try with resources statement is supported only since API level 19
        try {
            reader = new JsonReader(new InputStreamReader(in, WikipediaService.ENCODING));

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals(WikipediaQueryResponseFields.QUERY)) {
                    return readQuery(reader);
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();

        } catch (UnsupportedEncodingException e) {
            throw new WikipediaReaderException("Unsupported encoding: " + WikipediaService.ENCODING, e);
        } catch (IOException e) {
            throw new WikipediaReaderException(e.getMessage(), e);
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
            }
        }


        return EMPTY_LIST;

    }



    private List<WikipediaPlace> readQuery(JsonReader reader) throws IOException {

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(WikipediaQueryResponseFields.GEO_SEARCH)) {
                return readPlacesArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return EMPTY_LIST;
    }


    private List<WikipediaPlace> readPlacesArray(JsonReader reader) throws IOException {

        List<WikipediaPlace> places = new ArrayList();

        reader.beginArray();
        while (reader.hasNext() && reader.peek() != JsonToken.NULL) {
            places.add(readPlace(reader));
        }
        reader.endArray();

        return places;
    }


    public WikipediaPlace readPlace(JsonReader reader) throws IOException {
        long pageId = -1;
        String title = null;
        double lat = -1, lng = -1;
        double dist = -1;


        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(WikipediaQueryResponseFields.PAGE_ID)) {
                pageId = reader.nextLong();
            } else if (name.equals(WikipediaQueryResponseFields.TITLE)) {
                title = reader.nextString();
            } else if (name.equals(WikipediaQueryResponseFields.LATITUDE)) {
                lat = reader.nextDouble();
            } else if (name.equals(WikipediaQueryResponseFields.LONGITUDE)) {
                lng = reader.nextDouble();
            } else if (name.equals(WikipediaQueryResponseFields.DISTANCE)) {
                dist = reader.nextDouble();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new WikipediaPlace(pageId, title, lat, lng, dist);
    }


}
