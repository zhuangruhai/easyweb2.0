package com.aspire.webbas.client.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * <b>Title：</b>StringTools.java<br/>
 * <b>Description：</b> 字符串操作工具类<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2012-7-2 下午04:05:10<br/>
 * <b>Copyright (c) 2012 ASPire Tech.</b>
 * 
 */
public class StringTools {
	/**
	 * 判断字符串是否为NULL或空串
	 * 
	 * @param value
	 *            判断字符串
	 * @return true or false
	 */
	public static boolean isEmptyString(String value) {
		return value == null || "".equals(value.trim());
	}

	/**
	 * 判断字符串是否非空（非null和非""）
	 * 
	 * @param value
	 *            判断字符串
	 * @return true or false
	 */
	public static boolean isNotEmptyString(String value) {
		return value != null && !"".equals(value.trim());
	}

	/**
	 * 不足位数补零
	 * 
	 * @param str
	 *            需要补全0的字符
	 * @param strLength
	 *            总位数
	 * @return
	 */
	public static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}

	/**
	 * null字符串转换为空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String null2Str(String str) {
		if (null == str)
			return "";
		return str;
	}

	private static Pattern pattern = Pattern
			.compile("([\\p{Punct}&&[^\\$\\\\]])");

	public static String escapeRegex(String original) {
		Matcher matcher = pattern.matcher(escapeDollarBackslash(original));
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			String group = matcher.group();
			matcher.appendReplacement(buffer, "\\\\" + group);
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}

	public static String escapeDollarBackslash(String original) {
		if (null == original)
			return "";
		StringBuffer buffer = new StringBuffer(original.length());
		for (int i = 0; i < original.length(); i++) {
			char c = original.charAt(i);
			if (c == '\\' || c == '$') {
				buffer.append("\\").append(c);
			} else {
				buffer.append(c);
			}
		}
		return buffer.toString();
	}

	/**
	 * 去除一字符中不允许存在的字符
	 * 
	 * @param illengalChar
	 *            不允许存在的字符
	 * @param str
	 *            字符串
	 * @return 不存在不允许存在的字符的字符串
	 */
	public static String legalString(String illegalChar, String str) {
		for (int i = 0; i < illegalChar.length(); i++) {
			if (str.indexOf(illegalChar.charAt(i)) != -1) {
				// 文件名中存在非法字符
				str = str.replace(String.valueOf(illegalChar.charAt(i)), "");
			}
		}
		return str;
	}

	/**
	 * 去除文件名中非法字符
	 * 
	 * @param fileName
	 * @return
	 */
	public static String legalFileName(String fileName) {
		String illegalChar = "\\/:*?<>|\"";
		return legalString(illegalChar, fileName);
	}
}
