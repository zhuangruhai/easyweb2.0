package com.hichlink.easyweb.attachment;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.hichlink.easyweb.attachment.entity.AttachmentFile;
import com.hichlink.easyweb.attachment.entity.AttachmentGroup;
import com.hichlink.easyweb.attachment.entity.AttachmentType;
import com.hichlink.easyweb.attachment.exception.AttachIsFormalException;
import com.hichlink.easyweb.attachment.exception.AttachNumberOverLimitException;
import com.hichlink.easyweb.attachment.exception.AttachSizeOverLimitException;
import com.hichlink.easyweb.attachment.exception.AttachTotalSizeOverLimitException;
import com.hichlink.easyweb.attachment.exception.AttachTypeNotExistException;
import com.hichlink.easyweb.attachment.exception.AttachmentException;

/**
 * 附件组件接口.
 * @author xiongzy
 *
 */
public interface AttachmentInterface {
    /**
     * 增加附件（暂存）.
     * @param attachGroupId 附件组ID（可为空，如果为空，生成新的附件组）
     * @param fileName 原始文件名
     * @param contentType 内容类型（如text/plain，application/pdf等...）
     * @param attachFile File对象
     * @param attachTypeId 附件类型（可为空，商业文件、资质证明等）
     * @return 附件DO
     */
    public AttachmentFile addAttachmentFile(String attachGroupId, 
    		                                String fileName, 
    		                                String contentType, 
    		                                File file, 
                                            String attachTypeId) 
        throws AttachmentException;
    /**
     * 增加附件（暂存）.
     * @param attachGroupId 附件组ID（可为空，如果为空，生成新的附件组）
     * @param fileName 原始文件名
     * @param contentType 内容类型（如text/plain，application/pdf等...）
     * @param attachFile File对象
     * @param attachTypeId 附件类型（可为空，商业文件、资质证明等）
     * @return 附件DO
     */
     public AttachmentFile addFile(String attachGroupId, 
    		                       String fileName, 
    		                       String contentType, 
    		                       File file, 
                                   String attachTypeId) 
         throws AttachmentException;
    /**
     * 校验附件.
     * @param attachGroupId 附件组ID（可为空，如果为空，生成新的附件组）
     * @param fileName 原始文件名
     * @param attachFile File对象
     * @param attachTypeId 附件类型（可为空，商业文件、资质证明等）
     */
    public void validateAttachmentFile(String attachGroupId, 
    		                           String fileName, 
    		                           File file,
    		                           String attachTypeId, 
                                       int willAddNum, 
                                       long willAddSize, 
                                       int willDeleteNum, 
                                       long willDeleteSize) 
            throws AttachNumberOverLimitException,
                   AttachSizeOverLimitException,
                   AttachTypeNotExistException, 
                   AttachTotalSizeOverLimitException, 
                   AttachmentException;
    
    
    /**
     * 撤消暂存附件.
     * @param attachFileId 附件编号.
     */
    public void withdrawAttachmentFile(String attachFileId)
        throws AttachIsFormalException;
    
    /**
     * 单附件和多附件新建生效接口
     * 单附件和多附件新建时必须调用此接口使附件生效，否则附件只处于暂存状态
     * @param attachGroupId 附件组ID
     */
    public void formalAttachmentGroup(String attachGroupId)  
        throws AttachmentException ;
    
    /**
     * 多附件修改内容生效接口
     * 当使用多附件功能时，只要修改过附件组中的任何内容，
     * 必须调用此接口才能使附件内容生效，否则附件组内容仍然是修改前的内容。
     * @param addAttachFileIds 新增附件编号列表
     * @param deleteAttachFileIds 删除附件编号
     */
    public void changeAttachmentFiles(List<String> addAttachFileIds, 
    		                          List<String> deleteAttachFileIds)  
        throws AttachmentException ;
    
    /**
     * 多附件内容被修改后，调用此接口对修改内容进行生效。此接口功能同changeAttachmentFiles，是其简化形式，主要是为新的jquery上传组件构建
     * 新的jquery上传组件支持多附件上传，单附件是其特例，与老的ext组件不同的是不再传送添加的和删除的附件文件ID，仅传送附件组中最终保留的附件文件ID
     * @param attachGroupId  附件组ID
     * @param finalAttachFileIds  附件组中最终要保留的附件文件ID
     * @throws AttachmentException
     */
    public void formalAttachmentGroup(String attachGroupId, List<String> finalAttachFileIds) throws AttachmentException;
    
