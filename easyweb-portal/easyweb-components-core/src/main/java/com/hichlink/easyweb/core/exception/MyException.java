/*
 * 文件名：MyException.java
 * 版权：Copyright by 卓望数码
 * 创建人：zhuangruhai
 * 创建时间：2014年6月13日
 */

package com.hichlink.easyweb.core.exception;
/**
 * 
 * 自定义异常抛出类
 * @author zhuangruhai
 * @version 2014年6月13日
 * @see MyException
 * @since
 */
public class MyException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 7718073001503111829L;

    public MyException() {
        super();
    }

    public MyException(String message) {
        super(message);
    }

    public MyException(Throwable cause) {
        super(cause);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

}
