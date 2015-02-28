package com.analysis.common.weblog;


import com.analysis.common.weblog.parser.LocalTimeFieldParser;
import com.analysis.common.weblog.parser.RequestBodyFieldParser;
import com.analysis.common.weblog.parser.RequestFieldParser;
import com.analysis.common.weblog.parser.SimpleLongParser;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 *
 * Main class that parses log files and outputs data.
 * 
 * @author Crazyy
 */
public class ParseWebLog {

    private static final Logger logger = Logger.getLogger(ParseWebLog.class.getName());

    static {
        final InputStream inputStream = ParseWebLog.class.getResourceAsStream("/logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(inputStream);
        } catch (final IOException e) {
            Logger.getAnonymousLogger().severe("Could not load default logging.properties file");
            Logger.getAnonymousLogger().severe(e.getMessage());
        }
    }
    
    private static final String LOGFILE_PARAMETER = "logfile";
    private static final String CONFIG_PROPERTIES_PARAMETER = "config";
    private static final String LOG_FORMAT_PROP = "log-format";
    private static final String GZIP_EXTENSION = "gz";
    
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
            put("status", new SimpleLongParser());
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

    private final String metaPattern;
    private final InputStream logStream;
    private Outputter outputter;
    
    // each item in the map represents a log line
    // each map entry has as key the name of the nginx log variable
    // the object in the map can be:
    // - a String: for single values
    // - a Map of <String, String>: for values that are being split by FieldParsers
    private List<Map<String, Object>> logData;
    
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
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, parsePatternBuilder.toString());
        }
        Pattern logFilePattern = Pattern.compile(parsePatternBuilder.toString());
        return logFilePattern;
    }
    
    private static InputStream getStreamForFilename(String filename) throws FileNotFoundException, IOException {
        if (filename.endsWith(GZIP_EXTENSION)) {
            return new GZIPInputStream(new FileInputStream(filename));
        } else {
            return new FileInputStream(filename);
        }
    }
    
    public List<Map<String, Object>> getLogData() {
        return logData;
    }

    public ParseWebLog(InputStream logStream, String metaPattern) {
        this.logStream = logStream;
        this.metaPattern = metaPattern;
    }

    public void parseLog() {
        logData = new ArrayList<Map<String, Object>>();
        Pattern logFilePattern = getLogFilePattern(metaPattern);
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(logStream));
            String line;
            while ((line = br.readLine()) != null) {
                Map<String, Object> logLine = new HashMap<String, Object>();
                Matcher logFileMatcher = logFilePattern.matcher(line);

                if (logFileMatcher.matches()) {
                    for (int i = 1; i < logFileMatcher.groupCount(); i++) {
                        String logFieldName = logFieldNames.get(i - 1);
                        FieldParser fieldParser = fieldParsers.get(logFieldName);
                        Object fieldValue;
                        if (fieldParser != null) {
                            if (fieldParser instanceof SingleResultFieldParser) {
                                fieldValue = ((SingleResultFieldParser) fieldParser)
                                        .parse(logFileMatcher.group(i));
                            } else {
                                fieldValue = ((MultipleResultFieldParser) fieldParser)
                                        .parse(logFileMatcher.group(i));
                            }
                        } else {
                            fieldValue = logFileMatcher.group(i);
                        }
                        logLine.put(logFieldName, fieldValue);
                    }
                    logData.add(logLine);
                }

            }
            br.close();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error parsing log", ex);
        }
        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, "Parsed {0} log lines", new Object[]{logData.size()});
        }
    }

    public void outputParsedData(Outputter outputter) {
        try {
            outputter.outputData(logData);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error outputting log data using " + outputter.getClass().getName(), ex);
        }
    }
}
