package com.analysis.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.analysis.web.bean.Active;
import com.analysis.web.mapper.ActiveMapper;
import com.analysis.web.service.ActiveService;

@Service("activeService")
public class ActiveServiceImpl implements ActiveService {
	
	private static final Logger logger = LoggerFactory.getLogger(ActiveServiceImpl.class);

	@Autowired
	private ActiveMapper activeMapper;
	
	@Override
	public List<Active> findByChannelAndModel(String channel, String model) {
		if(StringUtils.isEmpty(channel)) {
			logger.error("request params channel is null");
			return new ArrayList<Active>();
		}
		
		return activeMapper.selectByChannelAndModel(channel, model);
	}
	
	@Override
	public List<Active> findByChannel(String channel) {
		if(StringUtils.isEmpty(channel)) {
			logger.error("request params channel is null");
		}
		
		return this.findByChannelAndModel(channel, null);
	}
}