    /**
     * 克隆附件组.
     * @param attachGroupId 附件组ID
     * @return 克隆的附件组ID 
     */
    public String cloneAttachmentGroup(String attachGroupId);
    
    /**
     * 克隆附件组.
     * @param attachGroupId 附件组ID
     */
    public void deleteAttachmentGroup(String attachGroupId);
    
    /**
     * 查询附件DO.
     * @param attachFileId 附件ID
     * @return 附件DO 
     */
    public AttachmentFile findAttachmentFile(String attachFileId) 
        throws AttachmentException;
    
    /**
     * 查询简单附件DO（不包括File对象）.
     * @param attachFileId 附件ID
     * @return 附件DO 
     */
    public AttachmentFile findSimpleAttachmentFile(String attachFileId) 
        throws AttachmentException;
    
    /**
     * 列表附件组附件DO.
     * @param attachmentFile 负载参数值的附件对象
     * @return 附件DO列表 
     */
    public List<AttachmentFile> listAttachmentFile(AttachmentFile attachmentFile);
    
    /**
     * 列表附件组附件DO.
     * @param attachGroupId 附件组ID
     * @return 附件DO列表 
     */
    public List<AttachmentFile> listAttachmentFile(String attachGroupId);
    
    /**
     * 列表有效附件组附件DO.
     * @param attachGroupId 附件组ID
     * @return 附件DO列表 
     */
    public List<AttachmentFile> listFormalAttachmentFile(String attachGroupId);
    
    /**
     * 查找附件类型.
     * @param typeId 附件类型
     * @return 附件类型
     * @throws DataAccessException DataAccessException
     */
    AttachmentType findAttachmentType(String typeId) 
        throws AttachmentException;
    
    /**
     * 清除过期的临时附件.
     * @param days 过期天数
     */
    public void clearTemp(int days);
    
    /**
     * 查找附件组
     * @param fileType
     * @return
     */
    public AttachmentGroup findAttachmentGroup(String groupId);
    
    /**
     * 是否公开是公开的附件类型
     * @param attachTypeId
     * @return
     */
    public boolean isPublicAttachmentType(String attachTypeId);
    
    /**
     * 根据附件组ID列表查询附件文件
     * @param attachGroupIdList
     * @return 以附件ID为KEY，附件ID对应的附件文件列表为值的Map
     */
    public Map<String, List<AttachmentFile>> listAttachmentFile(List<String> attachGroupIdList);

    /**
     * 根据附件组ID列表查询有效附件文件
     * @param attachGroupIdList
     * @return 以附件ID为KEY，附件ID对应的有效附件文件列表为值的Map
     */
    public Map<String, List<AttachmentFile>> listFormalAttachmentFile(List<String> attachGroupIdList);
    
    /**
     * 根据附件id列表查询附件文件
     * @param attachmentFileIds
     * @return
     */
    public List<AttachmentFile> listAttachmentFilesByIds(List<String> attachmentFileIds);
    
    
    /**
     * 批量添加附件（暂存），适用于后台需要批量上传附件的场景.
     * @param fileInfos  是一个map list对象，每个map中存放一个上传文件的信息，Map中需要填充的值有：
     * 1、attachGroupId 附件组ID（可为空，如果为空，生成新的附件组）
     * 2、fileName 原始文件名
     * 3、contentType 内容类型（如text/plain，application/pdf等...）
     * 4、attachFile File对象
     * 5、attachTypeId 附件类型（可为空，商业文件、资质证明等）
     * @return 附件DO
     */
    public List<AttachmentFile> batchAddAttachmentFiles(List<Map> fileInfos)  throws AttachmentException;
    
    /**
     * 批量附件生效接口
     * 当使用批量添加附件接口上传附件后必须调用此接口使附件生效，否则附件只处于暂存状态
     * @param attachGroupId 附件组ID
     */
    public void batchFormalAttachmentGroups(List<String> attachGroupIds)  throws AttachmentException ;
}
