package com.hichlink.easyweb.attachment.exception;

public class AttachNumberOverLimitException extends AttachmentException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7645748490000940663L;

	/**
     * 构造.
     * @param msg 错误信息.
     */
    public AttachNumberOverLimitException(String msg) {
        super(msg);
    }
}