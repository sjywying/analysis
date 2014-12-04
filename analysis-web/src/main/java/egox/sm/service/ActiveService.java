package egox.sm.service;

import java.util.List;

import egox.sm.bean.Active;

public interface ActiveService {
	
	public List<Active> findByChannelAndModel(String channel, String model);
	
	public List<Active> findByChannelAndModel(String channel);
}
