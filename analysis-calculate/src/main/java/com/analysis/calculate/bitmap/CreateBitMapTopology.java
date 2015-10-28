package com.analysis.calculate.bitmap;

import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;

import com.analysis.calculate.common.metadata.Configuration;
import com.analysis.calculate.common.metadata.NginxConstants;
import com.analysis.calculate.common.metadata.NginxTopicType;

public class CreateBitMapTopology {
	public static void main(String[] args) throws Exception {

        UpLoadBitmap.getInstance().init();

		final String spoutId = "create_bitmap_spout";
		final String parserBolt = "parser_bolt";
		final String persistentBolt = "persistent_bolt";

		final int STORM_WORKER_NUM = 5;

		BrokerHosts brokerHosts = new ZkHosts(Configuration.ZOOKEEPER_KAFKA_ROOT_PATH, Configuration.ZOOKEEPER_KAFKA_BROKER_PATH);

		SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, NginxTopicType.BITMAP.getTopic(), NginxConstants.KAFKA_ZKROOT_PREFIX+NginxTopicType.BITMAP.getTopic(), spoutId);
		spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(spoutId, new KafkaSpout(spoutConfig));
		builder.setBolt(WeblogFormatBolt.class.getSimpleName(), new WeblogFormatBolt()).shuffleGrouping(spoutId);
		builder.setBolt(ConvertBolt.class.getSimpleName(), new ConvertBolt()).shuffleGrouping(WeblogFormatBolt.class.getSimpleName());
		builder.setBolt(ChannelBitmapBolt.class.getSimpleName(), new ChannelBitmapBolt()).shuffleGrouping(ConvertBolt.class.getSimpleName());
		builder.setBolt(ModelBitmapBolt.class.getSimpleName(), new ModelBitmapBolt()).shuffleGrouping(ConvertBolt.class.getSimpleName());

		Config conf = new Config();
		if (args != null && args.length > 0) {
			conf.setNumWorkers(STORM_WORKER_NUM);
			StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
		} else {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("active_topology", conf, builder.createTopology());
//			Utils.sleep(100000);
//			cluster.killTopology("active_topology");
//			cluster.shutdown();
		}
	}
}