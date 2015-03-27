package com.analysis.common.chunzhen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.*;

/**
 * Created by crazyy on 15/3/25.
 */
public class BaiduHandler implements Handler {

    private static final String KEY_PROVINCE = "省";
    private static final String KEY_CITY = "市";

    private static final String VALUE_COUNTRY = "中国";
    private static final Map<String, String> zizhiqu = new HashMap<String, String>();

    private static Map<String, String[]> cache = new HashMap<String, String[]>();

    static {
        zizhiqu.put("内蒙古自治区", "内蒙古");
        zizhiqu.put("宁夏回族自治区", "宁夏");
        zizhiqu.put("新疆维吾尔自治区", "新疆");
        zizhiqu.put("西藏自治区", "西藏");
        zizhiqu.put("香港特别行政区", "香港");
    }


    @Override
    public Object execute(final ParserContext context, Object line) {

        IPMetadata iPMetadata = (IPMetadata) line;

        String[] addr = new String[0];
        if(cache.containsKey(iPMetadata.getAddress())) {
            addr = cache.get(iPMetadata.getAddress());
        } else {
            String latlng = getLanLnt(iPMetadata.getAddress());
            if(latlng == null || "".equals(latlng.trim())) {
                iPMetadata.setCountry(DistinctHandler.VALUE_COUNTRY_WEIZHI);
                iPMetadata.setProvince(DistinctHandler.VALUE_COUNTRY_WEIZHI);
                iPMetadata.setCity(DistinctHandler.VALUE_COUNTRY_WEIZHI);
            } else {
                addr = getAddInfo(latlng).split(",");
                cache.put(iPMetadata.getAddress(), addr);
            }
        }

        if(addr.length != 2 || "".equals(addr[0]) || "".equals(addr[1])) {
            iPMetadata.setCountry(DistinctHandler.VALUE_COUNTRY_WEIZHI);
            iPMetadata.setProvince(DistinctHandler.VALUE_COUNTRY_WEIZHI);
            iPMetadata.setCity(DistinctHandler.VALUE_COUNTRY_WEIZHI);
        } else {
            iPMetadata.setCountry(DistinctHandler.VALUE_COUNTRY);
            iPMetadata.setProvince(addr[0]);
            iPMetadata.setCity(addr[1]);
        }

        if(iPMetadata.check()) {
            context.setFinish(true);
        }

        return iPMetadata;
    }

