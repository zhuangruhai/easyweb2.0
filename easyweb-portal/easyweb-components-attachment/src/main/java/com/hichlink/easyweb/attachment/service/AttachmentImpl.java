package com.hichlink.easyweb.attachment.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs.CacheStrategy;
import org.apache.commons.vfs.FileContent;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.Selectors;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.impl.StandardFileSystemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hichlink.easyweb.attachment.AttachmentInterface;
import com.hichlink.easyweb.attachment.config.AttachmentConfig;
import com.hichlink.easyweb.attachment.constant.AttachmentConstants;
import com.hichlink.easyweb.attachment.dao.AttachmentFileMapper;
import com.hichlink.easyweb.attachment.dao.AttachmentGroupMapper;
import com.hichlink.easyweb.attachment.dao.AttachmentTypeMapper;
import com.hichlink.easyweb.attachment.entity.AttachmentFile;
import com.hichlink.easyweb.attachment.entity.AttachmentGroup;
import com.hichlink.easyweb.attachment.entity.AttachmentType;
import com.hichlink.easyweb.attachment.exception.AttachIsFormalException;
import com.hichlink.easyweb.attachment.exception.AttachNameLenghOverLimitException;
import com.hichlink.easyweb.attachment.exception.AttachNumberOverLimitException;
import com.hichlink.easyweb.attachment.exception.AttachSizeIsZeroException;
import com.hichlink.easyweb.attachment.exception.AttachSizeOverLimitException;
import com.hichlink.easyweb.attachment.exception.AttachTotalSizeOverLimitException;
import com.hichlink.easyweb.attachment.exception.AttachTypeNotExistException;
import com.hichlink.easyweb.attachment.exception.AttachmentException;

/**
 * 附件上传组件实现类
 * 
 * @author zhongcp
 * 
 */
@Service("attachmentInterface")
public class AttachmentImpl implements AttachmentInterface {
	private static final Logger logger = LoggerFactory
			.getLogger(AttachmentImpl.class);
	@Autowired
	private AttachmentFileMapper attachmentFileDao;
	@Autowired
	private AttachmentGroupMapper attachmentGroupDao;
	@Autowired
	private AttachmentTypeMapper attachmentTypeDao;

	/**
	 * 增加附件，单附件调用接口 （新增的文件都是处于暂停状态， 只有调用formalAttachmentGroup()之后文件才能生效）.
	 * 
	 * @param attachGroupId
	 *            附件组ID（可为空，如果为空，生成新的附件组）
	 * @param fileName
	 *            原始文件名
	 * @param contentType
	 *            内容类型（如text/plain，application/pdf等...）
	 * @param attachFile
	 *            File对象
	 * @param attachTypeId
	 *            附件类型（可为空，商业文件、资质证明等）
	 * @return 附件DO
	 */
	@Transactional(rollbackFor = Exception.class)
	public AttachmentFile addAttachmentFile(String attachGroupId,
			String fileName, String contentType, File file, String attachTypeId)
			throws AttachmentException {
		if (logger.isInfoEnabled()) {
			logger.info("进入暂存附件方法，参数attachGroupId=" + attachGroupId
					+ ",fileName=" + fileName + ",fileType=" + contentType
					+ ",file=" + file + ",attachTypeId=" + attachTypeId);
		}

		// 参数检查
		if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(contentType)
				|| null == file) {
			logger.error("暂存附件时，传入的参数为空");
			throw new IllegalArgumentException("暂存附件时，传入的参数为空");
		}

		if (file.length() == 0) {
			logger.error("不允许暂存0字节的文件！");
			throw new AttachmentException("不允许暂存0字节的文件！");
		}

		if (!file.exists()) {
			logger.error("暂存附件时，暂存的文件不存在");
			throw new IllegalArgumentException("暂存的文件不存在");
		}
		// 如果附件组id为空，则往附件组表中插入一条新的记录，并生成附件组id返回
		if (StringUtils.isEmpty(attachGroupId)) {
			AttachmentGroup attachmentGroup = new AttachmentGroup();
			attachmentGroup
					.setAttachGroupStatus(AttachmentConstants.ATTACH_STATUS_TEMP);
			if (null != attachTypeId) {
				attachmentGroup.setAttachTypeId(attachTypeId);
			}
			attachmentGroupDao.insert(attachmentGroup);
			attachGroupId = attachmentGroup.getAttachGroupId();
		}

		// 读取配置文件存储根目录和临时目录
		String uploadPath = AttachmentConfig.getInstance().getUploadPath();
		String tempPath = AttachmentConfig.getInstance()
				.getLocalAbsoluteTempPath();
		// 如果没有取到uploadPath，说明解析配置文件有问题
		if (uploadPath == null || uploadPath.trim().length() == 0
				|| tempPath == null || tempPath.trim().length() == 0) {
			throw new IllegalArgumentException(
					"解析配置文件错误，无法从配置文件中取到uploadPath值。");
		}

		Date today = new Date();
		String datePath = new SimpleDateFormat("yyMMdd").format(today);

		// 往附件文件表中插入记录
		AttachmentFile attachmentFile = new AttachmentFile();
		attachmentFile.setFileName(fileName);
		attachmentFile.setFileSize(new BigDecimal(file.length()));
		attachmentFile.setFileType(contentType);
		attachmentFile.setAttachGroupId(attachGroupId);
		attachmentFile
				.setAttachFileStatus(AttachmentConstants.ATTACH_STATUS_TEMP);
		attachmentFile.setFileSaveName(datePath);
		attachmentFile.setCreateDate(new Date());
		attachmentFileDao.insert(attachmentFile);
		attachmentFile.setFileSaveName(attachmentFile.getFileSaveName()
				+ "/"
				+ attachmentFile.getAttachFileId()
				+ fileName.substring(fileName.lastIndexOf("."),
						fileName.length()));
		attachmentFileDao.updateByPrimaryKeySelective(attachmentFile);

		// 保存文件到指定位置（配置文件指定的文件服务器目录）
		String fileUri = uploadPath + attachmentFile.getFileSaveName();
		// if(!file.isDirectory()){
		// fileUri = uploadPath +
		// attachmentFile.getFileSaveName()+File.separator+file.getName();
		// }
		FileObject vsfFileObject = null;
		FileObject localFileObject = null;
		try {
			FileSystemManager vfsManager = VFS.getManager();
			vsfFileObject = vfsManager.resolveFile(fileUri);
			if (!vsfFileObject.exists()) {
				vsfFileObject.createFile();
			}
			localFileObject = vfsManager.resolveFile(file.getAbsolutePath());
			synchronized (this) {
				vsfFileObject.copyFrom(localFileObject, Selectors.SELECT_SELF);
			}
		} catch (Exception e) {
			logger.error("保存附件时失败", e);
			throw new AttachmentException("保存附件时失败：", e);
		} finally {
			if (vsfFileObject != null) {
				try {
					vsfFileObject.close();
				} catch (FileSystemException e) {
					logger.error("关闭文件服务器文件失败", e);
				}
			}
			if (localFileObject != null) {
				try {
					localFileObject.close();
				} catch (IOException e) {
					logger.error("关闭文件失败", e);
				}
			}
		}
		try {

		} catch (Exception e) {
			logger.error("保存附件时失败", e);
			throw new AttachmentException("保存附件时失败：", e);
		}

