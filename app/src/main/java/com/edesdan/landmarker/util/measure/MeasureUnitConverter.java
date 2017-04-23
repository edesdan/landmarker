package com.edesdan.landmarker.util.measure;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class MeasureUnitConverter {

    private static final String regex = ",.*$";
    private static final DecimalFormat DECIMAL_FORMAT;
    private static final String NO_CONVERSION = " ";

    static {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
        DECIMAL_FORMAT = (DecimalFormat) nf;
        DECIMAL_FORMAT.applyPattern("0.0");
    }

    /**
     * This function will format the returned String in the device current locale and it alwayes should
     * be teh preferred way to do conversions.
     *
     * @param length length in double which you want to convert
     * @param from   Units of length <b>length</b> from which you want to convert
     * @param to     Units to which you want to convert your length
     * @return length as String after conversion <br/><br/>
     */
    public static synchronized String convertLengthAsString(double length, Units from, Units to) {

        if ((from == to) || (length <= 0)) {
            return DECIMAL_FORMAT
                    .format(from)
                    .replaceAll(regex, ""); // used just to format the string
        } else {

            double from_length__in_meter = from.length;

            double to_length_in_meter = to.length;

            double metre_len = (1 / to_length_in_meter);

            double d = length * from_length__in_meter * metre_len;

            try {
                String s = DECIMAL_FORMAT
                        .format(d)
                        .replaceAll(regex, ""); // the replaceAll is needed for some strange cases (see issue #6)

                return s;
            } catch (Exception e) {
                return NO_CONVERSION;
            }

        }
    }

    public static synchronized String formatLength(double length){
        return DECIMAL_FORMAT
                .format(length)
                .replaceAll(regex, "");
    }

    /**
     * @param length length in double which you want to convert
     * @param from   Units of length <b>length</b> from which you want to convert
     * @param to     Units to which you want to convert your length
     * @return length in double after conversion <br/><br/>
     * @see <code>double d = 0;<br/>
     * d = convertLength(10,Units.KILOMETRE,Units.METRE); <br/>
     * // converts 10 from KILOMETRE to METRE<br/>
     * System.out.println(d); // d=10000<br/><br/>
     * d = convertLength(1,Units.METRE,Units.KILOMETRE); <br/>
     * // converts 1 from METRE to KILOMETRE<br/>
     * System.out.println(d); // d=0.001 <br/></code>
     */
    @Deprecated
    public static synchronized double convertLength(double length, Units from, Units to) {
        if ((from == to) || (length <= 0)) {
            return length;
        } else {

            double from_length__in_meter = from.length;

            double to_length_in_meter = to.length;

            double metre_len = (1 / to_length_in_meter);

            double d = length * from_length__in_meter * metre_len;

            String s = DECIMAL_FORMAT
                    .format(d)
                    .replaceAll(regex, ""); // the replaceAll is needed for some strange cases (see issue #6)
            //d = Double.parseDouble(s);
            try {
                d = DECIMAL_FORMAT.parse(s).doubleValue();
            } catch (ParseException e) {
                d = 0.0;
            }
            return d;

        }
    }


}
