package com.analysis.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analysis.common.utils.Strings;
import com.analysis.web.bean.Manufacturer;
import com.analysis.web.mapper.ManufacturerMapper;
import com.analysis.web.service.ManufacturerService;

@Service("manufacturerActiveService")
public class ManufacturerServiceImpl implements ManufacturerService {

	@Autowired private ManufacturerMapper manufacturerMapper;
	
	@Override
	public List<Manufacturer> findByChannel(String channel) {
		if(Strings.isEmpty(channel)) return new ArrayList<Manufacturer>(0);
		
		return manufacturerMapper.findByChannel(channel);
	}

}
