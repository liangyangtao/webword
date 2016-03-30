package com.chart.highchart.theme;

import com.chart.highchart.config.ThemeConfig;

/**
 * <p>
 * 可以使用枚举实现, 更简洁
 * </p>
 * 
 * <p>
 * 两种实现方式, 第一种使用java对象封装; 第二种使用外部JSON配置文件, 该方式简单快速, 可以通过枚举实现
 * </p>
 * 
 * @author zile
 * 
 */
public interface Theme {

	/**
	 * 
	 */
	Theme DEFAULT = null;

	/**
	 * the JSON theme of chart
	 * 
	 * @return
	 */
	ThemeConfig themeConfig();

	/**
	 * 
	 * @return
	 */
	String themename();

}
