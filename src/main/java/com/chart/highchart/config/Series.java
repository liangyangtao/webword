package com.chart.highchart.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.chart.highchart.config.PlotOptions.Shape;
import com.chart.highchart.type.PlotOptionsType;
import com.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public final class Series extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1600505805482214740L;

	public static class Data {

		private final Object color;
		private final DataLabels dataLabels;
		private final String drilldown;
		// private final Event events;
		private final String id;
		private final Boolean isIntermediateSum;
		private final Boolean isSum;
		private final Number legendIndex;
		// private final Object marker;
		private final String name;
		private final Boolean sliced;
		private final Number x;
		private final Number y;

		private Data(DataBuilder builder) {
			this.color = builder.color;
			this.dataLabels = builder.dataLabels;
			this.drilldown = builder.drilldown;
			this.id = builder.id;
			this.isIntermediateSum = builder.isIntermediateSum;
			this.isSum = builder.isSum;
			this.legendIndex = builder.legendIndex;
			this.name = builder.name;
			this.sliced = builder.sliced;
			this.x = builder.x;
			this.y = builder.y;
		}

		public Object getColor() {
			return color;
		}

		public DataLabels getDataLabels() {
			return dataLabels;
		}

		public String getDrilldown() {
			return drilldown;
		}

		public String getId() {
			return id;
		}

		public Boolean getIsIntermediateSum() {
			return isIntermediateSum;
		}

		public Boolean getIsSum() {
			return isSum;
		}

		public Number getLegendIndex() {
			return legendIndex;
		}

		public String getName() {
			return name;
		}

		public Boolean getSliced() {
			return sliced;
		}

		public Number getX() {
			return x;
		}

		public Number getY() {
			return y;
		}

	}

	public static class DataBuilder implements Builder<Data> {

		private Object color;
		private DataLabels dataLabels;
		private String drilldown;
		// private Event events;
		private String id;
		private Boolean isIntermediateSum;
		private Boolean isSum;
		private Number legendIndex;
		// private Object marker;
		private String name;
		private Boolean sliced;
		private Number x;
		private Number y;

		public DataBuilder() {
		}

		public DataBuilder(String name, Number y) {
			this.name = name;
			this.y = y;
		}

		//

		public DataBuilder color(String color) {
			this.color = color;
			return this;
		}

		public DataBuilder color(Color color) {
			this.color = color;
			return this;
		}

		public DataBuilder dataLabels(DataLabels dataLabels) {
			this.dataLabels = dataLabels;
			return this;
		}

		/**
		 * <p>
		 * The id of a series in the drilldown.series array to use for a
		 * drilldown for this point.
		 * </p>
		 * 
		 * @param drilldown
		 * @return
		 */
		public DataBuilder drilldown(String drilldown) {
			this.drilldown = drilldown;
			return this;
		}

		public DataBuilder id(String id) {
			this.id = id;
			return this;
		}

		public DataBuilder isIntermediateSum(Boolean isIntermediateSum) {
			this.isIntermediateSum = isIntermediateSum;
			return this;
		}

		public DataBuilder isSum(Boolean isSum) {
			this.isSum = isSum;
			return this;
		}

		public DataBuilder legendIndex(Number legendIndex) {
			this.legendIndex = legendIndex;
			return this;
		}

		public DataBuilder name(String name) {
			this.name = name;
			return this;
		}

		public DataBuilder sliced(Boolean sliced) {
			this.sliced = sliced;
			return this;
		}

		public DataBuilder x(Number x) {
			this.x = x;
			return this;
		}

		public DataBuilder y(Number y) {
			this.y = y;
			return this;
		}

		@Override
		public Data build() {
			return new Data(this);
		}

	}

	/**
	 * 数据列的特殊性, 要基于HashMap实现, 不能基于对象属性封装的方式实现
	 * 
	 * @author zile
	 * 
	 */
	public static class SeriesBuilder implements Builder<Series> {

		private List<Object> data; // Object|Array|Number
		// private Function dataParser;
		private String dataURL; // Deprecated
		private String id;
		private Number index;
		private Number legendIndex;
		private String name;
		private Object stack; // Number|String
		private String type; // PlotOptions
		private Object xAxis; // Number|String
		private Object yAxis; // Number|String
		private Number zIndex;

		// plotOptions attribute
		private Shape shape;

		public SeriesBuilder() {
		}

		public SeriesBuilder(PlotOptionsType type) {
			this.type = type.getName();
			this.shape = type.newInstance();
		}

		public SeriesBuilder(Number... numbers) {
			data(numbers);
		}

		public SeriesBuilder(List<List<Number>> list) {
			data(list);
		}

		public SeriesBuilder(Data... datas) {
			data(datas);
		}

		public SeriesBuilder(String name, Number... numbers) {
			this.name = name;
			data(numbers);
		}

		public SeriesBuilder(String name, List<List<Number>> list) {
			this.name = name;
			data(list);
		}

		public SeriesBuilder(String name, Data... datas) {
			this.name = name;
			data(datas);
		}

		/**
		 * 数字
		 * 
		 * @param numbers
		 * @return
		 */
		public SeriesBuilder data(Number... numbers) {
			this.data = new ArrayList<Object>();
			for (Number e : numbers)
				this.data.add(e);
			return this;
		}

		/**
		 * 数组
		 * 
		 * @param list
		 * @return
		 */
		public SeriesBuilder data(List<List<Number>> list) {
			this.data = new ArrayList<Object>();
			for (List<Number> e : list)
				this.data.add(e);
			return this;
		}

		/**
		 * 对象
		 * 
		 * @param datas
		 * @return
		 */
		public SeriesBuilder data(Data... datas) {
			this.data = new ArrayList<Object>();
			for (Data e : datas)
				this.data.add(e);
			return this;
		}

		public SeriesBuilder dataURL(String dataURL) {
			this.dataURL = dataURL;
			return this;
		}

		public SeriesBuilder id(String id) {
			this.id = id;
			return this;
		}

		public SeriesBuilder index(Number index) {
			this.index = index;
			return this;
		}

		public SeriesBuilder legendIndex(Number legendIndex) {
			this.legendIndex = legendIndex;
			return this;
		}

		public SeriesBuilder name(String name) {
			this.name = name;
			return this;
		}

		public SeriesBuilder stack(Number stack) {
			this.stack = stack;
			return this;
		}

		public SeriesBuilder stack(String stack) {
			this.stack = stack;
			return this;
		}

		public SeriesBuilder type(PlotOptionsType type) {
			this.type = type.getName();
			this.shape = type.newInstance();
			return this;
		}

		public SeriesBuilder xAxis(Number xAxis) {
			this.xAxis = xAxis;
			return this;
		}

		public SeriesBuilder xAxis(String xAxis) {
			this.xAxis = xAxis;
			return this;
		}

		public SeriesBuilder yAxis(Number yAxis) {
			this.yAxis = yAxis;
			return this;
		}

		public SeriesBuilder yAxis(String yAxis) {
			this.yAxis = yAxis;
			return this;
		}

		public SeriesBuilder zIndex(Number zIndex) {
			this.zIndex = zIndex;
			return this;
		}

		/**
		 * <p>
		 * 设置PlotOptions参数, 如何设置参数请参考官方API
		 * http://api.highcharts.com/highcharts#plotOptions
		 * </p>
		 * 
		 * <p>
		 * PlotOptions parameters, call after type method, need fix.
		 * </p>
		 * 
		 * @param key
		 * @param value
		 * @return
		 */
		public SeriesBuilder put(String key, Object value) {
			// FIXME
			if (shape == null)
				this.shape = new PlotOptions.DefaultShape();
			this.shape.put(key, value);
			return this;
		}

		@Override
		public Series build() {
			return new Series(this);
		}

	}

	// private final List<Object> data;
	// private Function dataParser;
	// private final String dataURL; // Deprecated
	// private final String id;
	// private final Number index;
	// private final Number legendIndex;
	// private final String name;
	// private final Object stack; // Number|String
	// private final String type; // PlotOptions
	// private final Object xAxis; // Number|String
	// private final Object yAxis; // Number|String
	// private final Number zIndex;

	private Series(SeriesBuilder builder) {
		// this.data = builder.data;
		// this.dataURL = builder.dataURL;
		// this.id = builder.id;
		// this.index = builder.index;
		// this.legendIndex = builder.legendIndex;
		// this.name = builder.name;
		// this.stack = builder.stack;
		// this.type = builder.type;
		// this.xAxis = builder.xAxis;
		// this.yAxis = builder.yAxis;
		// this.zIndex = builder.zIndex;

		if (builder.shape != null) {
			putAll(builder.shape);
		}

		put("data", builder.data);
		put("dataURL", builder.dataURL);
		put("id", builder.id);
		put("index", builder.index);
		put("legendIndex", builder.legendIndex);
		put("name", builder.name);
		put("stack", builder.stack);
		put("type", builder.type);
		put("xAxis", builder.xAxis);
		put("yAxis", builder.yAxis);
		put("zIndex", builder.zIndex);

	}

}
