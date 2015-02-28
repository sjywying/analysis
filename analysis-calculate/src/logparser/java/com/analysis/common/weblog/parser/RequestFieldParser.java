package com.analysis.common.weblog.parser;

import com.analysis.common.weblog.FieldParserException;
import com.analysis.common.weblog.MultipleResultFieldParser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Parses the log field for the request.
 * f.e. GET /fr/index.html HTTP/1.0
 *  -> { "method": "GET", "url": "/fr/index.html", "http" : "HTTP/1.0" }
 * 
 * @author Crazyy
 */
public class RequestFieldParser extends MultipleResultFieldParser {

    public static final String KEY_METHOD = "_method_";
    public static final String KEY_URI = "_uri_";
    public static final String KEY_HTTP = "_http_";

    private static final String POST = "post";
    private static final String GET = "get";
    
    @Override
    public Map<String, Object> parse(String input) throws FieldParserException {
        Map<String, Object> fields = new HashMap<String, Object>();
        String[] parts = input.split(" ");
        if (parts.length == 3) {
            fields.put(KEY_METHOD, parts[0]);
            try {
                fields.put(KEY_URI, URLDecoder.decode(parts[1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                fields.put(KEY_URI, parts[1]);
            }
            fields.put(KEY_HTTP, parts[2]);

            if(GET.equals(parts[0])) {
                fields.putAll(parserUrl(parts[1]));
            }
        }

        return fields;
    }

    private static Map<String, String> parserUrl(String url) {
        Map<String, String> map = new HashMap<String, String>();

        int index = url.indexOf("?");
        if (index == -1) return null;

        String uri = url.substring(0, index);
        map.put(KEY_URI, uri);

        String paramsUrl = url.substring(index+1);
        String[] paramsArr = paramsUrl.split("&");
        for (String param : paramsArr) {
            String[] paramArr = param.split("=");
            if(paramArr != null && paramArr.length == 2) {
                map.put(paramArr[0].toLowerCase().trim(), paramArr[1]);
            }
        }

        return map;
    }
    
}
