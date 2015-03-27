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
//    private static final String PROPERTIES_KEY_LOG_FORMAT_DEFAULT_VALUE = "$remote_addr - $remote_user [$time_local] \"$request\" \"$http_referer\" $status $body_bytes_sent $request_body \"$http_user_agent\" \"$http_x_forwarded_for\"";
    private static final String PROPERTIES_KEY_LOG_FORMAT_DEFAULT_VALUE = "$remote_addr - $remote_user [$time_local] \"$request\" $status $body_bytes_sent \"$http_referer\" \"$http_user_agent\" \"$http_x_forwarded_for\"";


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
        initConfig();
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


    public static final ParseWebLogInstance getInstance() {
        return ParseWebLogInstanceHolder.INSTANCE;
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


    private static class ParseWebLogInstanceHolder {

        private static final ParseWebLogInstance INSTANCE = new ParseWebLogInstance();

    }

    public static void main(String[] args) {
        LogBean map = ParseWebLogInstance.getInstance().parseLog("0.0.0.1 - - [26/Feb/2015:00:00:00 +0800] \"POST /ishow//config/ HTTP/1.1\" \"-\" 200 45 {\\x22adccompany\\x22:\\x22101702\\x22,\\x22wd\\x22:\\x22%E9%98%B3%E6%98%A5%E5%B8%82%E7%AC%AC%E5%9B%9B%E4%B8%AD%E5%AD%A6\\x22,\\x22an\\x22:\\x22w-Android-gen-720x1280\\x22,\\x22av\\x22:\\x22APKPRJ112_1.0.0.15_20141231\\x22,\\x22cityid\\x22:\\x22513200\\x22,\\x22configMap\\x22:{\\x221\\x22:\\x221420793291000\\x22},\\x22imei\\x22:\\x22865914020664765\\x22,\\x22imsi\\x22:\\x22999030767168043\\x22,\\x22isSale\\x22:\\x220\\x22,\\x22model\\x22:\\x22UIMI4\\x22,\\x22pkgname\\x22:\\x22com.ishow.client.android.plugin.company\\x22,\\x22tid\\x22:\\x22e865914020664765\\x22} \"Dalvik/1.6.0 (Linux; U; Android 4.4.2; UIMI4 Build/KOT49H)\" \"-\"");
        System.out.println(map.buildString());
        System.out.println(map.toString());

        LogBean map1 = ParseWebLogInstance.getInstance().parseLog("211.140.5.112 - - [23/Mar/2015:00:03:18 +0800] \"GET /redirect/?serviceId=32&tid=e864029021545383&model=DOMI&adccompany=101702&av=APKPRJ112_1.0.0.14_20141209&pkgname=com.ishow.client.android.plugin.company&imsi=460008842507599&imei=864029021545383&an=w-Android-gen-540x960&cityId=330300&lat=27.997988&lng=120.679247&wd=%E9%98%B3%E6%98%A5%E5%B8%82%E7%AC%AC%E5%9B%9B%E4%B8%AD%E5%AD%A6 HTTP/1.1\" \"-\" 302 0 - \"Mozilla/5.0 (Linux; Android 4.4.2; DOMI Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36\" \"-\"");
        System.out.println(map1.buildString());
        System.out.println(map1.toString());
    }

}
