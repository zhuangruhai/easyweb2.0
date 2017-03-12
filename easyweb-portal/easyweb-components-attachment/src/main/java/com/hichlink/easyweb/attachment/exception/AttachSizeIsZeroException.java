package com.hichlink.easyweb.attachment.exception;

/**
 * 附件大小为0异常.
 * @author haomingli
 *
 */
public class AttachSizeIsZeroException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 675843055962174012L;

	/**
     * 构造.
     * @param msg 错误信息.
     */
    public AttachSizeIsZeroException(String msg) {
        super(msg);
    }
    
    /**
     * 构造.
     * @param msg 错误信息
     * @param t 前一异常
     */
    public AttachSizeIsZeroException(String msg, Throwable t) {
        super(msg, t);
        
        this.setStackTrace(t.getStackTrace());
    }
}