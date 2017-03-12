package com.hichlink.easyweb.attachment.config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hichlink.easyweb.configuration.config.ConfigurationHelper;

public class AttachmentConfig {
    /**
     * logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(AttachmentConfig.class);
    
    /*** LOCAL_FILE.*/
    private static final String LOCAL_FILE_TYPE = "file";
    
    /*** SEQUENCE前缀.*/
    private static final String SEQUENCE_PREFIX = "8";

    /**
     * 配置文件接口.
     */
    private Configuration configuration = null;
    
    /**
     * 附件组件配置对象.
     */
    private static AttachmentConfig attachmentConfig;

    /**
     * 私有构造方法.
     * @param configurationFileName 配置文件相对路径
     */
    private AttachmentConfig() {
        configuration = ConfigurationHelper.getConfiguration("attachment/attachment.properties");
        if (configuration == null) {
            logger.error("读attachemnt组件配置文件失败, 配置文件：" + "attachment/attachment.properties");
        }
    }

    /**
     * 获取AJAX服务端配置对象.
     * @return AJAX服务端配置对象
     */
    public static AttachmentConfig getInstance() {
        if (attachmentConfig == null) {
            attachmentConfig = new AttachmentConfig();
        }
        return attachmentConfig;
    }

    /**
     * 获取附件存储根路径.
     * @return 附件存储根路径
     * @throws Exception 异常
     */
    public String getUploadPath() {
        if (configuration == null ) {
            return null;
        }
        
        // 上载地址
        String uploadPath = getAbsoluteUploadPath();
        

        try {
            // 为了防止用户名／密码有@等特殊字符，进行转义            
            String userName = URLEncoder.encode(getUserName(), "UTF-8");
            String password = URLEncoder.encode(getPassword(), "UTF-8");
            
            String uri = null;
            if (LOCAL_FILE_TYPE.equalsIgnoreCase( getFileType())) {
                // 本地文件 URI Format
                // #   [file://] absolute-path
                uri = getFileType() + "://" + uploadPath;
            } else {
                //#URI Format 
                //#   ftp://[ username [: password ]@] hostname [: port ][ absolute-path ]
                uri = getFileType() + "://" + userName + ":" + password 
                         + "@" + getFileServerIp() 
                         + ":" + getFileServerPort() + uploadPath;
            }
            return uri;
        } catch (UnsupportedEncodingException e) {
            logger.error("用户名密码配置错误，用户名：" + getUserName() + " 密码：" + getPassword());
            return null;
        }
    }
    
    /**
     * 获取附件存储根路径.
     * @return 附件存储根路径
     * @throws Exception 异常
     */
    public String getSyncPath() {
        if (configuration == null ) {
            return null;
        }
        
        // 上载地址
        String syncPath = getAbsoluteSyncPath();
        

        try {
            // 为了防止用户名／密码有@等特殊字符，进行转义            
            String userName = URLEncoder.encode(getUserName(), "UTF-8");
            String password = URLEncoder.encode(getPassword(), "UTF-8");
            
            String uri = null;
            if (LOCAL_FILE_TYPE.equalsIgnoreCase( getFileType())) {
                // 本地文件 URI Format
                // #   [file://] absolute-path
                uri = getFileType() + "://" + syncPath;
            } else {
                //#URI Format 
                //#   ftp://[ username [: password ]@] hostname [: port ][ absolute-path ]
                uri = getFileType() + "://" + userName + ":" + password 
                         + "@" + getFileServerIp() 
                         + ":" + getFileServerPort() + syncPath;
            }
            return uri;
        } catch (UnsupportedEncodingException e) {
            logger.error("用户名密码配置错误，用户名：" + getUserName() + " 密码：" + getPassword());
            return null;
        }
    }
    
    /**
     * 获取本地临时路径.
     * @return 附件本地临时路径
     */
    public String getLocalAbsoluteTempPath() {
        if (configuration == null ) {
            return null;
        }
        
        String localPath = configuration.getString("LOCAL_ABSOLUTE_TEMP_PATH");
        if (localPath != null) {
            localPath = localPath + "/";
        }
        return localPath;
    }
    
    /**
     * 获取文件类型（file,ftp,http等）.
     * @return 文件类型（file,ftp,http等）
     */
    private String getFileType() {
        if (configuration == null ) {
            return null;
        }
        
        String fileType = configuration.getString("FILE_TYPE");
        return fileType;
    }
    
    /**
     * 获取文件服务器IP.
     * @return 文件服务器IP.
     */
    private String getFileServerIp() {
        if (configuration == null ) {
            return null;
        }
        
        String fileServerIp = configuration.getString("FILE_SERVER_IP");
        return fileServerIp;
    }
    
    /**
     * 获取文件服务器端口.
     * @return 文件服务器端口.
     */
    private String getFileServerPort() {
        if (configuration == null ) {
            return null;
        }
        
        String fileServerPort = configuration.getString("FILE_SERVER_PORT");
        return fileServerPort;
    }
    
    /**
     * 获取用户名.
     * @return 用户名.
     */
    private String getUserName() {
        if (configuration == null ) {
            return null;
        }
        
        String userName = configuration.getString("USERNAME");
        return userName;
    }
    
    /**
     * 获取密码.
     * @return 密码.
     */
    private String getPassword() {
        if (configuration == null ) {
            return null;
        }
        
        String password = configuration.getString("PASSWORD");
        return password;
    }
    
    /**
     * 获取上载附件路径.
     * @return 上载附件路径.
     */
    private String getAbsoluteUploadPath() {
        if (configuration == null ) {
            return null;
        }
        
        String path = configuration.getString("ABSOLUTE_UPLOAD_PATH");
        if (path != null) {
            path = path + "/";
        }
        return path;
    }
    
    /**
     * 获取上载附件同步路径.
     * @return 上载附件路径.
     */
    private String getAbsoluteSyncPath() {
        if (configuration == null ) {
            return null;
        }
        
        String path = configuration.getString("ABSOLUTE_SYNC_PATH");
        if (path != null) {
            path = path + "/";
        }
        return path;
    }    
    
    /**
     * 得到SIMS（根据SIMS id）.
     * @param simsId
     * @return
     */
    public String getSimsId() {
        if (configuration == null ) {
            return null;
        }
        
        String simsId = configuration.getString("SIMS_ID");
        return simsId;
    }
    
    /**
     * 得到sequence前缀(8 + SIMS_ID后三位).
     * @param sequence前缀
     * @return
     */
    public String getSequencePrefix() {
        String simsId = getSimsId();
        if (simsId == null) {
            return SEQUENCE_PREFIX;
        }
        return SEQUENCE_PREFIX + StringUtils.right(simsId, 3);
    }
    
    /**
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<String> listPublicAttachTypes() {
    	if(configuration == null){
    		return null;
    	}
        return configuration.getList("PUBLIC_ATTACHMENT_TYPES");
    }
    
}