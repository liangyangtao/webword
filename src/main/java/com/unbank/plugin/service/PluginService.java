package com.unbank.plugin.service;

import com.unbank.UnbankException;
import com.unbank.model.UnbankModel;
import com.unbank.model.param.Params;

/**
 * expose services transparently using JMS as the underlying communication
 * protocol
 * 
 * @author zile
 *
 */
public interface PluginService {

	/**
	 * 获得插件模型数据, 客户端自己处理视图
	 * 
	 * @param pkgname
	 *            e.g m1, m2
	 * @param params
	 * @return
	 * @throws UnbankException
	 */
	UnbankModel plugin(String pkgname, Params params) throws UnbankException;

	/**
	 * 获得插件模型数据, 客户端自己处理视图
	 * 
	 * @param id
	 * @param params
	 * @return
	 * @throws UnbankException
	 */
	UnbankModel plugin(int id, Params params) throws UnbankException;

	/**
	 * 获得模型数据与默认模板合并的结果
	 * 
	 * @param pkgname
	 *            e.g m1, m2
	 * @param params
	 * @return
	 * @throws UnbankException
	 */
	UnbankModel template(String pkgname, Params params) throws UnbankException;

	/**
	 * 获得模型数据与默认模板合并的结果
	 * 
	 * @param id
	 * @param params
	 * @return
	 * @throws UnbankException
	 */
	UnbankModel template(int id, Params params) throws UnbankException;

}
