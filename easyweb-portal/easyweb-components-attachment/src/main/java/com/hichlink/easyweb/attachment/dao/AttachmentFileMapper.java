/*** Auto generator by codegenerator 2014-07-30 13:50:35*/
package com.hichlink.easyweb.attachment.dao;

import com.hichlink.easyweb.attachment.entity.AttachmentFile;
import com.hichlink.easyweb.core.pagination.mybatis.pager.Page;
import java.util.List;

public interface AttachmentFileMapper {
    int deleteByPrimaryKey(String attachFileId);

    int insert(AttachmentFile record);

    int insertSelective(AttachmentFile record);

    List<AttachmentFile> pageQuery(Page<AttachmentFile> page);

    AttachmentFile selectByPrimaryKey(String attachFileId);

    int updateByPrimaryKeySelective(AttachmentFile record);

    int updateByPrimaryKey(AttachmentFile record);
    
    List<AttachmentFile> list(AttachmentFile attachmentFile);
    
    List<AttachmentFile> listTempFile(Integer days);
    
    void deleteAttachmentFileByGroupId(String groupId);
}
