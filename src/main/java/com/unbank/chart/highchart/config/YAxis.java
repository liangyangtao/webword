package com.unbank.chart.highchart.config;

import java.util.ArrayList;
import java.util.List;

import com.unbank.chart.highchart.type.Align;
import com.unbank.chart.highchart.type.VerticalAlign;
import com.unbank.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class YAxis extends Axis {

	public static class StackLabels {

		private final String align; // Align
		private final Boolean enabled;
		private final String format;
		// private Function formatter;
		private final Number rotation;
		private final Style style;
		private final String textAlign; // Align
		private final Boolean useHTML;
		private final String verticalAlign; // VerticalAlign
		private final Number x;
		private final Number y;

		private StackLabels(StackLabelsBuilder builder) {
			this.align = builder.align;
			this.enabled = builder.enabled;
			this.format = builder.format;
			this.rotation = builder.rotation;
			this.style = builder.style;
			this.textAlign = builder.textAlign;
			this.useHTML = builder.useHTML;
			this.verticalAlign = builder.verticalAlign;
			this.x = builder.x;
			this.y = builder.y;
		}

		public String getAlign() {
			return align;
		}

		public Boolean getEnabled() {
			return enabled;
		}

		public String getFormat() {
			return format;
		}

		public Number getRotation() {
			return rotation;
		}

		public Style getStyle() {
			return style;
		}

		public String getTextAlign() {
			return textAlign;
		}

		public Boolean getUseHTML() {
			return useHTML;
		}

		public String getVerticalAlign() {
			return verticalAlign;
		}

		public Number getX() {
			return x;
		}

		public Number getY() {
			return y;
		}

	}

	public static class StackLabelsBuilder implements Builder<StackLabels> {

		private String align; // Align
		private Boolean enabled;
		private String format;
		// private Function formatter;
		private Number rotation;
		private Style style;
		private String textAlign; // Align
		private Boolean useHTML;
		private String verticalAlign; // VerticalAlign
		private Number x;
		private Number y;

		public StackLabelsBuilder align(Align align) {
			this.align = align.getName();
			return this;
		}

		public StackLabelsBuilder enabled(Boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public StackLabelsBuilder format(String format) {
			this.format = format;
			return this;
		}

		public StackLabelsBuilder rotation(Number rotation) {
			this.rotation = rotation;
			return this;
		}

		public StackLabelsBuilder style(Style style) {
			this.style = style;
			return this;
		}

		public StackLabelsBuilder textAlign(Align textAlign) {
			this.textAlign = textAlign.getName();
			return this;
		}

		public StackLabelsBuilder useHTML(Boolean useHTML) {
			this.useHTML = useHTML;
			return this;
		}

		public StackLabelsBuilder verticalAlign(VerticalAlign verticalAlign) {
			this.verticalAlign = verticalAlign.getName();
			return this;
		}

		public StackLabelsBuilder x(Number x) {
			this.x = x;
			return this;
		}

		public StackLabelsBuilder y(Number y) {
			this.y = y;
			return this;
		}

		@Override
		public StackLabels build() {
			return new StackLabels(this);
		}

	}

	public static class YAxisBuilder extends AxisBuilder {

		private String gridLineInterpolation;
		private Object maxColor; // String|Color
		private Object minColor; // String|Color
		private Boolean reversedStacks;
		private StackLabels stackLabels;
		private List<List<Object>> stops; // [0.1, '#55BF3B']

		public YAxisBuilder gridLineInterpolation(String gridLineInterpolation) {
			this.gridLineInterpolation = gridLineInterpolation;
			return this;
		}

		public YAxisBuilder maxColor(String maxColor) {
			this.maxColor = maxColor;
			return this;
		}

		public YAxisBuilder maxColor(Color color) {
			this.maxColor = color;
			return this;
		}

		public YAxisBuilder minColor(String minColor) {
			this.minColor = minColor;
			return this;
		}

		public YAxisBuilder minColor(Color color) {
			this.minColor = color;
			return this;
		}

		public YAxisBuilder reversedStacks(Boolean reversedStacks) {
			this.reversedStacks = reversedStacks;
			return this;
		}

		public YAxisBuilder stackLabels(StackLabels stackLabels) {
			this.stackLabels = stackLabels;
			return this;
		}

		/**
		 * <p>
		 * Solid gauge series only. Color stops for the solid gauge. Use this in
		 * cases where a linear gradient between a minColor and maxColor is not
		 * sufficient. The stops is an array of tuples, where the first item is
		 * a float between 0 and 1 assigning the relative position in the
		 * gradient, and the second item is the color.
		 * </p>
		 * 
		 * <p>
		 * you can call this method more than once.
		 * </p>
		 * 
		 * @param position
		 *            between 0 and 1
		 * @param color
		 *            hex color
		 * @return
		 */
		public YAxisBuilder addStops(float position, String color) {
			if (stops == null) {
				stops = new ArrayList<List<Object>>();
			}
			List<Object> l = new ArrayList<Object>();
			l.add(position);
			l.add(color);
			stops.add(l);
			return this;
		}

		@Override
		public YAxis build() {
			return new YAxis(this);
		}

	}

	private final String gridLineInterpolation;
	private final Object maxColor; // String|Color
	private final Object minColor; // String|Color
	private final Boolean reversedStacks;
	private final StackLabels stackLabels;
	private final List<List<Object>> stops; // [0.1, '#55BF3B']

	private YAxis(YAxisBuilder builder) {
		super(builder);
		this.gridLineInterpolation = builder.gridLineInterpolation;
		this.maxColor = builder.maxColor;
		this.minColor = builder.minColor;
		this.reversedStacks = builder.reversedStacks;
		this.stackLabels = builder.stackLabels;
		this.stops = builder.stops;
	}

	public String getGridLineInterpolation() {
		return gridLineInterpolation;
	}

	public Object getMaxColor() {
		return maxColor;
	}

	public Object getMinColor() {
		return minColor;
	}

	public Boolean getReversedStacks() {
		return reversedStacks;
	}

	public StackLabels getStackLabels() {
		return stackLabels;
	}

	public List<List<Object>> getStops() {
		return stops;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
	}
}
