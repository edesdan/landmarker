package com.edesdan.landmarker.wikipedia.control;

import android.util.Log;

import com.edesdan.landmarker.wikipedia.boundary.WikipediaGeoSearchJSONResponseParser;
import com.edesdan.landmarker.wikipedia.boundary.WikipediaGeoSearchResponseReader;
import com.edesdan.landmarker.wikipedia.boundary.WikipediaService;
import com.edesdan.landmarker.wikipedia.entity.ClientConfig;
import com.edesdan.landmarker.wikipedia.entity.WikipediaPlace;

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

    private final WikipediaGeoSearchResponseReader wikipediaGeoSearchResponseReader = new WikipediaGeoSearchJSONResponseParser();
    private final ClientConfig config;

    public WikipediaClient(ClientConfig config) {
        this.config = config;
    }


    @Override
    public List<WikipediaPlace> getPlacesNearBy(double lat, double lng) throws WikipediaConnectionException {

        InputStream inputStream;

        try {
            //https://www.mediawiki.org/wiki/API:Showing_nearby_wiki_information
            String query = new WikipediaQuery.Builder("query")
                    .format("json")
                    .formatVersion("2")
                    .prop("coordinates", "extracts")
                    .generator("geosearch")
                    .colimit(20)
                    .exlimit("max")
                    .exIntro()
                    .explaintext()
                    .ggscoord(lat, lng)
                    .ggsradius("10000")
                    .ggslimit("20")
                    .codistancefrompoint(lat, lng)
                    .build();

            URL url = buildURL(query);
            inputStream = doRequest(url);

            return wikipediaGeoSearchResponseReader.readWikipediaPlaces(inputStream);


        } catch (UnsupportedEncodingException | WikipediaReaderException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //we wants to notify the user in case there are connection problems!
            throw new WikipediaConnectionException("Error performing the http request", e);
        }


        return Collections.emptyList();

    }


    private URL buildURL(String query) throws MalformedURLException {

        StringBuilder urlBuilder = new StringBuilder(ENDPOINT_URL).append(query);
        return new URL(urlBuilder.toString());
    }


    private InputStream doRequest(URL url) throws IOException {
        Log.d(TAG, "--> doRequest: " + url);

        //TODO remove just for test
        Log.d(TAG, "Search radius: " + config.getSearchRadius());

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
