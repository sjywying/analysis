package com.analysis.calculate.nginx.regactive;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.alibaba.fastjson.JSON;
import com.analysis.api.bean.RegActive;


public class RegActiveParserBolt extends BaseBasicBolt {
	
	public static final String FIELDS_TID = "tid";
	public static final String FIELDS_TID_VALUE_ERROR_DEFAULT = "other";
	public static final String FIELDS_CONTENT = "content";
	
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String log = (String) input.getValue(0);
		
		try {
			RegActive bean = JSON.parseObject(log, RegActive.class);
		
			collector.emit(new Values(bean.getTid(), bean));
		} catch (Exception e) {
			collector.emit(new Values(FIELDS_TID_VALUE_ERROR_DEFAULT, log+"#"+this.getClass().getName()+"#"+e.getMessage()));
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(FIELDS_TID, FIELDS_CONTENT));
	}
	
}