package com.analysis.common.weblog;

import java.util.Map;

/**
 * FieldParser that returns a map of results.
 * Allows for a parser to return multiple fields.
 *  f.e. "GET /aa" -> method: GET, url: /aa
 * 
 * @author Crazyy
 */
public abstract class MultipleResultFieldParser implements FieldParser {
    
    public boolean isSingleOutput() {
        return false;
    }

    public abstract Map<String, Object> parse(String input) throws FieldParserException;
}
