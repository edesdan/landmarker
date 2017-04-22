package com.androidexperiments.landmarker.wikipedia.reader.api;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by daniele on 10/02/16.
 */
public interface WikipediaImageURLsResponseReader {


    /**
     * Read data from an input stream and fill a List with URLs of images for the place.
     *
     * @param in input stream object from which to read data.
     * @return a list of URLs, always not null but can be empty.
     */
    List<URL> readWikipediaImageURLs(InputStream in) throws WikipediaReaderException;
}
