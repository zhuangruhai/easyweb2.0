package com.hichlink.easyweb.attachment.exception;

/**
 * 大小超限.
 * @author xiongzy
 *
 */
public class AttachSizeOverLimitException extends AttachmentException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8063023953647898629L;

	/**
     * 构造.
     * @param msg 错误信息.
     */
    public AttachSizeOverLimitException(String msg) {
        super(msg);
    }
}