package com.common.editor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * spring绑定数据，数据类型转换
 * 
 * @author 博彦
 *
 */
public class CommonDataBinder implements WebBindingInitializer {

	/**
	 * 全局注册数据类型
	 * 
	 */
	public void initBinder(WebDataBinder wdb, WebRequest webRequest) {
		wdb.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		wdb.registerCustomEditor(BigDecimal.class, new CustomBigDecimalEditor());
	}
}
