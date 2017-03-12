/*** Auto generator by codegenerator 2014-07-30 13:50:56*/
package com.hichlink.easyweb.attachment.entity;

import java.math.BigDecimal;
/**
 * 
 *<pre>
 * <b>Title：</b>AttachmentType.java<br/>
 * <b>Description：</b>附件类型实体<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年7月30日 下午2:07:01<br/>  
 * <b>Copyright (c) 2014 ASPire Tech.</b>   
 * </pre>
 */
public class AttachmentType{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1298288003749739120L;
	/** 同步. */
    public static final String SYNC_TYPE_SYNC = "sync";
    /**
    附件类型Id
     */
    private String attachTypeId;

    /**
    附件类型名称
     */
    private String attachTypeName;

    /**
    附件类型描述
     */
    private String attachTypeDesc;

    /**
    附件数量限制
     */
    private BigDecimal attachCountLimit;

    /**
    附件大小总限制（字节为单位）
     */
    private BigDecimal attachSizeLimit;

    /**
    单个附件大小限制（字节为单位）
     */
    private BigDecimal singleSizeLimit;

    /**
    文件后缀名限制（空表示不限制，如果支持多种后缀，通过；号分隔，如doc;pdf;rar等）
     */
    private String fileSuffixLimit;

    /**
    同步标志（同步：sync,为空表示不同步）
     */
    private String syncType;

    /**
    最大文件名长度,不能大于256
     */
    private BigDecimal maxFileNameLength;
    
    public boolean isSync() {
        return SYNC_TYPE_SYNC.equalsIgnoreCase(syncType);
    }
    /**
    附件类型Id
     * @return the value of ATTACHMENT_TYPE.ATTACH_TYPE_ID
     */
    public String getAttachTypeId() {
        return attachTypeId;
    }

    public void setAttachTypeId(String attachTypeId) {
        this.attachTypeId = attachTypeId == null ? null : attachTypeId.trim();
    }

    /**
    附件类型名称
     * @return the value of ATTACHMENT_TYPE.ATTACH_TYPE_NAME
     */
    public String getAttachTypeName() {
        return attachTypeName;
    }

    public void setAttachTypeName(String attachTypeName) {
        this.attachTypeName = attachTypeName == null ? null : attachTypeName.trim();
    }

    /**
    附件类型描述
     * @return the value of ATTACHMENT_TYPE.ATTACH_TYPE_DESC
     */
    public String getAttachTypeDesc() {
        return attachTypeDesc;
    }

    public void setAttachTypeDesc(String attachTypeDesc) {
        this.attachTypeDesc = attachTypeDesc == null ? null : attachTypeDesc.trim();
    }

    /**
    附件数量限制
     * @return the value of ATTACHMENT_TYPE.ATTACH_COUNT_LIMIT
     */
    public BigDecimal getAttachCountLimit() {
        return attachCountLimit;
    }

    public void setAttachCountLimit(BigDecimal attachCountLimit) {
        this.attachCountLimit = attachCountLimit;
    }

    /**
    附件大小总限制（字节为单位）
     * @return the value of ATTACHMENT_TYPE.ATTACH_SIZE_LIMIT
     */
    public BigDecimal getAttachSizeLimit() {
        return attachSizeLimit;
    }

    public void setAttachSizeLimit(BigDecimal attachSizeLimit) {
        this.attachSizeLimit = attachSizeLimit;
    }

    /**
    单个附件大小限制（字节为单位）
     * @return the value of ATTACHMENT_TYPE.SINGLE_SIZE_LIMIT
     */
    public BigDecimal getSingleSizeLimit() {
        return singleSizeLimit;
    }

    public void setSingleSizeLimit(BigDecimal singleSizeLimit) {
        this.singleSizeLimit = singleSizeLimit;
    }

    /**
    文件后缀名限制（空表示不限制，如果支持多种后缀，通过；号分隔，如doc;pdf;rar等）
     * @return the value of ATTACHMENT_TYPE.FILE_SUFFIX_LIMIT
     */
    public String getFileSuffixLimit() {
        return fileSuffixLimit;
    }

    public void setFileSuffixLimit(String fileSuffixLimit) {
        this.fileSuffixLimit = fileSuffixLimit == null ? null : fileSuffixLimit.trim();
    }

    /**
    同步标志（同步：sync,为空表示不同步）
     * @return the value of ATTACHMENT_TYPE.SYNC_TYPE
     */
    public String getSyncType() {
        return syncType;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType == null ? null : syncType.trim();
    }

    /**
    最大文件名长度,不能大于256
     * @return the value of ATTACHMENT_TYPE.MAX_FILE_NAME_LENGTH
     */
    public BigDecimal getMaxFileNameLength() {
        return maxFileNameLength;
    }

    public void setMaxFileNameLength(BigDecimal maxFileNameLength) {
        this.maxFileNameLength = maxFileNameLength;
    }
}
