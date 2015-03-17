package com.analysis.web.service;

import java.util.List;

import com.analysis.web.bean.Manufacturer;

public interface ManufacturerService {

	List<Manufacturer> findByChannel(String channel);
	
}
