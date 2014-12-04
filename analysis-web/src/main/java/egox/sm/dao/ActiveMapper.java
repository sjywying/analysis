package egox.sm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import egox.sm.bean.Active;


public interface ActiveMapper {
    
	List<Active> selectByChannelAndModel(
			@Param("channel") String channel,
			@Param("model")String model);
}