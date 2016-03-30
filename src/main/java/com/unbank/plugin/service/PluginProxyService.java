package com.unbank.plugin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unbank.UnbankException;
import com.unbank.model.UnbankModel;
import com.unbank.model.param.Params;

/**
 * 该类是解决通过JSM代理创建的接口在web环境中不走AOP的一种解决方案, 原因未知
 * 
 * @author zile
 * 
 */
@Service
public class PluginProxyService {

	@Autowired
	private PluginService pluginService;

	public UnbankModel plugin(String pkgname, Params params)
			throws UnbankException {
		return pluginService.plugin(pkgname, params);
	}

	public UnbankModel plugin(int id, Params params) throws UnbankException {
		return pluginService.plugin(id, params);
	}

	public UnbankModel template(String pkgname, Params params)
			throws UnbankException {
		return pluginService.template(pkgname, params);
	}

	public UnbankModel template(int id, Params params) throws UnbankException {
		return pluginService.template(id, params);
	}

}
