package com.analysis.common.chunzhen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by crazyy on 15/3/25.
 */
public class Parser {

private static final Logger logger = LoggerFactory.getLogger(Parser.class);

    private Inputter inputter = null;
    private Outputter outputter = null;
    private List<Handler> handlers = null;

    public Parser(Inputter inputter, Outputter outputter) {
        this(inputter, outputter, new ArrayList<Handler>());
    }

    public Parser(Inputter inputter, Outputter outputter, List<Handler> handlers) {
        if(inputter == null || outputter == null) {
            throw new RuntimeException("inputter and outputter is not allow null");
        }

        if(handlers == null) handlers = new ArrayList<Handler>();

        this.inputter = inputter;
        this.outputter = outputter;
        this.handlers = handlers;
    }

    public void addLast(Handler handler) {
        handlers.add(handler);
    }

    public void parser() {
        logger.debug("parser chunzhen ip starting...");

        String line = null;
        while ((line = inputter.readLine()) != null) {

            Object object = line;
            ParserContext context = new ParserContext();

            for (Iterator<Handler> iterator = handlers.iterator(); iterator.hasNext(); ) {
                Handler next =  iterator.next();

                if(object == null || context.isFinish()) {
                    break;
                } else {
                    object = next.execute(context, object);
                }

                if(object == null) {
                    logger.error("parser error log : {}", line);
//                    throw new RuntimeException("处理异常 "+next.getClass().getSimpleName() +", "+ line);
                }
            }

            if(object != null && context.isFinish()) {
                outputter.write(((IPMetadata) object).builtStr());
            } else {
                logger.error("parser error log : {}", line);
            }
        }

        logger.debug("parser chunzhen ip end...");

        inputter.close();
        outputter.close();
    }

    public static void main(String[] args) {
        Inputter inputter = null;
        Outputter outputter = null;
        try {
            inputter = new FileInputter("ip_20150713.txt");
            outputter = new FileOutputter("ip_out_20150713.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Handler h1 = new ToObjectHandler();
        Handler h2 = new DistinctHandler();
        Handler h3 = new BaiduHandler();
        Parser parser = new Parser(inputter, outputter);
        parser.addLast(h1);
        parser.addLast(h2);
        parser.addLast(h3);


        parser.parser();
    }
}
