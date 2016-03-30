package com.util;


public class ConstantUtils {
	
	public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

	public static class ContentEnum{
		public static final String SAVED = "SAVED";
		public static final String SUBMITED ="SUBMITTED";
		public static final String FAILED = "FAILED";
		public static final String PASSED = "PASSED";
	}
	
	public static class DocumentOperate{
		public static final String COPY = "copy";
		public static final String EDIT = "edit";
	}
	
	public static class ArticleType{
		public static final String TEMPLATE = "template";
		public static final String DOCUMENT = "document";
	}
	
	//分页默认值
	public static class DefaultPage{
		public static final Integer START_NUM = 0;
		public static final Integer PAGE_SIZE = 20;
	}
	
	//栏目组类型
	public static class WordColumnType{
		public static final Integer HOME_LEFT = 1;//首页左侧
		public static final Integer HOME_CENTRE = 2;//首页中间
		public static final Integer HOT = 3;//热点栏目
	}
	
	//分期显示数量
	public static final Integer journalCount = 9;
	
	public static class JournalType{
		public static final int DAY = 1;//日刊
		public static final int WEEK = 2;//周刊
		public static final int HALF_MONTH = 3;//半月刊
		public static final int MONTH = 4;//月刊
		public static final int DOUBLE_MONTH = 5;//双月刊
		public static final int SEASON = 6;//季刊
		public static final int HALF_YEAR = 7;//半年刊
		public static final int YEAR = 8;//年刊
	}
	
	//文档预览页面分页每页页数
	public static final int PREVIEW_PAGE = 20;
}
