package com.unbank.chart.highchart.config;

import com.unbank.chart.highchart.type.Align;
import com.unbank.chart.highchart.type.VerticalAlign;
import com.unbank.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class Navigation {

	public static class ButtonOptions {

		private final String align; // Align
		private final Boolean enabled;
		private final Number height;
		private final Object symbolFill; // String|Color
		private final Number symbolSize;
		private final Object symbolStroke; // String|Color
		private final Number symbolStrokeWidth;
		private final Number symbolX;
		private final Number symbolY;
		private final String text;
		private final ThemeMap theme;
		private final String verticalAlign; // VerticalAlign
		private final Number width;
		private final Number y;

		private ButtonOptions(ButtonOptionsBuilder builder) {
			this.align = builder.align;
			this.enabled = builder.enabled;
			this.height = builder.height;
			this.symbolFill = builder.symbolFill;
			this.symbolSize = builder.symbolSize;
			this.symbolStroke = builder.symbolStroke;
			this.symbolStrokeWidth = builder.symbolStrokeWidth;
			this.symbolX = builder.symbolX;
			this.symbolY = builder.symbolY;
			this.text = builder.text;
			this.theme = builder.theme;
			this.verticalAlign = builder.verticalAlign;
			this.width = builder.width;
			this.y = builder.y;
		}

		public String getAlign() {
			return align;
		}

		public Boolean getEnabled() {
			return enabled;
		}

		public Number getHeight() {
			return height;
		}

		public Object getSymbolFill() {
			return symbolFill;
		}

		public Number getSymbolSize() {
			return symbolSize;
		}

		public Object getSymbolStroke() {
			return symbolStroke;
		}

		public Number getSymbolStrokeWidth() {
			return symbolStrokeWidth;
		}

		public Number getSymbolX() {
			return symbolX;
		}

		public Number getSymbolY() {
			return symbolY;
		}

		public String getText() {
			return text;
		}

		public ThemeMap getTheme() {
			return theme;
		}

		public String getVerticalAlign() {
			return verticalAlign;
		}

		public Number getWidth() {
			return width;
		}

		public Number getY() {
			return y;
		}

	}

	private final ButtonOptions buttonOptions;
	private final Style menuItemHoverStyle;
	private final Style menuItemStyle;
	private final Style menuStyle;

	private Navigation(NavigationBuilder builder) {
		this.buttonOptions = builder.buttonOptions;
		this.menuItemHoverStyle = builder.menuItemHoverStyle;
		this.menuItemStyle = builder.menuItemStyle;
		this.menuStyle = builder.menuStyle;
	}

	public static class NavigationBuilder implements Builder<Navigation> {

		private ButtonOptions buttonOptions;
		private Style menuItemHoverStyle;
		private Style menuItemStyle;
		private Style menuStyle;

		public NavigationBuilder buttonOptions(ButtonOptions buttonOptions) {
			this.buttonOptions = buttonOptions;
			return this;
		}

		public NavigationBuilder menuItemHoverStyle(Style menuItemHoverStyle) {
			this.menuItemHoverStyle = menuItemHoverStyle;
			return this;
		}

		public NavigationBuilder menuItemStyle(Style menuItemStyle) {
			this.menuItemStyle = menuItemStyle;
			return this;
		}

		public NavigationBuilder menuStyle(Style menuStyle) {
			this.menuStyle = menuStyle;
			return this;
		}

		@Override
		public Navigation build() {
			return new Navigation(this);
		}

	}

	public static class ButtonOptionsBuilder implements Builder<ButtonOptions> {

		private String align; // Align
		private Boolean enabled;
		private Number height;
		private Object symbolFill; // String|Color
		private Number symbolSize;
		private Object symbolStroke; // String|Color
		private Number symbolStrokeWidth;
		private Number symbolX;
		private Number symbolY;
		private String text;
		private ThemeMap theme;
		private String verticalAlign; // VerticalAlign
		private Number width;
		private Number y;

		public ButtonOptionsBuilder align(Align align) {
			this.align = align.getName();
			return this;
		}

		public ButtonOptionsBuilder enabled(Boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public ButtonOptionsBuilder height(Number height) {
			this.height = height;
			return this;
		}

		public ButtonOptionsBuilder symbolFill(String symbolFill) {
			this.symbolFill = symbolFill;
			return this;
		}

		public ButtonOptionsBuilder symbolFill(Color color) {
			this.symbolFill = color;
			return this;
		}

		public ButtonOptionsBuilder symbolSize(Number symbolSize) {
			this.symbolSize = symbolSize;
			return this;
		}

		public ButtonOptionsBuilder symbolStroke(String symbolStroke) {
			this.symbolStroke = symbolStroke;
			return this;
		}

		public ButtonOptionsBuilder symbolStroke(Color color) {
			this.symbolStroke = color;
			return this;
		}

		public ButtonOptionsBuilder symbolStrokeWidth(Number symbolStrokeWidth) {
			this.symbolStrokeWidth = symbolStrokeWidth;
			return this;
		}

		public ButtonOptionsBuilder symbolX(Number symbolX) {
			this.symbolX = symbolX;
			return this;
		}

		public ButtonOptionsBuilder symbolY(Number symbolY) {
			this.symbolY = symbolY;
			return this;
		}

		public ButtonOptionsBuilder text(String text) {
			this.text = text;
			return this;
		}

		public ButtonOptionsBuilder theme(ThemeMap theme) {
			this.theme = theme;
			return this;
		}

		public ButtonOptionsBuilder verticalAlign(VerticalAlign verticalAlign) {
			this.verticalAlign = verticalAlign.getName();
			return this;
		}

		public ButtonOptionsBuilder width(Number width) {
			this.width = width;
			return this;
		}

		public ButtonOptionsBuilder y(Number y) {
			this.y = y;
			return this;
		}

		@Override
		public ButtonOptions build() {
			return new ButtonOptions(this);
		}

	}

	public ButtonOptions getButtonOptions() {
		return buttonOptions;
	}

	public Style getMenuItemHoverStyle() {
		return menuItemHoverStyle;
	}

	public Style getMenuItemStyle() {
		return menuItemStyle;
	}

	public Style getMenuStyle() {
		return menuStyle;
	}

}
