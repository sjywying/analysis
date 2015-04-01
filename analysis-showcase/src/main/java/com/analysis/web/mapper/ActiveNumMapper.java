package com.analysis.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.analysis.web.bean.ActiveNum;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;


public interface ActiveNumMapper {

	@ResultMap("ActiveNumMap")
	@Select("select adate, channel, sum(num) as num from biz_cust_active where channel=#{channel} group by adate order by adate")
	List<ActiveNum> selectByChannel(@Param("channel") String channel);

}