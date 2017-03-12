package com.hichlink.easyweb.core.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;
/**
 * 
 * <b>Title：</b>HttpRequester.java<br/>
 * <b>Description：</b> 对http post和get请求封装的工具类<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2012-10-11 下午05:50:56<br/>
 * <b>Copyright (c) 2012 ASPire Tech.</b>
 * 
 */
public class HttpRequester {
    private String defaultContentEncoding;

    private int connectTimeout = 10000;

    private int readTimeout = 10000;

    /**
     * 默认构造函数，使用默认的响应内容编码和超时时间
     */
    public HttpRequester() {
        this.defaultContentEncoding = Charset.defaultCharset().name();
    }

    /**
     * 构造函数，使用自定义的响应内容编码，使用默认的超时时间
     * 
     * @param contentEncodeing
     *            编码字符，如：GBK
     */
    public HttpRequester(String contentEncodeing) {
        this.defaultContentEncoding = contentEncodeing;
    }

    /**
     * 构造函数，使用能够自定义的超时时间，使用默认的响应内容编码
     * 
     * @param connectTimeout
     *            链接超时时间
     * @param readTimeout
     *            读取内容超时时间
     */
    public HttpRequester(int connectTimeout, int readTimeout) {
        this.defaultContentEncoding = Charset.defaultCharset().name();
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    /**
     * 构造函数，使用自定义的响应内容编码，自定义的超时时间
     * 
     * @param contentEncodeing
     *            编码字符，如：GBK
     * @param connectTimeout
     *            链接超时时间
     * @param readTimeout
     *            读取超时时间
     */
    public HttpRequester(String contentEncodeing, int connectTimeout, int readTimeout) {
        this.defaultContentEncoding = contentEncodeing;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    /**
     * 发送GET请求
     * 
     * @param urlString
     *            url地址
     * @return 响应对象
     * @throws IOException
     * @author zhuangruhai created at Sep 14, 2011
     */
    public HttpRespons sendGet(String urlString)
        throws IOException {
        return this.send(urlString, "GET", null, null);
    }

    /**
     * 发送GET请求
     * 
     * @param urlString
     *            url地址
     * @param params
     *            请求参数
     * @return 响应对象
     * @throws IOException
     * @author zhuangruhai created at Sep 14, 2011
     */
    public HttpRespons sendGet(String urlString, Map<String, String> params)
        throws IOException {
        return this.send(urlString, "GET", params, null);
    }

    /**
     * 发送GET请求
     * 
     * @param urlString
     *            url地址
     * @param params
     *            请求参数
     * @param propertys
     *            设置属性
     * @return 响应对象
     * @throws IOException
     * @author zhuangruhai created at Sep 14, 2011
     */
    public HttpRespons sendGet(String urlString, Map<String, String> params,
                               Map<String, String> propertys)
        throws IOException {
        return this.send(urlString, "GET", params, propertys);
    }

    /**
     * 发送POST请求
     * 
     * @param urlString
     *            url地址
     * @return 响应对象
     * @throws IOException
     * @author zhuangruhai created at Sep 14, 2011
     */
    public HttpRespons sendPost(String urlString)
        throws IOException {
        return this.send(urlString, "POST", null, null);
    }

    /**
     * 发送POST请求
     * 
     * @param urlString
     *            url地址
     * @param params
     *            请求参数
     * @return 响应对象
     * @throws IOException
     * @author zhuangruhai created at Sep 14, 2011
     */
    public HttpRespons sendPost(String urlString, Map<String, String> params)
        throws IOException {
        return this.send(urlString, "POST", params, null);
    }

    /**
     * 发送POST请求
     * 
     * @param urlString
     *            url地址
     * @param params
     *            请求参数
     * @param propertys
     *            请求属性
     * @return 响应对象
     * @throws IOException
     * @author zhuangruhai created at Sep 14, 2011
     */
    public HttpRespons sendPost(String urlString, Map<String, String> params,
                                Map<String, String> propertys)
        throws IOException {
        return this.send(urlString, "POST", params, propertys);
    }

    private HttpRespons send(String urlString, String method, Map<String, String> parameters,
                             Map<String, String> propertys)
        throws IOException {
        HttpURLConnection urlConnection = null;

        if (method.equalsIgnoreCase("GET") && parameters != null) {
            StringBuffer param = new StringBuffer();
            int i = 0;
            for (String key : parameters.keySet()) {
                if (i == 0)
                    param.append("?");
                else
                    param.append("&");
                param.append(key).append("=").append(parameters.get(key));
                i++ ;
            }
            urlString += param;
        }
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection)url.openConnection();

        urlConnection.setRequestMethod(method);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        urlConnection.setConnectTimeout(this.connectTimeout);
        urlConnection.setReadTimeout(this.readTimeout);

        if (propertys != null) for (String key : propertys.keySet()) {
            urlConnection.addRequestProperty(key, propertys.get(key));
        }

        if (method.equalsIgnoreCase("POST") && parameters != null) {
            StringBuffer param = new StringBuffer();
            for (String key : parameters.keySet()) {
                param.append("&");
                param.append(key).append("=").append(parameters.get(key));
            }
            urlConnection.getOutputStream().write(
                "".equals(param.toString()) ? param.toString().getBytes() : param.toString().substring(
                    1).getBytes());
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
        }

        return this.makeContent(urlString, urlConnection);
    }

    private HttpRespons makeContent(String urlString, HttpURLConnection urlConnection)
        throws IOException {
        HttpRespons httpResponser = new HttpRespons();
        try {
            InputStream in = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            httpResponser.contentCollection = new Vector<String>();
            StringBuffer temp = new StringBuffer();
            String line = bufferedReader.readLine();
            while (line != null) {
                httpResponser.contentCollection.add(line);
                temp.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            String ecod = urlConnection.getContentEncoding();
            if (ecod == null) ecod = this.defaultContentEncoding;

            httpResponser.urlString = urlString;

            httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
            httpResponser.file = urlConnection.getURL().getFile();
            httpResponser.host = urlConnection.getURL().getHost();
            httpResponser.path = urlConnection.getURL().getPath();
            httpResponser.port = urlConnection.getURL().getPort();
            httpResponser.protocol = urlConnection.getURL().getProtocol();
            httpResponser.query = urlConnection.getURL().getQuery();
            httpResponser.ref = urlConnection.getURL().getRef();
            httpResponser.userInfo = urlConnection.getURL().getUserInfo();

            httpResponser.content = new String(temp.toString().getBytes(), ecod);
            httpResponser.contentEncoding = ecod;
            httpResponser.code = urlConnection.getResponseCode();
            httpResponser.message = urlConnection.getResponseMessage();
            httpResponser.contentType = urlConnection.getContentType();
            httpResponser.method = urlConnection.getRequestMethod();
            httpResponser.connectTimeout = urlConnection.getConnectTimeout();
            httpResponser.readTimeout = urlConnection.getReadTimeout();

            return httpResponser;
        } catch (IOException e) {
            throw e;
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
    }

    /**
     * 默认的响应字符集
     */
    public String getDefaultContentEncoding() {
        return this.defaultContentEncoding;
    }

    /**
     * 设置默认的响应字符集
     */
    public void setDefaultContentEncoding(String defaultContentEncoding) {
        this.defaultContentEncoding = defaultContentEncoding;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
}