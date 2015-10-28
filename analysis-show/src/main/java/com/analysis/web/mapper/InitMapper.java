package com.analysis.web.mapper;

import org.apache.ibatis.annotations.Param;


public interface InitMapper {

	long updateChannelZX(@Param("mounth") int mounth,
					 @Param("cdate") String cdate,
					 @Param("schannel") String schannel,
					 @Param("tchannel") String tchannel,
					 @Param("num") int num);

	long updateCdate(@Param("mounth") int mounth,
					 @Param("cdate") String cdate,
					 @Param("channel") String channel,
					 @Param("num") int num);

	long updateAVByChannel(@Param("mounth") int mounth,
					 @Param("channel") String channel,
					 @Param("av") String av);


	int findCountByCdate(@Param("mounth") int mounth,
						 @Param("cdate") String cdate);

	long insertActive(@Param("mounth") int mounth,
					  @Param("cdate") String cdate,
					  @Param("random") float random,
					  @Param("currentDateNum") int currentDateNum,
					  @Param("preMonthNum") int preMonthNum);
}