package com.hichlink.easyweb.attachment.timer;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hichlink.easyweb.attachment.AttachmentInterface;
import com.hichlink.easyweb.attachment.service.AttachmentImpl;
import com.hichlink.easyweb.core.util.SpringContextHolder;

/**
 * @author haomingli
 * @version WEB_BAS 1.1.0.050
 * -----------------------------------------------------------------------------------
 * 以下配置需要放到quartz的配置计划任务中去，beforeDays 实体不配置默认值为为3，即清理3天前的临时附件
	<job>
		<job-detail>
			<name>attachmentClsJob</name>
			<group>DEFAULT</group>
			<job-class>
				com.aspire.sims.rtplt.component.attachment.timer.AttachmentClsJob
			</job-class>
			<volatility>false</volatility>
			<durability>false</durability>
			<recover>false</recover>
			<job-data-map allows-transient-data="true">
				<entry>
					<key>beforeDays</key>
					<value>3</value>
				</entry>
			</job-data-map>
		</job-detail>
		<trigger>
			<cron>
				<name>attachmentClsJobTrigger
				</name>
				<group>DEFAULT</group>
				<job-name>attachmentClsJob</job-name>
				<job-group>DEFAULT</job-group>
				<cron-expression>0 0 1 * * ?</cron-expression>
			</cron>
		</trigger>
	</job>
 *
 */
public class AttachmentClsJob implements Job {
	
	private static final Logger logger = LoggerFactory.getLogger(AttachmentClsJob.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if(logger.isDebugEnabled()){
			logger.debug("开始清理过期的临时目录附件");
		}
		int beforeDays = 3;
		try{
			JobDataMap dataMap = context.getJobDetail().getJobDataMap();
			//得到清理日期
			String tempDays = dataMap.getString("beforeDays");
			if(!StringUtils.isBlank(tempDays) && StringUtils.isNumeric(tempDays)){
				beforeDays = dataMap.getInt("beforeDays");
			}
			if(logger.isDebugEnabled()){
				logger.debug("清理 " + beforeDays + "天前的临时附件");
			}
			AttachmentInterface attachmentInterface = SpringContextHolder.getBean(AttachmentImpl.class);
			attachmentInterface.clearTemp(beforeDays);
			if(logger.isDebugEnabled()){
				logger.debug("结束清理过期的临时目录附件");
			}
		}catch(Exception e){
			logger.error("清理过期的临时目录附件出错",e);
		}
	}
}