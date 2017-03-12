package com.hichlink.easyweb.attachment.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hichlink.easyweb.attachment.AttachmentInterface;
import com.hichlink.easyweb.attachment.config.AttachmentConfig;
import com.hichlink.easyweb.attachment.constant.AttachmentConstants;
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
import com.hichlink.easyweb.attachment.util.FileHelper;
import com.hichlink.easyweb.attachment.util.ZipUtil;
import com.hichlink.easyweb.core.web.BaseController;
/**
 * 
 *<pre>
 * <b>Title：</b>AttachmentController.java<br/>
 * <b>Description：</b><br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年7月31日 上午10:23:48<br/>  
 * <b>Copyright (c) 2014 ASPire Tech.</b>   
 * </pre>
 */
@Controller
@RequestMapping("/attachment")
public class AttachmentController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(AttachmentController.class);


	private static final String SYSTEM_ERROR = "系统错误";

	@Autowired
	@Qualifier("attachmentInterface")
	private AttachmentInterface attachInterface;

	@RequestMapping(value = "/add.ajax")
	public void doAdd(HttpServletRequest request,HttpServletResponse response, String attachGroupId,
			String attachTypeId) throws IOException {
		
		/*MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile attachment = multipartRequest.getFile("attachment");*/
		AttachmentFile attachFile = null;
		StringBuilder fileName = new StringBuilder("");
		StringBuilder attachmentContentType =  new StringBuilder("");
		File  attachment = FileHelper.uploadFile(request, response, AttachmentConfig.getInstance().getLocalAbsoluteTempPath(), attachmentContentType, fileName);
		if (null  == attachment)return;
		if (logger.isDebugEnabled()) {
			logger.debug("进入暂存附件Action，参数attachGroupId=" + attachGroupId
					+ ",attachmentFileName=" + fileName
					+ ",attachmentContentType=" + attachmentContentType
					+ ",attachTypeId=" + attachTypeId + ",attachGroupId="
					+ attachGroupId);
		}
		try {
			// 校验附件
			attachInterface.validateAttachmentFile(attachGroupId,
					fileName.toString(), attachment, attachTypeId, 0, 0, 0, 0);
			// 增加附件
			attachFile = attachInterface.addAttachmentFile(attachGroupId,
					fileName.toString(), attachmentContentType.toString(), attachment,
					attachTypeId);

		} catch (AttachNumberOverLimitException e) {
			logger.error("附件组总数超过了上限", e);
			fail(response, "附件组总数超过了上限");
		} catch (AttachSizeOverLimitException e) {
			logger.error("附件的大小超过了上限", e);
			fail(response, e.getMessage());
		} catch (AttachTypeNotExistException e) {
			logger.error("附件的类型不在限制类型范围内", e);
			fail(response, "附件的类型不在限制类型范围内");
		} catch (AttachTotalSizeOverLimitException e) {
			logger.error("附件组总的大小超过了上限", e);
			fail(response, e.getMessage());

		} catch (AttachSizeIsZeroException e) {
			logger.error("不允许上传零字节的文件", e);
			fail(response, "不允许上传零字节的文件");

		} catch (AttachNameLenghOverLimitException e) {
			logger.error("附件文件名长度超过了上限", e);
			fail(response, "附件文件名长度超过了上限");
		} catch (AttachmentException e) {
			logger.error(SYSTEM_ERROR, e);
			fail(response, e.getMessage());
		} catch (Exception e) {
			logger.error("上载附件失败", e);
			fail(response, "上载附件失败");
		}finally{
			if (null != attachment && attachment.isFile()){
				attachment.deleteOnExit();
			}
		}
		logger.debug("离开暂存附件Action");
		success(response, attachFile);
	}

	/**
	 * 为多附件上传的专门处理接口
	 * 每次上传文件的时候，都把已经上传了的文件attachTypeId带上来，格式为：attachTypeId1,attachTypeId2
	 * ,attachTypeId3 每次上传都统计一下文件个数和文件大小总和，避免出现超过配置的文件个数和大小总量
	 * 
	 * @return
	 * @throws IOException
	 */
	/*@RequestMapping(value = "/addForMulti.ajax")
	public void doAddForMulti(HttpServletRequest request,HttpServletResponse response,
			String attachGroupId, String attachFileIds,
			String attachTypeId) throws IOException {
		String attachmentFileName = "", attachmentContentType = "";
		AttachmentFile attachFile = null;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile attachment = multipartRequest.getFile("attachment");
		if (null != attachment){
			attachmentFileName = attachment.getOriginalFilename();
			attachmentContentType = attachment.getContentType();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("进入暂存附件Action，参数attachGroupId=" + attachGroupId
					+ ",attachmentFileName=" + attachmentFileName
					+ ",attachmentContentType=" + attachmentContentType
					+ ",attachTypeId=" + attachTypeId + ",attachGroupId="
					+ attachGroupId);
		}

		
		// 得到待删除的附件数量（解决修改附件时，一个附件组的总数量的限制问题）
		long willAddNum = 0;
		long willAddSize = 0;
		long willDeleteNum = 0;
		long willDeleteSize = 0;

		try {

			// 第一次上传的时候attachFileIds为空，
			// 第二次上传的时候才能统一已经上传了的文件
			if (attachFileIds != null && attachFileIds.trim().length() > 0) {
				logger.debug("上次已上传的文件[" + attachFileIds + "]");
				for (String fileId : attachFileIds.split(",")) {
					// 统计已经上传了的文件个数
					willAddNum++;

					AttachmentFile attachFileTmp = attachInterface
							.findAttachmentFile(fileId);
					if (attachFileTmp != null) {
						// 统计已经上传了的文件大小
						willAddSize += attachFileTmp.getFileSize().intValue();
					}
				}
			}

			logger.debug("附件组[groupId=" + attachGroupId + "]已经上传了个"
					+ willAddNum + "文件,总大小为" + willAddSize);

			// 校验附件
			attachInterface.validateAttachmentFile(attachGroupId,
					attachmentFileName, attachment, attachTypeId,
					(int) willAddNum, willAddSize, (int) willDeleteNum,
					willDeleteSize);

			// 增加附件
			attachFile = attachInterface.addAttachmentFile(attachGroupId,
					attachmentFileName, attachmentContentType, attachment,
					attachTypeId);

		} catch (AttachNumberOverLimitException e) {
			logger.error("附件组总数超过了上限", e);
			fail(response, "附件组总数超过了上限");
		} catch (AttachSizeOverLimitException e) {
			logger.error("附件的大小超过了上限", e);
			fail(response, e.getMessage());
		} catch (AttachTypeNotExistException e) {
			logger.error("附件的类型不在限制类型范围内", e);
			fail(response, "附件的类型不在限制类型范围内");
		} catch (AttachTotalSizeOverLimitException e) {
			logger.error("附件组总的大小超过了上限", e);
			fail(response, e.getMessage());
		} catch (AttachSizeIsZeroException e) {
			logger.error("不允许上传零字节的文件", e);
			fail(response, "不允许上传零字节的文件");
		} catch (AttachNameLenghOverLimitException e) {
			logger.error("附件文件名长度超过了上限", e);
			fail(response, "附件文件名长度超过了上限");
		} catch (AttachmentException e) {
			logger.error(SYSTEM_ERROR, e);
			fail(response, SYSTEM_ERROR);
		} catch (Exception e) {
			logger.error("上载附件失败", e);
			fail(response, "上载附件失败");
		}

		logger.debug("离开暂存附件Action");
		success(response, attachFile);
	}*/

	/**
	 * 获取附件的文件列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list.ajax")
	@ResponseBody
	public Map<String, ? extends Object> doList(String attachGroupId) {

		List<AttachmentFile> list = null;

		try {
			list = attachInterface.listFormalAttachmentFile(attachGroupId);

			logger.debug("attachGroupId=" + attachGroupId + " 获取文件数："
					+ list.size());
		} catch (Exception e) {
			logger.error("无法获取groupId=" + attachGroupId + "的附件", e);
			return fail("无法获取groupId=" + attachGroupId + "的附件");
		}

		return success(list);
	}

	@RequestMapping(value = "/withdraw.ajax")
	@ResponseBody
	public Map<String, ? extends Object> doWithdraw(String attachGroupId,
			String attachFileId) {
		if (logger.isDebugEnabled()) {
			logger.debug("进入取消暂存附件Action，参数attachGroupId=" + attachGroupId
					+ "参数attachFileId=" + attachFileId);
		}

		// 参数检查.
		if (isEmpty(attachFileId)) {
			logger.error("取消暂存附件组时参数为空(attachFileId)");
			return fail("取消暂存附件组时参数为空(attachFileId)");
		}

		try {
			AttachmentFile attachFile = attachInterface
					.findSimpleAttachmentFile(attachFileId);
			if (attachFile == null) {
				return fail("取消暂存附件组时附件不存在");
			} else {
				// 在SESSION中保存当前删除的文件大小和数量
				// addAttributeValue(attachFile.getGroupId(), 1,
				// ATTACHMENT_WILL_DELETE_NUM);
				// addAttributeValue(attachFile.getGroupId(),
				// attachFile.getFileSize(), ATTACHMENT_WILL_DELETE_SIZE);
			}

			// 如果是暂存状态附件，直接删除
			if (AttachmentConstants.ATTACH_STATUS_TEMP
					.equalsIgnoreCase(attachFile.getAttachFileStatus())) {
				attachInterface.withdrawAttachmentFile(attachFileId);
			}
		} catch (AttachIsFormalException e) {
			// 如果附件是正式状态,不能直接删除,先记录待删除附件数量和大小
			if (logger.isDebugEnabled()) {
				logger.debug("附件是正式状态，不能删除");
			}
			return fail("附件是正式状态，不能删除");
		} catch (Exception e) {
			logger.error("取消暂存附件失败", e);
			return fail("取消暂存附件失败");
		}
		logger.debug("离开取消暂存附件Action");
		return success("删除成功");
	}

	@RequestMapping(value = "/download.ajax")
	public void doDownload(HttpServletResponse response, String attachFileId)
			throws IOException {
		logger.debug("进入下载附件Action，参数attachFileId=" + attachFileId);
		// 得到response
		FileInputStream fis = null;
		try {
			AttachmentFile attachFile = attachInterface
					.findAttachmentFile(attachFileId);
			if (null == attachFile) {
				logger.error("下载附件失败");
				outputString(response, "文件不存在");
			}
			AttachmentGroup group = attachInterface
					.findAttachmentGroup(attachFile.getAttachGroupId());
			/*
			 * if(!attachInterface.isPublicAttachmentType(group.getAttachTypeId()
			 * )){ if(!this.isUserLoggedIn()){ logger.error("下载附件失败-未登录");
			 * outputString(response,"文件不存在"); } }
			 */

			// 解决中文文件名的显示问题
			String encodeFileName = new String(attachFile.getFileName()
					.getBytes("GBK"), "ISO-8859-1");

			// 将附件以文件流方式写到response输出流中
			response.reset();
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ encodeFileName + "\"");

			fis = new FileInputStream(attachFile.getFile());
			IOUtils.copy(fis, response.getOutputStream());
			response.getOutputStream().flush();
		} catch (Exception e) {
			logger.error(SYSTEM_ERROR, e);
			outputString(response, "文件不存在");
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("关闭文件流出错", e);
					outputString(response, SYSTEM_ERROR);
				}
			}
		}

		logger.debug("离开下载附件Action");
	}

	@RequestMapping(value = "/downloadMulti.ajax")
	public void doDownloadMulti(HttpServletResponse response,
			String attachFileIds) throws IOException {
		logger.debug("进入下载附件Action，参数attachFileIds=" + attachFileIds);
		// 得到response
		try {
			List<AttachmentFile> attachFileList = attachInterface
					.listAttachmentFilesByIds(Arrays.asList(attachFileIds
							.split(",")));
			if (null == attachFileList || attachFileList.size() == 0) {
				logger.error("下载附件失败");
				outputString(response, "文件不存在");
			}
			AttachmentGroup group = attachInterface
					.findAttachmentGroup(attachFileList.get(0)
							.getAttachGroupId());
			/*
			 * if(!attachInterface.isPublicAttachmentType(group.getAttachTypeId()
			 * )){ if(!this.isUserLoggedIn()){ logger.error("下载附件失败-未登录");
			 * outputFileNotFound(); return null; } }
			 */
			String[] entryNames = new String[attachFileList.size()];
			File[] entryFiles = new File[attachFileList.size()];
			int i = 0;
			for (AttachmentFile attachmentFile : attachFileList) {
				entryNames[i] = attachmentFile.getFileName();
				entryFiles[i] = attachmentFile.getFile();
				i++;
			}

			// 将附件以文件流方式写到response输出流中
			response.reset();
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition",
					"attachment; filename=\"scripts.zip\"");

			ZipUtil.zipFiles(entryNames, entryFiles, response.getOutputStream());
			response.getOutputStream().flush();
		} catch (Exception e) {
			logger.error(SYSTEM_ERROR, e);
			outputString(response, "文件不存在");
		}
		logger.debug("离开下载附件Action");
	}

	@RequestMapping(value = "/viewType.ajax")
	@ResponseBody
	public Map<String, ? extends Object> doViewType(String attachTypeId) {
		logger.debug("进入展示附件类型Action，参数attachTypeId=" + attachTypeId);
		if (StringUtils.isEmpty(attachTypeId)) {
			logger.error("参数attachTypeId为空");
			return fail("参数attachTypeId为空");
		}
		AttachmentType attachmentType = attachInterface
				.findAttachmentType(attachTypeId);
		if (null == attachmentType) {
			logger.error("无此附件类型，参数attachFileId=" + attachTypeId);
			return fail("无此附件类型");
		}
		logger.debug("离开展示附件类型Action");
		return success(attachmentType);
	}

	@RequestMapping(value = "/view.ajax")
	public void doView(HttpServletResponse response, String attachFileId)
			throws IOException {
		logger.debug("进入展示图片附件Action，参数attachFileId=" + attachFileId);
		if (StringUtils.isEmpty(attachFileId)) {
			logger.error("参数attachFileId为空");
			fail(response, "参数为空");
		}
		AttachmentFile attachFile = attachInterface
				.findAttachmentFile(attachFileId);
		if (null == attachFile) {
			logger.error("无此图片，参数attachFileId=" + attachFileId);
			fail(response, "无此图片");
		}

		if (null == attachFile || attachFile.getFileType() == null) {
			logger.error("展示展示图片失败");
			fail(response, "展示图片失败");
		}

		// 如果不是图片，报错 flash
		// 上传拿到的内容类型都是application/octet-stream，无法判断是图片还是其他文件，故这里去掉这个判断。
		// if(!StringUtils.contains(attachFile.getFileType(), "image")){
		// logger.error("附件不是有效的图片类型，参数attachFileId=" + attachFileId);
		// setErrorReason("附件不是有效的图片类型");
		// return ERROR;
		// }

		// 得到response
		FileInputStream fis = null;
		try {
			// 将附件以文件流方式写到response输出流中(contentType=附件的fileType)
			response.reset();
			response.setContentType(attachFile.getFileType());
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			fis = new FileInputStream(attachFile.getFile());
			IOUtils.copy(fis, response.getOutputStream());
			response.getOutputStream().flush();
		} catch (Exception e) {
			logger.error(SYSTEM_ERROR, e);
			fail(response, SYSTEM_ERROR);
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("IOException", e);
				}
			}
		}
		logger.debug("离开下载附件Action");
	}
}
