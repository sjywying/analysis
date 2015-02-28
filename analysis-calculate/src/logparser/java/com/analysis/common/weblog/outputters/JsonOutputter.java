package com.analysis.common.weblog.outputters;

import com.alibaba.fastjson.JSON;
import com.analysis.common.weblog.Outputter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Outputs the log data as JSON.
 *
 * @author Crazyy
 */
public class JsonOutputter implements Outputter {

    private OutputStream os;
    
    public JsonOutputter(OutputStream os) {
        this.os = os;
    }
    
    @Override
    public void outputData(List<Map<String, Object>> logData) throws IOException {
        os.write(JSON.toJSONString(logData).getBytes());
    }

}
