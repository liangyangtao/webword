package com.chart.highchart;

import com.chart.ResultEntity;
import com.chart.highchart.config.OptionsConfig;
import com.chart.highchart.theme.Theme;

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
