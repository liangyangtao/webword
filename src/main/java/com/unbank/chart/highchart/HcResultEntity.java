package com.unbank.chart.highchart;

import com.unbank.chart.ResultEntity;
import com.unbank.chart.highchart.config.OptionsConfig;
import com.unbank.chart.highchart.theme.Theme;

public interface HcResultEntity<T> extends ResultEntity<T> {

	/**
	 * 主题
	 * 
	 * @return
	 */
	Theme getTheme();

	/**
	 * 全局配置
	 * 
	 * @return
	 */
	OptionsConfig getOptions();

}
