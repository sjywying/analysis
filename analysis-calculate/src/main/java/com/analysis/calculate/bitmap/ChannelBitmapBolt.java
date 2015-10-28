package com.analysis.calculate.bitmap;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import com.analysis.calculate.nginx.active.ActiveParserBolt;
import org.csource.common.DFSCustomIdClient;
import org.roaringbitmap.RoaringBitmap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChannelBitmapBolt extends BaseBasicBolt implements BitMapBoltInterface {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ChannelBitmapBolt.class);

//	private static String hours = "0000000000";
	private static String hours = "000000000000";

	private Map<String, RoaringBitmap> roaringBitmap;
	
	@Override
	public void execute(Tuple tuple, BasicOutputCollector collector) {

		int idInt = tuple.getIntegerByField(ConvertBolt.FIELDS_TID);
		LogBean bean = (LogBean) tuple.getValueByField(ActiveParserBolt.FIELDS_CONTENT);

		try {
//			String currentHours = bean.getCtime().substring(0, 10);
			String currentHours = bean.getCtime().substring(0, 12);
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
//					BitmapBean bitmapBean = new BitmapBean(hours, getModule(), key, roaringBitmap.get(key));
//
//					UpLoadBitmap.bitmapQueue.offer(bitmapBean);
					RoaringBitmap bitmap = roaringBitmap.get(key);
					long fileSize = bitmap.serializedSizeInBytes();

					UploadBitmapStream uploadStream = new UploadBitmapStream(bitmap);

					String filename = "/" + this.getModule() + "/" + hours + "_" + key;

					DFSCustomIdClient myFDFSClient = new DFSCustomIdClient(UpLoadBitmap.FDHT_NAMESPACE);
					int result = myFDFSClient.uploadFile(filename, fileSize, uploadStream, null);
				}

//				clear old data
				hours = currentHours;
				roaringBitmap = new HashMap<String, RoaringBitmap>();
			}

		}catch (Exception e) {
			e.printStackTrace();
//			collector.emit(new Values(TopicType.OTHER.getCode(), log));
			logger.error("exception message : {}", e.getMessage());
		}
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		roaringBitmap = new HashMap<String, RoaringBitmap>();

		final String fdfsConfigFilename = "classpath:fdfs_client.conf";
		final String fdhtConfigFilename = "classpath:fdht_client.conf";
		final String fdhtNamespace = "fdfs";

//      先初始化 然后进行文件上传
		try {
			DFSCustomIdClient.init(new Configuration(fdfsConfigFilename), new Configuration(fdhtConfigFilename));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getModule() {
		return "channel";
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}
}