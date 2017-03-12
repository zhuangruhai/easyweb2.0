package com.hichlink.easyweb.attachment.exception;

/**
 * 附件异常.
 * 
 * @author xiongzy
 * 
 */
public class AttachmentException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2825248730956311028L;

	/**
	 * 构造.
	 * 
	 * @param msg
	 *            错误信息.
	 */
	public AttachmentException(String msg) {
		super(msg);
	}

	/**
	 * 构造.
	 * 
	 * @param msg
	 *            错误信息
	 * @param t
	 *            前一异常
	 */
	public AttachmentException(String msg, Throwable t) {
		super(msg, t);

		this.setStackTrace(t.getStackTrace());
	}
}
