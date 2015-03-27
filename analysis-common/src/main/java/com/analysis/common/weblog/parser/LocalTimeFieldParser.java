package com.analysis.common.weblog.parser;


import com.analysis.common.weblog.FieldParserException;
import com.analysis.common.weblog.SingleResultFieldParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Converts a time string from nginx' $local_time into seconds since Epoch time.
 * 
 * @author Crazyy
 */
public class LocalTimeFieldParser extends SingleResultFieldParser {
//    public static final String DATE_FORMAT = "dd/MMM/yyyy:HH:mm:ssZ";
    private static final SimpleDateFormat US_LOCALE_DATE_FORMAT = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US);
    private static final SimpleDateFormat YMDHMS_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    public Object parse(String input) throws FieldParserException {
        try {
//            TODO
            return YMDHMS_DATE_FORMAT.format(US_LOCALE_DATE_FORMAT.parse(input));
        } catch (ParseException ex) {
            throw new FieldParserException("Cannot convert " + input + " to a valid local_time date", ex);
        }
    }

}
