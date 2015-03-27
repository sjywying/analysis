package com.analysis.common.chunzhen;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by crazyy on 15/3/25.
 */
public class DistinctHandler implements Handler {

    private static final String KEY_PROVINCE = "省";
    private static final String KEY_CITY = "市";

    public static final String VALUE_COUNTRY = "中国";
    public static final String VALUE_COUNTRY_WEIZHI = "未知";

    private static final Set<String> citys = new HashSet<String>();

    static {
        citys.add("北京");
        citys.add("上海");
        citys.add("天津");
        citys.add("重庆");
    }

    private static final Set<String> keys = new HashSet<String>();

    static {
        keys.add("大学");
        keys.add("校区");
        keys.add("省");
        keys.add("地区");
        keys.add("学院");
        keys.add("网吧");
        keys.add("会所");
//        keys.add("中国");
        keys.add("电信");
        keys.add("移动");



    }

    private static final Set<String> alone_keys = new HashSet<String>();
    static {
        alone_keys.add("香港");
        alone_keys.add("西藏");
        alone_keys.add("蒙古");
        alone_keys.add("湖北");
        alone_keys.add("河南");
        alone_keys.add("新疆");
        alone_keys.add("广西");
        alone_keys.add("宁夏");
        alone_keys.add("台湾");
        alone_keys.add("上海");
    }

    @Override
    public Object execute(final ParserContext context, Object line) {

        IPMetadata iPMetadata = (IPMetadata) line;

        int pindex = iPMetadata.getAddress().indexOf(KEY_PROVINCE);
        int cindex = iPMetadata.getAddress().indexOf(KEY_CITY);

//      省，市全部包含
        if(pindex != -1 && cindex != -1) {
            iPMetadata.setProvince(iPMetadata.getAddress().substring(0, pindex));
            iPMetadata.setCity(iPMetadata.getAddress().substring(pindex + 1, cindex));
        }


//      只包含省
        if(pindex != -1 && cindex == -1) {
            iPMetadata.setProvince(iPMetadata.getAddress().substring(0, pindex));

            if(iPMetadata.getAddress().length() == pindex+1) {
                iPMetadata.setCity("未知");
            }
        }


//      只包含市  黑龙江 内蒙古长度为3
        if(pindex == -1 && cindex != -1) {
            if(cindex > 3) {
                if(iPMetadata.getAddress().startsWith("黑龙江") || iPMetadata.getAddress().startsWith("内蒙古")) {
                    iPMetadata.setProvince(iPMetadata.getAddress().substring(0, 3));
                    iPMetadata.setCity(iPMetadata.getAddress().substring(3, cindex));
                } else {
                    iPMetadata.setProvince(iPMetadata.getAddress().substring(0, 2));
                    iPMetadata.setCity(iPMetadata.getAddress().substring(2, cindex));
                }
            } else {
                iPMetadata.setCity(iPMetadata.getAddress().substring(0, cindex));

//              北京市 上海市 重庆市 天津市 四个直辖市
                if(citys.contains(iPMetadata.getCity())) {
                    iPMetadata.setProvince(iPMetadata.getCity());
                }
            }

        }

        if(iPMetadata.getAddress() != null && iPMetadata.getCity() != null) {
            iPMetadata.setCountry(VALUE_COUNTRY);
        } else {
            if (iPMetadata.getAddress().length() < 6) {
                boolean temp = false;
                for (Iterator<String> iterator = keys.iterator(); iterator.hasNext(); ) {
                    String key = iterator.next();

                    if(iPMetadata.getAddress().contains(key) && !iPMetadata.getAddress().equals("北美地区")) {
                        iPMetadata.setCountry(VALUE_COUNTRY);
                        temp = true;
                        break;
                    }
                }
                if(!temp) {
                    iPMetadata.setCountry(iPMetadata.getAddress());
                    iPMetadata.setProvince("未知");
                    iPMetadata.setCity("未知");
                }

                for (Iterator<String> iterator = alone_keys.iterator(); iterator.hasNext(); ) {
                    String next =  iterator.next();

                    if(iPMetadata.getAddress().contains(next)) {
                        iPMetadata.setCountry(VALUE_COUNTRY);
                        iPMetadata.setProvince(iPMetadata.getAddress());
                        iPMetadata.setCity("未知");
                    }
                }

            }
        }

        if(iPMetadata.check()) {
            context.setFinish(true);
        }

        return iPMetadata;
    }

}
