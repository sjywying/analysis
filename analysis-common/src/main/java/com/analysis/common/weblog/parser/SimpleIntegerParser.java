package com.analysis.common.weblog.parser;

import com.analysis.common.weblog.FieldParserException;
import com.analysis.common.weblog.SingleResultFieldParser;

/**
 * Parser that treats a field as a int.
 * The default is to treat each field as a String.
 * 
 * @author Crazyy
 */
public class SimpleIntegerParser extends SingleResultFieldParser {

    @Override
    public Object parse(String input) throws FieldParserException {
        return Integer.parseInt(input);
    }

}
