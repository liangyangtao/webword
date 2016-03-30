package com.unbank.chart;

import java.util.List;

/**
 * 
 * @author zile
 * 
 * @param <T>
 */
public interface ResultEntity<T> {

	/**
	 * 获得实体对象
	 * 
	 * @return
	 */
	T getEntity();

	/**
	 * 异常信息结合
	 * 
	 * @return
	 */
	List<Throwable> getMessages();

	/**
	 * 图表类型
	 * 
	 * @return
	 */
	String getType();

	/**
	 * 执行时间, 单位毫秒
	 * 
	 * @return
	 */
	long getMs();

	/**
	 * 文件名称, 根据该名称导出图片
	 * 
	 * @return
	 */
	String filename();

}
