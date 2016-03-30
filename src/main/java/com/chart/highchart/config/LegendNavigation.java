package com.chart.highchart.config;

import com.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class LegendNavigation {

	private final Object activeColor; // String|Color
	private final Object animation; // Boolean|Animation
	private final Number arrowSize;
	private final Object inactiveColor;// String|Color
	private final Style style;

	private LegendNavigation(LegendNavigationBuilder builder) {
		this.activeColor = builder.activeColor;
		this.animation = builder.animation;
		this.arrowSize = builder.arrowSize;
		this.inactiveColor = builder.inactiveColor;
		this.style = builder.style;
	}

	public static class LegendNavigationBuilder implements
			Builder<LegendNavigation> {

		private Object activeColor; // String|Color
		private Object animation; // Boolean|Animation
		private Number arrowSize;
		private Object inactiveColor;// String|Color
		private Style style;

		public LegendNavigationBuilder activeColor(String activeColor) {
			this.activeColor = activeColor;
			return this;
		}

		public LegendNavigationBuilder activeColor(Color color) {
			this.activeColor = color;
			return this;
		}

		public LegendNavigationBuilder animation(Animation animation) {
			this.animation = animation;
			return this;
		}

		public LegendNavigationBuilder animation(Boolean animation) {
			this.animation = animation;
			return this;
		}

		public LegendNavigationBuilder arrowSize(Number arrowSize) {
			this.arrowSize = arrowSize;
			return this;
		}

		public LegendNavigationBuilder inactiveColor(String inactiveColor) {
			this.inactiveColor = inactiveColor;
			return this;
		}

		public LegendNavigationBuilder inactiveColor(Color color) {
			this.inactiveColor = color;
			return this;
		}

		public LegendNavigationBuilder style(Style style) {
			this.style = style;
			return this;
		}

		@Override
		public LegendNavigation build() {
			return new LegendNavigation(this);
		}

	}

	public Object getActiveColor() {
		return activeColor;
	}

	public Object getAnimation() {
		return animation;
	}

	public Number getArrowSize() {
		return arrowSize;
	}

	public Object getInactiveColor() {
		return inactiveColor;
	}

	public Style getStyle() {
		return style;
	}

}
