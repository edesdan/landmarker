package com.androidexperiments.landmarker.wikipedia.reader.impl;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.androidexperiments.landmarker.wikipedia.client.boundary.WikipediaService;
import com.androidexperiments.landmarker.wikipedia.reader.api.WikipediaImageURLsResponseReader;
import com.androidexperiments.landmarker.wikipedia.reader.api.WikipediaReaderException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * {
 * "batchcomplete": "",
 * "limits": {
 * "images": 500
 * },
 * "query": {
 * "pages": {
 * "13533595": {
 * "pageid": 13533595,
 * "ns": 6,
 * "title": "File:The Spire.jpg",
 * "imagerepository": "local",
 * "imageinfo": [
 * {
 * "url": "https://upload.wikimedia.org/wikipedia/en/2/2f/The_Spire.jpg",
 * "descriptionurl": "https://en.wikipedia.org/wiki/File:The_Spire.jpg",
 * "descriptionshorturl": "https://en.wikipedia.org/w/index.php?curid=13533595",
 * "mime": "image/jpeg"
 * }
 * ]
 * },
 * "33285577": {
 * "pageid": 33285577,
 * "ns": 6,
 * "title": "File:Commons-logo.svg",
 * "imagerepository": "local",
 * "imageinfo": [
 * {
 * "url": "https://upload.wikimedia.org/wikipedia/en/4/4a/Commons-logo.svg",
 * "descriptionurl": "https://en.wikipedia.org/wiki/File:Commons-logo.svg",
 * "descriptionshorturl": "https://en.wikipedia.org/w/index.php?curid=33285577",
 * "mime": "image/svg+xml"
 * }
 * ]
 * },
 * }
 * }
 * }
 */
public class WikipediaImageURLsJSONResponseParser implements WikipediaImageURLsResponseReader {

    private static final String TAG = WikipediaGeoSearchJSONResponseParser.class.getSimpleName();

    private static final List EMPTY_LIST = Collections.emptyList();

    private static final int MAX_NUMBER_OF_PAGES_TO_READ = 1;

    private static final long MAX_IMAGE_SIZE_IN_BYTES_SUPPORTED = 5 /*MB*/ * 1048576; //TODO add real tests with different size!!

    @Override
    public List<URL> readWikipediaImageURLs(InputStream in) throws WikipediaReaderException {
        Log.d(TAG, "readWikipediaImageURLs()");
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


    private List<URL> readQuery(JsonReader reader) throws IOException, WikipediaReaderException {

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(WikipediaQueryResponseFields.PAGES)) {
                return readPages(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return EMPTY_LIST;
    }


    private List<URL> readPages(JsonReader reader) throws IOException, WikipediaReaderException {
        URL url = null;
        List<URL> urlList = new ArrayList<>();
        int pageReadCounter = 0;

        reader.beginObject();
        while (reader.hasNext()) {

            if (reader.peek() != JsonToken.NULL) {
                reader.nextName();
                try {
                    url = readURLFromPage(reader);
                    if (url != null) {
                        urlList.add(url);
                        pageReadCounter++;
                    }
                } catch (WikipediaReaderException e) {
                    //Do not add to the list, do not increment the counter
                }
            }

            if (pageReadCounter >= MAX_NUMBER_OF_PAGES_TO_READ)
                return urlList;
        }
        reader.endObject();

        return urlList;

    }

    /**
     * It's an array but it seems to contain just one element
     *
     * @param reader
     * @return
     * @throws IOException
     */
    private URL readURLFromPage(JsonReader reader) throws IOException, WikipediaReaderException {

        URL url = null;
        reader.beginObject();

        while (reader.hasNext()) {

            if (reader.nextName().equals(WikipediaQueryResponseFields.IMAGE_INFO)) {
                url = readImageInfoArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return url;

    }

    private URL readImageInfoArray(JsonReader reader) throws IOException, WikipediaReaderException {
        //  Log.d(TAG, "readImageInfoArray");

        reader.beginArray(); //its an array with just one element (strange wikipedia response???)
        URL url = readImageInfo(reader);
        reader.endArray();

        return url;
    }


    private URL readImageInfo(JsonReader reader) throws IOException, WikipediaReaderException {
        //   Log.d(TAG, "readImageInfo");

        URL url = null;
        boolean skipCurrentURL = false;

        reader.beginObject();
        while (reader.hasNext()) {

            String name = reader.nextName();
            if (name.equals(WikipediaQueryResponseFields.URL)) {
                url = new URL(reader.nextString());
            } else if (name.equals(WikipediaQueryResponseFields.MIME)) {

                String imageType = reader.nextString();

                if (!imageType.equals("image/jpeg") && !imageType.equals("image/png")) {
                    skipCurrentURL = true;
                }
                reader.endObject();
                break;//mime is the last in the object
            } else if (name.equals(WikipediaQueryResponseFields.SIZE)) {
                long imageSizeInBytes = reader.nextLong();
                if (imageSizeInBytes > MAX_IMAGE_SIZE_IN_BYTES_SUPPORTED) {
                    skipCurrentURL = true;
                }
            } else {
                reader.skipValue();
            }


        }
        // reader.endObject();

        Log.d(TAG, "URL: " + url);

        if (skipCurrentURL)
            return null;
        else
            return url;

    }


}
