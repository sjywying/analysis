package com.analysis.common.weblog.parser;

import com.alibaba.fastjson.JSON;
import com.analysis.common.weblog.FieldParserException;
import com.analysis.common.weblog.MultipleResultFieldParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Parses the log field for the request_body.
 * f.e. {\x22adccompany\x22:\x22100322\x22,\x22an\x22:\x22w-Android-gen-480x782\x22}
 *  -> {"adccompany":"100322","an":"w-Android-gen-480x782"}        Map
 * 
 * @author Crazyy
 */
public class RequestBodyFieldParser extends MultipleResultFieldParser {

    @Override
    public Map<String, Object> parse(String input) throws FieldParserException {
        Map<String, Object> fields = new HashMap<String, Object>();

        if(!"-".equals(input)) {
            String inputFormat = input.replace("\\x22", "\"");
            fields = JSON.parseObject(inputFormat, Map.class);
        }

        return fields;
    }
    
}
