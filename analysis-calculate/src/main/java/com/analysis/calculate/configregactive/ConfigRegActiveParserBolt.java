package com.analysis.calculate.configregactive;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.analysis.api.bean.ConfigRegActive;
import com.analysis.common.utils.DateUtils;


public class ConfigRegActiveParserBolt extends BaseBasicBolt {
	
	public static final String FIELDS_TID = "tid";
	public static final String FIELDS_TID_VALUE_ERROR_DEFAULT = "other";
	public static final String FIELDS_CONTENT = "content";
	public static final String FIELDS_USERAGENT = "ua";
	
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String log = (String) input.getValue(0);
		
		String ua = "";
		try {
			String[] logArr = log.split(",");
			
			//	日志进行文件切分时会产生一个只包含日期的行
			if(logArr.length < 3) return ;
			
			ConfigRegActive bean = new ConfigRegActive();
			bean.setCtime(DateUtils.transition(logArr[0]));
			bean.setIp(logArr[1]);
			bean.setTid(logArr[2]);
			bean.setImsi(logArr[3]);
			bean.setImei(logArr[4]);
			bean.setC(logArr[5]);
			bean.setM(logArr[6]);
			bean.setAv(logArr[7]);
			bean.setPname(logArr[8]);
			bean.setAn(logArr[9]);
			bean.setCcode(logArr[10]);
			if(logArr.length >= 12) {
				ua = logArr[11];
			}
		
			collector.emit(new Values(bean.getTid(), bean, ua));
		} catch (Exception e) {
			collector.emit(new Values(FIELDS_TID_VALUE_ERROR_DEFAULT, log+"#"+this.getClass().getName()+"#"+e.getMessage(), ua));
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(FIELDS_TID, FIELDS_CONTENT, FIELDS_USERAGENT));
	}
	
}