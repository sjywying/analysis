package com.analysis.common.weblog.parser;

import com.analysis.common.weblog.FieldParserException;
import com.analysis.common.weblog.SingleResultFieldParser;

/**
 * Parser that treats a field as a long.
 * The default is to treat each field as a String.
 * 
 * @author Crazyy
 */
public class SimpleLongParser extends SingleResultFieldParser {

    @Override
    public Object parse(String input) throws FieldParserException {
        return Long.parseLong(input);
    }

}
