/*
 * <p>Title: 知识自动化平台</p>
 * <p>Description: PluginUserExt.java</p>
 * <p>Copyright: Copyright (c) 2015 北京银联信投资顾问有限责任公司，版权所有. </p>
 * <p>Company: 北京银联信投资顾问有限责任公司</p>
 * @author knight
 * @date 2015-5-20
 * @version 1.0
 */
package com.web.plugin.bean;

import com.database.bean.PluginUser;

/**
 * <p>Title: PluginUserExt</p>
 * <p>Description: TODO</p>
 * @author knight
 * @date 2015-5-20
 */
public class PluginUserExt extends PluginUser {

	private String pluginParams;

	/**
	 * @return the pluginParams
	 */
	public String getPluginParams() {
		return pluginParams;
	}

	/**
	 * @param pluginParams the pluginParams to set
	 */
	public void setPluginParams(String pluginParams) {
		this.pluginParams = pluginParams;
	}
	
}
