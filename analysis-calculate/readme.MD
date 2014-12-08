				--------------------kafka other topic (0. 日志处理异常)	
				|
nginx --> kafka nginx topic---------kafka in_indexpage topic (1. 进入首页)
				|
				|-------------------kafka click_shortcut topic (2. 点击快捷服务)
				|
				|-------------------.......

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
	
以解决问题：
	1. redis 存储时出现key 前包含 \xac\xed\x00\x05t\x00\x10