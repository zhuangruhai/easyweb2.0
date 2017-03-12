/*** Auto generator by codegenerator 2014-07-30 13:50:35*/
package com.hichlink.easyweb.attachment.entity;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 *<pre>
 * <b>Title：</b>AttachmentFile.java<br/>
 * <b>Description：</b>附件实体<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年7月30日 下午2:06:27<br/>  
 * <b>Copyright (c) 2014 ASPire Tech.</b>   
 * </pre>
 */
public class AttachmentFile{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4700839315567607524L;

	/**
    文件流水号
     */
    private String attachFileId;

    /**
    附件组ID
     */
    private String attachGroupId;

    /**
    文件名称
     */
    private String fileName;

    /**
    文件存储名
     */
    private String fileSaveName;

    /**
    文件大小（字节为单位）
     */
    private BigDecimal fileSize;

    /**
    文件类型（doc,pdf等）
     */
    private String fileType;

    /**
    附件状态（temp：暂存；formal：正式）
     */
    private String attachFileStatus;

    /**
    附件创建时间
     */
    private Date createDate;
    /**
     * 附件文件对象，附件表中没有此属性
     */
    private File file;
    /**
    文件流水号
     * @return the value of ATTACHMENT_FILE.ATTACH_FILE_ID
     */
    public String getAttachFileId() {
        return attachFileId;
    }

    public void setAttachFileId(String attachFileId) {
        this.attachFileId = attachFileId == null ? null : attachFileId.trim();
    }

    /**
    附件组ID
     * @return the value of ATTACHMENT_FILE.ATTACH_GROUP_ID
     */
    public String getAttachGroupId() {
        return attachGroupId;
    }

    public void setAttachGroupId(String attachGroupId) {
        this.attachGroupId = attachGroupId == null ? null : attachGroupId.trim();
    }

    /**
    文件名称
     * @return the value of ATTACHMENT_FILE.FILE_NAME
     */
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
    文件存储名
     * @return the value of ATTACHMENT_FILE.FILE_SAVE_NAME
     */
    public String getFileSaveName() {
        return fileSaveName;
    }

    public void setFileSaveName(String fileSaveName) {
        this.fileSaveName = fileSaveName == null ? null : fileSaveName.trim();
    }

    /**
    文件大小（字节为单位）
     * @return the value of ATTACHMENT_FILE.FILE_SIZE
     */
    public BigDecimal getFileSize() {
        return fileSize;
    }

    public void setFileSize(BigDecimal fileSize) {
        this.fileSize = fileSize;
    }

    /**
    文件类型（doc,pdf等）
     * @return the value of ATTACHMENT_FILE.FILE_TYPE
     */
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    /**
    附件状态（temp：暂存；formal：正式）
     * @return the value of ATTACHMENT_FILE.ATTACH_FILE_STATUS
     */
    public String getAttachFileStatus() {
        return attachFileStatus;
    }

    public void setAttachFileStatus(String attachFileStatus) {
        this.attachFileStatus = attachFileStatus == null ? null : attachFileStatus.trim();
    }

    /**
    附件创建时间
     * @return the value of ATTACHMENT_FILE.CREATE_DATE
     */
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
