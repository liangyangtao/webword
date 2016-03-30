package com.unbank.chart.highchart.config;

import java.util.HashMap;

import com.unbank.chart.util.Builder;

/**
 * 图形, 先使用HashMap方式，暂时不封装具体属性。如果使用HashMap方式，就不需要定制如此多的类型了，以下方式是为了今后细化扩展用。
 * 
 * @author zile
 * 
 */
public class PlotOptions {

	/**
	 * 
	 * @author zile
	 * 
	 */
	public static abstract class Shape extends HashMap<String, Object> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6397568442697608151L;

	}

	public static class Area extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = -417888072956662622L;
	}

	public static class Arearange extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5911459867339111766L;
	}

	public static class Areaspline extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5511786313303341470L;
	}

	public static class Areasplinerange extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1577677721878477426L;
	}

	public static class Bar extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = -402477073248780676L;
	}

	public static class Boxplot extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = -290083863547293174L;
	}

	public static class Bubble extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2757654496603224314L;
	}

	public static class Column extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2462903578981089039L;
	}

	public static class Columnrange extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3562561273808569252L;
	}

	public static class Errorbar extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2220847695324145002L;
	}

	public static class Funnel extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7158374321698277384L;
	}

	public static class Gauge extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2223935298870685602L;
	}

	public static class Heatmap extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1474984555622227006L;
	}

	/**
	 * 曲线图
	 * 
	 * @author zile
	 * 
	 */
	public static class Line extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = -9062670990784507658L;
		// private Boolean allowPointSelect;
		// private Object animation; // Boolean|Animation
		// private Object color; // String | Color
		// private Boolean connectEnds;
		// private Boolean connectNulls;
		// private Number cropThreshold;
		// private String cursor;
		// private String dashStyle; // DashStyles
	}

	public static class Pie extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8220181140598763322L;
	}

	public static class Pyramid extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2951306367208707718L;
	}

	public static class Scatter extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3144104067505279705L;
	}

	public static class Series extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5127207928500793733L;
	}

	public static class Solidgauge extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7681137770339275164L;
	}

	public static class Spline extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5981084661604461954L;
	}

	public static class Waterfall extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5731712922119486460L;
	}

	/**
	 * 缺省类型
	 * 
	 * @author zile
	 * 
	 */
	public static class DefaultShape extends Shape {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3685384839874601771L;
	}

	/*
	 * 
	 */

	public static class PlotOptionsBuilder implements Builder<PlotOptions> {

		private Area area;
		private Arearange arearange;
		private Areaspline areaspline;
		private Areasplinerange areasplinerange;
		private Bar bar;
		private Boxplot boxplot;
		private Bubble bubble;
		private Column column;
		private Columnrange columnrange;
		private Errorbar errorbar;
		private Funnel funnel;
		private Gauge gauge;
		private Heatmap heatmap;
		private Line line;
		private Pie pie;
		private Pyramid pyramid;
		private Scatter scatter;
		private Series series;
		private Solidgauge solidgauge;
		private Spline spline;
		private Waterfall waterfall;

		public PlotOptionsBuilder area(Area area) {
			this.area = area;
			return this;
		}

		public PlotOptionsBuilder arearange(Arearange arearange) {
			this.arearange = arearange;
			return this;
		}

		public PlotOptionsBuilder areaspline(Areaspline areaspline) {
			this.areaspline = areaspline;
			return this;
		}

		public PlotOptionsBuilder areasplinerange(
				Areasplinerange areasplinerange) {
			this.areasplinerange = areasplinerange;
			return this;
		}

		public PlotOptionsBuilder bar(Bar bar) {
			this.bar = bar;
			return this;
		}

		public PlotOptionsBuilder boxplot(Boxplot boxplot) {
			this.boxplot = boxplot;
			return this;
		}

		//

		public PlotOptionsBuilder bubble(Bubble bubble) {
			this.bubble = bubble;
			return this;
		}

		public PlotOptionsBuilder column(Column column) {
			this.column = column;
			return this;
		}

		public PlotOptionsBuilder columnrange(Columnrange columnrange) {
			this.columnrange = columnrange;
			return this;
		}

		public PlotOptionsBuilder errorbar(Errorbar errorbar) {
			this.errorbar = errorbar;
			return this;
		}

		public PlotOptionsBuilder funnel(Funnel funnel) {
			this.funnel = funnel;
			return this;
		}

		//

		public PlotOptionsBuilder gauge(Gauge gauge) {
			this.gauge = gauge;
			return this;
		}

		public PlotOptionsBuilder heatmap(Heatmap heatmap) {
			this.heatmap = heatmap;
			return this;
		}

		public PlotOptionsBuilder line(Line line) {
			this.line = line;
			return this;
		}

		public PlotOptionsBuilder pie(Pie pie) {
			this.pie = pie;
			return this;
		}

		public PlotOptionsBuilder pyramid(Pyramid pyramid) {
			this.pyramid = pyramid;
			return this;
		}

		//

		public PlotOptionsBuilder scatter(Scatter scatter) {
			this.scatter = scatter;
			return this;
		}

		public PlotOptionsBuilder series(Series series) {
			this.series = series;
			return this;
		}

		public PlotOptionsBuilder solidgauge(Solidgauge solidgauge) {
			this.solidgauge = solidgauge;
			return this;
		}

		public PlotOptionsBuilder spline(Spline spline) {
			this.spline = spline;
			return this;
		}

		public PlotOptionsBuilder waterfall(Waterfall waterfall) {
			this.waterfall = waterfall;
			return this;
		}

		@Override
		public PlotOptions build() {
			return new PlotOptions(this);
		}

	}

	private final Area area;
	private final Arearange arearange;
	private final Areaspline areaspline;
	private final Areasplinerange areasplinerange;
	private final Bar bar;
	private final Boxplot boxplot;
	private final Bubble bubble;
	private final Column column;
	private final Columnrange columnrange;
	private final Errorbar errorbar;
	private final Funnel funnel;
	private final Gauge gauge;
	private final Heatmap heatmap;
	private final Line line;
	private final Pie pie;
	private final Pyramid pyramid;
	private final Scatter scatter;
	private final Series series;
	private final Solidgauge solidgauge;
	private final Spline spline;
	private final Waterfall waterfall;

	private PlotOptions(PlotOptionsBuilder builder) {
		this.area = builder.area;
		this.arearange = builder.arearange;
		this.areaspline = builder.areaspline;
		this.areasplinerange = builder.areasplinerange;
		this.bar = builder.bar;
		this.boxplot = builder.boxplot;
		this.bubble = builder.bubble;
		this.column = builder.column;
		this.columnrange = builder.columnrange;
		this.errorbar = builder.errorbar;
		this.funnel = builder.funnel;
		this.gauge = builder.gauge;
		this.heatmap = builder.heatmap;
		this.line = builder.line;
		this.pie = builder.pie;
		this.pyramid = builder.pyramid;
		this.scatter = builder.scatter;
		this.series = builder.series;
		this.solidgauge = builder.solidgauge;
		this.spline = builder.spline;
		this.waterfall = builder.waterfall;
	}

	public Area getArea() {
		return area;
	}

	public Arearange getArearange() {
		return arearange;
	}

	public Areaspline getAreaspline() {
		return areaspline;
	}

	public Areasplinerange getAreasplinerange() {
		return areasplinerange;
	}

	public Bar getBar() {
		return bar;
	}

	public Boxplot getBoxplot() {
		return boxplot;
	}

	public Bubble getBubble() {
		return bubble;
	}

	public Column getColumn() {
		return column;
	}

	public Columnrange getColumnrange() {
		return columnrange;
	}

	public Errorbar getErrorbar() {
		return errorbar;
	}

	public Funnel getFunnel() {
		return funnel;
	}

	public Gauge getGauge() {
		return gauge;
	}

	public Heatmap getHeatmap() {
		return heatmap;
	}

	public Line getLine() {
		return line;
	}

	public Pie getPie() {
		return pie;
	}

	public Pyramid getPyramid() {
		return pyramid;
	}

	public Scatter getScatter() {
		return scatter;
	}

	public Series getSeries() {
		return series;
	}

	public Solidgauge getSolidgauge() {
		return solidgauge;
	}

	public Spline getSpline() {
		return spline;
	}

	public Waterfall getWaterfall() {
		return waterfall;
	}

}
