package com.winksi.calculate.common.metadata;

public class Configuration {

//	注意:此处可能会转化为hostname，而不是直接用ip进行连接，所以要配置host文件
//	此值和kafka-server.properties配置文件有关系
	public static final String ZOOKEEPER_KAFKA_ROOT_PATH = "118.192.72.149:2181/kafka";
	public static final String ZOOKEEPER_KAFKA_BROKER_PATH = "/brokers";
	
}
