package com.analysis.common.weblog;

/**
 * Defines parsers that do transformations on log fields.
 * 
 * If isSingleOutput returns true: parseMany has to be called,
 * otherwise parse has to be called.
 * 
 * @author Crazyy
 */
public interface FieldParser {
    boolean isSingleOutput();
}
