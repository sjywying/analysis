package com.analysis.web.service;

import java.util.List;

import com.analysis.web.bean.ManufacturerActive;

public interface ManufacturerActiveService {

	List<ManufacturerActive> findByChannel(String channel);
	
}
