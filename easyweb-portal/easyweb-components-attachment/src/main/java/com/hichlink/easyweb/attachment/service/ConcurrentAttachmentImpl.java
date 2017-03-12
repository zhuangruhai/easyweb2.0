package com.hichlink.easyweb.attachment.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.hichlink.easyweb.attachment.ConcurrentAttachment;
import com.hichlink.easyweb.attachment.entity.AttachmentFile;

public class ConcurrentAttachmentImpl implements ConcurrentAttachment {

	public List<AttachmentFile> copy(String srcDirPath, String destDirPath)
			throws InterruptedException, ExecutionException {
		long stardate = System.currentTimeMillis();
		ExecutorService exec = Executors.newFixedThreadPool(2);
		CompletionService ecs = new ExecutorCompletionService(exec);
		List<Future<AttachmentFile>> futures = new ArrayList<Future<AttachmentFile>>();
		File srcDir = new File(srcDirPath);
		try {
			if (srcDir.isDirectory()) {
				File files[] = srcDir.listFiles();
				int n = files.length;
				for (File file : files) {
					String srcFilePath = file.getAbsolutePath();
					String destFilePath = destDirPath
							+ srcFilePath.substring(srcFilePath
									.lastIndexOf(File.separator));
					System.out.println(destFilePath);
					AttachmentCallable attCall = new AttachmentCallable(
							file.getAbsolutePath(), destFilePath);
					futures.add(ecs.submit(attCall));
				}
				for (int i = 0; i < n; i++) {
					ecs.take().get();
				}
			}
		} finally {
			for (Future<AttachmentFile> f : futures)
				f.cancel(true);
			exec.shutdown();

		}
		long enddate = System.currentTimeMillis();
		System.out.println("total time:" + (enddate - stardate));
		return null;

	}

	public List<AttachmentFile> copy(File[] srcFiles, String attachGroupId,
			String contentType, String attachTypeId) {
		List<AttachmentFile> attFiles = new ArrayList<AttachmentFile>();
		Executor exec = Executors.newFixedThreadPool(2);
		CompletionService<AttachmentFile> ecs = new ExecutorCompletionService<AttachmentFile>(
				exec);
		int n = srcFiles.length;
		List<Future<AttachmentFile>> futures = new ArrayList<Future<AttachmentFile>>(
				n);

		try {
			for (File file : srcFiles) {
				// String srcFilePath = file.getAbsolutePath();
				AttachmentCallable attCall = new AttachmentCallable(
						attachGroupId, file.getName(), contentType, file,
						attachTypeId);
				futures.add(ecs.submit(attCall));
			}
			for (int i = 0; i < n; i++) {
				try {
					attFiles.add(ecs.take().get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} finally {
			for (Future<AttachmentFile> f : futures)
				f.cancel(true);

		}

		return attFiles;
	}

}
