package com.analysis.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.analysis.common.utils.Strings;

public class IPMetadataParser {
	
	private static final Logger logger = LoggerFactory.getLogger(IPMetadataParser.class);

	private static TreeMap<Long, String> ipcountry = new TreeMap<Long, String>();
	
	private static final long MAX_IP_NUMBER = 4294967295l;
	private static boolean isinit = false;
	
	public static void init() {
		if(isinit) {
			logger.error("object is initialized.");
		} else {
			parser();
		}
	}
	
//	TODO 解析文件出错，异常退出。
	private static synchronized void parser() {
		File file = null;
		FileReader fileReader = null;
		BufferedReader br = null;
		try {
			file = new File(IPMetadataParser.class.getResource("/").getPath() + "dbip-country.csv");
			if(!file.exists()) {
				logger.error("file is not found: dbip-country.csv.");
//				System.exit(0);
			}
			
			fileReader = new FileReader(file);
			
			br = new BufferedReader(fileReader);
			
			long endindex = 0l;
			String line="";
			String[] arrs=null;
			while ((line=br.readLine())!=null) {
				arrs=line.replace("\"", "").split(",");
				if(arrs != null && arrs.length == 3) {
					long iplongstar = ipToLong(arrs[0]);
					long iplongend	= ipToLong(arrs[1]);
					
					if(iplongstar != endindex) {
						logger.error("file dbip-country.csv is illegal, ip not is not continuous. ip:{}", line);
						break;
					}
					
					endindex = iplongend+1;
					ipcountry.put(iplongstar, arrs[2]);
				} else {
					logger.error("file dbip-country.csv is illegal, has exist array length not equal 3.");
					break;
				}
			}
			
			if(endindex-1 != MAX_IP_NUMBER) {
				logger.error("file dbip-country.csv has readed. but last long number is {}, not equal 255.255.255.255 to number", endindex-1);
			} else {
				logger.debug("file dbip-country.csv has readed. last long number is {}", endindex-1);
			}
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException message : {}", e.getMessage());
//			System.exit(0);
		} catch (IOException e) {
			logger.error("IOException message : {}", e.getMessage());
//			System.exit(0);
		}
		
		try {
			if(br != null) br.close();
			if(fileReader != null) fileReader.close();
		} catch (IOException e) {
			logger.error("close io channel IOException : {}", e.getMessage());
//			System.exit(0);
		}
		
		logger.debug("finish init ip treemap");
		
		isinit = true;
	}
	
	public static String getCountry(String ip) {
		if(Strings.isEmpty(ip)) return "";
		Map.Entry<Long,String> entry = ipcountry.floorEntry(ipToLong(ip));
		if(entry == null) return "";
		
		return entry.getValue();
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