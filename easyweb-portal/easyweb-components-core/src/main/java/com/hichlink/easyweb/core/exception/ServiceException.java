package com.hichlink.easyweb.core.exception;
/**
 * 
 * Service层公用的Exception, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 * @author zhuangruhai
 * @version 2014年6月13日
 * @see ServiceException
 * @since
 */
public class ServiceException extends RuntimeException {

	/**
     * 
     */
    private static final long serialVersionUID = 2894706745708814956L;

    public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
