package com.chart.highchart.config;

import com.chart.highchart.type.Align;
import com.chart.highchart.type.VerticalAlign;
import com.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class DataLabels {

	public static class DataLabelsBuilder implements Builder<DataLabels> {

		private String align; // Align
		private Object backgroundColor; // String|Color
		private Object borderColor; // String|Color
		private Number borderRadius;
		private Number borderWidth;
		private Object color; // String|Color
		private Boolean crop;
		private Boolean defer;
		private Boolean enabled;
		private String format;
		// private Function formatter;
		private Boolean inside;
		private String overflow;
		private Number padding;
		private Number rotation;
		private Object shadow; // Boolean|Object
		private Style style;
		private Boolean useHTML;
		private String verticalAlign; // VerticalAlign
		private Number x;
		private Number y;
		private Number zIndex;

		public DataLabelsBuilder align(Align align) {
			this.align = align.getName();
			return this;
		}

		public DataLabelsBuilder backgroundColor(String backgroundColor) {
			this.backgroundColor = backgroundColor;
			return this;
		}

		public DataLabelsBuilder backgroundColor(Color color) {
			this.backgroundColor = color;
			return this;
		}

		public DataLabelsBuilder borderColor(String borderColor) {
			this.borderColor = borderColor;
			return this;
		}

		public DataLabelsBuilder borderColor(Color color) {
			this.borderColor = color;
			return this;
		}

		public DataLabelsBuilder borderRadius(Number borderRadius) {
			this.borderRadius = borderRadius;
			return this;
		}

		public DataLabelsBuilder borderWidth(Number borderWidth) {
			this.borderWidth = borderWidth;
			return this;
		}

		public DataLabelsBuilder color(String color) {
			this.color = color;
			return this;
		}

		public DataLabelsBuilder color(Color color) {
			this.color = color;
			return this;
		}

		public DataLabelsBuilder crop(Boolean crop) {
			this.crop = crop;
			return this;
		}

		public DataLabelsBuilder defer(Boolean defer) {
			this.defer = defer;
			return this;
		}

		public DataLabelsBuilder enabled(Boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public DataLabelsBuilder format(String format) {
			this.format = format;
			return this;
		}

		public DataLabelsBuilder inside(Boolean inside) {
			this.inside = inside;
			return this;
		}

		public DataLabelsBuilder overflow(String overflow) {
			this.overflow = overflow;
			return this;
		}

		public DataLabelsBuilder padding(Number padding) {
			this.padding = padding;
			return this;
		}

		public DataLabelsBuilder rotation(Number rotation) {
			this.rotation = rotation;
			return this;
		}

		public DataLabelsBuilder shadow(Boolean shadow) {
			this.shadow = shadow;
			return this;
		}

		/**
		 * @see Highchart
		 * 
		 * @deprecated
		 * @param shadow
		 * @return
		 */
		@Deprecated
		public DataLabelsBuilder shadow(Object shadow) {
			// this.shadow = shadow;
			// TODO
			return this;
		}

		public DataLabelsBuilder style(Style style) {
			this.style = style;
			return this;
		}

		public DataLabelsBuilder useHTML(Boolean useHTML) {
			this.useHTML = useHTML;
			return this;
		}

		public DataLabelsBuilder verticalAlign(VerticalAlign verticalAlign) {
			this.verticalAlign = verticalAlign.getName();
			return this;
		}

		public DataLabelsBuilder x(Number x) {
			this.x = x;
			return this;
		}

		public DataLabelsBuilder y(Number y) {
			this.y = y;
			return this;
		}

		public DataLabelsBuilder zIndex(Number zIndex) {
			this.zIndex = zIndex;
			return this;
		}

		@Override
		public DataLabels build() {
			return new DataLabels(this);
		}

	}

	private final String align; // Align
	private final Object backgroundColor; // String|Color
	private final Object borderColor; // String|Color
	private final Number borderRadius;
	private final Number borderWidth;
	private final Object color; // String|Color
	private final Boolean crop;
	private final Boolean defer;
	private final Boolean enabled;
	private final String format;
	// private Function formatter;
	private final Boolean inside;
	private final String overflow;
	private final Number padding;
	private final Number rotation;
	private final Object shadow; // Boolean|Object
	private final Style style;
	private final Boolean useHTML;
	private final String verticalAlign; // VerticalAlign
	private final Number x;
	private final Number y;
	private final Number zIndex;

	private DataLabels(DataLabelsBuilder builder) {
		this.align = builder.align;
		this.backgroundColor = builder.backgroundColor;
		this.borderColor = builder.borderColor;
		this.borderRadius = builder.borderRadius;
		this.borderWidth = builder.borderWidth;
		this.color = builder.color;
		this.crop = builder.crop;
		this.defer = builder.defer;
		this.enabled = builder.enabled;
		this.format = builder.format;
		this.inside = builder.inside;
		this.overflow = builder.overflow;
		this.padding = builder.padding;
		this.rotation = builder.rotation;
		this.shadow = builder.shadow;
		this.style = builder.style;
		this.useHTML = builder.useHTML;
		this.verticalAlign = builder.verticalAlign;
		this.x = builder.x;
		this.y = builder.y;
		this.zIndex = builder.zIndex;
	}

	public String getAlign() {
		return align;
	}

	public Object getBackgroundColor() {
		return backgroundColor;
	}

	public Object getBorderColor() {
		return borderColor;
	}

	public Number getBorderRadius() {
		return borderRadius;
	}

	public Number getBorderWidth() {
		return borderWidth;
	}

	public Object getColor() {
		return color;
	}

	public Boolean getCrop() {
		return crop;
	}

	public Boolean getDefer() {
		return defer;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public String getFormat() {
		return format;
	}

	public Boolean getInside() {
		return inside;
	}

	public String getOverflow() {
		return overflow;
	}

	public Number getPadding() {
		return padding;
	}

	public Number getRotation() {
		return rotation;
	}

	public Object getShadow() {
		return shadow;
	}

	public Style getStyle() {
		return style;
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

	public Number getzIndex() {
		return zIndex;
	}

}
