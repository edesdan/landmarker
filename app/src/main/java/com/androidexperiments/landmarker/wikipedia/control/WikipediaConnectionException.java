package com.androidexperiments.landmarker.wikipedia.control;

/**
 * Created by danieleds on 07/12/2015.
 */
public class WikipediaConnectionException extends Exception{
    public WikipediaConnectionException(String detailMessage) {
        super(detailMessage);
    }

    public WikipediaConnectionException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
