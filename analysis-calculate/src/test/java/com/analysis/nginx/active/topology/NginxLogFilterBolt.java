package com.analysis.nginx.active.topology;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.alibaba.fastjson.JSON;
import com.analysis.calculate.active.ActiveBean;
import com.analysis.calculate.common.metadata.TopicType;

public class NginxLogFilterBolt extends BaseBasicBolt {
	
	public static final String FIELDS_TYPE = "type";
	public static final String FIELDS_CONTENT = "content";
	
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String log = (String) input.getValue(0);
		try {
			ActiveBean bean = NginxLogParser.parser(log);
			if(bean.check()) {
				collector.emit(new Values(TopicType.ACTIVE.getTopic(), JSON.toJSONString(bean)));
			}
		} catch (Exception e) {
			collector.emit(new Values(TopicType.OTHER.getTopic(), log));
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(FIELDS_TYPE, FIELDS_CONTENT));
	}
	
	
}