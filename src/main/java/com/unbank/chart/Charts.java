package com.unbank.chart;

import com.unbank.chart.highchart.config.ChartConfig;

/**
 * 
 * @author zile
 * 
 */
public class Charts {

	public static <T extends ChartConfig, V extends ResultEntity<T>> T exportForObject(
			AbstractChart<T, V> chart) {
		chart.template();
		return chart.chartConfigEntity();
	}

	public static <T extends ChartConfig, V extends ResultEntity<T>> V exportForEntity(
			AbstractChart<T, V> chart) {
		chart.template();
		return chart.exportForEntity();
	}

}
