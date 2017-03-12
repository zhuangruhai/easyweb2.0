package com.hichlink.easyweb.attachment.exception;
/**
 * 
 *<pre>
 * <b>Title：</b>AttachTotalSizeOverLimitException.java<br/>
 * <b>Description：</b>附件总大小超过限制异常<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年7月30日 下午1:45:30<br/>  
 * <b>Copyright (c) 2014 ASPire Tech.</b>   
 * </pre>
 */
public class AttachTotalSizeOverLimitException extends AttachmentException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1029679375614912641L;

	/**
     * 构造.
     * @param msg 错误信息.
     */
    public AttachTotalSizeOverLimitException(String msg) {
        super(msg);
    }
}