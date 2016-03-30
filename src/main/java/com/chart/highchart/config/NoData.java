package com.chart.highchart.config;

import com.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class NoData {

	private final Attr attr;
	private final Position position;
	private final Style style;

	private NoData(NoDataBuilder builder) {
		this.attr = builder.attr;
		this.position = builder.position;
		this.style = builder.style;
	}

	public static class NoDataBuilder implements Builder<NoData> {

		private Attr attr;
		private Position position;
		private Style style;

		public NoDataBuilder attr(Attr attr) {
			this.attr = attr;
			return this;
		}

		public NoDataBuilder position(Position position) {
			this.position = position;
			return this;
		}

		public NoDataBuilder style(Style style) {
			this.style = style;
			return this;
		}

		@Override
		public NoData build() {
			return new NoData(this);
		}
	}

	public Attr getAttr() {
		return attr;
	}

	public Position getPosition() {
		return position;
	}

	public Style getStyle() {
		return style;
	}

}
