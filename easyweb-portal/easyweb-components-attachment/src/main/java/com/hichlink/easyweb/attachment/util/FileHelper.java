package com.hichlink.easyweb.attachment.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
/**
 * 
 *<pre>
 * <b>Title：</b>FileHelper.java<br/>
 * <b>Description：</b><br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年8月1日 下午5:24:15<br/>  
 * <b>Copyright (c) 2014 ASPire Tech.</b>   
 * </pre>
 */
public class FileHelper {
	private static Log log = LogFactory.getLog(FileHelper.class);

	static private final int BUF_SIZE = 1024 * 10;

	// 保存的文件名，当上传文件为分块多次请求时，需要确保文件名唯一,
	private static Map<String, String> savedFileNameCache = new HashMap<String, String>();

	/**
	 * 上传文件
	 * 
	 * @param is
	 *            文件输入流
	 * @param fullName
	 *            保存文件绝对路径
	 * @param isCopy
	 *            如果出现同名文件，是否追加
	 * @return
	 */
	public static boolean uploadFile(InputStream is, String fullName,
			boolean isAppend) {
		int chunkSize = 50 * 1024 * 1024;
		OutputStream bos = null;
		try {
			bos = new FileOutputStream(fullName, isAppend);
			int bytesRead = 0;
			byte[] buffer = new byte[chunkSize];
			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.close();
			is.close();
			return true;
		} catch (Exception e) {
			log.error("上传文件时发生错误：", e);
			return false;
		}finally{
			if (null != bos){
				try {
					bos.close();
				} catch (IOException e) {
					log.error("关闭流出错：", e);
					return false;
				}
			}
			if (null != is){
				try {
					is.close();
				} catch (IOException e) {
					log.error("关闭流出错：", e);
					return false;
				}
			}
			
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param is
	 *            文件输入流
	 * @param fullName
	 *            保存文件绝对路径
	 * @return
	 */
	public static boolean uploadFile(InputStream is, String fullName) {
		OutputStream bos = null;
		try {
			bos = new FileOutputStream(fullName);
			int bytesRead = 0;
			byte[] buffer = new byte[BUF_SIZE];
			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			
			return true;
		} catch (Exception e) {
			log.error("上传文件时发生错误：", e);
			return false;
		}finally{
			if (null != bos){
				try {
					bos.close();
				} catch (IOException e) {
					log.error("关闭流出错：", e);
					return false;
				}
			}
			if (null != is){
				try {
					is.close();
				} catch (IOException e) {
					log.error("关闭流出错：", e);
					return false;
				}
			}
			
		}
	}

	/**
	 * 获取文件的后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getSuffiex(String fileName) {
		int index = fileName.lastIndexOf(".");
		String suffiex = "";
		if (index >= 0)
			suffiex = fileName.substring(index);
		return suffiex;
	}

	/**
	 * 获取文件除去后缀后的名字
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileDisplayName(String fileName) {
		int index = fileName.lastIndexOf(".");
		if (index == -1)
			return fileName;
		String displayName = fileName.substring(0, index);
		return displayName;
	}

	/**
	 * <p>
	 * 上传文件
	 * <p>
	 * 
	 * @param request
	 * @param response
	 * @param dirPath
	 *            保存文件目录的绝对路径
	 * @return 保存在文件系统的文件名，参照：{@code 文件名+<_yyyyMMddHHmmss>.<后缀>}
	 * @throws IOException
	 * @throws IllegalStateException
	 * @see <p>
	 *      仅适用于plupload或者基于其实现的上传操作客户端
	 *      </p>
	 */
	public static File uploadFile(HttpServletRequest request,
			HttpServletResponse response, String dirPath,
			StringBuilder contentType, StringBuilder originalFilename)
			throws IllegalStateException, IOException {
		String filename = null;
		String savedFileName = null;
	
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// 保存文件目录绝对路径
		File dir = new File(dirPath);
		if (!dir.isDirectory() || !dir.exists()) {
			dir.mkdirs();
		}
		// 判断当前表单是否为"multipart/form-data"
		if (isMultipart && null != request.getParameter("chunks")) {
			// 当前正在处理的文件分块序号
			int chunk = Integer.valueOf(request.getParameter("chunk"));
			// 分块上传总数
			int chunks = Integer.valueOf(request.getParameter("chunks"));
			filename = request.getParameter("name");
			originalFilename.append(filename);
			MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) request;
			try {
				Iterator<String> i = multiReq.getFileNames();
				while (i.hasNext()) {
					MultipartFile f = multiReq.getFile((String) i.next());
					InputStream input = f.getInputStream();
					// 文件名
					if (null != contentType
							&& "".equals(contentType.toString())) {
						contentType.append(f.getContentType());
					}
					if (!savedFileNameCache.containsKey(filename)) {
						savedFileNameCache.put(filename,
								getSavedFileName(filename));
					}

					// 保存文件绝对路径
					String fullPath = dirPath + "/"
							+ savedFileNameCache.get(filename);
					if (chunk == 0) {
						File file = new File(fullPath);
						if (file.exists()) {
							file.delete();
						}
						// 上传文件
						FileHelper.uploadFile(input, fullPath);
					}
					if (chunk > 0) {
						// 追加文件
						FileHelper.uploadFile(input, fullPath, true);
					}
					if (chunk + 1 == chunks || chunks == 0) {
						savedFileName = savedFileNameCache.get(filename);
						savedFileNameCache.remove(filename);
						break;
					}
					chunk++;
				}
			} catch (Exception e) {
				log.error("文件分块上传出错", e);
			}
			
			return null  == savedFileName ? null : new File(dirPath + "/"  + savedFileName);
		} else {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile attachment = multipartRequest.getFile("attachment");
			if (null != attachment) {
				originalFilename.append(attachment.getOriginalFilename());
				contentType.append(attachment.getContentType());
				String fullPath = dirPath + "/"
						+ getSavedFileName(attachment.getOriginalFilename());
				File file = new File(fullPath);
				attachment.transferTo(file);
				return file;
			}
		}
		return null;
	}

	/**
	 * <p>
	 * 获取文件系统保存的文件名
	 * </p>
	 * 
	 * @param filename
	 * @return
	 */
	private static String getSavedFileName(String filename) {
		StringBuffer sb = new StringBuffer();
		String suffic = filename.substring(filename.lastIndexOf("."));
		//String file = filename.substring(0, filename.lastIndexOf("."));
		sb.append(System.currentTimeMillis() + (new Random()).nextInt(1000))
				.append(suffic);
		return sb.toString();
	}

}