package com.analysis.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analysis.common.utils.Strings;
import com.analysis.web.bean.ManufacturerActive;
import com.analysis.web.mapper.ManufacturerActiveMapper;
import com.analysis.web.service.ManufacturerActiveService;

@Service("manufacturerActiveService")
public class ManufacturerActiveServiceImpl implements ManufacturerActiveService{

	@Autowired private ManufacturerActiveMapper manufacturerActiveMapper;
	
	@Override
	public List<ManufacturerActive> findByChannel(String channel) {
		if(Strings.isEmpty(channel)) return new ArrayList<ManufacturerActive>(0);
		
		return manufacturerActiveMapper.findByChannel(channel);
	}

}
