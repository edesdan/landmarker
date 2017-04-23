package com.androidexperiments.landmarker.wikipedia.control;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class WikipediaQueryTest {

    @Test
    public void testQueryBuilder() throws Exception {

        final String expectedQuery = "?action=query&generator=geosearch&explaintext&exintro&prop=coordinates|extracts&colimit=20&codistancefrompoint=37.786952|-122.399523&exlimit=max&ggscoord=37.786952|-122.399523&ggsradius=10000&ggslimit=20&format=json&formatversion=2";

        double lat = 37.786952;
        double lng = -122.399523;

        String wikipediaQuery = new WikipediaQuery.Builder("query")
                .format("json")
                .formatVersion("2") // according to wikipedia it could change without warning
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

        Assert.assertThat(expectedQuery, is(equalTo(wikipediaQuery)));

    }
}