package com.lucene.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lucene.service.LuceneService;

public class IndexesJob extends QuartzJobBean implements StatefulJob {

	private LuceneService luceneService;
	public static boolean loadkeywordflag=true;
	@Override
	public void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		luceneService.doIndexes();
	}

	public void setLuceneService(LuceneService luceneService) {
		this.luceneService = luceneService;
	}

}