package com.hichlink.easyweb.attachment.exception;

/**
 * 
 * <pre>
 * <b>Title：</b>AttachTypeNotExistException.java<br/>
 * <b>Description：</b>附件类型不存在异常<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年7月30日 下午1:46:03<br/>  
 * <b>Copyright (c) 2014 ASPire Tech.</b>
 * </pre>
 */
public class AttachTypeNotExistException extends AttachmentException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5903133077522866074L;

	/**
	 * 构造.
	 * 
	 * @param msg
	 *            错误信息.
	 */
	public AttachTypeNotExistException(String msg) {
		super(msg);
	}
}