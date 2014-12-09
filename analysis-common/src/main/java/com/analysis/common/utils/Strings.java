package com.analysis.common.utils;

import java.util.Arrays;

public class Strings {
	
	private static final int[] positions = {1,4,10,16,23};
	private static final int positions_length_max_index = positions.length;
	private static final String salt = "ZD2587";
	
	public static boolean compareTidMD5(String tid, String md5salt) {
		String pwd = md5salt.substring(3, md5salt.length() - 2);
		byte[] pwdRemSalt = removeSalt(pwd);
		String tidmd5 = getMD5((tid+salt).getBytes());
		return Arrays.equals(pwdRemSalt, tidmd5.getBytes());
	}
	
	public static byte[] removeSalt(String value) {
		byte[] varr = value.getBytes();
		byte[] varrnew = new byte[32];
		
		int varrIndex = 0;
		int index = 0;
		for (int i = 0; i < varr.length; i++) {
			if(index < positions_length_max_index && i != positions[index]) {
				varrnew[varrIndex] = varr[i];
				varrIndex++;
			} else {
				if(index == positions_length_max_index-1) continue;
				else index++;
			}
		}
		
		return varrnew;
	}
	
	public static boolean isEmpty(Object str) {
		return (str == null || "".equals(str));
	}

	public static String getMD5(byte[] source) {
		String s = null;

		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
				'E', 'F' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
										// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
											// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

}
