package com.hichlink.easyweb.core.util;

import java.util.Vector;
/**
 * 
 * <b>Title：</b>HttpRespons.java<br/>
 * <b>Description：</b> http请求响应<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2012-10-11 下午05:51:07<br/>  
 * <b>Copyright (c) 2012 ASPire Tech.</b>   
 *
 */
public class HttpRespons {

	String urlString;

	int defaultPort;

	String file;

	String host;

	String path;

	int port;

	String protocol;

	String query;

	String ref;

	String userInfo;

	String contentEncoding;

	String content;

	String contentType;

	int code;

	String message;

	String method;

	int connectTimeout;

	int readTimeout;

	Vector<String> contentCollection;

	public String getContent() {
		return content;
	}

	public String getContentType() {
		return contentType;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Vector<String> getContentCollection() {
		return contentCollection;
	}

	public String getContentEncoding() {
		return contentEncoding;
	}

	public String getMethod() {
		return method;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public String getUrlString() {
		return urlString;
	}

	public int getDefaultPort() {
		return defaultPort;
	}

	public String getFile() {
		return file;
	}

	public String getHost() {
		return host;
	}

	public String getPath() {
		return path;
	}

	public int getPort() {
		return port;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getQuery() {
		return query;
	}

	public String getRef() {
		return ref;
	}

	public String getUserInfo() {
		return userInfo;
	}

}