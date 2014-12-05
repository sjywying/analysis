package com.analysis.web.service;

import java.util.List;

import com.analysis.web.bean.Active;

public interface ActiveService {
	
	public List<Active> findByChannelAndModel(String channel, String model);
	
	public List<Active> findByChannel(String channel);
}
