package com.androidexperiments.landmarker.wikipedia.reader.impl;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.androidexperiments.landmarker.wikipedia.client.boundary.WikipediaPlaceInfo;
import com.androidexperiments.landmarker.wikipedia.client.boundary.WikipediaService;
import com.androidexperiments.landmarker.wikipedia.client.entity.WikipediaPlaceInfoImpl;
import com.androidexperiments.landmarker.wikipedia.reader.api.WikipediaPageResponseReader;
import com.androidexperiments.landmarker.wikipedia.reader.api.WikipediaReaderException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by danieleds on 07/12/2015.
 * <p/>
 * Parse the Json response from Wikipedia.
 * <p/>
 * JSON base structure:
 * {
 * "batchcomplete":"",
 * "query":{
 * "pages":{
 * "49603":{
 * "pageid":49603,
 * "ns":0,
 * "title":"Colosseum"
 * }
 * }
 * }
 * }
 */
public class WikipediaPageJSONResponseParser implements WikipediaPageResponseReader {

    private static final String TAG = WikipediaPageJSONResponseParser.class.getSimpleName();


    /**
     * Read a page content from an input stream.
     *
     * @param in
     * @return a WikipediaPlaceInfo containing the content of the requested page.
     */
    @Override
    public WikipediaPlaceInfo readPlaceInfo(InputStream in) throws WikipediaReaderException {
        Log.d(TAG, "readPlaceInfo()");
        WikipediaPlaceInfo result = null;
        JsonReader reader = null;

        //try with resources statement is supported only since API level 19
        try {

            reader = new JsonReader(new InputStreamReader(in, WikipediaService.ENCODING));

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals(WikipediaQueryResponseFields.QUERY)) {
                    result = readQuery(reader);
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();

        } catch (UnsupportedEncodingException e) {
            throw new WikipediaReaderException("Unsupported encoding: " + WikipediaService.ENCODING, e);
        } catch (IOException e) {
            throw new WikipediaReaderException(e.getMessage() + WikipediaService.ENCODING, e);
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
            }
        }


        return result;
    }

    private WikipediaPlaceInfo readQuery(JsonReader reader) throws IOException {

        WikipediaPlaceInfo result = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(WikipediaQueryResponseFields.PAGES)) {
                result = readPages(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return result;
    }

    private WikipediaPlaceInfo readPages(JsonReader reader) throws IOException {
        WikipediaPlaceInfo result = null;

        reader.beginObject();
        while (reader.hasNext()) {

            if (reader.peek() != JsonToken.NULL) {
                String name = reader.nextName();
                result = readFirstPage(reader);
            }
            break; //Note here that we are just interested in the first page
        }
        reader.endObject();

        return result;

    }

    private WikipediaPlaceInfo readFirstPage(JsonReader reader) throws IOException {
        WikipediaPlaceInfoImpl wikipediaPlaceInfo = new WikipediaPlaceInfoImpl();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(WikipediaQueryResponseFields.PAGE_ID)) {
                String pageId = String.valueOf(reader.nextLong());
                wikipediaPlaceInfo.setPageId(pageId);
            } else if (name.equals(WikipediaQueryResponseFields.EXTRACT)) {
                String extract = reader.nextString();
                wikipediaPlaceInfo.setDescription(extract);
            } else if (name.equals(WikipediaQueryResponseFields.FULLURL)) {
                String url = reader.nextString();
                wikipediaPlaceInfo.setPageUriString(url);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return wikipediaPlaceInfo;

    }


}