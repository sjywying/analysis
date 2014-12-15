package com.analysis.web.service;

import java.util.List;

import com.analysis.web.bean.ActiveNum;


public interface ConfigRegActiveService {
	
	List<ActiveNum> findByChannel(String channel);
	
	int countByChannelAndCtime(String channel, String ctime);
	
}
