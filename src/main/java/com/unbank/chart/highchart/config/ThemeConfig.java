package com.unbank.chart.highchart.config;

/**
 * 样式可进一步进行抽象, 待续...
 * 
 * @author zile
 * 
 */
public class ThemeConfig extends HighchartsConfig {

	// TODO 提取样式属性, 等级结构和HighchartsConfig对换

	protected ThemeConfig(HighchartsConfigBuilder builder) {
		super(builder);
	}

	public static class ThemeConfigBuilder extends HighchartsConfigBuilder {

		@Override
		public ThemeConfig build() {
			return new ThemeConfig(this);
		}

	}

}
