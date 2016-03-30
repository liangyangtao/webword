package com.unbank.chart.highchart.config;

import com.unbank.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class Loading {

	private final Number hideDuration;
	private final Style labelStyle;
	private final Number showDuration;
	private final Style style;

	private Loading(LoadingBuilder builder) {
		this.hideDuration = builder.hideDuration;
		this.labelStyle = builder.labelStyle;
		this.showDuration = builder.showDuration;
		this.style = builder.style;
	}

	public static class LoadingBuilder implements Builder<Loading> {

		private Number hideDuration;
		private Style labelStyle;
		private Number showDuration;
		private Style style;

		public LoadingBuilder hideDuration(Number hideDuration) {
			this.hideDuration = hideDuration;
			return this;
		}

		public LoadingBuilder labelStyle(Style labelStyle) {
			this.labelStyle = labelStyle;
			return this;
		}

		public LoadingBuilder showDuration(Number showDuration) {
			this.showDuration = showDuration;
			return this;
		}

		public LoadingBuilder style(Style style) {
			this.style = style;
			return this;
		}

		@Override
		public Loading build() {
			return new Loading(this);
		}

	}

	public Number getHideDuration() {
		return hideDuration;
	}

	public Style getLabelStyle() {
		return labelStyle;
	}

	public Number getShowDuration() {
		return showDuration;
	}

	public Style getStyle() {
		return style;
	}

}
