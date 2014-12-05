package com.winksi.nginx.topology;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.alibaba.fastjson.JSON;
import com.winksi.calculate.common.metadata.Constants;
import com.winksi.calculate.common.metadata.TopicType;

public class NginxLogParserBolt extends BaseBasicBolt {
	
	public static final String FIELDS_TYPE = "type";
	public static final String FIELDS_CONTENT = "content";
	
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String log = (String) input.getValue(0);
		Map<String, String> content = new HashMap<String, String>(0);
		try {
			content = NginxLogParser.parser(log);
			collector.emit(new Values(content.get(Constants.NGINX_REQUEST_PARAMS_TYPE), JSON.toJSONString(content)));
		} catch (Exception e) {
			collector.emit(new Values(TopicType.OTHER.getCode(), log));
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(FIELDS_TYPE, FIELDS_CONTENT));
	}
	
	
}