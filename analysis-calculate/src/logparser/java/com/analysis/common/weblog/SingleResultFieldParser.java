package com.analysis.common.weblog;

/**
 * FieldParser that only return 1 string result.
 * 
 * @author Crazyy
 */
public abstract class SingleResultFieldParser implements FieldParser {

    public boolean isSingleOutput() {
        return true;
    }
    
    public abstract Object parse(String input) throws FieldParserException;
}
