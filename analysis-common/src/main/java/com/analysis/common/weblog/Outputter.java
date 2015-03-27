package com.analysis.common.weblog;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Interface for all classes that outputters the parsed log data.
 * 
 * @author Crazyy
 */
public interface Outputter {
    /**
    * @param logData A list for each log line a map with:
    *    - key: log variable
    *    - value: String/Map<String, String>: value(s) being logged.
    * 
    * @throws java.io.IOException
    */
    void outputData(List<Map<String, Object>> logData) throws IOException ;
}
