package com.chart.highchart.config;

import java.util.ArrayList;
import java.util.List;

import com.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class Color {

	// FIXME array|object { x1: 0, y1: 0,x2: 0, y2: 1 }
	private final List<Number> linearGradient;
	private final List<List<Object>> stops;

	private Color(ColorBuilder builder) {
		this.linearGradient = builder.linearGradient;
		this.stops = builder.stops;
	}

	public static class ColorBuilder implements Builder<Color> {

		private List<Number> linearGradient;
		private List<List<Object>> stops;

		public ColorBuilder linearGradient(Number a, Number b, Number c,
				Number d) {
			linearGradient = new ArrayList<Number>();
			linearGradient.add(a);
			linearGradient.add(b);
			linearGradient.add(c);
			linearGradient.add(d);
			return this;
		}

		/**
		 * rgb or hex color
		 * 
		 * @param n
		 * @param color
		 * @return
		 */
		public ColorBuilder stops(Number n, String color) {
			if (stops == null) {
				stops = new ArrayList<List<Object>>();
			}
			List<Object> list = new ArrayList<Object>();
			list.add(n);
			list.add(color);
			stops.add(list);
			return this;
		}

		@Override
		public Color build() {
			return new Color(this);
		}
	}

	public List<Number> getLinearGradient() {
		return linearGradient;
	}

	public List<List<Object>> getStops() {
		return stops;
	}

}
