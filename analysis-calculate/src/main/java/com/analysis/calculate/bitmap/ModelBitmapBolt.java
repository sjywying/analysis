package com.analysis.calculate.bitmap;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.alibaba.fastjson.JSON;
import com.analysis.calculate.nginx.active.ActiveParserBolt;
import org.roaringbitmap.RoaringBitmap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ModelBitmapBolt extends BaseBasicBolt implements BitMapBoltInterface {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ModelBitmapBolt.class);

	private static String hours = "0000000000";

	private Map<String, RoaringBitmap> roaringBitmap;
	
	@Override
	public void execute(Tuple tuple, BasicOutputCollector collector) {

		int idInt = tuple.getIntegerByField(ConvertBolt.FIELDS_TID);
		LogBean bean = (LogBean) tuple.getValueByField(ActiveParserBolt.FIELDS_CONTENT);
		try {
			String currentHours = bean.getCtime().substring(0, 10);
			if(hours.equals(currentHours)) {
				if(roaringBitmap.containsKey(bean.getChannel())) {
					roaringBitmap.get(bean.getChannel()).add(idInt);
				} else {
					RoaringBitmap bitmap = new RoaringBitmap();
					bitmap.add(idInt);
					roaringBitmap.put(bean.getChannel(), bitmap);
				}
			} else {
				Set<String> keySet = roaringBitmap.keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext(); ) {
					String key =  iterator.next();
					BitmapBean bitmapBean = new BitmapBean(hours, getModule(), key, roaringBitmap.get(key));

					UpLoadBitmap.bitmapQueue.offer(bitmapBean);
				}

//				clear old data
				hours = currentHours;
				roaringBitmap = new HashMap<String, RoaringBitmap>();
			}

//			collector.emit(new Values(bean));
		}catch (Exception e) {
//			collector.emit(new Values(TopicType.OTHER.getCode(), log));
			logger.error("exception message : {}", e.getMessage());
		}
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		roaringBitmap = new HashMap<String, RoaringBitmap>();
	}


	@Override
	public String getModule() {
		return "model";
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}
}