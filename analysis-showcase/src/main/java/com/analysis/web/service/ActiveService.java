package com.analysis.web.service;

import java.util.List;

import com.analysis.web.bean.ActiveNum;

public interface ActiveService {
	
	public List<ActiveNum> findByChannel(String channel);
}
