package com.analysis.calculate.registe;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.analysis.api.bean.Registe;

public class RegisteParserBolt extends BaseBasicBolt {
	
	public static final String FIELDS_TID = "tid";
	public static final String FIELDS_TID_VALUE_ERROR_DEFAULT = "other";
	public static final String FIELDS_CONTENT = "content";
	
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String log = (String) input.getValue(0);
		
		try {
			String[] logArr = log.split(",");
			Registe bean = new Registe();
			bean.setCtime(logArr[0]);
			bean.setIp(logArr[1]);
			bean.setTid(logArr[2]);
			bean.setImsi(logArr[3]);
			bean.setImei(logArr[4]);
			bean.setC(logArr[5]);
			bean.setM(logArr[6]);
			bean.setAv(logArr[7]);
			bean.setPname(logArr[8]);
			bean.setR(logArr[9]);
			bean.setCcode(logArr[10]);
			bean.setL(logArr[11]);
			if(logArr.length >= 13) {
				bean.setUa(logArr[12]);
			}
		
			collector.emit(new Values(bean.getTid(), bean));
		} catch (Exception e) {
			collector.emit(new Values(FIELDS_TID_VALUE_ERROR_DEFAULT, log));
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(FIELDS_TID, FIELDS_CONTENT));
	}
	
}