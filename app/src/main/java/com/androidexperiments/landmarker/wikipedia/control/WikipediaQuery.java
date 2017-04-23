package com.androidexperiments.landmarker.wikipedia.control;

/**
 * Created by danieleds on 15/12/2015.
 */
public class WikipediaQuery {

    protected static final String VALUE_SEPARATOR = "|";
    private static final String PARAMETERS_SEPARATOR = "&";
    private static final String PARAMETER_VALUE_SEPARATOR = "=";


    public static class Builder {

        // Required parameters
        private final String action;
        // Optional parameters - initialized to default values
        private String format;
        private String formatVersion;
        private String prop;
        private String generator;
        private String colimit;
        private String exlimit;
        private String codistancefrompoint;

        private boolean explaintext;
        private boolean exintro;

        //GeoData
        private String ggscoord;
        private String ggsradius;
        private String ggslimit;


        public Builder(String action) {
            this.action = action;
        }

        public Builder colimit(Integer colimit) {
            this.colimit = String.valueOf(colimit);
            return this;
        }

        public Builder exlimit(String val) {
            this.exlimit = val;
            return this;
        }

        public Builder codistancefrompoint(double lat, double lon) {
            codistancefrompoint = String.valueOf(lat).concat(VALUE_SEPARATOR).concat(String.valueOf(lon));
            return this;
        }

        public Builder format(String val) {
            format = val;
            return this;
        }

        public Builder formatVersion(String val) {
            formatVersion = val;
            return this;
        }

        public Builder prop(String... props) {
            prop = "";
            for (String p : props) {
                prop = prop + p + VALUE_SEPARATOR;
            }

            //remove last MULTI_VALUE_SEPARATOR char
            if (!prop.isEmpty()) {
                prop = prop.substring(0, prop.length() - 1);
            }
            return this;
        }


        public Builder generator(String val) {
            generator = val;
            return this;
        }


        public Builder explaintext() {
            explaintext = true;
            return this;
        }

        public Builder exIntro() {
            exintro = true;
            return this;
        }


        /**
         * Coordinate around which to search: two floating-point values separated by pipe (|)
         *
         * @param lat
         * @param lon
         * @return see https://www.mediawiki.org/wiki/Extension:GeoData
         */
        public Builder ggscoord(double lat, double lon) {
            ggscoord = String.valueOf(lat).concat(VALUE_SEPARATOR).concat(String.valueOf(lon));
            return this;
        }

        /**
         * Search radius in meters (10-10000). This parameter is required.
         *
         * @return see https://www.mediawiki.org/wiki/Extension:GeoData
         */
        public Builder ggsradius(String radiusInMeters) {
            ggsradius = radiusInMeters;
            return this;
        }

        /**
         * Maximum number of pages to return. No more than 500 (5000 for bots) allowed. Default: 10.
         *
         * @param limit
         * @return
         */
        public Builder ggslimit(String limit) {
            ggslimit = limit;
            return this;
        }

        public String build() {

            StringBuilder urlBuilder = new StringBuilder("?").append("action").append(PARAMETER_VALUE_SEPARATOR).append(action);

            if (generator != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("generator").append(PARAMETER_VALUE_SEPARATOR).append(generator);
            if (explaintext)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("explaintext");
            if (exintro)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("exintro");
            if (prop != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("prop").append(PARAMETER_VALUE_SEPARATOR).append(prop);
            if (colimit != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("colimit").append(PARAMETER_VALUE_SEPARATOR).append(colimit);
            if (codistancefrompoint != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("codistancefrompoint").append(PARAMETER_VALUE_SEPARATOR).append(codistancefrompoint);
            if (exlimit != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("exlimit").append(PARAMETER_VALUE_SEPARATOR).append(exlimit);
            if (ggscoord != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("ggscoord").append(PARAMETER_VALUE_SEPARATOR).append(ggscoord);
            if (ggsradius != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("ggsradius").append(PARAMETER_VALUE_SEPARATOR).append(ggsradius);
            if (ggslimit != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("ggslimit").append(PARAMETER_VALUE_SEPARATOR).append(ggslimit);
            if (format != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("format").append(PARAMETER_VALUE_SEPARATOR).append(format);
            if (formatVersion != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("formatversion").append(PARAMETER_VALUE_SEPARATOR).append(formatVersion);

            return urlBuilder.toString();
        }
    }


}
