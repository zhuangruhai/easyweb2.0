package  com.hichlink.easyweb.core.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.hichlink.easyweb.core.exception.MyException;

/**
 * 
 * <b>Title：</b>BaseServlet.java<br/>
 * <b>Description：</b> 对Servlet基本的一些操作封装<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2013-5-27 下午02:28:03<br/>
 * <b>Copyright (c) 2013 ASPire Tech.</b>
 * 
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = -5866539598543442097L;
	protected static final String UTF8 = "UTF-8";
	
	public final static String ACTION_NAME = "action";
	public final static String DEFAULT_ACTION = "doAction";
	public BaseServlet() {		
		super();		
	}
	public final void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);

	}

	public final void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if(isNotEmptyString(request.getParameter(ACTION_NAME))){
			String action = request.getParameter(ACTION_NAME).trim();
			try {
				getClass().getDeclaredMethod(action, HttpServletRequest.class,HttpServletResponse.class).invoke(this,request,response);
			} catch (IllegalArgumentException e) {
				throw new MyException("The argument of method is invalid!", e);
			} catch (IllegalAccessException e) {
				throw new MyException(e);
			} catch (InvocationTargetException e) {
				throw new MyException("The method invoke is fail!", e);
			} catch (SecurityException e) {
				throw new MyException(e);
			} catch (NoSuchMethodException e) {
				throw new MyException("The method is not exist!", e);
			}		 
			
		}else{
			try {
				getClass().getMethod(DEFAULT_ACTION, HttpServletRequest.class,HttpServletResponse.class).invoke(this,request,response);
			} catch (SecurityException e) {
				throw new MyException(e);
			} catch (NoSuchMethodException e) {
				throw new MyException("The method is not exist!", e);
			} catch (IllegalArgumentException e) {
				throw new MyException( "The argument of method is invalid!", e);
			} catch (IllegalAccessException e) {
				throw new MyException(e);
			} catch (InvocationTargetException e) {
				throw new MyException("The method invoke is fail!", e);
			}
		}
	}
	/**
	 * 默认方法
	 */
	protected void doAction(HttpServletRequest request, HttpServletResponse response){
		
	}
	/**
	 * 判断字符串是否为NULL或空串
	 * 
	 * @param value
	 *            判断字符串
	 * @return true or false
	 */
	protected boolean isEmptyString(String value) {
		return value == null || "".equals(value.trim());
	}

	/**
	 * 判断字符串是否非空（非null和非""）
	 * 
	 * @param value
	 *            判断字符串
	 * @return true or false
	 */
	protected boolean isNotEmptyString(String value) {
		return value != null && !"".equals(value.trim());
	}

	protected void output(final HttpServletResponse response,
			String contentType, String characterEncoding, String obj)
			throws IOException {
		response.setContentType(contentType);
		response.setCharacterEncoding(characterEncoding);
		disableCache(response);
		response.getWriter().write(obj);
		response.getWriter().flush();
		response.getWriter().close();
	}

	protected void outputString(final HttpServletResponse response, String obj)
			throws IOException {
		output(response, "text/html", UTF8, obj);
	}

	protected void outputJS(HttpServletRequest request,
			HttpServletResponse response, String msg) throws IOException {
		output(response, "application/javascript", UTF8, msg);
	}

	protected void disableCache(HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
	}

	protected void outputRtn(HttpServletRequest request,
			HttpServletResponse response, String outputString)
			throws IOException {
		if (StringUtils.isNotEmpty(request.getParameter("rtnName"))) {
			outputString = " var " + request.getParameter("rtnName") + "="
					+ outputString;
		}
		if (StringUtils.isNotEmpty(request.getParameter("jsoncallback"))) {
			outputString = request.getParameter("jsoncallback") + "("
					+ outputString + ")";
		}
		outputString(response, outputString);
	}

	protected String getCookie(HttpServletRequest request,
			HttpServletResponse response, String cookiename) {
		Cookie[] cookies = request.getCookies();
		if (null != cookies && cookies.length > 0) {
			for (Cookie c : cookies) {

				if (c.getName().equals(cookiename)) {
					try {
						return URLDecoder.decode(c.getValue(), UTF8);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						return "";
					}
				}
			}
		}
		return "";
	}

	@SuppressWarnings("unused")
	protected void setCookie(HttpServletRequest request,
			HttpServletResponse response, String cookiename,
			String cookievalue, int expiry) throws Exception {
		Cookie cookie = null;
		try {
			new Cookie(cookiename, URLEncoder.encode(cookievalue, UTF8));
		} catch (Exception e) {
			throw e;
		}
		// cookie.setDomain("");
		if (expiry != 0 && null != cookie)
			cookie.setMaxAge(expiry);
		response.addCookie(cookie);
	}

	private static String capitalize(String name) {
		return (new StringBuilder()).append(name.substring(0, 1).toUpperCase())
				.append(name.substring(1)).toString();
	}

	protected Object findObject(@SuppressWarnings("rawtypes") Class clazz,
			HttpServletRequest request) throws Exception {
		Object indata = clazz.newInstance();
		try {
			Field arr[] = clazz.getDeclaredFields();
			String fname = "";
			Method method = null;
			int len = arr.length;
			for (int i = 0; i < len; i++) {
				Field field = arr[i];
				if (Modifier.isFinal(field.getModifiers())
						|| Modifier.isStatic(field.getModifiers()))
					continue;
				fname = field.getName();
				method = clazz.getDeclaredMethod(
						(new StringBuilder()).append("set")
								.append(capitalize(fname)).toString(),
						new Class[] { field.getType() });
				if (field.getType() == Boolean.TYPE) {
					method.invoke(indata, new Object[] { Boolean
							.valueOf(request.getParameter(fname)) });

					continue;
				}
				if (field.getType() == Byte.TYPE) {
					method.invoke(indata, new Object[] { Byte.valueOf(request
							.getParameter(fname)) });
					continue;
				}
				if (field.getType() == Short.TYPE) {
					method.invoke(indata, new Object[] { Short.valueOf(request
							.getParameter(fname)) });
					continue;
				}
				if (field.getType() == Integer.TYPE) {
					method.invoke(indata, new Object[] { Integer
							.valueOf(request.getParameter(fname)) });
					continue;
				}
				if (field.getType() == Long.TYPE) {
					method.invoke(indata, new Object[] { Long.valueOf(request
							.getParameter(fname)) });
					continue;
				}
				if (field.getType() == Float.TYPE) {
					method.invoke(indata, new Object[] { Float.valueOf(request
							.getParameter(fname)) });
					continue;
				}
				if (field.getType() == Double.TYPE)
					method.invoke(indata, new Object[] { Double.valueOf(request
							.getParameter(fname)) });
				else
					method.invoke(indata,
							new Object[] { request.getParameter(fname) });

			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		return indata;
	}

	protected <T> void exportObject(T indata, HttpServletRequest request)
			throws Exception {
		Class<? extends Object> clazz = indata.getClass();
		try {
			Field arr[] = clazz.getDeclaredFields();
			String fname = "";
			Method method = null;
			int len = arr.length;
			for (int i = 0; i < len; i++) {
				Field field = arr[i];
				if (Modifier.isFinal(field.getModifiers())
						|| Modifier.isStatic(field.getModifiers()))
					continue;
				fname = field.getName();
				method = clazz.getDeclaredMethod(
						(new StringBuilder()).append("set")
								.append(capitalize(fname)).toString(),
						new Class[] { field.getType() });
				if (field.getType() == Boolean.TYPE) {
					method.invoke(indata, new Object[] { Boolean
							.valueOf(request.getParameter(fname)) });

					continue;
				}
				if (field.getType() == Byte.TYPE) {
					method.invoke(indata, new Object[] { Byte.valueOf(request
							.getParameter(fname)) });
					continue;
				}
				if (field.getType() == Short.TYPE) {
					method.invoke(indata, new Object[] { Short.valueOf(request
							.getParameter(fname)) });
					continue;
				}
				if (field.getType() == Integer.TYPE) {
					method.invoke(indata, new Object[] { Integer
							.valueOf(request.getParameter(fname)) });
					continue;
				}
				if (field.getType() == Long.TYPE) {
					method.invoke(indata, new Object[] { Long.valueOf(request
							.getParameter(fname)) });
					continue;
				}
				if (field.getType() == Float.TYPE) {
					method.invoke(indata, new Object[] { Float.valueOf(request
							.getParameter(fname)) });
					continue;
				}
				if (field.getType() == Double.TYPE)
					method.invoke(indata, new Object[] { Double.valueOf(request
							.getParameter(fname)) });
				else
					method.invoke(indata,
							new Object[] { request.getParameter(fname) });

			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获得请求者IP地址
	 * 
	 * @param request
	 * @return
	 */
	protected String getIpAddr(HttpServletRequest request) {
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
}
