package com.analysis.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.analysis.web.bean.ManufacturerActive;

public interface ManufacturerActiveMapper {

	@ResultMap("ManufacturerActive")
	@Select("select mnum, yyyymm from biz_manufacturer_active where channel=#{channel} order by yyyymm asc")
	List<ManufacturerActive> findByChannel(@Param("channel") String channel);
	
}
