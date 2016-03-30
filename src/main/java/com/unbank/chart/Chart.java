package com.unbank.chart;

import com.unbank.chart.highchart.config.ChartConfig;

/**
 * 
 * @author zile
 * 
 * @param <T>
 */
public interface Chart<T extends ChartConfig> {

	/**
	 * the JSON global configuration options of chart
	 * 
	 * @return
	 */
	// JsonObject jsonOptions();

	/**
	 * the JSON theme of chart
	 * 
	 * @return
	 */
	// JsonObject jsonTheme();

	/**
	 * the JSON data of chart
	 * 
	 * @return
	 */
	// JsonObject jsonData();

	/**
	 * 
	 * @return
	 */
	T chartConfigEntity();

}
