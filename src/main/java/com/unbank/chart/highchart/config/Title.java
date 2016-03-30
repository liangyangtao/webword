package com.unbank.chart.highchart.config;

import com.unbank.chart.highchart.type.Align;
import com.unbank.chart.highchart.type.VerticalAlign;
import com.unbank.chart.util.Builder;

/**
 * title, subtitle, legend title
 * 
 * @author zile
 * 
 */
public class Title {

	private final String align; // Align
	private final Boolean floating;
	private final Number margin;
	private final Style style;
	private final String text;
	private final Boolean useHTML;
	private final String verticalAlign; // VerticalAlign
	private final Number x;
	private final Number y;

	protected Title(TitleBuilder builder) {
		this.align = builder.align;
		this.floating = builder.floating;
		this.margin = builder.margin;
		this.style = builder.style;
		this.text = builder.text;
		this.useHTML = builder.useHTML;
		this.verticalAlign = builder.verticalAlign;
		this.x = builder.x;
		this.y = builder.y;
	}

	protected Title(SubtitleBuilder builder) {
		this.align = builder.align;
		this.floating = builder.floating;
		this.style = builder.style;
		this.text = builder.text;
		this.useHTML = builder.useHTML;
		this.verticalAlign = builder.verticalAlign;
		this.x = builder.x;
		this.y = builder.y;
		this.margin = null;
	}

	protected Title(LegendTitleBuilder builder) {
		this.style = builder.style;
		this.text = builder.text;
		this.align = null;
		this.floating = null;
		this.margin = null;
		this.useHTML = null;
		this.verticalAlign = null;
		this.x = null;
		this.y = null;
	}

	/**
	 * for chart title
	 * 
	 * @author zile
	 * 
	 */
	public static class TitleBuilder extends SubtitleBuilder {

		private Number margin;

		public TitleBuilder margin(Number margin) {
			this.margin = margin;
			return this;
		}

	}

	public static class SubtitleBuilder extends LegendTitleBuilder {

		protected String align; // Align
		protected Boolean floating;
		protected Boolean useHTML;
		protected String verticalAlign; // VerticalAlign
		protected Number x;
		protected Number y;

		public SubtitleBuilder align(Align align) {
			this.align = align.getName();
			return this;
		}

		public SubtitleBuilder floating(Boolean floating) {
			this.floating = floating;
			return this;
		}

		public SubtitleBuilder useHTML(Boolean useHTML) {
			this.useHTML = useHTML;
			return this;
		}

		public SubtitleBuilder verticalAlign(VerticalAlign verticalAlign) {
			this.verticalAlign = verticalAlign.getName();
			return this;
		}

		public SubtitleBuilder x(Number x) {
			this.x = x;
			return this;
		}

		public SubtitleBuilder y(Number y) {
			this.y = y;
			return this;
		}

	}

	/**
	 * for chart legend title
	 * 
	 * @author zile
	 * 
	 */
	public static class LegendTitleBuilder implements Builder<Title> {

		protected Style style;
		protected String text;

		public LegendTitleBuilder style(Style style) {
			this.style = style;
			return this;
		}

		public LegendTitleBuilder text(String text) {
			this.text = text;
			return this;
		}

		@Override
		public Title build() {
			return new Title(this);
		}

	}

	public String getAlign() {
		return align;
	}

	public Boolean getFloating() {
		return floating;
	}

	public Number getMargin() {
		return margin;
	}

	public Style getStyle() {
		return style;
	}

	public String getText() {
		return text;
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
