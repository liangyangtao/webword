package com.chart.highchart;

import java.io.File;
import java.util.List;

import com.chart.AbstractChart;
import com.chart.highchart.config.Credits;
import com.chart.highchart.config.Credits.CreditsBuilder;
import com.chart.highchart.config.Drilldown;
import com.chart.highchart.config.Drilldown.DrilldownBuilder;
import com.chart.highchart.config.Exporting;
import com.chart.highchart.config.Exporting.ExportingBuilder;
import com.chart.highchart.config.Global;
import com.chart.highchart.config.Global.GlobalBuilder;
import com.chart.highchart.config.Highchart;
import com.chart.highchart.config.Highchart.HighchartBuilder;
import com.chart.highchart.config.HighchartsConfig;
import com.chart.highchart.config.HighchartsConfig.HighchartsConfigBuilder;
import com.chart.highchart.config.Labels;
import com.chart.highchart.config.Labels.LabelsBuilder;
import com.chart.highchart.config.Lang;
import com.chart.highchart.config.Lang.LangBuilder;
import com.chart.highchart.config.Legend;
import com.chart.highchart.config.Legend.LegendBuilder;
import com.chart.highchart.config.Loading;
import com.chart.highchart.config.Loading.LoadingBuilder;
import com.chart.highchart.config.Navigation;
import com.chart.highchart.config.Navigation.NavigationBuilder;
import com.chart.highchart.config.NoData;
import com.chart.highchart.config.NoData.NoDataBuilder;
import com.chart.highchart.config.OptionsConfig;
import com.chart.highchart.config.OptionsConfig.OptionsConfigBuilder;
import com.chart.highchart.config.Pane;
import com.chart.highchart.config.Pane.PaneBuilder;
import com.chart.highchart.config.PlotOptions;
import com.chart.highchart.config.PlotOptions.PlotOptionsBuilder;
import com.chart.highchart.config.Series;
import com.chart.highchart.config.Title;
import com.chart.highchart.config.Title.SubtitleBuilder;
import com.chart.highchart.config.Title.TitleBuilder;
import com.chart.highchart.config.Tooltip;
import com.chart.highchart.config.Tooltip.TooltipBuilder;
import com.chart.highchart.config.XAxis;
import com.chart.highchart.config.XAxis.XAxisBuilder;
import com.chart.highchart.config.YAxis;
import com.chart.highchart.config.YAxis.YAxisBuilder;
import com.chart.highchart.def.DefaultConfig;
import com.chart.highchart.def.UnbankDefaultConfig;
import com.chart.highchart.theme.Theme;
import com.chart.highchart.type.PlotOptionsType;
import com.chart.highchart.type.ThemeType;

/**
 * 
 * @author zile
 * 
 */
