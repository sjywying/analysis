package com.analysis.web.test;

import com.analysis.web.mapper.InitMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author Gavin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class Activetest {

    private HashMap<Integer, Integer> monthCounts = new HashMap<Integer, Integer>();

    @Autowired
    private InitMapper initMapper;

    @Before
    public void before() {
        monthCounts.put(201500, 0);
        monthCounts.put(201501, 4888);
        monthCounts.put(201502, 2050300+4888);
        monthCounts.put(201503, 2293624+2050300+4888);
        monthCounts.put(201504, 2498474+2293624+2050300+4888);
        monthCounts.put(201505, 2610400+2498474+2293624+2050300+4888);
        monthCounts.put(201506, 2711449+2610400+2498474+2293624+2050300+4888);
        monthCounts.put(201507, 1190512+2711449+2610400+2498474+2293624+2050300+4888);

    }
    @Test
    public void test() {

        List<String> cdates = getDatePeriod(new Date(), 172);

        for (Iterator<String> iterator = cdates.iterator(); iterator.hasNext(); ) {
            String cdate =  iterator.next();

            Random random = new Random();
            float s = (float) ((random.nextInt(5)*0.1 + 2) * 0.1);

            int month = Integer.parseInt(cdate.substring(0,6));
            int currentDateNum = initMapper.findCountByCdate(month, cdate);

            initMapper.insertActive(month, cdate, s, currentDateNum, monthCounts.get(month-1));
        }


        System.out.printf("插入成功");

    }

    private List<String> getDatePeriod(Date date, int beforeDays){
        List<String> datePeriodList = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        for(int i=beforeDays-1;i>=0;i--){
            cal.set(Calendar.DAY_OF_YEAR , inputDayOfYear-i);
            datePeriodList.add(dateFormat.format(cal.getTime()));
        }
        return datePeriodList;
    }

}
