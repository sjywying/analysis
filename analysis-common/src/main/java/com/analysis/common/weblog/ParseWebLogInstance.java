package com.analysis.common.weblog;


import com.analysis.common.weblog.parser.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Main class that parses log files and outputs data.
 * 
 * @author Crazyy
 */
public class ParseWebLogInstance {

    private static final Logger logger = LoggerFactory.getLogger(ParseWebLogInstance.class);
    private static final String CONFIG_PROPERTIES_FILE = "config.properties";
    private static final String PROPERTIES_KEY_LOG_FORMAT = "log-format";
    private static final String PROPERTIES_KEY_LOG_FORMAT_DEFAULT_VALUE = "$remote_addr - $remote_user [$time_local] \"$request\" \"$http_referer\" $status $body_bytes_sent $request_body \"$http_user_agent\" \"$http_x_forwarded_for\"";
//    private static final String PROPERTIES_KEY_LOG_FORMAT_DEFAULT_VALUE = "$remote_addr - $remote_user [$time_local] \"$request\" $status $body_bytes_sent \"$http_referer\" \"$http_user_agent\" \"$http_x_forwarded_for\"";


    private static final Set<Character> charactersToEscape = new HashSet<Character>() {
        {
            add('[');
            add(']');
        }
    };

    private static final Map<String, FieldParser> fieldParsers = new HashMap<String, FieldParser>() {
        {
            put("time_local", new LocalTimeFieldParser());
            put("request", new RequestFieldParser());
            put("request_body", new RequestBodyFieldParser());
            put("status", new SimpleIntegerParser());
            put("body_bytes_sent", new SimpleLongParser());
        }
    };

    private static final List<String> logFieldNames = new ArrayList<String>();

    // Returns a pattern where all punctuation characters are escaped.
    private static final Pattern escaper = Pattern.compile("([\\[\\]])");
    private static final Pattern extractVariablePattern = Pattern.compile("\\$[a-zA-Z0-9_]*");

    private static String escapeRE(String str) {
        return escaper.matcher(str).replaceAll("\\\\$1");
    }

    private Properties prop = new Properties();
    private final String metaPattern;


    private ParseWebLogInstance() {
//        initConfig();
        this.metaPattern = prop.getProperty(PROPERTIES_KEY_LOG_FORMAT, PROPERTIES_KEY_LOG_FORMAT_DEFAULT_VALUE);
    }

    private Pattern getLogFilePattern(String metaPattern) {
        Matcher matcher = extractVariablePattern.matcher(metaPattern);
        int parsedPosition = 0;
        StringBuilder parsePatternBuilder = new StringBuilder();

        while (matcher.find()) {
            if (parsedPosition < matcher.start()) {
                String residualPattern = metaPattern.substring(parsedPosition, matcher.start());
                parsePatternBuilder.append(escapeRE(residualPattern));
            }
            String logFieldName = metaPattern.substring(matcher.start() + 1, matcher.end());
            logFieldNames.add(logFieldName);
            parsedPosition = matcher.end();
            char splitCharacter = metaPattern.charAt(matcher.end());
            parsePatternBuilder.append("([^");
            if (charactersToEscape.contains(splitCharacter)) {
                parsePatternBuilder.append("\\");
            }
            parsePatternBuilder.append(splitCharacter);
            parsePatternBuilder.append("]*)");
        }
        parsePatternBuilder.append(metaPattern.substring(parsedPosition, metaPattern.length()));
        Pattern logFilePattern = Pattern.compile(parsePatternBuilder.toString());

        logger.debug(parsePatternBuilder.toString());

        return logFilePattern;
    }

    public LogBean parseLog(String line) {
        Pattern logFilePattern = getLogFilePattern(metaPattern);
        LogBean logBean = new LogBean();
        try {
            Matcher logFileMatcher = logFilePattern.matcher(line);

            if (logFileMatcher.matches()) {
                for (int i = 1; i < logFileMatcher.groupCount(); i++) {
                    String logFieldName = logFieldNames.get(i - 1);
                    FieldParser fieldParser = fieldParsers.get(logFieldName);
                    if (fieldParser != null) {
                        if (fieldParser instanceof SingleResultFieldParser) {
                            logBean.put(logFieldName, ((SingleResultFieldParser) fieldParser).parse(logFileMatcher.group(i)));
                        } else {
                            logBean.put(logFieldName, ((MultipleResultFieldParser) fieldParser).parse(logFileMatcher.group(i)));
                        }
                    } else {
                        logBean.put(logFieldName, logFileMatcher.group(i));
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Error parsing log: {}", ex);
        }
        return logBean;
    }

//    private static class ParseWebLogInstanceHolder {
//        private static final ParseWebLogInstance INSTANCE = new ParseWebLogInstance();
//    }
    private static ParseWebLogInstance INSTANCE;
    public static final ParseWebLogInstance getInstance() {

        if(INSTANCE == null) {
            INSTANCE = new ParseWebLogInstance();
        }
        return INSTANCE;
    }

    private void initConfig() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream configStream = loader.getResourceAsStream(CONFIG_PROPERTIES_FILE);

        try {
            prop.load(configStream);
        } catch (IOException ex) {
            logger.error("Unable to load config.properties: {}", ex);
        }
    }
}
