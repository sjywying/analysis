package com.analysis.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.analysis.web.bean.DAUNum;

public interface DAUMapper {

	@ResultMap("DAUNum")
	@Select("select ctime, channel, pvnum, uvnum from biz_nginx_dau where channel=#{channel} order by ctime asc")
	List<DAUNum> findByChannel(@Param("channel") String channel);
	
	@ResultMap("DAUNum")
	@Select("select ctime, channel, pvnum, uvnum from biz_nginx_dau where channel=#{channel} and ctime>#{startCtime} order by ctime asc")
	List<DAUNum> findLastMonthByChannel(@Param("channel") String channel, @Param("startCtime") String startCtime);
	
}
