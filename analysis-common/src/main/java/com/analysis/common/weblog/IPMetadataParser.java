package com.analysis.common.weblog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPMetadataParser {

	public static void main(String[] args) {
		System.out.printf(IPMetadataParser.getCountry("211.99.38.155"));

		Matcher m = addressPattern.matcher("\"0.0.0.0\",\"0.255.255.255\",\"US\",\"Califo rnia\",\"\"");
		int i = 0;
//		System.out.printf(m.find() + "");
		while (m.find()) {
			System.out.println(m.groupCount());
			System.out.printf(m.group(2));
			System.out.printf(m.group(5) + "[[[[");
		}

		System.out.printf("");
	}

	private static final Logger logger = LoggerFactory.getLogger(IPMetadataParser.class);

	private static TreeMap<Long, String> ipcountry = new TreeMap<Long, String>();

	private static final long MAX_IP_NUMBER = 4294967295l;
	private static boolean isinit = false;

	private static final Pattern addressPattern = Pattern.compile("\"([^ ]*)\",\"([^ ]*)\",\"([^ ]*)\",\"([^\"]*)\",\"([^\"]*)\"");


	static {
		if(isinit) {
			logger.error("object is initialized.");
		} else {
			parser();
		}
	}
	
	private static synchronized void parser() {
//		FileInputStream in = null;
//		BufferedReader br = null;
//		try {
//			in = new FileInputStream(new File(IPMetadataParser.class.getResource("/").getPath()+"dbipv4-city-2015-03.csv"));
//			br = new BufferedReader(new InputStreamReader(in, "utf-8"));
//
//			long endindex = 0l;
//			String line="";
//			while ((line=br.readLine())!=null) {
//				Matcher m = addressPattern.matcher(line);
//				m.find();
//
//				if(m.groupCount() == 5) {
//					long iplongstar = ipToLong(m.group(1));
//					long iplongend	= ipToLong(m.group(2));
//
//					if(iplongstar != endindex) {
//						logger.error("file dbip-country.csv is illegal, ip not is not continuous. ip:{}", line);
//						break;
//					}
//
//					endindex = iplongend+1;
//					if("cn".equals(m.group(3).toLowerCase())) {
//						ipcountry.put(iplongstar, m.group(3).toLowerCase() + ":" + m.group(4).toLowerCase() + ":" + m.group(5).toLowerCase());
//					} else {
//						ipcountry.put(iplongstar, m.group(3).toLowerCase() + "::");
//					}
//				} else {
//					logger.error("file dbip-country.csv is illegal, has exist array length not equal 5.");
//					break;
//				}
//			}
//
//			if(endindex-1 != MAX_IP_NUMBER) {
//				logger.error("file dbip-country.csv has readed. but last long number is {}, not equal 255.255.255.255 to number", endindex-1);
//			} else {
//				logger.debug("file dbip-country.csv has readed. last long number is {}", endindex-1);
//			}
//			isinit = true;
//		} catch (FileNotFoundException e) {
//			logger.error("FileNotFoundException message : {}", e.getMessage());
////			System.exit(0);
//			isinit = false;
//		} catch (IOException e) {
//			logger.error("IOException message : {}", e.getMessage());
////			System.exit(0);
//			isinit = false;
//		} finally {
//			try {
//				br.close();
//				in.close();
//			} catch (IOException e) {
//
//			}
//		}
		
		logger.debug("finish init ip treemap");
	}
	
	public static String getCountry(String ip) {
		if(ip == null || "".equals(ip.trim())) return "";
		Map.Entry<Long,String> entry = ipcountry.floorEntry(ipToLong(ip));
		if(entry == null) return "";
		
		return entry.getValue();
	}
	
	public static boolean foreignIp(String ip) {
		try {
			String lg = getCountry(ip);
			if(!"cn".equals(lg)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
    public static long ipToLong(String strIp) {
        long[] ip = new long[4];
        int position1 = strIp.indexOf(".");
        int position2 = strIp.indexOf(".", position1 + 1);
        int position3 = strIp.indexOf(".", position2 + 1);
        ip[0] = Long.parseLong(strIp.substring(0, position1));
        ip[1] = Long.parseLong(strIp.substring(position1+1, position2));
        ip[2] = Long.parseLong(strIp.substring(position2+1, position3));
        ip[3] = Long.parseLong(strIp.substring(position3+1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] <<  8 ) + ip[3];
    }
   
    public static String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        sb.append(String.valueOf((longIp >>> 24)));
        sb.append(".");
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>>  8 ));
        sb.append(".");
        sb.append(String.valueOf((longIp & 0x000000FF)));
        return sb.toString();
    }
}
