package com.analysis.web.test;

import com.analysis.web.mapper.InitMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 *
 * @author Gavin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class AVtest {

    private HashMap<String, String> channelAVs = new HashMap<String, String>();
    private List<Integer> mounth = new ArrayList<Integer>();

    @Autowired
    private InitMapper initMapper;

    @Before
    public void before() {
        channelAVs.put("100401", "APKPRJ152_3.0.0.16_20141103");
        channelAVs.put("100602", "APKPRJ152_3.0.0.16_20141126");
        channelAVs.put("100502", "APKPRJ152_3.0.0.16_20141205");
        channelAVs.put("100604", "APKPRJ152_3.0.0.16_20141115");
        channelAVs.put("100705", "APKPRJ152_3.0.0.16_20141223");
        channelAVs.put("100702", "APKPRJ152_3.0.0.16_20140905");
        channelAVs.put("100701", "APKPRJ152_3.0.0.16_20141022");

        mounth.add(201501);
        mounth.add(201502);
        mounth.add(201503);
        mounth.add(201504);
        mounth.add(201505);
        mounth.add(201506);
        mounth.add(201507);
    }
    @Test
    public void test() {

        Set<String> keySet = channelAVs.keySet();

        for (Iterator iterator = keySet.iterator(); iterator.hasNext(); ) {
            String channel =  (String) iterator.next();
            String av = channelAVs.get(channel);

            for (Iterator<Integer> stringIterator = mounth.iterator(); stringIterator.hasNext(); ) {
                Integer m =  stringIterator.next();
                initMapper.updateAVByChannel(m, channel, av);
            }

        }

        System.out.printf("插入成功");

    }
}
