				--------------------kafka other topic (0. 日志处理异常)	
				|
nginx --> kafka nginx topic---------kafka in_indexpage topic (1. 进入首页)
				|
				|-------------------kafka click_shortcut topic (2. 点击快捷服务)
				|
				|-------------------.......
				
				
kafka_spout--->parser_bolt--->filter_bolt-------------------------------->persistent_bolt	
									|
									|
									|--------->handler_bolt-------------->persistent_bolt

如果想添加统计分类信息需要修改：
		1. Constants.Type 枚举类型
		2. kafka create topic
		3. reboot storm topology
		4. create new topology for analyze data 
		
		

问题：
	1. mysql redis 数据一致性	关于一致性，分为：强一致性、弱一致性、最终一致性
			1. 数据一致性
			2. mysql 崩溃	数据恢复：已经持久化用mysql自己的恢复机制
			3. redis 崩溃	数据恢复：如果使用
			
	1. redis crash
	2. 上下游数据处理能力不一致
	3. 动态管理拓扑
	4. flume压力测试
	5. 异常的数据也进行存储
	6. 各个层面单点问题
	7. kafka数据丢失情况
	8. 防止别人刷数据
	9. 异常日志迅速报警
	
	
	1. 看treemap 源码
	2. 看String.split(",") 源码
	
以解决问题：
	1. redis 存储时出现key 前包含 \xac\xed\x00\x05t\x00\x10
	2. flume没有数据过一段时间就会有几分钟无法传输数据
	3. 无法并列执行bolt，每次都是随机的分给一个bolt，另外一个分支得不到。
	4. 存入redis 速度慢
	5. 自动按月分表
	6. 多个web程序启动会出现现成安全问题（数据重复插入mysql） 同时操作redis 都有此问题


2015.05.06
	1. 实现bitmap
	2. 动态管理拓扑	// storm 无状态 可以随意停止 借助zk
	3. fastdfs/fastdht 创建连接池