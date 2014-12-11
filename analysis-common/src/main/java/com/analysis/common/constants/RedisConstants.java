package com.analysis.common.constants;

public interface RedisConstants {
	
	/*******************仅仅在redis中存储注册的tid集合*********************/
	public static final String REGISTE_SET_TID_MEMCACHE = "reg_tid_memcache";
	/*******************在redis,mysql中存储注册的tid集合*********************/
	public static final String REGISTE_SET_TID_PERSISTENT = "reg_tid_persistent";
	/*******************以此值为key，tid为filed进行hash存储*********************/
	public static final String REGISTE_HASH_CONTENT_ACTIVE = "reg_content";
	/**************以此值为key，tid为filed进行hash存储异常数据*********************/
	public static final String REGISTE_HASH_CONTENT_ERROR = "reg_content_error";
	
	
	
	public static final String REGACTIVE_SET_TID_MEMCACHE = "regactive_tid_memcache";
	public static final String REGACTIVE_SET_TID_PERSISTENT = "regactive_tid_persistent";
	public static final String REGACTIVE_HASH_CONTENT = "regactive_content";
	public static final String REGACTIVE_HASH_CONTENT_ERROR = "regactive_content_error";
	
}
