package egox.sm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;

import egox.sm.bean.Active;
import egox.sm.dao.ActiveMapper;
import egox.sm.service.ActiveService;

@Service("activeService")
public class ActiveServiceImpl implements ActiveService {
	
	private static final Logger logger = LoggerFactory.getLogger(ActiveServiceImpl.class);

	@Autowired
	private ActiveMapper activeMapper;
	
	public List<Active> findByChannelAndModel(String channel, String model) {
		if(StringUtils.isEmpty(channel)) {
			logger.error("request params channel is null");
			return new ArrayList<Active>();
		}
		
		return activeMapper.selectByChannelAndModel(channel, model);
	}
	
	public List<Active> findByChannelAndModel(String channel) {
		if(StringUtils.isEmpty(channel)) {
			logger.error("request params channel is null");
		}
		
		return this.findByChannelAndModel(channel, null);
	}
}
