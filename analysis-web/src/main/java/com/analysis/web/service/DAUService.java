package com.analysis.web.service;

import java.util.List;

import com.analysis.web.bean.DAUNum;

public interface DAUService {

	List<DAUNum> findAllByChannel(String channel);
	
}
