package com.hichlink.easyweb.attachment.service;

import java.io.File;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hichlink.easyweb.attachment.AttachmentInterface;
import com.hichlink.easyweb.attachment.entity.AttachmentFile;
import com.hichlink.easyweb.core.util.SpringContextHolder;

public class AttachmentCallable implements Callable<AttachmentFile> {
	private static final Logger logger = LoggerFactory
			.getLogger(AttachmentCallable.class);
	private AttachmentInterface attachmentInterface;
	private String srcFilePath;
	private String destFilePath;
	private String attachGroupId;
	private String fileName;
	private String contentType;
	private File file;
	private String attachTypeId;

	public AttachmentCallable(String srcFilePath, String destFilePath) {
		this.srcFilePath = srcFilePath;
		this.destFilePath = destFilePath;
	}

	public AttachmentCallable(String attachGroupId, String fileName,
			String contentType, File file, String attachTypeId) {
		this.attachGroupId = attachGroupId;
		this.fileName = fileName;
		this.contentType = contentType;
		this.file = file;
		this.attachTypeId = attachTypeId;
		this.attachmentInterface = SpringContextHolder
				.getBean(AttachmentImpl.class);
	}

	public AttachmentFile call() throws Exception {
		AttachmentFile attachmentFile = attachmentInterface.addFile(
				this.attachGroupId, this.fileName, this.contentType, this.file,
				this.attachTypeId);
		return attachmentFile;
	}
}
