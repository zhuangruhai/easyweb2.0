package com.hichlink.easyweb.core.util;

import java.security.MessageDigest;

/**
 * <b>Title：</b>MD5Util.java<br/>
 * <b>Description：</b> md5加密工具类<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年5月13日 下午5:40:52<br/>
 * <b>Copyright (c) 2014 ASPire Tech.</b>
 * 
 */
public class MD5Util {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String md5Encode(String origin) {
		String resultString = null;

		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {

		}
		return resultString;
	}
	public static void main(String[] args){
		System.out.println("d6161835304f1279d80c9e85f0ee2568".equalsIgnoreCase(MD5Util.md5Encode("91zhushuo0002220140515094942111111")));
	}
}
