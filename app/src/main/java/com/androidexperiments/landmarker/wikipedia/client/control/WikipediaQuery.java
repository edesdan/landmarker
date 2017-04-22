package com.androidexperiments.landmarker.wikipedia.client.control;

import com.androidexperiments.landmarker.wikipedia.client.boundary.WikipediaService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


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
        private String pageids;
        private String titles;
        private String format;
        private String prop;
        private String inprop;
        private String exsectionformat;
        private String list;
        private String generator;
        private String gimlimit;
        private boolean explaintext;

        //GeoData
        private String gscoord;
        private String gsradius;
        private String gslimit;

        //ImageInfo (https://www.mediawiki.org/wiki/API:Imageinfo)
        private String iiprop;


        public Builder(String action) {
            this.action = action;
        }

        public Builder pageids(String val) {
            pageids = val;
            return this;
        }

        public Builder titles(String val) {
            try {
                titles = URLEncoder.encode(val, WikipediaService.ENCODING); //it's really just to encode the spaces in the name
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                titles = "";
            }
            return this;
        }

        public Builder format(String val) {
            format = val;
            return this;
        }

        public Builder prop(String... props) {
            prop = "";
            for(String p: props){
                prop = prop + p + VALUE_SEPARATOR;
            }

            //remove last MULTI_VALUE_SEPARATOR char
            if(!prop.isEmpty()){
                prop = prop.substring(0,prop.length()-1);
            }
            return this;
        }

        public Builder inprop(String... props) {
            inprop = "";
            for(String p: props){
                inprop = inprop + p + VALUE_SEPARATOR;
            }

            //remove last MULTI_VALUE_SEPARATOR char
            if(!inprop.isEmpty()){
                inprop = inprop.substring(0,inprop.length()-1);
            }
            return this;
        }

        public Builder generator(String val) {
            generator = val;
            return this;
        }

        public Builder iiprop(String... props){

            iiprop = "";

            for(String p: props){
                iiprop = iiprop + p + VALUE_SEPARATOR;
            }

            //remove last MULTI_VALUE_SEPARATOR char
            if(!iiprop.isEmpty()){
                iiprop = iiprop.substring(0,iiprop.length()-1);
            }

            return this;
        }

        public Builder gimlimit(String val){
            gimlimit = val;
            return this;
        }

        public Builder exsectionformat(String val) {
            exsectionformat = val;
            return this;
        }

        public Builder explaintext() {
            explaintext = true;
            return this;
        }

        public Builder list(String val) {
            list = val;
            return this;
        }

        /**
         * Coordinate around which to search: two floating-point values separated by pipe (|)
         * @param lat
         * @param lon
         * @return
         *
         * see https://www.mediawiki.org/wiki/Extension:GeoData
         */
        public Builder gscoord(double lat, double lon) {
            gscoord = String.valueOf(lat).concat(VALUE_SEPARATOR).concat(String.valueOf(lon));
            return this;
        }

        /**
         * Search radius in meters (10-10000). This parameter is required.
         *
         * @return
         *
         * see https://www.mediawiki.org/wiki/Extension:GeoData
         */
        public Builder gsradius(String radiusInMeters ){
            gsradius = radiusInMeters;
            return this;
        }

        /**
         * Maximum number of pages to return. No more than 500 (5000 for bots) allowed. Default: 10.
         * @param limit
         * @return
         */
        public Builder gslimit(String limit ) {
            gslimit = limit;
            return this;
        }

        public String build() {

            StringBuilder urlBuilder = new StringBuilder("?").append("action").append(PARAMETER_VALUE_SEPARATOR).append(action);

            if (titles != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("titles").append(PARAMETER_VALUE_SEPARATOR).append(titles);
            if (format != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("format").append(PARAMETER_VALUE_SEPARATOR).append(format);
            if (pageids != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("pageids").append(PARAMETER_VALUE_SEPARATOR).append(pageids);
            if (prop != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("prop").append(PARAMETER_VALUE_SEPARATOR).append(prop);
            if(inprop != null)
                urlBuilder.append((PARAMETERS_SEPARATOR)).append("inprop").append(PARAMETER_VALUE_SEPARATOR).append(inprop);
            if (exsectionformat != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("exsectionformat").append(PARAMETER_VALUE_SEPARATOR).append(exsectionformat);
            if (explaintext)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("explaintext");
            if (list != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("list").append(PARAMETER_VALUE_SEPARATOR).append(list);
            if (gscoord != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("gscoord").append(PARAMETER_VALUE_SEPARATOR).append(gscoord);
            if(gsradius != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("gsradius").append(PARAMETER_VALUE_SEPARATOR).append(gsradius);
            if(gslimit != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("gslimit").append(PARAMETER_VALUE_SEPARATOR).append(gslimit);
            if(gimlimit != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("gimlimit").append(PARAMETER_VALUE_SEPARATOR).append(gimlimit);
            if(iiprop != null)
                urlBuilder.append((PARAMETERS_SEPARATOR)).append("iiprop").append(PARAMETER_VALUE_SEPARATOR).append(iiprop);
           if(generator != null)
                urlBuilder.append(PARAMETERS_SEPARATOR).append("generator").append(PARAMETER_VALUE_SEPARATOR).append(generator);


            return urlBuilder.toString();
        }
    }


}
