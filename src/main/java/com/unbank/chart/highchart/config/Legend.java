package com.unbank.chart.highchart.config;

import com.unbank.chart.highchart.type.Align;
import com.unbank.chart.highchart.type.Layout;
import com.unbank.chart.highchart.type.VerticalAlign;
import com.unbank.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class Legend {

	private final String align; // Align
	private final Object backgroundColor; // String|Color
	private final Object borderColor;// String|Color
	private final Number borderRadius;
	private final Number borderWidth;
	private final Boolean enabled;
	private final Boolean floating;
	private final Number itemDistance;
	private final Style itemHiddenStyle;
	private final Style itemHoverStyle;
	private final Number itemMarginBottom;
	private final Number itemMarginTop;
	private final Style itemStyle;
	private final Number itemWidth;
	private final String labelFormat;
	// private Function labelFormatter;
	private final String layout;
	private final Number lineHeight;
	private final Number margin;
	private final Number maxHeight;
	private final LegendNavigation navigation;
	private final Number padding;
	private final Boolean reversed;
	private final Boolean rtl;
	private final Object shadow; // Boolean|Object
	private final Style style; // Deprecated
	private final Number symbolHeight;
	private final Number symbolPadding;
	private final Number symbolRadius;
	private final Number symbolWidth;
	private final Title title;
	private final Boolean useHTML;
	private final String verticalAlign; // VerticalAlign
	private final Number width;
	private final Number x;
	private final Number y;

	private Legend(LegendBuilder builder) {
		this.align = builder.align;
		this.backgroundColor = builder.backgroundColor;
		this.borderColor = builder.borderColor;
		this.borderRadius = builder.borderRadius;
		this.borderWidth = builder.borderWidth;
		this.enabled = builder.enabled;
		this.floating = builder.floating;
		this.itemDistance = builder.itemDistance;
		this.itemHiddenStyle = builder.itemHiddenStyle;
		this.itemHoverStyle = builder.itemHoverStyle;
		this.itemMarginBottom = builder.itemMarginBottom;
		this.itemMarginTop = builder.itemMarginTop;
		this.itemStyle = builder.itemStyle;
		this.itemWidth = builder.itemWidth;
		this.labelFormat = builder.labelFormat;
		this.layout = builder.layout;
		this.lineHeight = builder.lineHeight;
		this.margin = builder.margin;
		this.maxHeight = builder.maxHeight;
		this.navigation = builder.navigation;
		this.padding = builder.padding;
		this.reversed = builder.reversed;
		this.rtl = builder.rtl;
		this.shadow = builder.shadow;
		this.style = builder.style;
		this.symbolHeight = builder.symbolHeight;
		this.symbolPadding = builder.symbolPadding;
		this.symbolRadius = builder.symbolRadius;
		this.symbolWidth = builder.symbolWidth;
		this.title = builder.title;
		this.useHTML = builder.useHTML;
		this.verticalAlign = builder.verticalAlign;
		this.width = builder.width;
		this.x = builder.x;
		this.y = builder.y;
	}

	public static class LegendBuilder implements Builder<Legend> {

		private String align; // Align
		private Object backgroundColor; // String|Color
		private Object borderColor;// String|Color
		private Number borderRadius;
		private Number borderWidth;
		private Boolean enabled;
		private Boolean floating;
		private Number itemDistance;
		private Style itemHiddenStyle;
		private Style itemHoverStyle;
		private Number itemMarginBottom;
		private Number itemMarginTop;
		private Style itemStyle;
		private Number itemWidth;
		private String labelFormat;
		// private Function labelFormatter;
		private String layout;
		private Number lineHeight;
		private Number margin;
		private Number maxHeight;
		private LegendNavigation navigation;
		private Number padding;
		private Boolean reversed;
		private Boolean rtl;
		private Object shadow; // Boolean|Object
		private Style style; // Deprecated
		private Number symbolHeight;
		private Number symbolPadding;
		private Number symbolRadius;
		private Number symbolWidth;
		private Title title;
		private Boolean useHTML;
		private String verticalAlign; // VerticalAlign
		private Number width;
		private Number x;
		private Number y;

		public LegendBuilder align(Align align) {
			this.align = align.getName();
			return this;
		}

		public LegendBuilder backgroundColor(String backgroundColor) {
			this.backgroundColor = backgroundColor;
			return this;
		}

		public LegendBuilder backgroundColor(Color color) {
			this.backgroundColor = color;
			return this;
		}

		public LegendBuilder borderColor(String borderColor) {
			this.borderColor = borderColor;
			return this;
		}

		public LegendBuilder borderColor(Color color) {
			this.borderColor = color;
			return this;
		}

		public LegendBuilder borderRadius(Number borderRadius) {
			this.borderRadius = borderRadius;
			return this;
		}

		public LegendBuilder borderWidth(Number borderWidth) {
			this.borderWidth = borderWidth;
			return this;
		}

		public LegendBuilder enabled(Boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public LegendBuilder floating(Boolean floating) {
			this.floating = floating;
			return this;
		}

		public LegendBuilder itemDistance(Number itemDistance) {
			this.itemDistance = itemDistance;
			return this;
		}

		public LegendBuilder itemHiddenStyle(Style itemHiddenStyle) {
			this.itemHiddenStyle = itemHiddenStyle;
			return this;
		}

		public LegendBuilder itemHoverStyle(Style itemHoverStyle) {
			this.itemHoverStyle = itemHoverStyle;
			return this;
		}

		public LegendBuilder itemMarginBottom(Number itemMarginBottom) {
			this.itemMarginBottom = itemMarginBottom;
			return this;
		}

		public LegendBuilder itemMarginTop(Number itemMarginTop) {
			this.itemMarginTop = itemMarginTop;
			return this;
		}

		public LegendBuilder itemStyle(Style itemStyle) {
			this.itemStyle = itemStyle;
			return this;
		}

		public LegendBuilder itemWidth(Number itemWidth) {
			this.itemWidth = itemWidth;
			return this;
		}

		public LegendBuilder labelFormat(String labelFormat) {
			this.labelFormat = labelFormat;
			return this;
		}

		public LegendBuilder layout(Layout layout) {
			this.layout = layout.getName();
			return this;
		}

		public LegendBuilder lineHeight(Number lineHeight) {
			this.lineHeight = lineHeight;
			return this;
		}

		public LegendBuilder margin(Number margin) {
			this.margin = margin;
			return this;
		}

		public LegendBuilder maxHeight(Number maxHeight) {
			this.maxHeight = maxHeight;
			return this;
		}

		public LegendBuilder navigation(LegendNavigation navigation) {
			this.navigation = navigation;
			return this;
		}

		public LegendBuilder padding(Number padding) {
			this.padding = padding;
			return this;
		}

		public LegendBuilder reversed(Boolean reversed) {
			this.reversed = reversed;
			return this;
		}

		public LegendBuilder rtl(Boolean rtl) {
			this.rtl = rtl;
			return this;
		}

		public LegendBuilder shadow(Boolean shadow) {
			this.shadow = shadow;
			return this;
		}

		/**
		 * @deprecated
		 * @see #Chart
		 * @param object
		 * @return
		 */
		@Deprecated
		public LegendBuilder shadow(Object object) {
			// this.shadow = object;
			// TODO
			return this;
		}

		/**
		 * CSS styles for the legend area. In the 1.x versions the position of
		 * the legend area was determined by CSS. In 2.x, the position is
		 * determined by properties like align, verticalAlign, x and y, but the
		 * styles are still parsed for backwards compatibility.
		 * 
		 * @deprecated
		 * @param style
		 * @return
		 */
		@Deprecated
		public LegendBuilder style(Style style) {
			this.style = style;
			return this;
		}

		public LegendBuilder symbolHeight(Number symbolHeight) {
			this.symbolHeight = symbolHeight;
			return this;
		}

		public LegendBuilder symbolPadding(Number symbolPadding) {
			this.symbolPadding = symbolPadding;
			return this;
		}

		public LegendBuilder symbolRadius(Number symbolRadius) {
			this.symbolRadius = symbolRadius;
			return this;
		}

		public LegendBuilder symbolWidth(Number symbolWidth) {
			this.symbolWidth = symbolWidth;
			return this;
		}

		/**
		 * use LegendTitleBuilder
		 * 
		 * @param title
		 * @return
		 */
		public LegendBuilder title(Title title) {
			this.title = title;
			return this;
		}

		public LegendBuilder useHTML(Boolean useHTML) {
			this.useHTML = useHTML;
			return this;
		}

		public LegendBuilder verticalAlign(VerticalAlign verticalAlign) {
			this.verticalAlign = verticalAlign.getName();
			return this;
		}

		public LegendBuilder width(Number width) {
			this.width = width;
			return this;
		}

		public LegendBuilder x(Number x) {
			this.x = x;
			return this;
		}

		public LegendBuilder y(Number y) {
			this.y = y;
			return this;
		}

		@Override
		public Legend build() {
			return new Legend(this);
		}

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

	public Boolean getEnabled() {
		return enabled;
	}

	public Boolean getFloating() {
		return floating;
	}

	public Number getItemDistance() {
		return itemDistance;
	}

	public Style getItemHiddenStyle() {
		return itemHiddenStyle;
	}

	public Style getItemHoverStyle() {
		return itemHoverStyle;
	}

	public Number getItemMarginBottom() {
		return itemMarginBottom;
	}

	public Number getItemMarginTop() {
		return itemMarginTop;
	}

	public Style getItemStyle() {
		return itemStyle;
	}

	public Number getItemWidth() {
		return itemWidth;
	}

	public String getLabelFormat() {
		return labelFormat;
	}

	public String getLayout() {
		return layout;
	}

	public Number getLineHeight() {
		return lineHeight;
	}

	public Number getMargin() {
		return margin;
	}

	public Number getMaxHeight() {
		return maxHeight;
	}

	public LegendNavigation getNavigation() {
		return navigation;
	}

	public Number getPadding() {
		return padding;
	}

	public Boolean getReversed() {
		return reversed;
	}

	public Boolean getRtl() {
		return rtl;
	}

	public Object getShadow() {
		return shadow;
	}

	public Style getStyle() {
		return style;
	}

	public Number getSymbolHeight() {
		return symbolHeight;
	}

	public Number getSymbolPadding() {
		return symbolPadding;
	}

	public Number getSymbolRadius() {
		return symbolRadius;
	}

	public Number getSymbolWidth() {
		return symbolWidth;
	}

	public Title getTitle() {
		return title;
	}

	public Boolean getUseHTML() {
		return useHTML;
	}

	public String getVerticalAlign() {
		return verticalAlign;
	}

	public Number getWidth() {
		return width;
	}

	public Number getX() {
		return x;
	}

	public Number getY() {
		return y;
	}

}
