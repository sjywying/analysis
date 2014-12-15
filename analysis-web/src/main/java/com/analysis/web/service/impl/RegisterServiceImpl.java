package com.analysis.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analysis.web.mapper.RegisterMapper;
import com.analysis.web.service.RegisterService;

@Service("registerService")
public class RegisterServiceImpl implements RegisterService {
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

	@Autowired private RegisterMapper registerMapper;
	
}
