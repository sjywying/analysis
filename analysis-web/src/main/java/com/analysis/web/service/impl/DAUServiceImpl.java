package com.analysis.web.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analysis.common.utils.Strings;
import com.analysis.web.bean.DAUNum;
import com.analysis.web.mapper.DAUMapper;
import com.analysis.web.service.DAUService;

@Service("dauService")
public class DAUServiceImpl implements DAUService{

	@Autowired private DAUMapper daupvMapper;
	@Autowired private DAUMapper dauuvMapper;

	@Override
	public List<DAUNum> findAllByChannel(String channel) {
		if(Strings.isEmpty(channel)) return new ArrayList<DAUNum>(0);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		Date tasktime = calendar.getTime();  
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");  
		
		return daupvMapper.findLastMonthByChannel(channel, df.format(tasktime));
	}
	

}
