package com.hichlink.easyweb.core.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**  
 * <b>Title：</b>WebUtil.java<br/>
 * <b>Description：</b> web公共操作方法工具类<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年5月7日 下午6:16:27<br/>  
 * <b>Copyright (c) 2014 ASPire Tech.</b>   
 *   
 */
public class WebUtil {
    public static void output2JSONStr(HttpServletResponse response, Object obj)
        throws IOException {
        output(response,JSON.toJSONString(obj),"application/json");
    }
    public static void output2Text(HttpServletResponse response, String obj)
        throws IOException {
        output(response,obj,"text/html");
    }
    public static void output(HttpServletResponse response, String obj,String contentType) throws IOException{
        response.setContentType(contentType);
        response.setCharacterEncoding("utf-8");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.getWriter().write(obj);
        response.getWriter().flush();
        response.getWriter().close();
    }
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    public static String getHost(HttpServletRequest request){
    	String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        return ip;
    }
}
