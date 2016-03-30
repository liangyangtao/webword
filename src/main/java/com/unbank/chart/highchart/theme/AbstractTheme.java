package com.unbank.chart.highchart.theme;

import com.unbank.chart.highchart.config.ThemeConfig;
import com.unbank.chart.highchart.config.ThemeConfig.ThemeConfigBuilder;

/**
 * 
 * @author zile
 * 
 */
public abstract class AbstractTheme implements Theme {

	private ThemeConfig themeConfig;
	private ThemeConfigBuilder themeConfigBuilder = new ThemeConfigBuilder();

	public AbstractTheme() {
		config(themeConfigBuilder);
		themeConfig = themeConfigBuilder.build();
	}

	@Override
	public ThemeConfig themeConfig() {
		return themeConfig;
	}

	/**
	 * configuration theme options, do not call build method, supper class will
	 * do this!
	 * 
	 * @param themeBuilder
	 */
	protected abstract void config(ThemeConfigBuilder themeConfigBuilder);

}
