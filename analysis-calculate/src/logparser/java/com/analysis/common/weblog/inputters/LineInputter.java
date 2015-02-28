package com.analysis.common.weblog.inputters;

import com.analysis.common.weblog.Inputter;

/**
 * inputter the log data.
 *
 * @author Crazyy
 */
public class LineInputter implements Inputter {

    private String line;

    public LineInputter(String line) {
        this.line = line;
    }

    @Override
    public String readline() {
        return line;
    }
}