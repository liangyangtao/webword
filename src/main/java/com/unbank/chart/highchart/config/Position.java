package com.unbank.chart.highchart.config;

import com.unbank.chart.highchart.type.Align;
import com.unbank.chart.highchart.type.VerticalAlign;
import com.unbank.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class Position {

	private final String align;
	private final String verticalAlign;
	private final Number x;
	private final Number y;

	private Position(PositionBuilder builder) {
		this.align = builder.align;
		this.verticalAlign = builder.verticalAlign;
		this.x = builder.x;
		this.y = builder.y;
	}

	public static class PositionBuilder implements Builder<Position> {

		private String align;
		private String verticalAlign;
		private Number x;
		private Number y;

		public PositionBuilder align(Align align) {
			this.align = align.getName();
			return this;
		}

		public PositionBuilder verticalAlign(VerticalAlign verticalAlign) {
			this.verticalAlign = verticalAlign.getName();
			return this;
		}

		public PositionBuilder x(Number x) {
			this.x = x;
			return this;
		}

		public PositionBuilder y(Number y) {
			this.y = y;
			return this;
		}

		@Override
		public Position build() {
			return new Position(this);
		}

	}

	public String getAlign() {
		return align;
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
