/*** Auto generator by codegenerator 2014-07-30 13:50:47*/
package com.hichlink.easyweb.attachment.entity;

import java.util.Date;
/**
 * 
 *<pre>
 * <b>Title：</b>AttachmentGroup.java<br/>
 * <b>Description：</b>附件组实体<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年7月30日 下午2:06:51<br/>  
 * <b>Copyright (c) 2014 ASPire Tech.</b>   
 * </pre>
 */
public class AttachmentGroup{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8554345472462327826L;

	/**
    附件组ID
     */
    private String attachGroupId;

    /**
    附件类型Id（为空表示无类型）
     */
    private String attachTypeId;

    /**
    附件状态（temp：暂存；formal：正式）
     */
    private String attachGroupStatus;

    /**
    附件组创建时间
     */
    private Date createDate;

    /**
    附件组ID
     * @return the value of ATTACHMENT_GROUP.ATTACH_GROUP_ID
     */
    public String getAttachGroupId() {
        return attachGroupId;
    }

    public void setAttachGroupId(String attachGroupId) {
        this.attachGroupId = attachGroupId == null ? null : attachGroupId.trim();
    }

    /**
    附件类型Id（为空表示无类型）
     * @return the value of ATTACHMENT_GROUP.ATTACH_TYPE_ID
     */
    public String getAttachTypeId() {
        return attachTypeId;
    }

    public void setAttachTypeId(String attachTypeId) {
        this.attachTypeId = attachTypeId == null ? null : attachTypeId.trim();
    }

    /**
    附件状态（temp：暂存；formal：正式）
     * @return the value of ATTACHMENT_GROUP.ATTACH_GROUP_STATUS
     */
    public String getAttachGroupStatus() {
        return attachGroupStatus;
    }

    public void setAttachGroupStatus(String attachGroupStatus) {
        this.attachGroupStatus = attachGroupStatus == null ? null : attachGroupStatus.trim();
    }

    /**
    附件组创建时间
     * @return the value of ATTACHMENT_GROUP.CREATE_DATE
     */
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
