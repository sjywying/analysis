package com.analysis.common.weblog;

/**
 * Interface for all classes that outputters the parsed log data.
 * 
 * @author Crazyy
 */
public interface Inputter {

    /**
     *
     * @return  a log line
     * @throws java.io.IOException
     */
    String readline() ;


}
