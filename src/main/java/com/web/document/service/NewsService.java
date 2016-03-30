package com.web.document.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.common.service.CommonService;

public class NewsService{
	public static Logger logger = Logger.getLogger(RiskService.class);
	
	@Value("${newsByJson}")
	private String newsByJson;
	
	@Value("${newsByNewsId}")
	private String newsByNewsId;
	
	@Value("${newsByMulti}")
	private String newsByMulti;
	
	@Value("${picUrl}")
	private String picUrl;
	
}
