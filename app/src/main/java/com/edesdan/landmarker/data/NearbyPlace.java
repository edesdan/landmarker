package com.edesdan.landmarker.data;


import android.content.Context;
import android.location.Location;

import com.edesdan.landmarker.util.measure.MeasureUnitConverter;
import com.edesdan.landmarker.util.measure.Units;
import com.google.android.gms.maps.model.LatLng;


public abstract class NearbyPlace {

    /**
     * Get the place name.
     * <p>
     * Note: the name corrispond to teh title of teh page.
     *
     * @return the name of the place.
     */
    public abstract String getName();

    /**
     * Get the latitude of the place.
     *
     * @return the latitude
     */
    public abstract double getLatitude();

    /**
     * Get the longitude of the place.
     *
     * @return the longitude
     */
    public abstract double getLongitude();

    /**
     * The place's address, in human-readable format
     *
     * @return
     */
    public abstract String getAddress();


    /**
     * A description of the place.
     *
     * @return a small description (introduction) of the place
     */
    public abstract String getDescription();

    public abstract double getDistance();

    /**
     * Use this method to calculate the exact distance from the user.
     * We need to use this because now the user can change the search point
     * directly on the map.
     *
     * @param user location
     * @return
     */
    static public float getDistanceBetween(LatLng place, Location user) {

        if (user == null) {
            return 0;
        }

        float[] result = new float[1];

        Location.distanceBetween(place.latitude,
                place.longitude,
                user.getLatitude(),
                user.getLongitude(),
                result);

        return result[0];

    }


    static public LatLng fromLocationToLatLng(Location location) {

        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();

        LatLng latLng = new LatLng(latitude, longitude);

        return latLng;
    }


    public String formatPlaceDistance(double distanceInMeters, String unitMeasure, Context context) {

        String preferredMeasureUnit = unitMeasure;
        String formattedPlaceDistance = "";
        String suffix = ""; // TODO implements suffix (km or miles)

        switch (preferredMeasureUnit) {
            case "km": {
                if (distanceInMeters >= 1000) {
                    String fromMetersToKm = MeasureUnitConverter.convertLengthAsString(distanceInMeters, Units.METRE, Units.KILOMETRE);
                    formattedPlaceDistance = fromMetersToKm.concat(" " + suffix);

                } else {
                    formattedPlaceDistance = MeasureUnitConverter.formatLength(distanceInMeters).concat(" " + suffix);
                }
                break;
            }
            case "mi": {
                String fromMetersToMiles = MeasureUnitConverter.convertLengthAsString(distanceInMeters, Units.METRE, Units.MILE);
                formattedPlaceDistance = fromMetersToMiles.concat(" " + suffix);
                break;
            }
        }

        return formattedPlaceDistance;
    }
}
