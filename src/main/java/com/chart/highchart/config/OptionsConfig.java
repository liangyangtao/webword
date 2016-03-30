package com.chart.highchart.config;

import com.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class OptionsConfig extends AbstractChartConfig {

	private final Global global;
	private final Lang lang;

	private OptionsConfig(OptionsConfigBuilder builder) {
		this.global = builder.global;
		this.lang = builder.lang;
	}

	public static class OptionsConfigBuilder implements Builder<OptionsConfig> {

		private Global global;
		private Lang lang;

		public OptionsConfigBuilder global(Global global) {
			this.global = global;
			return this;
		}

		public OptionsConfigBuilder lang(Lang lang) {
			this.lang = lang;
			return this;
		}

		@Override
		public OptionsConfig build() {
			return new OptionsConfig(this);
		}

	}

	public Global getGlobal() {
		return global;
	}

	public Lang getLang() {
		return lang;
	}

}