public abstract class HighCharts extends
		AbstractChart<HighchartsConfig, HcResultEntity<HighchartsConfig>> {

	/**
	 * 主题
	 */
	private Theme theme = ThemeType.GRID.theme();
	private OptionsConfig optionsConfig;
	private HighchartsConfig highchartsConfig;
	private PlotOptionsType type;

	// default config
	private DefaultConfig defaultConfig = new UnbankDefaultConfig();

	protected OptionsConfigBuilder optionsConfigBuilder = new OptionsConfigBuilder();
	protected HighchartsConfigBuilder highchartsConfigBuilder = new HighchartsConfigBuilder();

	public HighCharts(PlotOptionsType type) {
		this.type = type;
	}

	@Override
	public HighchartsConfig chartConfigEntity() {
		return highchartsConfig;
	}

	/**
	 * 模板方法
	 */
	@Override
	protected void process() {
		theme();
		options();
		highchart();
	}

	@Override
	protected HcResultEntity<HighchartsConfig> exportForEntity() {
		return new HcResultEntity<HighchartsConfig>() {

			private final String filename;

			{
				long nanoTime = System.nanoTime();
				StringBuilder path = new StringBuilder();
				long v = nanoTime;
				long m;
				while (v > 100) {
					m = v % 100;
					v = v / 100;
					path.append(String.valueOf(m) + File.separatorChar);
				}
				path.append(nanoTime);
				filename = path.toString();
			}

			@Override
			public HighchartsConfig getEntity() {
				return highchartsConfig;
			}

			@Override
			public List<Throwable> getMessages() {
				return messages;
			}

			@Override
			public String getType() {
				if (type == null) {
					return PlotOptionsType.COMBINATIONS.getName();
				} else {
					return type.toString();
				}
			}

			@Override
			public long getMs() {
				return time;
			}

			@Override
			public Theme getTheme() {
				return theme;
			}

			@Override
			public OptionsConfig getOptions() {
				return optionsConfig;
			}

			@Override
			public String toString() {
				return highchartsConfig.jsonObject().toString();
			}

			@Override
			public String filename() {
				return filename;
			}
		};
	}

	/*
	 * 主题
	 */

	/**
	 * Highcharts 主题
	 * 
	 * @return
	 */
	protected abstract ThemeType getThemeType();

	/*
	 * 配置生命周期阶段
	 */

	/**
	 * <p>
	 * Global options that don't apply to each chart. These options, like the
	 * lang options, must be set using the Highcharts.setOptions method.
	 * </p>
	 * 
	 * @param globalBuilder
	 * @return
	 */
	protected Global global(GlobalBuilder globalBuilder) {
		return defaultConfig.global(globalBuilder) ? globalBuilder.build()
				: null;
	}

	/**
	 * <p>
	 * Language object. The language object is global and it can't be set on
	 * each chart initiation. Instead, use Highcharts.setOptions to set it
	 * before any chart is initiated.
	 * </p>
	 * 
	 * @param langBuilder
	 * @return
	 */
	protected Lang lang(LangBuilder langBuilder) {
		return defaultConfig.lang(langBuilder) ? langBuilder.build() : null;
	}

	/*
	 * 图形生命周期阶段
	 */

	/**
	 * <p>
	 * 图表配置
	 * </p>
	 * 
	 * <p>
	 * Options regarding the chart area and plot area as well as general chart
	 * options.
	 * </p>
	 * 
	 * @param highchartBuilder
	 * @return
	 */
	protected Highchart chart(HighchartBuilder highchartBuilder) {
		return null;
	}

	/**
	 * <p>
	 * 颜色设置
	 * </p>
	 * 
	 * <p>
	 * An array containing the default colors for the chart's series. When all
	 * colors are used, new colors are pulled from the start again.
	 * </p>
	 * 
	 * @return
	 */
	protected String[] colors() {
		return defaultConfig.colors();
	}

	/**
	 * 
	 * <p>
	 * 图表版权信息。显示在图表右下方的包含链接的文字，默认是Highcharts官网地址。通过指定credits.enabled=
	 * false即可不显示该信息。
	 * </p>
	 * 
	 * <p>
	 * Highchart by default puts a credits label in the lower right corner of
	 * the chart. This can be changed using these options.
	 * </p>
	 * 
	 * @param creditsBuilder
	 * @return
	 */
	protected Credits credits(CreditsBuilder creditsBuilder) {
		return defaultConfig.credits(creditsBuilder) ? creditsBuilder.build()
				: null;
	}

	/**
	 * 
	 * <p>
	 * Options for drill down, the concept of inspecting increasingly high
	 * resolution data through clicking on chart items like columns or pie
	 * slices.
	 * </p>
	 * 
	 * @param drilldownBuilder
	 * @return
	 */
	protected Drilldown drilldown(DrilldownBuilder drilldownBuilder) {
		return null;
	}

	/**
	 * <p>
	 * 导出功能按钮。通过引入exporting.js即可增加图表导出为常见文件功能。
	 * </p>
	 * 
	 * <p>
	 * Options for the exporting module. For an overview on the matter, see the
	 * docs.
	 * </p>
	 * 
	 * @param exportingBuilder
	 * @return
	 */
	protected Exporting exporting(ExportingBuilder exportingBuilder) {
		return defaultConfig.exporting(exportingBuilder) ? exportingBuilder
				.build() : null;
	}

	/**
	 * <p>
	 * HTML标签
	 * </p>
	 * 
	 * <p>
	 * HTML labels that can be positioned anywhere in the chart area.
	 * </p>
	 * 
	 * @param labelsBuilder
	 * @return
	 */
	protected Labels labels(LabelsBuilder labelsBuilder) {
		return null;
	}

	/**
	 * <p>
	 * 图例。用不同形状、颜色、文字等 标示不同数据列，通过点击标示可以显示或隐藏该数据列。
	 * </p>
	 * 
	 * <p>
	 * The legend is a box containing a symbol and name for each series item or
	 * point item in the chart.
	 * </p>
	 * 
	 * @param legendBuilder
	 * @return
	 */
	protected Legend legend(LegendBuilder legendBuilder) {
		return null;
	}

	/**
	 * <p>
	 * The loading options control the appearance of the loading screen that
	 * covers the plot area on chart operations.
	 * </p>
	 * 
	 * @param loadingBuilder
	 * @return
	 */
	protected Loading loading(LoadingBuilder loadingBuilder) {
		return null;
	}

	/**
	 * <p>
	 * 导航栏设置
	 * </p>
	 * 
	 * <p>
	 * A collection of options for buttons and menus appearing in the exporting
	 * module.
	 * </p>
	 * 
	 * @param navigationBuilder
	 * @return
	 */
	protected Navigation navigation(NavigationBuilder navigationBuilder) {
		return null;
	}

	/**
	 * 
	 * <p>
	 * Options for displaying a message like "No data to display".
	 * </p>
	 * 
	 * @param noDataBuilder
	 * @return
	 */
	protected NoData noData(NoDataBuilder noDataBuilder) {
		return null;
	}

	/**
	 * <p>
	 * Applies only to polar charts and angular gauges. This configuration
	 * object holds general options for the combined X and Y axes set. Each
	 * xAxis or yAxis can reference the pane by index.
	 * </p>
	 * 
	 * @param paneBuilder
	 * @return
	 */
	protected Pane pane(PaneBuilder paneBuilder) {
		return null;
	}

	/**
	 * <p>
	 * 这里采用HashMap方式进行属性构建, 需要查看官方API进行属性设置 <a
	 * href='http://api.highcharts.com/highcharts#plotOptions'
	 * >http://api.highcharts.com/highcharts#plotOptions</a>
	 * </p>
	 * 
	 * <p>
	 * The plotOptions is a wrapper object for config objects for each series
	 * type. The config objects for each series can also be overridden for each
	 * series item as given in the series array.
	 * </p>
	 * 
	 * @param plotOptionsBuilder
	 * @return
	 */
	protected PlotOptions plotOptions(PlotOptionsBuilder plotOptionsBuilder) {
		return null;
	}

	/**
	 * <p>
	 * 数据列。图表上一个或多个数据系列，比如曲线图中的一条曲线，柱状图中的一个柱形。
	 * </p>
	 * 
	 * <p>
	 * The actual series to append to the chart.
	 * </p>
	 * 
	 * @return
	 */
	protected abstract List<Series> series();

	/**
	 * <p>
	 * 图表副标题，副标题是非必须的。
	 * </p>
	 * 
	 * <p>
	 * The chart's subtitle
	 * </p>
	 * 
	 * @param subtitleBuilder
	 * @return
	 */
	protected Title subtitle(SubtitleBuilder subtitleBuilder) {
		return defaultConfig.subtitle(subtitleBuilder) ? subtitleBuilder
				.build() : null;
	}

	/**
	 * <p>
	 * 图表标题，包含标题和副标题（subTitle）。
	 * </p>
	 * 
	 * <p>
	 * The chart's main title.
	 * </p>
	 * 
	 * @param titleBuilder
	 * @return
	 */
	protected abstract Title title(TitleBuilder titleBuilder);

	/**
	 * <p>
	 * 数据提示框。当鼠标悬停在某点上时，以框的形式提示改点的数据，比如该点的值，数据单位等。数据提示框内提示的信息完全可以通过格式化函数动态指定。
	 * </p>
	 * 
	 * <p>
	 * Options for the tooltip that appears when the user hovers over a series
	 * or point.
	 * </p>
	 * 
	 * @param tooltipBuilder
	 * @return
	 */
	protected Tooltip tooltip(TooltipBuilder tooltipBuilder) {
		return null;
	}

	/**
	 * <p>
	 * X坐标轴，通常情况下，x轴显示在图表的底部 坐标轴
	 * </p>
	 * 
	 * <p>
	 * The X axis or category axis. Normally this is the horizontal axis, though
	 * if the chart is inverted this is the vertical axis. In case of multiple
	 * axes, the xAxis node is an array of configuration objects.
	 * </p>
	 * 
	 * @param xAxisBuilder
	 * @return
	 */
	protected abstract XAxis xAxis(XAxisBuilder xAxisBuilder);

	/**
	 * <p>
	 * 处理列表类型的XAxis对象，具有更高的优先级，如果返回的不是null。混合图形会使用这个方法来替代xAxis(XAxisBuilder)方法
	 * <p>
	 * 
	 * @see #xAxis(XAxisBuilder)
	 * @return
	 */
	protected List<XAxis> xAxis() {
		return null;
	}

	/**
	 * <p>
	 * Y坐标轴，通常情况下，y轴显示在图表的左侧
	 * </p>
	 * 
	 * <p>
	 * The Y axis or value axis. Normally this is the vertical axis, though if
	 * the chart is inverted this is the horizontal axis. In case of multiple
	 * axes, the yAxis node is an array of configuration objects.
	 * </p>
	 * 
	 * @param yAxisBuilder
	 * @return
	 */
	protected abstract YAxis yAxis(YAxisBuilder yAxisBuilder);

	/**
	 * <p>
	 * 处理列表类型的YAxis对象，具有更高的优先级，如果返回的不是null。混合图形会使用这个方法来替代yAxis(YAxisBuilder)方法
	 * <p>
	 * 
	 * @see #yAxis(YAxisBuilder)
	 * @return
	 */
	protected List<YAxis> yAxis() {
		return null;
	}

	/**
	 * 主题样式
	 */
	private void theme() {
		ThemeType t = getThemeType();
		if (t != null) {
			theme = t.theme();
		}
	}

	/**
	 * 全局配置, 2个阶段
	 */
	private void options() {
		// 1 global
		GlobalBuilder globalBuilder = new GlobalBuilder();
		Global global = global(globalBuilder);
		if (global != null)
			optionsConfigBuilder.global(global);

		// 2 lang
		LangBuilder langBuilder = new LangBuilder();
		Lang lang = lang(langBuilder);
		if (lang != null)
			optionsConfigBuilder.lang(lang);

		// 构建全局配置
		optionsConfig = optionsConfigBuilder.build();
	}

	/**
	 * 图形配置核心方法, 18个阶段
	 */
	private void highchart() {

		// 1 chart
		HighchartBuilder chartBuilder = new HighchartBuilder(type);
		Highchart chart = chart(chartBuilder);
		if (chart != null) {
			highchartsConfigBuilder.chart(chart);
		} else {
			highchartsConfigBuilder.chart(chartBuilder.build());
		}

		// 2 colors
		String[] colors = colors();
		if (colors != null) {
			highchartsConfigBuilder.colors(colors);
		}

		// 3 credits
		CreditsBuilder creditsBuilder = new CreditsBuilder();
		Credits credits = credits(creditsBuilder);
		if (credits != null)
			highchartsConfigBuilder.credits(credits);

		// 4 drilldown
		DrilldownBuilder drilldownBuilder = new DrilldownBuilder();
		Drilldown drilldown = drilldown(drilldownBuilder);
		if (drilldown != null)
			highchartsConfigBuilder.drilldown(drilldown);

		// 5 exporting
		ExportingBuilder exportingBuilder = new ExportingBuilder();
		Exporting exporting = exporting(exportingBuilder);
		if (exporting != null)
			highchartsConfigBuilder.exporting(exporting);

		// 6 labels
		LabelsBuilder labelsBuilder = new LabelsBuilder();
		Labels labels = labels(labelsBuilder);
		if (labels != null)
			highchartsConfigBuilder.labels(labels);

		// 7 legend
		LegendBuilder legendBuilder = new LegendBuilder();
		Legend legend = legend(legendBuilder);
		if (legend != null)
			highchartsConfigBuilder.legend(legend);

		// 8 loading
		LoadingBuilder loadingBuilder = new LoadingBuilder();
		Loading loading = loading(loadingBuilder);
		if (loading != null)
			highchartsConfigBuilder.loading(loading);

		// 9 navigation
		NavigationBuilder navigationBuilder = new NavigationBuilder();
		Navigation navigation = navigation(navigationBuilder);
		if (navigation != null)
			highchartsConfigBuilder.navigation(navigation);

		// 10 noData
		NoDataBuilder noDataBuilder = new NoDataBuilder();
		NoData noData = noData(noDataBuilder);
		if (noData != null)
			highchartsConfigBuilder.noData(noData);

		// 11 pane
		PaneBuilder paneBuilder = new PaneBuilder();
		Pane pane = pane(paneBuilder);
		if (pane != null)
			highchartsConfigBuilder.pane(pane);

		// 12 plotOptions
		PlotOptionsBuilder plotOptionsBuilder = new PlotOptionsBuilder();
		PlotOptions plotOptions = plotOptions(plotOptionsBuilder);
		if (plotOptions != null)
			highchartsConfigBuilder.plotOptions(plotOptions);

		// 13 abstract series
		List<Series> series = series();
		if (series != null && series.size() != 0)
			highchartsConfigBuilder.series(series);

		// 14 abstract subtitle
		SubtitleBuilder subtitleBuilder = new SubtitleBuilder();
		Title subtitle = subtitle(subtitleBuilder);
		if (subtitle != null)
			highchartsConfigBuilder.subtitle(subtitle);

		// 15 abstract title
		TitleBuilder titleBuilder = new TitleBuilder();
		Title title = title(titleBuilder);
		if (title != null)
			highchartsConfigBuilder.title(title);

		// 16 tooltip
		TooltipBuilder tooltipBuilder = new TooltipBuilder();
		Tooltip tooltip = tooltip(tooltipBuilder);
		if (tooltip != null)
			highchartsConfigBuilder.tooltip(tooltip);

		// 17 abstract xAxis
		XAxisBuilder xAxisBuilder = new XAxisBuilder();
		List<XAxis> xlist = xAxis();
		if (xlist != null && xlist.size() != 0) {
			highchartsConfigBuilder.xAxis(xlist);
		} else {
			XAxis xAxis = xAxis(xAxisBuilder);
			if (xAxis != null)
				highchartsConfigBuilder.xAxis(xAxis);
		}

		// 18 abstract yAxis
		YAxisBuilder yAxisBuilder = new YAxisBuilder();
		List<YAxis> ylist = yAxis();
		if (ylist != null && ylist.size() != 0) {
			highchartsConfigBuilder.yAxis(ylist);
		} else {
			YAxis yAxis = yAxis(yAxisBuilder);
			if (yAxis != null)
				highchartsConfigBuilder.yAxis(yAxis);
		}

		// 构建图形
		highchartsConfig = highchartsConfigBuilder.build();
	}
}
