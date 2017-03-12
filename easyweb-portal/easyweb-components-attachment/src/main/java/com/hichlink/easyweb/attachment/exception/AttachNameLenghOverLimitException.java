package com.hichlink.easyweb.attachment.exception;

/**
 * 附件是正式附件异常.
 * @author xiongzy
 *
 */
public class AttachNameLenghOverLimitException extends AttachmentException {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -8374699736316881048L;

    /**
     * 构造.
     * @param msg 错误信息.
     */
    public AttachNameLenghOverLimitException(String msg) {
        super(msg);
    }
}