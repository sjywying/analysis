package com.analysis.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.analysis.web.bean.Manufacturer;

public interface ManufacturerMapper {

	@ResultMap("Manufacturer")
	@Select("select cdate, regnum, activenum from biz_manufacturer where channel=#{channel} order by cdate asc")
	List<Manufacturer> findByChannel(@Param("channel") String channel);
	
}
