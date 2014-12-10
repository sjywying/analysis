package com.analysis.calculate.nginx;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class NginxLogParserBolt extends BaseBasicBolt {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(NginxLogParserBolt.class);

	protected static final String FIELDS_CONTENT = "content";
	
	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String log = (String) input.getValue(0);
		Map<String, String> content = new HashMap<String, String>(0);
		try {
			content = NginxLogParser.parser(log);
			
			collector.emit(new Values(content));
		} catch (ParseException e) {
			logger.error("format date to yyyyMMddHHmmss error");
		} catch (Exception e) {
//			collector.emit(new Values(TopicType.OTHER.getCode(), log));
			logger.error("exception message : {}", e.getMessage());
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(FIELDS_CONTENT));
	}
	
	
}