package com.lucene.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class IKJob extends QuartzJobBean implements StatefulJob {

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		//更新每天更新一次加载词典标志
		IndexesJob.loadkeywordflag=true;
		
	}

}
