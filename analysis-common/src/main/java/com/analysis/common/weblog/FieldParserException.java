package com.analysis.common.weblog;

/**
 * Marker exception for everything that goes wrong parsing a log field.
 * 
 * @author Crazyy
 */
public class FieldParserException extends Exception {
    public FieldParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
