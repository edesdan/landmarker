package com.androidexperiments.landmarker.wikipedia.client.entity;

import com.androidexperiments.landmarker.data.NearbyPlace;


public class WikipediaPlace extends NearbyPlace {

    private String pageId, name;

    private double latitude, longitude;

    private double distance;


    public WikipediaPlace(long pageId, String name, double latitude, double longitude, double distance) {
        this.pageId = String.valueOf(pageId);
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }


    public String getPageId() {
        return pageId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    /**
     * The place's address, in human-readable format
     * <p>
     * // TODO
     *
     * @return
     */
    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public String getDescription() {
        return null; //TODO not implemented yet
    }

    @Override
    public double getDistance() {
        return distance;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof WikipediaPlace))
            return false;
        WikipediaPlace place = (WikipediaPlace) o;
        return place.distance == distance &&
                place.latitude == latitude &&
                place.longitude == longitude &&
                place.pageId.equals(pageId) &&
                place.name.equals(name);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + hashcodeFromFromDouble(distance);
        result = 31 * result + hashcodeFromFromDouble(latitude);
        result = 31 * result + hashcodeFromFromDouble(longitude);
        result = 31 * result + pageId.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    private int hashcodeFromFromDouble(double d) {
        long l = Double.doubleToLongBits(d);
        int hashcode = (int) (l ^ (l >>> 32));
        return hashcode;
    }

    @Override
    public String toString() {
        return "WikipediaPlace{" +
                "pageId='" + pageId + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", distance=" + distance +
                '}';
    }
}
