package com.hichlink.easyweb.attachment;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.hichlink.easyweb.attachment.entity.AttachmentFile;

public interface ConcurrentAttachment {
	public List<AttachmentFile> copy(String srcDirPath, String destDirPath)
			throws InterruptedException, ExecutionException;
	public List<AttachmentFile> copy(File[] srcFiles, String attachGroupId,
			String contentType, String attachTypeId)
	throws InterruptedException, ExecutionException;
}
