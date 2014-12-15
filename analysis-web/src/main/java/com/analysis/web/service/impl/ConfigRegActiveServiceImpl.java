package com.analysis.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analysis.common.utils.DateUtils;
import com.analysis.common.utils.Strings;
import com.analysis.web.bean.ActiveNum;
import com.analysis.web.mapper.ConfigRegActiveMapper;
import com.analysis.web.service.ConfigRegActiveService;

@Service("configRegActiveService")
public class ConfigRegActiveServiceImpl implements ConfigRegActiveService {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigRegActiveServiceImpl.class);

	@Autowired private ConfigRegActiveMapper configRegActiveMapper;

	@Override
	public List<ActiveNum> findByChannel(String channel) {
		List<ActiveNum> list = new ArrayList<ActiveNum>();
		
		List<String> yyyymms = DateUtils.getMonthStartWith("201407");
		for (String yyyymm : yyyymms) {
			ActiveNum an = new ActiveNum();
			an.setChannel(channel);
			an.setYyyymm(yyyymm);
			int num = countByChannelAndCtime(channel, yyyymm);
			an.setNum(num);
			
			list.add(an);
		}
		
		return list;
	}

	@Override
	public int countByChannelAndCtime(String channel, String ctime) {
		if(Strings.isEmpty(channel)) return 0;
		
//		Map<String, String> paramsmap = new HashMap<String, String>();
//		paramsmap.put("channel", channel);
//		paramsmap.put("ctime", ctime);
		return configRegActiveMapper.countByChannelAndCtime(channel, ctime);
	}
	
}