    private static String getLanLnt(String addr) {
        try {
            String key = baidukey.get(random.nextInt(baidukey.size()));
            Content content = Request.Get("http://api.map.baidu.com/geocoder/v2/?address="+addr+"&output=json&ak="+key).execute().returnContent();
            JSONObject contentMap = JSON.parseObject(content.asString());
            Object status = contentMap.get("status");
            if((((Integer)status)) == 0) {
                Map<String, Double> latlng = ((Map)(((Map)contentMap.get("result")).get("location")));
                return String.valueOf(latlng.get("lat"))+","+String.valueOf(latlng.get("lng"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getAddInfo(String latlng) {
        try {
            String key = baidukey.get(random.nextInt(baidukey.size()));
            Content content = Request.Get("http://api.map.baidu.com/geocoder/v2/?location="+latlng+"&output=json&ak="+key).execute().returnContent();
            JSONObject contentMap = JSON.parseObject(content.asString());
            Object status = contentMap.get("status");
            if((((Integer)status)) == 0) {
                Map<String, Double> addr = ((Map)(((Map)contentMap.get("result")).get("addressComponent")));

                String province = String.valueOf(addr.get("province"));
                String city = String.valueOf(addr.get("city"));

                if(zizhiqu.containsKey(province)) province = zizhiqu.get(province);
                if(province.endsWith("省") || province.endsWith("市")) province = province.substring(0, province.length()-1);

                if(city.endsWith("市")) city = city.substring(0, city.length()-1);

                return province+","+city;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        BaiduHandler aa = new BaiduHandler();
        System.out.println(random.nextInt());
        String latlng = getLanLnt("北京朝阳区将台路芳源里小区宽带");
        System.out.printf(latlng);
        String addr = getAddInfo(latlng);
        System.out.printf(addr);
    }

    private static String baidukeystr = "OEde1BG6gzxS4bWaW9qraG8L\n" +
            "cY5AsgzY3j0x8ibd2dNMlxsM\n" +
            "QyMvs30DhIHOX89IdcEqBdD3\n" +
            "FZI0vcsh7dGOKRSxGvx2jGwm\n" +
            "uXYtwaXDdsYhKmbPa4GFysd3\n" +
            "5IfDN7jgrKGMM6WNjiK2gMQi\n" +
            "GyY86neIfbEoURd98mIPE23l\n" +
            "g0FYqVQI7nOYyLdys2EdNO6V\n" +
            "ggZwsSv2ZwGTbgmetP1U8Zzl\n" +
            "KwgASGBkvwOkoSLARVaK62xX\n" +
            "89FXSX0sPtsCwewQrgnWBTCH\n" +
            "sxgfYvt50Rp2yqDXueQjqd2E\n" +
            "rG8Zi14vMcf9DbkG5dr5NlqM\n" +
            "0FgFx3GrmU2jhY0t6NiW33YK\n" +
            "wEwOTB1HrrRL4kItZa6ziul1\n" +
            "pix2eeGw2gdqFqYoGcClkfFE\n" +
            "eU79UHlP9RihsDMLWS6Sens7\n" +
            "rpqAstv9hL5gg04QP5Hwx9Wy\n" +
            "EejxVXcgGsYMHaHZrbX7aR7x\n" +
            "XbxTLTQ4qaDF7Op79XtUuPfM\n" +
            "D1Rk9Hoo6XsbAco4w320OaH2\n" +
            "AN6Zx9I8DVxE6OmfIaTFnvxr\n" +
            "DzqiPZznCOB1XAKiHP6E4LVQ\n" +
            "qVTSThd6mBwYTni9io4rBeuR\n" +
            "Qztr6GE22PYhKUn7ylyNKZeg\n" +
            "9eQ3kNRalZLDRCg0MkdSkY0C\n" +
            "66cYERm2AhGpNXbuAw0LPbmW\n" +
            "GopVXOV6hgzYQ0krLhY6ltqI\n" +
            "jhRXDNdlPmvXbeTLLkqsyf8A\n" +
            "bn38RsIYml6tNGoEdiPOm7y2\n" +
            "aPGYklYYGDGHq0FG1pM7APHi\n" +
            "GeKeuIGt4VEAtcS2GPjFZeHF\n" +
            "WXIeV6cQhMgFVgW66aVNvgBL\n" +
            "L9wmItDg51izUmjuse4XBZZt\n" +
            "gEPSd0jZsYFYjpjUc3pHfDBx\n" +
            "mfmmyYAGVDdNAzas65M9BD3C\n" +
            "lLr0kmckduXrGjs787XSxXkz\n" +
            "tpdZsVRvzNWXGM29rqaaC3y3\n" +
            "lMqyyIWBhTeRGrKaq8FnHvkr\n" +
            "0VXgFlwHZewMmy4Uj5hWWwYW\n" +
            "cCSuwsIymx028CFuAGRdg0pQ\n" +
            "Ybi24kOIgwdGbkff95cNabbI\n" +
            "xGDqSNCnR4K9d5TOCDGxzPZ7\n" +
            "yaSviLfSbzYHtwrzZhijgSq7\n" +
            "NyzrsPlntFd5NTk6NWhaPIU2\n" +
            "UYqciLyC3SazXZnCdyw2Lo12\n" +
            "CWGwTrGX7kRkyMVvG8GrNfEi\n" +
            "GpQB832A0ZOHu5a2EcbDMMaU\n" +
            "2V2XWDAzLDBojGGSyg4kEShG\n" +
            "NWkG16pzl4RPq9MlRYnItEMD\n" +
            "qOB6pAt5Zh09cOaEFD35EiGy\n" +
            "jCyw2QOZnKUO3EX3eHf9yKa9\n" +
            "efZnLvOwcbSPR3ODuOilglib\n" +
            "B7var0HA5eWwOprivVvCkGO5\n" +
            "LOlfEXbn7n5jrkmktUGgXg1w\n" +
            "8UvdEyzWDba2H2IolgoQZHHy\n" +
            "IcvC3QUQ2MqBzwhG7FS4an7I\n" +
            "MmEAjpqNQBdst4xLGZpqiPLj\n" +
            "tTqkjDrERXvUIEccoGOtKoU3\n" +
            "X7VYsUaWBkXQyfrIpHGqUprE\n" +
            "pyDLMc8bOarTo8rk46NZIr3y\n" +
            "7RDuGjegCThFvOKx15KoTuQm\n" +
            "wUKAAzxx5YlEchQqaCfhQpzw\n" +
            "rAumhG3Woarc89wvstu265Bb\n" +
            "4PXwhkKKb6zv1Lu72Kjc0yPY\n" +
            "8oGOPSMhrRGM7pciAqWKvrMY\n" +
            "GeLmydzOo5k6k8rhtdSQN0kC\n" +
            "dLSRPTgblVGBq77UG9CVktv2\n" +
            "KQ7vW9h02qvmoGQBi5vFqs8S\n" +
            "uEdmzlTHGtfAZvMnsfPy4Ypo\n" +
            "ULvaWZu3yFMn8HfFs9b27T54\n" +
            "zCmK5UPsXqzzo3Vm4E5WYhfn\n" +
            "hb4uhVfvz0sTU0efdiiXWtEr\n" +
            "6zXPHTeajB6PmgrxcTnDiUZO\n" +
            "GPFwwtmqp0CicGmFj9mvB9KA\n" +
            "jgsy4x3vzxxR0E8AA4n8PElS\n" +
            "kCl8DsDu4KOjH52lkiObVBen\n" +
            "G6TLaZ2IyokCXKHA9BGhgPAt\n" +
            "MGRK8y8PEQ2VMpilNivZ0q10\n" +
            "4E1586dbf1d90bed9f1bd31b7d48f96c\n" +
            "UH06WxbXZjKEFGEt3hEqEV09\n" +
            "Atkr1BYTbVYBfE710efLvME2\n" +
            "UthP7wGviU5bnPhgBUy6r8ML\n" +
            "kStWwEkHamPEkRF6kQXIhMUR\n" +
            "OFwS3NmQjvRFwMYoSblfgU5d\n" +
            "L5rHTKAMSN19oT2qcAUf0L1n\n" +
            "EGmB5m4rt19PCZphjpMSgaDf\n" +
            "bhIx7fIGGMDKZDzn28MD7f63\n" +
            "nnCcTHeUAzQ8ktd9LIkIN4V9\n" +
            "U5p41Vzs21whP8jgGajn3c8m\n" +
            "hF8T3E3rGpAbTh4P7TKaqyP1\n" +
            "VzYEQ7EcfEwCmI2kYrsOgKkV\n" +
            "QLyRbWlrGAub66iG9FRWjZC9\n" +
            "NSRAj9UPrao7MSWieD1DrCnD\n" +
            "qnciThn6RGyqQzVaArISjNUO";

    private static final List<String> baidukey = new ArrayList<String>();
    static {
        String[] keysarr = baidukeystr.split("\n");
        for (int i = 0; i < keysarr.length; i++) {
            baidukey.add(keysarr[i]);
        }
    }

    private static final Random random = new Random(baidukey.size());

}
