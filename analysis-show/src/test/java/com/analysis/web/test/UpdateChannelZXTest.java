package com.analysis.web.test;

import com.analysis.web.mapper.InitMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Gavin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class UpdateChannelZXTest {

    private HashMap<String, String> mounthnum = new HashMap<String, String>();
    private String schannel;
    private String tchannel;

    @Autowired
    private InitMapper initMapper;

    @Before
    public void before() {

        String m1 = "335";
        String m2 = "6465\t6417\t6746\t6473\t6558\t6041\t6694\t6412\t6377\t6849\t6103\t6510\t6852\t6901\t6179\t6980\t6278\t6662\t7757\t7644\t7480\t7852\t7395\t7481\t7157\t7581\t7138\t7687";
        String m3 = "5745\t6071\t6349\t6147\t5843\t5968\t6488\t5836\t6317\t5864\t6364\t6535\t6924\t6920\t7547\t7420\t7147\t7454\t8292\t8414\t7826\t8329\t7084\t7741\t7645\t7918\t7547\t8533\t8426\t8480\t8218";
        String m4 = "6655\t6613\t6590\t7495\t7455\t7328\t7814\t7364\t6853\t6431\t6897\t7051\t7420\t7416\t7921\t7803\t8428\t7835\t8616\t8731\t8181\t8652\t7490\t8102\t8012\t8268\t7921\t8841\t7861\t8792";
        String m5 = "7130\t7309\t7610\t7441\t7932\t7260\t7795\t8724\t8338\t7873\t8387\t6956\t7362\t7358\t7913\t6983\t7670\t7018\t7878\t7204\t7399\t7916\t7439\t8912\t8813\t9094\t8713\t9725\t8647\t9672\t9088";
        String m6 = "7848\t8814\t10436\t9168\t11125\t8866\t10254\t8716\t9802\t8580\t9865\t8451\t8098\t9693\t8704\t7680\t8436\t7720\t8665\t7924\t8138\t10308\t8182\t9803\t9694\t10803\t9584\t10697\t10311\t10639";
        String m7 = "7829\t7398\t7436\t7574\t7435\t7352\t7278\t7988\t8488\t7822\t9252\t7696\t8908";
        mounthnum.put("201501", m1);
        mounthnum.put("201502", m2);
        mounthnum.put("201503", m3);
        mounthnum.put("201504", m4);
        mounthnum.put("201505", m5);
        mounthnum.put("201506", m6);
        mounthnum.put("201507", m7);
        schannel = "100401";     //努比亚
        tchannel = "100402";     //中兴
    }
    @Test
    public void test() {

        Set<String> keySet = mounthnum.keySet();

        for (Iterator iterator = keySet.iterator(); iterator.hasNext(); ) {
            String key =  (String) iterator.next();
            String value = mounthnum.get(key);

            String[] nums = value.split("\t");

            for (int i = 1; i <= nums.length; i++) {
                String num = nums[i-1];

                String cdate = "";
                if(i < 10) {
                    cdate = key + "0" + i;
                } else {
                    cdate = key + i;
                }

                initMapper.updateChannelZX(Integer.parseInt(key), cdate, schannel, tchannel, Integer.parseInt(num));
            }
            
        }

        System.out.printf("插入成功"+ schannel);

    }
}
