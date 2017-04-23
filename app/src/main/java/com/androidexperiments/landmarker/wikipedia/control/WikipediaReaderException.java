package com.androidexperiments.landmarker.wikipedia.control;

/**
 * Created by danieleds on 07/12/2015.
 */
public class WikipediaReaderException extends Exception{

    public WikipediaReaderException(String detailMessage) {
        super(detailMessage);
    }

    public WikipediaReaderException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