		if (logger.isInfoEnabled()) {
			logger.debug("离开暂存附件方法，返回值=" + attachmentFile);
		}
		return attachmentFile;
	}

	/**
	 * 增加附件，多附件调用接口 （新增的文件都是处于暂停状态， 只有调用changeAttachmentFiles()之后文件才能生效）.
	 * 
	 * @param attachGroupId
	 *            附件组ID（可为空，如果为空，生成新的附件组）
	 * @param fileName
	 *            原始文件名
	 * @param contentType
	 *            内容类型（如text/plain，application/pdf等...）
	 * @param attachFile
	 *            File对象
	 * @param attachTypeId
	 *            附件类型（可为空，商业文件、资质证明等）
	 * @return 附件DO
	 */
	@Transactional(rollbackFor = Exception.class)
	public AttachmentFile addFile(String attachGroupId, String fileName,
			String contentType, File file, String attachTypeId)
			throws AttachmentException {
		if (logger.isInfoEnabled()) {
			logger.info("进入暂存附件方法，参数attachGroupId=" + attachGroupId
					+ ",fileName=" + fileName + ",fileType=" + contentType
					+ ",file=" + file + ",attachTypeId=" + attachTypeId);
		}

		// 参数检查
		if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(contentType)
				|| null == file) {
			logger.error("暂存附件时，传入的参数为空");
			throw new IllegalArgumentException("暂存附件时，传入的参数为空");
		}

		if (file.length() == 0) {
			logger.error("不允许暂存0字节的文件！");
			throw new AttachmentException("不允许暂存0字节的文件！");
		}

		if (!file.exists()) {
			logger.error("暂存附件时，暂存的文件不存在");
			throw new IllegalArgumentException("暂存的文件不存在");
		}

		// 如果附件组id为空，则往附件组表中插入一条新的记录，并生成附件组id返回
		if (StringUtils.isEmpty(attachGroupId)) {
			AttachmentGroup attachmentGroup = new AttachmentGroup();
			attachmentGroup
					.setAttachGroupStatus(AttachmentConstants.ATTACH_STATUS_TEMP);
			if (null != attachTypeId) {
				attachmentGroup.setAttachTypeId(attachTypeId);
			}
			attachmentGroupDao.insert(attachmentGroup);
			attachGroupId = attachmentGroup.getAttachGroupId();
		}

		// 读取配置文件存储根目录和临时目录
		String uploadPath = AttachmentConfig.getInstance().getUploadPath();

		// 如果没有取到uploadPath，说明解析配置文件有问题
		if (uploadPath == null || uploadPath.trim().length() == 0) {
			throw new IllegalArgumentException(
					"解析配置文件错误，无法从配置文件中取到uploadPath值。");
		}

		Date today = new Date();
		String datePath = new SimpleDateFormat("yyMMdd").format(today);

		// 往附件文件表中插入记录
		AttachmentFile attachmentFile = new AttachmentFile();
		attachmentFile.setFileName(fileName);
		attachmentFile.setFileSize(BigDecimal.valueOf(file.length()));
		attachmentFile.setFileType(contentType);
		attachmentFile.setAttachGroupId(attachGroupId);
		attachmentFile
				.setAttachFileStatus(AttachmentConstants.ATTACH_STATUS_TEMP);
		attachmentFile.setFileSaveName(datePath);
		attachmentFile.setCreateDate(new Date());
		attachmentFileDao.insert(attachmentFile);
		attachmentFile.setFileSaveName(attachmentFile.getFileSaveName()
				+ "/"
				+ attachmentFile.getAttachFileId()
				+ fileName.substring(fileName.lastIndexOf("."),
						fileName.length()));
		attachmentFileDao.updateByPrimaryKeySelective(attachmentFile);

		// 保存文件到指定位置（配置文件指定的文件服务器目录）
		String fileUri = uploadPath + attachmentFile.getFileSaveName();
		if (!file.isDirectory()) {
			fileUri = uploadPath + attachmentFile.getFileSaveName()
					+ File.separator + file.getName();
		}
		FileObject vsfFileObject = null;
		FileObject localFileObject = null;
		try {
			FileSystemManager vfsManager = VFS.getManager();
			vsfFileObject = vfsManager.resolveFile(fileUri);
			if (!vsfFileObject.exists()) {
				vsfFileObject.createFile();
			}

			localFileObject = vfsManager.resolveFile(file.getAbsolutePath());
			synchronized (this) {
				vsfFileObject.copyFrom(localFileObject, Selectors.SELECT_SELF);
			}
		} catch (Exception e) {
			logger.error("保存附件时失败", e);
			throw new AttachmentException("保存附件时失败：", e);
		} finally {
			if (vsfFileObject != null) {
				try {
					vsfFileObject.close();
				} catch (FileSystemException e) {
					logger.error("关闭文件服务器文件失败", e);
				}
			}
			if (localFileObject != null) {
				try {
					localFileObject.close();
				} catch (IOException e) {
					logger.error("关闭文件失败", e);
				}
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("离开暂存附件方法，返回值=" + attachmentFile);
		}
		return attachmentFile;
	}

	/**
	 * 校验附件.
	 * 
	 * @param attachGroupId
	 *            附件组ID（可为空，如果为空，生成新的附件组）
	 * @param fileName
	 *            原始文件名
	 * @param attachFile
	 *            File对象
	 * @param attachTypeId
	 *            附件类型（可为空，商业文件、资质证明等）
	 */
	public void validateAttachmentFile(String attachGroupId, String fileName,
			File file, String attachTypeId, int willAddNum, long willAddSize,
			int willDeleteNum, long willDeleteSize)
			throws AttachNumberOverLimitException,
			AttachSizeOverLimitException, AttachTypeNotExistException,
			AttachTotalSizeOverLimitException, AttachSizeIsZeroException,
			AttachNameLenghOverLimitException

	{
		if (logger.isDebugEnabled()) {
			logger.debug("进入校验附件方法，参数attachGroupId=" + attachGroupId
					+ ",fileName=" + fileName + ",file=" + file
					+ ",attachTypeId=" + attachTypeId);
		}

		// 参数检查
		if (StringUtils.isEmpty(fileName) || null == file) {
			logger.error("校验附件，传入的参数为空");
			throw new IllegalArgumentException("校验附件时，传入的参数为空");
		}

		if (file.length() == 0) {
			logger.error("不允许暂存0字节的文件！");
			throw new AttachmentException("不允许暂存0字节的文件！");
		}

		if (!file.exists()) {
			logger.error("暂存附件时，暂存的文件不存在");
			throw new IllegalArgumentException("暂存的文件不存在");
		}

		// 得到文件后缀名
		String fileSuffixName = StringUtils.substringAfterLast(fileName, ".");

		// 进行文件类型的检查
		if (StringUtils.isNotEmpty(attachTypeId)) {
			AttachmentType attachType = attachmentTypeDao
					.selectByPrimaryKey(attachTypeId);
			if (null == attachType) {
				if (logger.isDebugEnabled()) {
					logger.debug("不存在对应的附件类型，对上传的附件不做类型的检查，attachTypeId="
							+ attachTypeId);
				}
				return;
			} else {
				// 检查文件名
				// System.err.println("文件名长度：" + fileName);
				if (fileName.length() > 255) {
					logger.error("文件名总长度(含后缀名)超出数据库库最大限制（256个字符）。");
					throw new AttachNameLenghOverLimitException(
							"文件名总长度(含后缀名)超出数据库库最大限制(256个字符)");
				}

				String filePrefixName = "";
				if (fileName.indexOf(".") != -1) {
					filePrefixName = fileName.substring(0,
							fileName.indexOf("."));
				} else {
					filePrefixName = fileName;
				}
				if (filePrefixName.length() > attachType.getMaxFileNameLength()
						.intValue()) {
					logger.error("文件名超出" + attachType.getMaxFileNameLength()
							+ "个字符。");
					throw new AttachNameLenghOverLimitException("文件名长度超出"
							+ attachType.getMaxFileNameLength()
							+ "个字符限制(1个中文字相当于2个字符)");
				}

				// 检查附件大小
				if (file.length() > attachType.getSingleSizeLimit().intValue()) {
					logger.error("附件的大小超过了上限");
					throw new AttachSizeOverLimitException("附件的大小超过了上限,不得超过"
							+ formateFileSize(attachType.getSingleSizeLimit()
									.intValue()));
				}
				// 检查附件类型
				if (null != attachType.getFileSuffixLimit()) {
					if (StringUtils.isEmpty(fileSuffixName)
							|| attachType.getFileSuffixLimit().indexOf(
									fileSuffixName.toLowerCase()) < 0) {
						logger.error("附件的类型不在限制类型范围内");
						throw new AttachTypeNotExistException("附件的类型不在限制类型范围内");
					}
				}
				// 检查整个附件组的大小和附件总数
				if (null != attachGroupId) {
					// HashMap rtnMap = (HashMap) attachmentFileDao
					// .getStatDataByGroup(attachGroupId);
					// int count = ((java.math.BigDecimal) rtnMap.get("count"))
					// .intValue();
					// int totalSize = 0;
					// if (count > 0) {
					// totalSize = ((java.math.BigDecimal) rtnMap
					// .get("totalSize")).intValue();
					// }
					// 多附件接口不需要在这里统计原来附件组的文件大小
					if (file.length() + willAddSize - willDeleteSize > attachType
							.getAttachSizeLimit().intValue()) {
						logger.error("附件组总的大小超过了上限");
						throw new AttachTotalSizeOverLimitException(
								"附件组总的大小超过了上限,不得超过"
										+ formateFileSize(attachType
												.getAttachSizeLimit()
												.intValue()));
					}
					// 这里的1是算新上传的那个文件
					if (1 + willAddNum - willDeleteNum > attachType
							.getAttachCountLimit().intValue()) {
						logger.error("附件组总数超过了上限");
						throw new AttachNumberOverLimitException("附件组总数超过了上限:"
								+ attachType.getAttachCountLimit().intValue());
					}
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("离开校验附件方法");
		}
		return;
	}

	/**
	 * 对文件大小进行格式化
	 * 
	 * @param size
	 * @return
	 */
	private String formateFileSize(int size) {
		String fomattedSize = "0KB";
		if (size < 1024) {
			fomattedSize = size + "B";
		} else if (size >= 1024 && size < 1024 * 1024) {
			fomattedSize = (size % 1024 == 0 ? size / 1024 : String.format(
					"%.2f", (double) size / 1024)) + "KB";
		} else {
			fomattedSize = (size % (1024 * 1024) == 0 ? size / (1024 * 1024)
					: String.format("%.2f", (double) size / (1024 * 1024)))
					+ "MB";
		}
		return fomattedSize;
	}

	/**
	 * 多附件修改内容生效接口 当使用多附件功能时，只要修改过附件组中的任何内容， 必须调用此接口才能使附件内容生效，否则附件组内容仍然是修改前的内容。
	 * 
	 * @param addAttachFileIds
	 *            新增附件编号列表
	 * @param deleteAttachFileIds
	 *            删除附件编号
	 */
	@Transactional(rollbackFor = Exception.class)
	public void changeAttachmentFiles(List addAttachFileIds,
			List deleteAttachFileIds) throws AttachmentException {
		if (logger.isDebugEnabled()) {
			logger.debug("进入修改附件方法，参数addAttachFileIds" + addAttachFileIds
					+ ",deleteAttachFileIds=" + deleteAttachFileIds);
		}

		// 新增附件（生效）
		String attachFileId = null;
		if (null != addAttachFileIds) {
			for (int i = 0; i < addAttachFileIds.size(); i++) {
				attachFileId = (String) addAttachFileIds.get(i);
				AttachmentFile attachFile = attachmentFileDao
						.selectByPrimaryKey(attachFileId);
				if (attachFile != null
						&& AttachmentConstants.ATTACH_STATUS_TEMP
								.equals(attachFile.getAttachFileStatus())) {
					formalAttachmentFile(attachFile);
				}
			}
		}

		// 删除附件
		if (null != deleteAttachFileIds) {
			for (int i = 0; i < deleteAttachFileIds.size(); i++) {
				attachFileId = (String) deleteAttachFileIds.get(i);
				AttachmentFile attachFile = attachmentFileDao
						.selectByPrimaryKey(attachFileId);
				// 如果是暂存状态，则需要删除表中记录同时删除文件
				if (attachFile != null
						&& AttachmentConstants.ATTACH_STATUS_TEMP
								.equals(attachFile.getAttachFileStatus())) {
					// 删除附件文件
					deleteFile(attachFile.getFileSaveName());
				}

				if (attachFile != null) {
					attachmentFileDao.deleteByPrimaryKey(attachFileId);
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("离开修改附件方法，无返回值");
		}

	}

	/**
	 * 多附件内容被修改后，调用此接口对修改内容进行生效。此接口功能同changeAttachmentFiles，是其简化形式，
	 * 主要是为新的jquery上传组件构建
	 * 新的jquery上传组件支持多附件上传，单附件是其特例，与老的ext组件不同的是不再传送添加的和删除的附件文件ID
	 * ，仅传送附件组中最终保留的附件文件ID
	 * 
	 * @param attachGroupId
	 *            附件组ID
	 * @param finalAttachFileIds
	 *            附件组中最终要保留的附件文件ID
	 * @throws AttachmentException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void formalAttachmentGroup(String attachGroupId,
			List<String> finalAttachFileIds) throws AttachmentException {
		if (logger.isInfoEnabled()) {
			logger.info("进入生效附件组方法，参数attachGroupId=" + attachGroupId
					+ ",finalAttachFileIds=" + finalAttachFileIds);
		}
		if (null == attachGroupId) {
			logger.error("生效附件组时，传入的参数附件组id为空");
			return;
		}
		if (finalAttachFileIds == null || finalAttachFileIds.size() == 0) {
			logger.error("生效附件组时，传入的参数附件组finalAttachFileIds为空");
			return;
		}

		// 找到新添加的和删除的附件文件ID
		List<String> addIds = new ArrayList<String>();
		List<String> delIds = new ArrayList<String>();
		// 最终附件IDset
		Set<String> finalIdSet = new HashSet<String>();
		// 库中附件IDset
		Map<String, AttachmentFile> orignAttachMap = new HashMap<String, AttachmentFile>();
		for (String finalId : finalAttachFileIds) {
			finalIdSet.add(finalId);
		}
		AttachmentFile attachmentFile = new AttachmentFile();
		attachmentFile.setAttachGroupId(attachGroupId);
		List<AttachmentFile> attachFileList = attachmentFileDao
				.list(attachmentFile);

		for (int i = 0; i < attachFileList.size(); i++) {
			AttachmentFile attachFile = (AttachmentFile) attachFileList.get(i);
			if (!finalIdSet.contains(attachFile.getAttachFileId())) {// 删除的，即最终附件ID里不包括的
				delIds.add(attachFile.getAttachFileId());
			}
			// 顺便放到map中
			orignAttachMap.put(attachFile.getAttachFileId(), attachFile);
		}
		for (String finalId : finalAttachFileIds) {
			AttachmentFile attachFile = orignAttachMap.get(finalId);
			if (attachFile != null
					&& AttachmentConstants.ATTACH_STATUS_TEMP
							.equalsIgnoreCase(attachFile.getAttachFileStatus())) {
				addIds.add(finalId);
			}
		}

		changeAttachmentFiles(addIds.subList(0, addIds.size()),
				delIds.subList(0, delIds.size()));

		if (logger.isDebugEnabled()) {
			logger.debug("离开formalAttachmentGroup(String, List<String>)，无返回值");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.sims.rtplt.component.attachment.AttachmentInterface#
	 * cloneAttachmentGroup(java.lang.String)
	 */
	public String cloneAttachmentGroup(String attachGroupId) {
		if (logger.isDebugEnabled()) {
			logger.debug("进入克隆附件组方法，参数attachGroupId=" + attachGroupId);
		}

		if (null == attachGroupId) {
			logger.error("克隆附件组时，传入的参数attachGroupId为空");
			return null;
		}

		AttachmentGroup attachGroup = attachmentGroupDao
				.selectByPrimaryKey(attachGroupId);
		if (null == attachGroup) {
			logger.error("克隆附件组时，对应的附件组不存在");
			return null;
		}

		// 插入新的附件组（为了产生新的附件组件ID，将附件组id设为空）
		attachGroup.setAttachGroupId(null);
		attachmentGroupDao.insert(attachGroup);
		String cloneId = attachGroup.getAttachGroupId();

		// 查找附件组对应的所有附件
		AttachmentFile attachFile = new AttachmentFile();
		attachFile.setAttachGroupId(attachGroupId);
		List<AttachmentFile> attachFileList = attachmentFileDao
				.list(attachFile);
		for (int i = 0; i < attachFileList.size(); i++) {
			// 修改附件的组id值为新插入的组id，fileSaveName字段值不发生变化（为了产生新的附件组件ID，将附件组id设为空）
			AttachmentFile tmpFile = (AttachmentFile) attachFileList.get(i);
			tmpFile.setAttachFileId(null);
			tmpFile.setAttachGroupId(attachGroup.getAttachGroupId());
			attachmentFileDao.insert(tmpFile);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("离开克隆附件组方法，返回:" + cloneId);
		}
		return cloneId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.sims.rtplt.component.attachment.AttachmentInterface#
	 * findAttachmentFile(java.lang.String)
	 */
	public AttachmentFile findAttachmentFile(String attachFileId)
			throws AttachmentException {
		if (logger.isInfoEnabled()) {
			logger.info("进入查找附件方法，参数attachFileId=" + attachFileId);
		}

		if (null == attachFileId) {
			logger.error("查找附件时，传入的参数attachFileId为空");
			return null;
		}

		AttachmentFile attachFile = attachmentFileDao
				.selectByPrimaryKey(attachFileId);
		if (null == attachFile) {
			logger.error("查找附件时，对应的附件记录不存在");
			return null;
		}

		// 将保存在文件服务器的文件复制成本地文件返回
		String fileUri = AttachmentConfig.getInstance().getUploadPath()
				+ attachFile.getFileSaveName();
		String fileName = StringUtils.substringAfterLast(fileUri, "/");
		String localFileName = AttachmentConfig.getInstance()
				.getLocalAbsoluteTempPath() + fileName;
		FileObject vsfFileObject = null;
		FileContent fileContent = null;
		FileOutputStream fileOutputStream = null;
		try {
			FileSystemManager vfsManager = VFS.getManager();
			vsfFileObject = vfsManager.resolveFile(fileUri);
			fileContent = vsfFileObject.getContent();

			// 新建一个本地临时文件，设置到附件DO
			File localFile = new File(localFileName);
			fileOutputStream = new FileOutputStream(localFile);
			synchronized (this) {
				IOUtils.copy(fileContent.getInputStream(), fileOutputStream);
			}
			attachFile.setFile(localFile);
		} catch (Exception e) {
			logger.error("从文件服务器取附件失败", e);
			throw new AttachmentException("查找附件时失败", e);
		} finally {

			if (fileContent != null) {
				try {
					fileContent.close();
				} catch (FileSystemException e) {
					logger.error("关闭文件服务器文件失败", e);
				}
			}

			/*************************************************
			 * vsfFileObject需要关闭 2009-2-11 SIMP发现
			 **/
			if (vsfFileObject != null) {
				try {
					vsfFileObject.close();
				} catch (FileSystemException e) {
					logger.error("关闭文件服务器文件失败", e);
				}
			}

			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					logger.error("关闭文件失败", e);
				}
			}
		}

		if (logger.isInfoEnabled()) {
			logger.info("离开查找附件方法，返回值=" + attachFile);
		}
		return attachFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.sims.rtplt.component.attachment.AttachmentInterface#
	 * findAttachmentFile(java.lang.String)
	 */
	public AttachmentFile findSimpleAttachmentFile(String attachFileId)
			throws AttachmentException {
		if (logger.isInfoEnabled()) {
			logger.info("进入查找简单附件方法，参数attachFileId=" + attachFileId);
		}

		if (null == attachFileId) {
			logger.error("查找简单附件时，传入的参数attachFileId为空");
			return null;
		}

		AttachmentFile attachFile = attachmentFileDao
				.selectByPrimaryKey(attachFileId);
		if (null == attachFile) {
			logger.error("查找简单附件时，对应的附件记录不存在");
			return null;
		}

		if (logger.isInfoEnabled()) {
			logger.info("离开查找附件方法，返回值=" + attachFile);
		}
		return attachFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.sims.rtplt.component.attachment.AttachmentInterface#
	 * listAttachmentFile(java.lang.String)
	 */
	public List<AttachmentFile> listAttachmentFile(AttachmentFile attachmentFile) {
		if (logger.isDebugEnabled()) {
			logger.debug("进入列表附件方法，参数attachmentFile=" + attachmentFile);
		}

		if (null == attachmentFile || attachmentFile.getAttachGroupId() == null) {
			logger.error("列表附件时，传入的参数attachmentFile为空或者attachGroupId为空");
			return null;
		}
		List<AttachmentFile> attachFiles = attachmentFileDao
				.list(attachmentFile);
		if (null == attachFiles) {
			logger.error("列表附件时，对应的附件记录不存在");
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("离开列表附件方法，返回值=" + attachFiles);
		}
		return attachFiles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.sims.rtplt.component.attachment.AttachmentInterface#
	 * listAttachmentFile(java.lang.String)
	 */
	public List<AttachmentFile> listAttachmentFile(String attachGroupId) {
		if (logger.isDebugEnabled()) {
			logger.debug("进入列表附件方法，参数attachGroupId=" + attachGroupId);
		}

		if (null == attachGroupId) {
			logger.error("列表附件时，传入的参数attachGroupId为空");
			return null;
		}

		AttachmentFile attachmentFile = new AttachmentFile();
		attachmentFile.setAttachGroupId(attachGroupId);
		List<AttachmentFile> attachFiles = attachmentFileDao
				.list(attachmentFile);
		if (null == attachFiles) {
			logger.error("列表附件时，对应的附件记录不存在");
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("离开列表附件方法，返回值=" + attachFiles);
		}
		return attachFiles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.sims.rtplt.component.attachment.AttachmentInterface#
	 * listAttachmentFile(java.lang.String)
	 */
	public Map<String, List<AttachmentFile>> listAttachmentFile(
			List<String> attachGroupIdList) {
		if (logger.isDebugEnabled()) {
			logger.debug("进入列表附件方法，参数attachGroupIdList=" + attachGroupIdList);
		}

		if (null == attachGroupIdList || attachGroupIdList.size() == 0) {
			logger.error("列表附件时，传入的参数attachGroupIdList为空");
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("'");
		for (Iterator<String> iter = attachGroupIdList.iterator(); iter
				.hasNext();) {
			sb.append(iter.next());
			if (iter.hasNext()) {
				sb.append("','");
			}
		}
		sb.append("'");

		AttachmentFile attachmentFile = new AttachmentFile();
		attachmentFile.addOtherField("groupIds", sb.toString());
		List<AttachmentFile> attachFiles = attachmentFileDao
				.list(attachmentFile);
		if (null == attachFiles) {
			logger.error("列表附件时，对应的附件记录不存在");
			return null;
		}
		HashMap<String, List<AttachmentFile>> filesMap = new HashMap<String, List<AttachmentFile>>();
		for (Iterator iterator = attachFiles.iterator(); iterator.hasNext();) {
			AttachmentFile file = (AttachmentFile) iterator.next();
			List<AttachmentFile> list = filesMap.get(file.getAttachGroupId());
			if (list == null) {
				list = new LinkedList<AttachmentFile>();
				filesMap.put(file.getAttachGroupId(), list);
			}
			list.add(file);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("离开列表附件方法，返回值=" + filesMap);
		}
		return filesMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.sims.rtplt.component.attachment.AttachmentInterface#
	 * listFormalAttachmentFile(java.lang.String)
	 */
	public List<AttachmentFile> listFormalAttachmentFile(String attachGroupId) {
		if (logger.isDebugEnabled()) {
			logger.debug("进入列表有效附件方法，参数attachGroupId=" + attachGroupId);
		}

		if (null == attachGroupId) {
			logger.error("列表有效附件时，传入的参数attachGroupId为空");
			return null;
		}

		AttachmentFile attachmentFile = new AttachmentFile();
		attachmentFile.setAttachGroupId(attachGroupId);
		attachmentFile
				.setAttachFileStatus(AttachmentConstants.ATTACH_STATUS_FORMAL);
		List<AttachmentFile> attachFiles = attachmentFileDao
				.list(attachmentFile);
		if (null == attachFiles) {
			logger.error("列表有效附件时，对应的附件记录不存在");
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("离开有效列表附件方法，返回值=" + attachFiles);
		}
		return attachFiles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.sims.rtplt.component.attachment.AttachmentInterface#
	 * listFormalAttachmentFile(java.lang.String)
	 */
	public Map<String, List<AttachmentFile>> listFormalAttachmentFile(
			List<String> attachGroupIdList) {
		if (logger.isDebugEnabled()) {
			logger.debug("进入列表有效附件方法，参数attachGroupIdList=" + attachGroupIdList);
		}

		if (null == attachGroupIdList || attachGroupIdList.size() == 0) {
			logger.error("列表有效附件时，传入的参数attachGroupIdList为空");
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("'");
		for (Iterator<String> iter = attachGroupIdList.iterator(); iter
				.hasNext();) {
			sb.append(iter.next());
			if (iter.hasNext()) {
				sb.append("','");
			}
		}
		sb.append("'");

		AttachmentFile attachmentFile = new AttachmentFile();
		attachmentFile.addOtherField("groupIds", sb.toString());
		attachmentFile
				.setAttachFileStatus(AttachmentConstants.ATTACH_STATUS_FORMAL);
		List<AttachmentFile> attachFiles = attachmentFileDao
				.list(attachmentFile);
		if (null == attachFiles) {
			logger.error("列表有效附件时，对应的附件记录不存在");
			return null;
		}
		Map<String, List<AttachmentFile>> filesMap = new HashMap<String, List<AttachmentFile>>();
		for (Iterator iterator = attachFiles.iterator(); iterator.hasNext();) {
			AttachmentFile file = (AttachmentFile) iterator.next();
			List<AttachmentFile> list = filesMap.get(file.getAttachGroupId());
			if (list == null) {
				list = new LinkedList<AttachmentFile>();
				filesMap.put(file.getAttachGroupId(), list);
			}
			list.add(file);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("离开列表有效附件方法，返回值=" + filesMap);
		}
		return filesMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.sims.rtplt.component.attachment.AttachmentInterface#
	 * deleteAttachmentGroup(java.lang.String)
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteAttachmentGroup(String attachGroupId) {
		if (logger.isDebugEnabled()) {
			logger.debug("进入deleteAttachmentGroup方法，参数attachGroupId="
					+ attachGroupId);
		}
		if (null == attachGroupId) {
			logger.error("删除附件时，传入的参数attachGroupId为空");
			return;
		}

		List<AttachmentFile> attachmentFiles = this
				.listAttachmentFile(attachGroupId);
		if (attachmentFiles != null && attachmentFiles.size() > 0) {
			for (Iterator it = attachmentFiles.iterator(); it.hasNext();) {
				AttachmentFile attachmentFile = (AttachmentFile) it.next();
				// 如果是暂存状态，则需要删除表中记录同时删除文件
				if (AttachmentConstants.ATTACH_STATUS_TEMP
						.equals(attachmentFile.getAttachFileStatus())) {
					deleteFile(attachmentFile.getFileSaveName());
				}
			}
		}

		// 删除附件
		attachmentFileDao.deleteAttachmentFileByGroupId(attachGroupId);

		// 删除附件组
		attachmentGroupDao.deleteByPrimaryKey(attachGroupId);

		if (logger.isDebugEnabled()) {
			logger.debug("离开deleteAttachmentGroup");
		}
	}

	/**
	 * 单附件和多附件新建生效接口 单附件和多附件新建时必须调用此接口使附件生效，否则附件只处于暂存状态
	 * 
	 * @param attachGroupId
	 *            附件组ID
	 */
	@Transactional(rollbackFor = Exception.class)
	public void formalAttachmentGroup(String attachGroupId)
			throws AttachmentException {
		if (logger.isInfoEnabled()) {
			logger.info("进入生效附件组方法，参数attachGroupId=" + attachGroupId);
		}
		if (null == attachGroupId) {
			logger.error("生效附件组时，传入的参数附件组id为空");
			return;
		}

		// 查找对应的附件组记录，并判断状态是否为暂存状态
		AttachmentGroup attachGroup = attachmentGroupDao
				.selectByPrimaryKey(attachGroupId);
		if (null == attachGroup) {
			logger.error("生效附件组时，对应的附件组记录不存在，attachGroupId=" + attachGroupId);
			return;
		}
		if (!AttachmentConstants.ATTACH_STATUS_TEMP.equals(attachGroup
				.getAttachGroupStatus())) {
			logger.error("生效附件组时，对应的附件组状态不为暂存状态，不能生效附件组");
			return;
		}

		// 根据附件类型判断是否需要同步
		boolean bSync = false;
		String attachTypeId = attachGroup.getAttachTypeId();
		if (!StringUtils.isEmpty(attachTypeId)) {
			AttachmentType attachmentType = attachmentTypeDao
					.selectByPrimaryKey(attachTypeId);
			if (attachmentType != null) {
				bSync = attachmentType.isSync();
			}
		}

		// 查找所有的附件记录
		AttachmentFile attachmentFile = new AttachmentFile();
		attachmentFile.setAttachGroupId(attachGroupId);
		List<AttachmentFile> attachFileList = attachmentFileDao
				.list(attachmentFile);

		for (int i = 0; i < attachFileList.size(); i++) {
			AttachmentFile attachFile = (AttachmentFile) attachFileList.get(i);
			if (AttachmentConstants.ATTACH_STATUS_TEMP.equals(attachFile
					.getAttachFileStatus())) {
				formalAttachmentFile(attachFile, bSync);
			}
		}

		// 修改附件组记录状态为正式
		attachGroup
				.setAttachGroupStatus(AttachmentConstants.ATTACH_STATUS_FORMAL);
		attachmentGroupDao.updateByPrimaryKey(attachGroup);

		if (logger.isInfoEnabled()) {
			logger.info("离开生效附件组方法");
		}
	}

	/**
	 * 撤消暂存附件.
	 * 
	 * @param attachFileId
	 *            附件编号.
	 */
	@Transactional(rollbackFor = Exception.class)
	public void withdrawAttachmentFile(String attachFileId)
			throws AttachIsFormalException, AttachmentException {
		if (logger.isInfoEnabled()) {
			logger.info("进入取消暂存附件方法，参数attachFileId=" + attachFileId);
		}

		if (null == attachFileId) {
			logger.error("取消暂存附件时，传入的参数附件id为空");
			throw new AttachmentException("取消暂存附件时，传入的参数附件id为空");
		}

		// 附件id不为空，则删除附件表中的对应记录
		AttachmentFile attachFile = attachmentFileDao
				.selectByPrimaryKey(attachFileId);
		if (null == attachFile) {
			logger.error("取消暂存附件时，对应的附件记录不存在，attachFileId=" + attachFileId);
			throw new AttachmentException("取消暂存附件时，对应的附件记录不存在，attachFileId="
					+ attachFileId);
		}

		// 如果附件状态不为暂存，则记录错误 并返回
		if (!AttachmentConstants.ATTACH_STATUS_TEMP.equalsIgnoreCase(attachFile
				.getAttachFileStatus())) {
			if (logger.isInfoEnabled()) {
				logger.info("取消暂存附件时，对应的附件状态不为暂存，不能取消暂存");
			}
			throw new AttachIsFormalException("取消暂存附件时，对应的附件状态不为暂存，不能取消暂存");
		}

		// 删除附件文件
		deleteFile(attachFile.getFileSaveName());

		// 删除表中对应记录
		attachmentFileDao.deleteByPrimaryKey(attachFile.getAttachFileId());

		if (logger.isInfoEnabled()) {
			logger.info("离开取消暂存附件方法，无返回值");
		}
	}

	/**
	 * 生效单个附件.
	 * 
	 * @param attachmentFile
	 */
	private void formalAttachmentFile(AttachmentFile attachmentFile)
			throws AttachmentException {
		if (attachmentFile == null) {
			throw new AttachmentException("生效附件时参数为空");
		}

		// 查找对应的附件组记录，并判断状态是否为暂存状态
		AttachmentGroup attachGroup = attachmentGroupDao
				.selectByPrimaryKey(attachmentFile.getAttachGroupId());
		if (null == attachGroup) {
			logger.error("生效附件时，对应的附件组记录不存在，attachGroupId="
					+ attachmentFile.getAttachGroupId());
			return;
		}

		// 根据附件类型判断是否需要同步
		boolean bSync = false;
		String attachTypeId = attachGroup.getAttachTypeId();
		if (!StringUtils.isEmpty(attachTypeId)) {
			AttachmentType attachmentType = attachmentTypeDao
					.selectByPrimaryKey(attachTypeId);
			if (attachmentType != null) {
				bSync = attachmentType.isSync();
			}
		}

		// 生效单个附件.
		formalAttachmentFile(attachmentFile, bSync);
	}

	/**
	 * 生效单个附件.
	 * 
	 * @param attachmentFile
	 */
	@Transactional(rollbackFor = Exception.class)
	private void formalAttachmentFile(AttachmentFile attachmentFile,
			boolean bSync) throws AttachmentException {
		// 得到附件保存路径和同步路径
		String uploadPath = AttachmentConfig.getInstance().getUploadPath();
		String syncPath = AttachmentConfig.getInstance().getSyncPath();

		// 将附件从暂存目录移到正式目录
		String fileSaveName = attachmentFile.getFileSaveName();

		// 文件的上传地址和同步地址
		String uploadFileUri = uploadPath + fileSaveName;
		String syncFileUri = syncPath + fileSaveName;
		FileObject uploadFileObject = null;
		FileObject syncFileObject = null;
		try {
			// 如果这类附件需要同步，复制一份到同步目录（附件同步组件会定时从这个目录做附件同步）
			if (bSync) {
				FileSystemManager vfsManager = VFS.getManager();
				uploadFileObject = vfsManager.resolveFile(uploadPath
						+ attachmentFile.getFileSaveName());

				syncFileObject = vfsManager.resolveFile(syncFileUri);
				// 如果文件不存在，则创建
				if (!syncFileObject.exists()) {
					syncFileObject.createFile();
				}
				synchronized (this) {
					syncFileObject.copyFrom(uploadFileObject,
							Selectors.SELECT_ALL);
				}
			}
		} catch (Exception e) {
			logger.error("生效附件复制时失败", e);
			throw new AttachmentException("生效附件复制时失败", e);
		} finally {
			if (uploadFileObject != null) {
				try {
					uploadFileObject.close();
				} catch (FileSystemException e) {
					logger.error("关闭文件服务器文件失败", e);
				}
			}
			if (syncFileObject != null) {
				try {
					syncFileObject.close();
				} catch (IOException e) {
					logger.error("关闭文件失败", e);
				}
			}
		}

		// 修改附件记录的状态和文件保存名
		attachmentFile
				.setAttachFileStatus(AttachmentConstants.ATTACH_STATUS_FORMAL);
		attachmentFileDao.updateByPrimaryKeySelective(attachmentFile);
	}

	/**
	 * 删除文件.
	 * 
	 * @param fileSaveName
	 *            文件名
	 */
	private void deleteFile(String fileSaveName) {
		// 得到附件保存路径，并删除附件
		String vfsFileUri = AttachmentConfig.getInstance().getUploadPath()
				+ fileSaveName;
		FileObject vfsFileObject = null;
		try {
			FileSystemManager vfsManager = VFS.getManager();
			vfsFileObject = vfsManager.resolveFile(vfsFileUri);
			vfsFileObject.delete();
		} catch (Exception e) {
			logger.error("删除文件时失败", e);
		} finally {
			/*****************************
			 * 需要关闭vfsFileObject 2009-2-11 SIMP发现
			 */
			if (vfsFileObject != null) {
				try {
					vfsFileObject.close();
				} catch (FileSystemException e) {
					logger.error("关闭文件失败", e);
				}
			}
		}
	}

	/**
	 * 查找附件类型.
	 * 
	 * @param typeId
	 *            附件类型
	 * @return 附件类型
	 * @throws DataAccessException
	 *             DataAccessException
	 */
	public AttachmentType findAttachmentType(String typeId)
			throws AttachmentException {
		if (logger.isDebugEnabled()) {
			logger.debug("进入findAttachmentType(String), 输入参数[" + typeId + "]");
		}
		if (StringUtils.isEmpty(typeId)) {
			logger.error("参数为空");
			throw new AttachmentException("参数为空");
		}

		AttachmentType type = attachmentTypeDao.selectByPrimaryKey(typeId);
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.sims.rtplt.component.attachment.AttachmentInterface#
	 * findAttachmentGroup(java.lang.String)
	 */
	public AttachmentGroup findAttachmentGroup(String groupId) {
		return attachmentGroupDao.selectByPrimaryKey(groupId);
	}

	/**
	 * 是否公开是公开的附件类型
	 * 
	 * @param attachTypeId
	 * @return
	 */
	public boolean isPublicAttachmentType(String attachTypeId) {
		List<String> publicTypeList = AttachmentConfig.getInstance()
				.listPublicAttachTypes();
		if (publicTypeList != null) {
			for (String typeId : publicTypeList) {
				if (typeId != null && typeId.equals(attachTypeId)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 清除过期的临时附件.
	 * 
	 * @param beforeDays
	 *            过期天数
	 */
	@Transactional(rollbackFor = Exception.class)
	public void clearTemp(int beforeDays) {
		// 先删除附件表中状态为临时的附件组
		List<AttachmentFile> tempFiles = attachmentFileDao
				.listTempFile(beforeDays);
		if (tempFiles != null) {
			// 删除所有状态为暂存且时间直过N天的文件和表记录
			for (Iterator<AttachmentFile> it = tempFiles.iterator(); it
					.hasNext();) {
				AttachmentFile attachmentFile = (AttachmentFile) it.next();
				deleteFile(attachmentFile.getFileSaveName());
				attachmentFileDao.deleteByPrimaryKey(attachmentFile
						.getAttachFileId());
			}
		}

		// 再删除附件组表中状态为临时的过期的附件组
		attachmentGroupDao.clearTempGroup(beforeDays);

		// 最后再删除本地临时目录过期的文件
		String localTempPath = AttachmentConfig.getInstance()
				.getLocalAbsoluteTempPath();
		File localPath = new File(localTempPath);
		if (localPath.exists()) {
			File[] localFiles = localPath.listFiles();
			if (localFiles != null) {
				Calendar nowTime = Calendar.getInstance();
				nowTime.add(Calendar.DATE, -beforeDays);
				Date nowDate = nowTime.getTime();
				for (int i = 0; i < localFiles.length; i++) {
					File localFile = localFiles[i];
					Long lastModified = localFile.lastModified();
					Date fileDate = new Date(lastModified);
					// 文件时间和系统时间比较，如果是N天前的文件，删除
					if (fileDate.before(nowDate)) {
						localFile.delete();
					}
				}
			}
		}
	}

	public List<AttachmentFile> listAttachmentFilesByIds(
			List<String> attachmentFileIds) {
		List<AttachmentFile> attachmentFiles = new LinkedList<AttachmentFile>();
		for (String attachmentFileId : attachmentFileIds) {
			attachmentFiles.add(findAttachmentFile(attachmentFileId));
		}
		return attachmentFiles;
	}

	/**
	 * 批量添加附件（暂存）,批量添加附件（暂存），适用于后台需要批量上传附件的场景.
	 * 
	 * @param files
	 *            是一个map list对象，每个map中存放一个上传文件的信息，Map中需要填充的值有： 1、attachGroupId
	 *            附件组ID（可为空，如果为空，生成新的附件组） 2、fileName 原始文件名 3、contentType
	 *            内容类型（如text/plain，application/pdf等...） 4、attachFile File对象
	 *            5、attachTypeId 附件类型（可为空，商业文件、资质证明等）
	 * @return 附件DO
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<AttachmentFile> batchAddAttachmentFiles(List<Map> fileInfos)
			throws AttachmentException {
		long start = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
			logger.info("进入批量暂存附件方法，参数fileInfos=" + fileInfos);
		}
		if (fileInfos == null || fileInfos.size() == 0) {
			logger.error("fileInfos 为空或大小为0");
			throw new IllegalArgumentException("fileInfos 为空或大小为0");
		}

		// 读取配置文件存储根目录和临时目录
		String uploadPath = AttachmentConfig.getInstance().getUploadPath();

		// 如果没有取到uploadPath，说明解析配置文件有问题
		if (uploadPath == null || uploadPath.trim().length() == 0) {
			throw new IllegalArgumentException(
					"解析配置文件错误，无法从配置文件中取到uploadPath值。");
		}

		List<AttachmentFile> attachmentFiles = new ArrayList<AttachmentFile>();
		List<AttachmentFile> noGroupIdAttachFiles = new ArrayList<AttachmentFile>();
		Date today = new Date();
		String datePath = new SimpleDateFormat("yyMMdd").format(today);
		for (Map fileInfo : fileInfos) {
			String fileName = (String) fileInfo.get("fileName");
			String contentType = (String) fileInfo.get("contentType");
			File file = (File) fileInfo.get("attachFile");
			String attachGroupId = (String) fileInfo.get("attachGroupId");
			String attachTypeId = (String) fileInfo.get("attachTypeId");
			// 参数检查
			if (StringUtils.isEmpty(fileName)
					|| StringUtils.isEmpty(contentType) || null == file) {
				logger.error("暂存附件时，传入的参数为空");
				throw new IllegalArgumentException("暂存附件时，传入的参数为空");
			}

			if (file.length() == 0) {
				logger.error("不允许暂存0字节的文件！");
				throw new AttachmentException("不允许暂存0字节的文件！");
			}

			if (!file.exists()) {
				logger.error("暂存附件时，暂存的文件不存在");
				throw new IllegalArgumentException("暂存的文件不存在");
			}

			// 往附件文件表中插入记录
			AttachmentFile attachmentFile = new AttachmentFile();
			attachmentFile.setFileName(fileName);
			attachmentFile.setFileSize(BigDecimal.valueOf(file.length()));
			attachmentFile.setFileType(contentType);
			if (StringUtils.isEmpty(attachGroupId)) {
				attachmentFile.addOtherField("attachTypeId", attachTypeId);
			} else {
				attachmentFile.setAttachGroupId(attachGroupId);
			}
			attachmentFile
					.setAttachFileStatus(AttachmentConstants.ATTACH_STATUS_TEMP);
			attachmentFile.setFileSaveName(datePath
					+ attachmentFile.getAttachFileId()
					+ fileName.substring(fileName.lastIndexOf("."),
							fileName.length()));
			attachmentFile.setFile(file);
			attachmentFiles.add(attachmentFile);
			if (StringUtils.isEmpty(attachGroupId)) {
				noGroupIdAttachFiles.add(attachmentFile);
			}
		}

		// 批量生成附件组ID
		/*
		 * List<String> attacheGroupIds = new ArrayList<String>();
		 * if(noGroupIdAttachFiles != null && noGroupIdAttachFiles.size() > 0){
		 * SequenceGenerator sequenceGenerator =
		 * (SequenceGenerator)BeanLocator.getBeanInstance("sequenceGenerator");
		 * List<String> tmpAttacheGroupIds =
		 * sequenceGenerator.generate(noGroupIdAttachFiles.size(),
		 * "SEQ_ATTACH_GROUP_ID"); //8位，不够左补零 and prefix for(String
		 * attacheGroupId : tmpAttacheGroupIds){ attacheGroupId =
		 * String.format("%8s", attacheGroupId); attacheGroupId =
		 * attacheGroupId.replaceAll("\\s", "0");
		 * attacheGroupIds.add(AttachmentConfig
		 * .getInstance().getSequencePrefix() + attacheGroupId); } }
		 */

		// 为没有附件组ID的附件设置附件组ID并返回要插入的附件组列表
		List<AttachmentGroup> attachmentGroups = new ArrayList<AttachmentGroup>();
		for (int i = 0; i < noGroupIdAttachFiles.size(); i++) {
			AttachmentFile attachmentFile = noGroupIdAttachFiles.get(i);
			AttachmentGroup attachmentGroup = new AttachmentGroup();
			attachmentGroup
					.setAttachGroupStatus(AttachmentConstants.ATTACH_STATUS_TEMP);
			String attachTypeId = (String) attachmentFile
					.getOtherField("attachTypeId");
			if (null != attachTypeId) {
				attachmentGroup.setAttachTypeId(attachTypeId);
			}

			// attachmentGroup.setAttachGroupId(attacheGroupIds.get(i));
			// attachmentGroups.add(attachmentGroup);
			attachmentGroupDao.insert(attachmentGroup);
			String attachGroupId = attachmentGroup.getAttachGroupId();
			attachmentFile.setAttachGroupId(attachGroupId);
			attachmentFileDao.insert(attachmentFile);
			String attachFileId = attachmentFile.getAttachFileId();
			attachmentFile.setFileSaveName(datePath
					+ attachmentFile.getAttachFileId()
					+ attachmentFile.getFileName().substring(
							attachmentFile.getFileName().lastIndexOf("."),
							attachmentFile.getFileName().length()));
			attachmentFileDao.updateByPrimaryKeySelective(attachmentFile);
		}
		// 批量插入附件组
		// attachmentGroupDao.batchInsertAttachmentGroups(attachmentGroups);

		long end1 = System.currentTimeMillis();

		// 批量插入文件表
		// attachmentFileDao.batchInsertAttachmentFiles(attachmentFiles, true);
		long end2 = System.currentTimeMillis();
		for (AttachmentFile attachmentFile : attachmentFiles) {
			// 保存文件到指定位置（配置文件指定的文件服务器目录）
			String fileUri = uploadPath + attachmentFile.getFileSaveName();

			FileObject vsfFileObject = null;
			FileContent fileContent = null;
			FileInputStream fileInputStream = null;
			OutputStream outputStream = null;
			try {
				FileSystemManager vfsManager = VFSManagerContainer.getManager();
				vsfFileObject = vfsManager.resolveFile(fileUri.substring(0,
						fileUri.lastIndexOf("/")));
				// 判断文件目录是否存在
				if (!vsfFileObject.exists()) {
					vsfFileObject.createFolder();
				}
				vsfFileObject = vfsManager.resolveFile(fileUri);
				fileContent = vsfFileObject.getContent();
				fileInputStream = new FileInputStream(attachmentFile.getFile());
				synchronized (this) {
					outputStream = fileContent.getOutputStream();
					IOUtils.copy(fileInputStream, outputStream);
					outputStream.flush();
				}
			} catch (Exception e) {
				logger.error("保存附件时失败", e);
				throw new AttachmentException("保存附件时失败：", e);
			} finally {
				if (fileContent != null) {
					fileContent = null;
				}

				if (vsfFileObject != null) {
					vsfFileObject = null;
				}

				if (fileInputStream != null) {
					try {
						fileInputStream.close();
					} catch (IOException e) {
						logger.error("关闭文件流失败", e);
					}
				}
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException e) {
						logger.error("关闭文件流失败", e);
					}
				}

			}

		}
		if (logger.isInfoEnabled()) {
			logger.debug("离开批量暂存附件方法，返回值=" + attachmentFiles);
		}
		long end = System.currentTimeMillis();
		System.out.println("文件数量:" + fileInfos.size() + ",总执行时间:"
				+ (end - start) + ".参数检查：" + (end1 - start) + ",文件记录批量插入："
				+ (end2 - end1) + ",文件上传:" + (end - end2));
		return attachmentFiles;
	}

	/**
	 * 批量附件生效接口,attachGroupIds不能超过1000个 当使用批量添加附件接口上传附件后必须调用此接口使附件生效，否则附件只处于暂存状态
	 * 
	 * @param attachGroupId
	 *            附件组ID
	 */
	@Transactional(rollbackFor = Exception.class)
	public void batchFormalAttachmentGroups(List<String> attachGroupIds)
			throws AttachmentException {
		long start = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
			logger.info("进入批量生效附件组方法，参数attachGroupIds=" + attachGroupIds);
		}
		if (null == attachGroupIds || attachGroupIds.size() == 0) {
			logger.error("批量生效附件组时，传入的参数附件组id为空");
			throw new AttachmentException("批量生效附件组时，传入的参数附件组id为空");
		}
		if (attachGroupIds.size() > 1000) {
			logger.error("每次生效的附件组ID不能超过1000个");
			throw new AttachmentException("每次生效的附件组ID不能超过1000个");
		}
		String[] groupIdArray = attachGroupIds
				.toArray(new String[attachGroupIds.size()]);

		// 查找要生效附件组记录
		AttachmentGroup attachmentGroup = new AttachmentGroup();
		attachmentGroup.addOtherField("groupIdArray", groupIdArray);
		List<AttachmentGroup> attachGroups = attachmentGroupDao
				.list(attachmentGroup);
		if (attachGroups == null || attachGroups.size() == 0) {
			logger.error("生效的附件组不存在");
			throw new AttachmentException("生效的附件组不存在");
		}
		// 放入map中便于查找，key为groupId
		Map<String, AttachmentGroup> attachmentGroupMap = new HashMap<String, AttachmentGroup>();
		for (AttachmentGroup group : attachGroups) {
			attachmentGroupMap.put(group.getAttachGroupId(), group);
		}

		// 查找要生效的附件文件记录
		AttachmentFile attachmentFile = new AttachmentFile();
		attachmentFile.addOtherField("groupIdArray", groupIdArray);
		List<AttachmentFile> attachmentFiles = attachmentFileDao
				.list(attachmentFile);
		if (attachmentFiles == null || attachmentFiles.size() == 0) {
			logger.error("生效的附件文件不存在");
			throw new AttachmentException("生效的附件文件不存在");
		}

		// 列出所有的附件类型，用来判断是否需要进行附件同步
		List<AttachmentType> attachmentTypes = attachmentTypeDao
				.list(new AttachmentType());
		// 放入map便于查找,key为附件类型Id
		Map<String, AttachmentType> attachmentTypeMap = new HashMap<String, AttachmentType>();
		if (attachmentTypes != null && attachmentTypes.size() > 0) {
			for (AttachmentType attachmentType : attachmentTypes)
				attachmentTypeMap.put(attachmentType.getAttachTypeId(),
						attachmentType);
		}

		// 过滤出需要生效的附件组列表
		List<AttachmentGroup> formalingAttachmentGroups = new ArrayList<AttachmentGroup>();
		for (AttachmentGroup group : attachGroups) {
			if (AttachmentConstants.ATTACH_STATUS_TEMP.equals(group
					.getAttachGroupStatus())) {
				formalingAttachmentGroups.add(group);
			}
		}

		// 过滤出需要生效的附件列表及需要同步的附件列表
		List<AttachmentFile> formalingAttachmentFiles = new ArrayList<AttachmentFile>();
		List<AttachmentFile> syningAttachmentFiles = new ArrayList<AttachmentFile>();
		for (AttachmentFile attachFile : attachmentFiles) {
			if (AttachmentConstants.ATTACH_STATUS_TEMP.equals(attachFile
					.getAttachFileStatus())) {
				formalingAttachmentFiles.add(attachFile);
			}
			AttachmentGroup attachGroup = attachmentGroupMap.get(attachFile
					.getAttachGroupId());
			if (!StringUtils.isEmpty(attachGroup.getAttachTypeId())) {
				AttachmentType attachmentType = attachmentTypeMap
						.get(attachGroup.getAttachTypeId());
				if (attachmentType != null && attachmentType.isSync()) {
					syningAttachmentFiles.add(attachFile);
				}
			}
		}

		long end1 = System.currentTimeMillis();

		// 把需要同步文件放到同步目录
		for (AttachmentFile synAttach : syningAttachmentFiles) {
			copyToSynDir(synAttach);
		}
		long end2 = System.currentTimeMillis();

		// 更新附件组状态为生效
		for (AttachmentGroup formalAc : formalingAttachmentGroups) {
			formalAc.setAttachGroupStatus(AttachmentConstants.ATTACH_STATUS_FORMAL);
			attachmentGroupDao.updateByPrimaryKeySelective(formalAc);
		}
		// attachmentGroupDao.batchUpdateAttachmentGroups(formalingAttachmentGroups);
		long end3 = System.currentTimeMillis();

		// 更新附件文件状态为生效
		for (AttachmentFile formalAf : formalingAttachmentFiles) {
			formalAf.setAttachFileStatus(AttachmentConstants.ATTACH_STATUS_FORMAL);
			attachmentFileDao.updateByPrimaryKeySelective(formalAf);
		}
		// attachmentFileDao.batchUpdateAttachmentFiles(formalingAttachmentFiles);
		long end = System.currentTimeMillis();

		System.out.println("组装数据时间:" + (end1 - start) + ",同步文件时间："
				+ (end2 - end1) + ",更改附件组状态时间：" + (end3 - end2) + ",更改附件状态时间："
				+ (end - end3) + ",总执行时间【数据量=" + attachGroupIds.size() + "】:"
				+ (end - start));
		if (logger.isInfoEnabled()) {
			logger.info("离开批量生效附件组方法");
		}
	}

	private void copyToSynDir(AttachmentFile attachmentFile) {
		// 得到附件保存路径和同步路径
		String uploadPath = AttachmentConfig.getInstance().getUploadPath();
		String syncPath = AttachmentConfig.getInstance().getSyncPath();

		// 将附件从暂存目录移到正式目录
		String fileSaveName = attachmentFile.getFileSaveName();

		// 文件的上传地址和同步地址
		String uploadFileUri = uploadPath + fileSaveName;
		String syncFileUri = syncPath + fileSaveName;
		FileObject uploadFileObject = null;
		FileContent uploadFileContent = null;
		FileObject syncFileObject = null;
		FileContent syncFileContent = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			FileSystemManager vfsManager = VFSManagerContainer.getManager();
			uploadFileObject = vfsManager.resolveFile(uploadFileUri);
			uploadFileContent = uploadFileObject.getContent();
			syncFileObject = vfsManager.resolveFile(syncFileUri.substring(0,
					syncFileUri.lastIndexOf("/")));
			// 判断文件目录是否存在
			if (!syncFileObject.exists()) {
				syncFileObject.createFolder();
			}
			syncFileObject = vfsManager.resolveFile(syncFileUri);
			syncFileContent = syncFileObject.getContent();

			synchronized (this) {
				inputStream = uploadFileContent.getInputStream();
				outputStream = syncFileContent.getOutputStream();
				IOUtils.copy(inputStream, outputStream);
				outputStream.flush();
			}
		} catch (Exception e) {
			logger.error("拷贝文件到同步目录时失败", e);
			throw new AttachmentException("拷贝同步文件时失败：", e);
		} finally {
			uploadFileContent = null;
			uploadFileObject = null;
			syncFileContent = null;
			syncFileObject = null;

			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("关闭文件流失败", e);
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error("关闭文件流失败", e);
				}
			}
		}
	}

}

/**
 * 附件组件 个性化的文件系统管理者，继承于 StandardFileSystemManager，考虑到性能，缓存策略设置为手动
 * 
 * @author haomingli
 * 
 */
class VFSManagerContainer {

	private static final Logger logger = LoggerFactory
			.getLogger(VFSManagerContainer.class);

	private static StandardFileSystemManager fileSystemManager;

	public static FileSystemManager getManager() {
		if (fileSystemManager == null) {
			fileSystemManager = new StandardFileSystemManager();
			try {
				fileSystemManager.setCacheStrategy(CacheStrategy.MANUAL);
				fileSystemManager.init();
			} catch (FileSystemException e) {
				logger.error("init StandardFileSystemManager failed ", e);
			}
		}
		return fileSystemManager;
	}
}
