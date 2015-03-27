package com.analysis.common.weblog.parser;

import com.analysis.common.weblog.FieldParserException;
import com.analysis.common.weblog.SingleResultFieldParser;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Converts a nginx log date to number of seconds since 1970.
 *
 * @author Crazyy
 */
public class ISO8601DateFieldParser extends SingleResultFieldParser {

    @Override
    public Object parse(String input) throws FieldParserException {
        DateTimeFormatter parser2 = ISODateTimeFormat.dateTimeNoMillis();
        return String.valueOf(parser2.parseDateTime(input).toDate().getTime());
    }

}
