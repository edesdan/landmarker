package com.androidexperiments.landmarker.wikipedia.boundary;

import com.androidexperiments.landmarker.wikipedia.control.WikipediaQuery;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static com.androidexperiments.landmarker.wikipedia.boundary.WikipediaService.ENDPOINT_URL;


public class WikipediaGeoSearchJSONResponseParserTest {


    @Test
    public void testParsingResponse() throws Exception {


    }

    private URL buildQuery() throws MalformedURLException {


        double lat = 37.786952;
        double lng = -122.399523;

        String wikipediaQuery = new WikipediaQuery.Builder("query")
                .format("json")
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

        return buildURL(wikipediaQuery);


    }


    private URL buildURL(String query) throws MalformedURLException {

        StringBuilder urlBuilder = new StringBuilder(ENDPOINT_URL).append(query);
        return new URL(urlBuilder.toString());
    }

}