package com.unbank.chart.highchart.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.unbank.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class HighchartsConfig extends AbstractChartConfig {

	private final Highchart chart;
	private final List<String> colors;
	private final Credits credits;
	private final Drilldown drilldown;
	private final Exporting exporting;
	private final Labels labels;
	private final Legend legend;
	private final Loading loading;
	private final Navigation navigation;
	private final NoData noData;
	private final Pane pane;
	private final PlotOptions plotOptions;
	private final List<Series> series;
	private final Title subtitle;
	private final Title title;
	private final Tooltip tooltip;
	private final Object xAxis;
	private final Object yAxis;

	protected HighchartsConfig(HighchartsConfigBuilder builder) {
		this.chart = builder.chart;
		this.colors = builder.colors;
		this.credits = builder.credits;
		this.drilldown = builder.drilldown;
		this.exporting = builder.exporting;
		this.labels = builder.labels;
		this.legend = builder.legend;
		this.loading = builder.loading;
		this.navigation = builder.navigation;
		this.noData = builder.noData;
		this.pane = builder.pane;
		this.plotOptions = builder.plotOptions;
		this.series = builder.series;
		this.subtitle = builder.subtitle;
		this.title = builder.title;
		this.tooltip = builder.tooltip;
		this.xAxis = builder.xAxis;
		this.yAxis = builder.yAxis;
	}

	public static class HighchartsConfigBuilder implements
			Builder<HighchartsConfig> {

		private Highchart chart;
		private List<String> colors;
		private Credits credits;
		private Drilldown drilldown;
		private Exporting exporting;
		private Labels labels;
		private Legend legend;
		private Loading loading;
		private Navigation navigation;
		private NoData noData;
		private Pane pane;
		private PlotOptions plotOptions;
		private List<Series> series;
		private Title subtitle;
		private Title title;
		private Tooltip tooltip;
		private Object xAxis;
		private Object yAxis;

		/**
		 * Options regarding the chart area and plot area as well as general
		 * chart options.
		 */
		public HighchartsConfigBuilder chart(Highchart chart) {
			this.chart = chart;
			return this;
		}

		/**
		 * Highchart by default puts a credits label in the lower right corner
		 * of the chart. This can be changed using these options.
		 */
		public HighchartsConfigBuilder colors(String... colors) {
			this.colors = new ArrayList<String>();
			for (String color : colors)
				this.colors.add(color);
			return this;
		}

		/**
		 * Highchart by default puts a credits label in the lower right corner
		 * of the chart. This can be changed using these options.
		 */
		public HighchartsConfigBuilder credits(Credits credits) {
			this.credits = credits;
			return this;
		}

		/**
		 * Options for drill down, the concept of inspecting increasingly high
		 * resolution data through clicking on chart items like columns or pie
		 * slices.
		 * 
		 * @param drilldown
		 * @return
		 */
		public HighchartsConfigBuilder drilldown(Drilldown drilldown) {
			this.drilldown = drilldown;
			return this;
		}

		/**
		 * Options for the exporting module.
		 * 
		 * @param exporting
		 * @return
		 */
		public HighchartsConfigBuilder exporting(Exporting exporting) {
			this.exporting = exporting;
			return this;
		}

		/**
		 * HTML labels that can be positioned anywhere in the chart area.
		 * 
		 * @param labels
		 * @return
		 */
		public HighchartsConfigBuilder labels(Labels labels) {
			this.labels = labels;
			return this;
		}

		/**
		 * The legend is a box containing a symbol and name for each series item
		 * or point item in the chart.
		 * 
		 * @param legend
		 * @return
		 */
		public HighchartsConfigBuilder legend(Legend legend) {
			this.legend = legend;
			return this;
		}

		/**
		 * The loading options control the appearance of the loading screen that
		 * covers the plot area on chart operations.
		 * 
		 * @param loading
		 * @return
		 */
		public HighchartsConfigBuilder loading(Loading loading) {
			this.loading = loading;
			return this;
		}

		/**
		 * A collection of options for buttons and menus appearing in the
		 * exporting module.
		 * 
		 * @param navigation
		 * @return
		 */
		public HighchartsConfigBuilder navigation(Navigation navigation) {
			this.navigation = navigation;
			return this;
		}

		/**
		 * Options for displaying a message like "No data to display". This
		 * feature requires the file no-data-to-display.js to be loaded in the
		 * page. The actual text to display is set in the lang.noData option.
		 * 
		 * @param noData
		 * @return
		 */
		public HighchartsConfigBuilder noData(NoData noData) {
			this.noData = noData;
			return this;
		}

		/**
		 * Applies only to polar charts and angular gauges. This configuration
		 * object holds general options for the combined X and Y axes set. Each
		 * xAxis or yAxis can reference the pane by index.
		 * 
		 * @param pane
		 * @return
		 */
		public HighchartsConfigBuilder pane(Pane pane) {
			this.pane = pane;
			return this;
		}

		/**
		 * The plotOptions is a wrapper object for config objects for each
		 * series type. The config objects for each series can also be
		 * overridden for each series item as given in the series array.
		 * 
		 * @param plotOptions
		 * @return
		 */
		public HighchartsConfigBuilder plotOptions(PlotOptions plotOptions) {
			this.plotOptions = plotOptions;
			return this;
		}

		/**
		 * The actual series to append to the chart. In addition to the members
		 * listed below, any member of the plotOptions for that specific type of
		 * plot can be added to a series individually. For example, even though
		 * a general lineWidth is specified in plotOptions.series, an individual
		 * lineWidth can be specified for each series.
		 * 
		 * @param series
		 * @return
		 */
		public HighchartsConfigBuilder series(Series... series) {
			series(Arrays.asList(series));
			return this;
		}

		public HighchartsConfigBuilder series(List<Series> series) {
			this.series = series;
			return this;
		}

		/**
		 * The chart's subtitle
		 * 
		 * @param subtitle
		 * @return
		 */
		public HighchartsConfigBuilder subtitle(Title subtitle) {
			this.subtitle = subtitle;
			return this;
		}

		/**
		 * The chart's main title.
		 * 
		 * @param title
		 * @return
		 */
		public HighchartsConfigBuilder title(Title title) {
			this.title = title;
			return this;
		}

		/**
		 * Options for the tooltip that appears when the user hovers over a
		 * series or point.
		 * 
		 * @param tooltip
		 * @return
		 */
		public HighchartsConfigBuilder tooltip(Tooltip tooltip) {
			this.tooltip = tooltip;
			return this;
		}

		/**
		 * The X axis or category axis. Normally this is the horizontal axis,
		 * though if the chart is inverted this is the vertical axis. In case of
		 * multiple axes, the xAxis node is an array of configuration objects.
		 * 
		 * @param xAxis
		 * @return
		 */
		public HighchartsConfigBuilder xAxis(XAxis xAxis) {
			this.xAxis = xAxis;
			return this;
		}

		public HighchartsConfigBuilder xAxis(List<XAxis> xAxis) {
			this.xAxis = xAxis;
			return this;
		}

		/**
		 * The Y axis or value axis. Normally this is the vertical axis, though
		 * if the chart is inverted this is the horizontal axis. In case of
		 * multiple axes, the yAxis node is an array of configuration objects.
		 * 
		 * @param yAxis
		 * @return
		 */
		public HighchartsConfigBuilder yAxis(YAxis yAxis) {
			this.yAxis = yAxis;
			return this;
		}

		public HighchartsConfigBuilder yAxis(List<YAxis> yAxis) {
			this.yAxis = yAxis;
			return this;
		}

		@Override
		public HighchartsConfig build() {
			return new HighchartsConfig(this);
		}

	}

	public Highchart getChart() {
		return chart;
	}

	public List<String> getColors() {
		return colors;
	}

	public Credits getCredits() {
		return credits;
	}

	public Drilldown getDrilldown() {
		return drilldown;
	}

	public Exporting getExporting() {
		return exporting;
	}

	public Labels getLabels() {
		return labels;
	}

	public Legend getLegend() {
		return legend;
	}

	public Loading getLoading() {
		return loading;
	}

	public Navigation getNavigation() {
		return navigation;
	}

	public NoData getNoData() {
		return noData;
	}

	public Pane getPane() {
		return pane;
	}

	public PlotOptions getPlotOptions() {
		return plotOptions;
	}

	public List<Series> getSeries() {
		return series;
	}

	public Title getSubtitle() {
		return subtitle;
	}

	public Title getTitle() {
		return title;
	}

	public Tooltip getTooltip() {
		return tooltip;
	}

	public Object getxAxis() {
		return xAxis;
	}

	public Object getyAxis() {
		return yAxis;
	}

}
