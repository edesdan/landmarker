package com.androidexperiments.landmarker.wikipedia.client.control;

import android.util.Log;

import com.androidexperiments.landmarker.wikipedia.client.boundary.WikipediaConnectionException;
import com.androidexperiments.landmarker.wikipedia.client.boundary.WikipediaPlaceInfo;
import com.androidexperiments.landmarker.wikipedia.client.boundary.WikipediaService;
import com.androidexperiments.landmarker.wikipedia.client.entity.ClientConfig;
import com.androidexperiments.landmarker.wikipedia.client.entity.WikipediaPlace;
import com.androidexperiments.landmarker.wikipedia.reader.api.WikipediaGeoSearchResponseReader;
import com.androidexperiments.landmarker.wikipedia.reader.api.WikipediaImageURLsResponseReader;
import com.androidexperiments.landmarker.wikipedia.reader.api.WikipediaPageResponseReader;
import com.androidexperiments.landmarker.wikipedia.reader.api.WikipediaReaderException;
import com.androidexperiments.landmarker.wikipedia.reader.impl.WikipediaGeoSearchJSONResponseParser;
import com.androidexperiments.landmarker.wikipedia.reader.impl.WikipediaImageURLsJSONResponseParser;
import com.androidexperiments.landmarker.wikipedia.reader.impl.WikipediaPageJSONResponseParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;


/**
 * Created by danieleds on 07/12/2015.
 */
public class WikipediaClient implements WikipediaService {

    public static final String TAG = WikipediaClient.class.getSimpleName();

    private final WikipediaPageResponseReader wikipediaJSONResponseReader = new WikipediaPageJSONResponseParser();
    private final WikipediaGeoSearchResponseReader wikipediaGeoSearchResponseReader = new WikipediaGeoSearchJSONResponseParser();
    private final WikipediaImageURLsResponseReader wikipediaImageURLsResponseReader = new WikipediaImageURLsJSONResponseParser();

    private final ClientConfig config;

    public WikipediaClient(ClientConfig config) {
        this.config = config;
    }


    @Override
    public WikipediaPlaceInfo getPlaceInfoByPageId(String pageId) throws WikipediaConnectionException {
        Log.d(TAG, "--> getPlaceInfoByPageId for: " + pageId);

        InputStream in;

        try {
            String query = new WikipediaQuery.Builder("query")
                    .prop("extracts","info")
                    .inprop("url")
                    .format("json")
                    .exsectionformat("raw")
                    .pageids(pageId)
                    .build();
            URL url = buildURL(query);
            in = doRequest(url);
            WikipediaPlaceInfo placeInfo = wikipediaJSONResponseReader.readPlaceInfo(in);
            return placeInfo;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //we wants to notify the user in case there are connection problems!
            throw new WikipediaConnectionException("Error performing the http request", e);
        } catch (WikipediaReaderException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<WikipediaPlace> getPlacesNearBy(double lat, double lng) throws WikipediaConnectionException {

        InputStream in;

        try {
            //https://www.mediawiki.org/wiki/API:Showing_nearby_wiki_information
            String query = new WikipediaQuery.Builder("query").
                    list("geosearch").
                    gscoord(lat, lng).
                    gsradius(config.getSearchRadius()).
                    gslimit(config.getResultsLimit()).
                    format("json").
                    build();
            URL url = buildURL(query);
            in = doRequest(url);
            List<WikipediaPlace> placesNearBy = wikipediaGeoSearchResponseReader.readWikipediaPlaces(in);
            return placesNearBy;


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //we wants to notify the user in case there are connection problems!
            throw new WikipediaConnectionException("Error performing the http request", e);
        } catch (WikipediaReaderException e) {
            e.printStackTrace();
        }


        return Collections.emptyList();

    }

    @Override
    public List<URL> getImageURLsForPageId(String pageId) throws WikipediaConnectionException {

        InputStream in;

        String query = new WikipediaQuery.Builder("query").
                prop("imageinfo").
                iiprop("url","mime","size").
                generator("images").
                gimlimit("max").
                pageids(pageId).
                format("json").
                build();

        URL url = null;
        try {

            url = buildURL(query);
            in = doRequest(url);
            List<URL> imageURLs = wikipediaImageURLsResponseReader.readWikipediaImageURLs(in);
            return imageURLs;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (WikipediaReaderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }


    private URL buildURL(String query) throws MalformedURLException {

        StringBuilder urlBuilder = new StringBuilder(ENDPOINT_URL).append(query);
        return new URL(urlBuilder.toString());
    }


    private InputStream doRequest(URL url) throws IOException {
        Log.d(TAG, "--> doRequest: " + url);

        //TODO remove just for test
        Log.d(TAG,"Search radius: " + config.getSearchRadius());

        InputStream data;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // expect HTTP 200 OK
        int httpResultCode = connection.getResponseCode();
        if (httpResultCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Http result code is not 200 OK! Result code: " + String.valueOf(httpResultCode));
        }
        data = connection.getInputStream();

        return data;

    }


}
