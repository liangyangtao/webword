package com.chart.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chart.Charts;
import com.chart.highchart.ColumnChart;
import com.chart.highchart.CombinationsCharts;
import com.chart.highchart.HcResultEntity;
import com.chart.highchart.HighCharts;
import com.chart.highchart.PieChart;
import com.chart.highchart.ScatterChart;
import com.chart.highchart.config.Axis;
import com.chart.highchart.config.Axis.PlotLinesBuilder;
import com.chart.highchart.config.Highchart;
import com.chart.highchart.config.Highchart.HighchartBuilder;
import com.chart.highchart.config.HighchartsConfig;
import com.chart.highchart.config.Legend;
import com.chart.highchart.config.Legend.LegendBuilder;
import com.chart.highchart.config.PlotOptions;
import com.chart.highchart.config.PlotOptions.Line;
import com.chart.highchart.config.PlotOptions.PlotOptionsBuilder;
import com.chart.highchart.config.Series;
import com.chart.highchart.config.Series.DataBuilder;
import com.chart.highchart.config.Series.SeriesBuilder;
import com.chart.highchart.config.Style;
import com.chart.highchart.config.Title;
import com.chart.highchart.config.Title.SubtitleBuilder;
import com.chart.highchart.config.Title.TitleBuilder;
import com.chart.highchart.config.Tooltip;
import com.chart.highchart.config.Tooltip.DashStyles;
import com.chart.highchart.config.Tooltip.TooltipBuilder;
import com.chart.highchart.config.XAxis;
import com.chart.highchart.config.XAxis.XAxisBuilder;
import com.chart.highchart.config.YAxis;
import com.chart.highchart.config.YAxis.YAxisBuilder;
import com.chart.highchart.type.Align;
import com.chart.highchart.type.Layout;
import com.chart.highchart.type.PlotOptionsType;
import com.chart.highchart.type.ThemeType;
import com.chart.highchart.type.VerticalAlign;
import com.chart.util.GsonUtil;

/**
 * 各种图形通过图形类库构建的例子
 * 
 * @author zile
 * 
 */
public class Client {

	/**
	 * 曲线图构建例子
	 */
	public HcResultEntity<HighchartsConfig> lineChartTest() {
		// 两种方式构建, 其他图形同理
		// 直接用HighCharts
		// new HighCharts(PlotOptionsType.LINE)
		// 或
		// new LineChart()

		return Charts.exportForEntity(new HighCharts(PlotOptionsType.LINE) {

			@Override
			protected Highchart chart(HighchartBuilder highchartBuilder) {
				highchartBuilder.zoomType("x");
				highchartBuilder.spacingRight(20);
				return highchartBuilder.build();
			}

			@Override
			protected ThemeType getThemeType() {
				return ThemeType.GRID;
			}

			@Override
			protected List<Series> series() {
				List<Series> list = new ArrayList<Series>();

				SeriesBuilder seriesBuilder1 = new SeriesBuilder();
				seriesBuilder1.name("Tokyo").data(7.0, 6.9, 9.5, 14.5, 18.2,
						21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.);
				list.add(seriesBuilder1.build());

				// add new attributes
				// seriesBuilder1.put("pointInterval", 24 * 3600 * 1000);

				//
				SeriesBuilder seriesBuilder2 = new SeriesBuilder();
				seriesBuilder2.name("New York").data(-0.2, 0.8, 5.7, 11.3,
						17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5);
				list.add(seriesBuilder2.build());

				//
				SeriesBuilder seriesBuilder3 = new SeriesBuilder();
				seriesBuilder3.name("Berlin").data(-0.9, 0.6, 3.5, 8.4, 13.5,
						17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0);
				list.add(seriesBuilder3.build());

				return list;
			}

			@Override
			protected Title subtitle(SubtitleBuilder subtitleBuilder) {
				subtitleBuilder.text("Source: WorldClimate.com");
				subtitleBuilder.x(-20);
				return subtitleBuilder.build();
			}

			@Override
			protected Title title(TitleBuilder titleBuilder) {
				titleBuilder.text("Monthly Average Temperature");
				titleBuilder.x(-20);
				return titleBuilder.build();
			}

			@Override
			protected XAxis xAxis(XAxisBuilder xAxisBuilder) {
				xAxisBuilder.categories("Jan", "Feb", "Mar", "Apr", "May",
						"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
				// xAxisBuilder.type(type);
				// xAxisBuilder.maxZoom(maxZoom);
				return xAxisBuilder.build();
			}

			@Override
			protected YAxis yAxis(YAxisBuilder yAxisBuilder) {
				Axis.TitleBuilder tb = new Axis.TitleBuilder();
				tb.text("Temperature (°C)");
				yAxisBuilder.title(tb.build());

				//
				PlotLinesBuilder plb = new PlotLinesBuilder();
				plb.color("#808080");
				plb.width(1);
				plb.value(0);
				yAxisBuilder.plotLines(plb.build());
				return yAxisBuilder.build();
			}

			@Override
			protected Tooltip tooltip(TooltipBuilder tooltipBuilder) {
				tooltipBuilder.valueSuffix("°C");
				// tooltipBuilder.shared(false);
				// super.tooltip(tooltipBuilder);
				return tooltipBuilder.build();
			}

			@Override
			protected Legend legend(LegendBuilder legendBuilder) {
				legendBuilder.layout(Layout.VERTICAL);
				legendBuilder.align(Align.RIGHT);
				legendBuilder.borderWidth(0);
				legendBuilder.verticalAlign(VerticalAlign.MIDDLE);
				// super.legend(legendBuilder);
				return legendBuilder.build();
			}

			@Override
			protected PlotOptions plotOptions(
					PlotOptionsBuilder plotOptionsBuilder) {
				Line line = new Line();
				Map<String, Object> dataLabels = new HashMap<String, Object>();
				dataLabels.put("enabled", true);

				line.put("enableMouseTracking", false);
				line.put("dataLabels", dataLabels);

				plotOptionsBuilder.line(line);

				// super.plotOptions(plotOptionsBuilder);
				return plotOptionsBuilder.build();
			}

		});
	}

	/**
	 * 柱形图构建例子
	 * 
	 * @return
	 */
	public HcResultEntity<HighchartsConfig> columnChartTest() {
		return Charts.exportForEntity(new ColumnChart() {

			@Override
			protected Tooltip tooltip(TooltipBuilder tooltipBuilder) {
				tooltipBuilder
						.headerFormat("<span style='font-size:10px'>{point.key}</span>");
				tooltipBuilder.pointFormat("");
				tooltipBuilder
						.footerFormat("<table><tbody><tr><td style='color:{series.color};padding:0'>{series.name}: </td><td style='padding:0'><b>{point.y:.1f} mm</b></td></tr></tbody></table>");
				tooltipBuilder.shared(true);
				tooltipBuilder.useHTML(true);
				return tooltipBuilder.build();
			}

			@Override
			protected PlotOptions plotOptions(
					PlotOptionsBuilder plotOptionsBuilder) {
				PlotOptions.Column column = new PlotOptions.Column();
				column.put("pointPadding", 0.2);
				column.put("borderWidth", 0);
				plotOptionsBuilder.column(column);
				return plotOptionsBuilder.build();
			}

			@Override
			protected YAxis yAxis(YAxisBuilder yAxisBuilder) {
				Axis.TitleBuilder tb = new Axis.TitleBuilder();
				tb.text("Rainfall (mm)");
				yAxisBuilder.min(0);
				yAxisBuilder.title(tb.build());
				return yAxisBuilder.build();
			}

			@Override
			protected XAxis xAxis(XAxisBuilder xAxisBuilder) {
				xAxisBuilder.categories("Jan", "Feb", "Mar", "Apr", "May",
						"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
				return xAxisBuilder.build();
			}

			@Override
			protected Title title(TitleBuilder titleBuilder) {
				titleBuilder.text("Monthly Average Rainfall");
				return titleBuilder.build();
			}

			@Override
			protected Title subtitle(SubtitleBuilder subtitleBuilder) {
				subtitleBuilder.text("Source: WorldClimate.com");
				return subtitleBuilder.build();
			}

			@Override
			protected List<Series> series() {
				List<Series> series = new ArrayList<Series>();
				// 序列1
				SeriesBuilder sb1 = new SeriesBuilder("Tokyo", 49.9, 71.5,
						106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1,
						95.6, 54.4);
				series.add(sb1.build());
				// 序列2
				SeriesBuilder sb2 = new SeriesBuilder("New York", 83.6, 78.8,
						98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5,
						106.6, 92.3);
				series.add(sb2.build());
				// 序列3
				SeriesBuilder sb3 = new SeriesBuilder("London", 48.9, 38.8,
						39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3,
						51.2);
				series.add(sb3.build());
				// 序列4
				SeriesBuilder sb4 = new SeriesBuilder("Berlin", 42.4, 33.2,
						34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8,
						51.1);
				series.add(sb4.build());
				return series;
			}

			@Override
			protected ThemeType getThemeType() {
				return ThemeType.GRID;
			}
		});
	}

	/**
	 * 饼图构建例子
	 * 
	 * @return
	 */
	public HcResultEntity<HighchartsConfig> pieChartTest() {
		return Charts.exportForEntity(new PieChart() {

			@Override
			protected PlotOptions plotOptions(
					PlotOptionsBuilder plotOptionsBuilder) {
				PlotOptions.Pie pie = new PlotOptions.Pie();
				pie.put("allowPointSelect", true);
				pie.put("cursor", "pointer");
				PlotOptions.Pie dataLabels = new PlotOptions.Pie();
				dataLabels.put("enabled", true);
				dataLabels.put("color", "#000000");
				dataLabels.put("connectorColor", "#000000");
				pie.put("dataLabels", dataLabels);
				plotOptionsBuilder.pie(pie);
				// Function与Event 暂时没有实现
				return plotOptionsBuilder.build();
			}

			@Override
			protected Tooltip tooltip(TooltipBuilder tooltipBuilder) {
				tooltipBuilder
						.pointFormat("{series.name}: <b>{point.percentage:.1f}%</b>");
				return tooltipBuilder.build();
			}

			@Override
			protected Highchart chart(HighchartBuilder highchartBuilder) {
				// 重载调用时无法精确匹配方法
				highchartBuilder.plotBackgroundColor((String) null);
				highchartBuilder.plotShadow(false);
				return highchartBuilder.build();
			}

			@Override
			protected XAxis xAxis(XAxisBuilder xAxisBuilder) {
				return null;
			}

			@Override
			protected YAxis yAxis(YAxisBuilder yAxisBuilder) {
				return null;
			}

			@Override
			protected Title title(TitleBuilder titleBuilder) {
				titleBuilder
						.text("Browser market shares at a specific website, 2010");
				return titleBuilder.build();
			}

			@Override
			protected Title subtitle(SubtitleBuilder subtitleBuilder) {
				return null;
			}

			@Override
			protected List<Series> series() {
				List<Series> series = new ArrayList<Series>();
				SeriesBuilder s = new SeriesBuilder();
				s.type(PlotOptionsType.PIE);
				s.name("Browser share");
				//
				DataBuilder d1 = new Series.DataBuilder("Firefox", 45.0);
				//
				DataBuilder d2 = new Series.DataBuilder("IE", 26.8);
				//
				DataBuilder d3 = new Series.DataBuilder("Chrome", 12.8);
				d3.sliced(true);
				//
				DataBuilder d4 = new Series.DataBuilder("Safari", 8.5);
				//
				DataBuilder d5 = new Series.DataBuilder("Opera", 6.2);
				//
				DataBuilder d6 = new Series.DataBuilder("Others", 0.7);
				//
				s.data(d1.build(), d2.build(), d3.build(), d4.build(),
						d5.build(), d6.build());
				//
				series.add(s.build());
				return series;
			}

			@Override
			protected ThemeType getThemeType() {
				return ThemeType.GRID;
			}
		});
	}

	/**
	 * 散点图构建例子
	 * 
	 * @return
	 */
	public HcResultEntity<HighchartsConfig> scatterChartTest() {
		return Charts.exportForEntity(new ScatterChart() {

			@Override
			protected PlotOptions plotOptions(
					PlotOptionsBuilder plotOptionsBuilder) {
				PlotOptions.Scatter scatter = new PlotOptions.Scatter();
				// marker 构建
				PlotOptions.Scatter marker = new PlotOptions.Scatter();
				marker.put("radius", 5);
				PlotOptions.Scatter states = new PlotOptions.Scatter();
				PlotOptions.Scatter hover = new PlotOptions.Scatter();
				hover.put("hover", true);
				hover.put("lineColor", "rgb(100,100,100)");
				states.put("hover", hover);
				marker.put("states", states);
				scatter.put("marker", marker);
				// states 构建
				PlotOptions.Scatter states2 = new PlotOptions.Scatter();
				PlotOptions.Scatter hover2 = new PlotOptions.Scatter();
				PlotOptions.Scatter marker2 = new PlotOptions.Scatter();
				marker2.put("enabled", false);
				hover2.put("marker", marker2);
				states2.put("hover", hover2);
				scatter.put("states", states2);
				// tooltip 构建
				PlotOptions.Scatter tooltip = new PlotOptions.Scatter();
				tooltip.put("headerFormat", "<b>{series.name}</b><br>");
				tooltip.put("pointFormat", "{point.x} cm, {point.y} kg");
				scatter.put("tooltip", tooltip);
				// scatter对象构建
				plotOptionsBuilder.scatter(scatter);
				return plotOptionsBuilder.build();
			}

			@Override
			protected Legend legend(LegendBuilder legendBuilder) {
				legendBuilder.layout(Layout.VERTICAL);
				legendBuilder.align(Align.LEFT);
				legendBuilder.verticalAlign(VerticalAlign.TOP);
				legendBuilder.x(100);
				legendBuilder.y(70);
				legendBuilder.floating(true);
				legendBuilder.backgroundColor("#FFFFFF");
				legendBuilder.borderWidth(1);
				return legendBuilder.build();
			}

			@Override
			protected Highchart chart(HighchartBuilder highchartBuilder) {
				highchartBuilder.zoomType("xy");
				return highchartBuilder.build();
			}

			@Override
			protected YAxis yAxis(YAxisBuilder yAxisBuilder) {
				Axis.TitleBuilder title = new Axis.TitleBuilder();
				title.text("Weight (kg)");
				yAxisBuilder.title(title.build());
				return yAxisBuilder.build();
			}

			@SuppressWarnings("deprecation")
			@Override
			protected XAxis xAxis(XAxisBuilder xAxisBuilder) {
				Axis.TitleBuilder title = new Axis.TitleBuilder();
				title.text("Height (cm)");
				title.enabled(true);
				xAxisBuilder.title(title.build());
				xAxisBuilder.startOnTick(true);
				xAxisBuilder.endOnTick(true);
				xAxisBuilder.showLastLabel(true);
				return xAxisBuilder.build();
			}

			@Override
			protected Title title(TitleBuilder titleBuilder) {
				titleBuilder
						.text("Height Versus Weight of 507 Individuals by Gender");
				return titleBuilder.build();
			}

			@Override
			protected Title subtitle(SubtitleBuilder subtitleBuilder) {
				subtitleBuilder.text("Source: Heinz  2003");
				return subtitleBuilder.build();
			}

			@Override
			protected List<Series> series() {
				List<Series> series = new ArrayList<Series>();
				// Female
				SeriesBuilder Female = new SeriesBuilder();
				Female.name("Female");
				// TODO
				// Female.color("rgba(223, 83, 83, .5)");
				Female.data(femaleTestData());
				series.add(Female.build());
				// Male
				SeriesBuilder male = new SeriesBuilder();
				male.name("Male");
				// male.color("rgba(119, 152, 191, .5)");
				male.data(maleTestData());
				series.add(male.build());
				return series;
			}

			@Override
			protected ThemeType getThemeType() {
				return ThemeType.GRID;
			}

			/**
			 * 测试数据, 女
			 * 
			 * @return
			 */
			private List<List<Number>> femaleTestData() {
				List<List<Number>> all = new ArrayList<List<Number>>();
				// 100条测试数据
				for (int i = 0; i < 100; i++) {
					// 身高随机数据生成
					BigDecimal height = new BigDecimal(40 * Math.random());
					height = height.setScale(2, BigDecimal.ROUND_HALF_UP);
					height = height.add(new BigDecimal(150));
					// 体重随机数据生成
					BigDecimal weight = new BigDecimal(20 * Math.random());
					weight = weight.setScale(2, BigDecimal.ROUND_HALF_UP);
					weight = weight.add(new BigDecimal(30));
					//
					all.add(info(height, weight));
				}
				return all;
			}

			/**
			 * 测试数据, 男
			 * 
			 * @return
			 */
			private List<List<Number>> maleTestData() {
				List<List<Number>> all = new ArrayList<List<Number>>();
				// 100条测试数据
				for (int i = 0; i < 100; i++) {
					// 身高随机数据生成
					BigDecimal height = new BigDecimal(60 * Math.random());
					height = height.setScale(2, BigDecimal.ROUND_HALF_UP);
					height = height.add(new BigDecimal(160));
					// 体重随机数据生成
					BigDecimal weight = new BigDecimal(40 * Math.random());
					weight = weight.setScale(2, BigDecimal.ROUND_HALF_UP);
					weight = weight.add(new BigDecimal(40));
					//
					all.add(info(height, weight));
				}
				return all;
			}

			/**
			 * 
			 * @param height
			 *            身高
			 * @param weight
			 *            体重
			 * @return
			 */
			private List<Number> info(Number height, Number weight) {
				List<Number> list = new ArrayList<Number>();
				list.add(height);
				list.add(weight);
				return list;
			}
		});
	}

	/**
	 * 柱曲混合图构建例子, 使用CombinationsCharts
	 * 
	 * @return
	 */
	public HcResultEntity<HighchartsConfig> combinationsChartsTest1() {
		return Charts.exportForEntity(new CombinationsCharts() {

			@Override
			protected Legend legend(LegendBuilder legendBuilder) {
				legendBuilder.layout(Layout.VERTICAL);
				legendBuilder.align(Align.LEFT);
				legendBuilder.x(120);
				legendBuilder.y(100);
				legendBuilder.verticalAlign(VerticalAlign.TOP);
				legendBuilder.floating(true);
				legendBuilder.backgroundColor("#FFFFFF");
				return legendBuilder.build();
			}

			@Override
			protected Tooltip tooltip(TooltipBuilder tooltipBuilder) {
				tooltipBuilder.shared(true);
				return tooltipBuilder.build();
			}

			@Override
			protected Highchart chart(HighchartBuilder highchartBuilder) {
				highchartBuilder.zoomType("xy");
				return highchartBuilder.build();
			}

			@Override
			protected YAxis yAxis(YAxisBuilder yAxisBuilder) {
				// 混合图形使用重载方法yAxis()
				return null;
			}

			@Override
			protected List<YAxis> yAxis() {
				List<YAxis> list = new ArrayList<YAxis>();
				// Primary yAxis, 左侧主轴
				YAxisBuilder primary = new YAxisBuilder();
				Axis.LabelsBuilder labels = new Axis.LabelsBuilder();
				Style style = new Style();
				style.put("color", "#89A54E");
				labels.format("{value}°C");
				labels.style(style);
				Axis.TitleBuilder title = new Axis.TitleBuilder();
				title.text("Temperature");
				title.style(style);
				primary.labels(labels.build());
				primary.title(title.build());
				// Secondary yAxis, 右侧副轴
				YAxisBuilder secondary = new YAxisBuilder();
				labels = new Axis.LabelsBuilder();
				style = new Style();
				style.put("color", "#4572A7");
				labels.format("{value} mm");
				labels.style(style);
				title = new Axis.TitleBuilder();
				title.text("Rainfall");
				title.style(style);
				secondary.labels(labels.build());
				secondary.title(title.build());
				secondary.opposite(true);
				// 构建列表
				list.add(primary.build());
				list.add(secondary.build());
				return list;
			}

			@Override
			protected XAxis xAxis(XAxisBuilder xAxisBuilder) {
				xAxisBuilder.categories("Jan", "Feb", "Mar", "Apr", "May",
						"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
				return xAxisBuilder.build();
			}

			@Override
			protected Title title(TitleBuilder titleBuilder) {
				titleBuilder
						.text("Average Monthly Temperature and Rainfall in Tokyo");
				return titleBuilder.build();
			}

			@Override
			protected Title subtitle(SubtitleBuilder subtitleBuilder) {
				subtitleBuilder.text("Source: WorldClimate.com");
				return subtitleBuilder.build();
			}

			@Override
			protected List<Series> series() {
				List<Series> list = new ArrayList<Series>();
				// 第一个使用柱状 降雨量
				SeriesBuilder rainfall = new SeriesBuilder();
				TooltipBuilder tooltip = new TooltipBuilder();
				tooltip.valueSuffix(" mm");
				rainfall.name("Rainfall");
				// TODO
				// rainfall.color("#4572A7");
				rainfall.type(PlotOptionsType.COLUMN); // 柱形图
				rainfall.yAxis(1); // 使用列表中的第一个作为参照轴
				rainfall.data(49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6,
						148.5, 216.4, 194.1, 95.6, 54.4);
				// rainfall.tooltip(tooltip.build()); // 添加一个后缀
				// 第二个使用曲线图 温度
				SeriesBuilder temperature = new SeriesBuilder();
				tooltip = new TooltipBuilder();
				tooltip.valueSuffix("°C");
				temperature.name("Temperature");
				// temperature.color("#89A54E");
				temperature.type(PlotOptionsType.SPLINE);
				temperature.data(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5,
						23.3, 18.3, 13.9, 9.6);
				// temperature.tooltip(tooltip.build());
				// 添加到列表中
				list.add(rainfall.build());
				list.add(temperature.build());
				return list;
			}

			@Override
			protected ThemeType getThemeType() {
				return ThemeType.GRID;
			}
		});
	}

	/**
	 * 多个Y轴的混合图例子
	 * 
	 * @return
	 */
	public HcResultEntity<HighchartsConfig> combinationsChartsTest2() {
		return Charts.exportForEntity(new CombinationsCharts() {

			@Override
			protected Legend legend(LegendBuilder legendBuilder) {
				legendBuilder.layout(Layout.VERTICAL);
				legendBuilder.align(Align.LEFT);
				legendBuilder.x(120);
				legendBuilder.y(100);
				legendBuilder.verticalAlign(VerticalAlign.TOP);
				legendBuilder.floating(true);
				legendBuilder.backgroundColor("#FFFFFF");
				return legendBuilder.build();
			}

			@Override
			protected Tooltip tooltip(TooltipBuilder tooltipBuilder) {
				tooltipBuilder.shared(true);
				return tooltipBuilder.build();
			}

			@Override
			protected Highchart chart(HighchartBuilder highchartBuilder) {
				highchartBuilder.zoomType("xy");
				return highchartBuilder.build();
			}

			@Override
			protected ThemeType getThemeType() {
				return ThemeType.GRID;
			}

			@Override
			protected List<Series> series() {
				List<Series> list = new ArrayList<Series>();
				// 第一个使用柱状 降雨量
				SeriesBuilder rainfall = new SeriesBuilder();
				TooltipBuilder tooltip = new TooltipBuilder();
				tooltip.valueSuffix(" mm");
				rainfall.name("Rainfall");
				rainfall.type(PlotOptionsType.COLUMN); // 柱形图
				rainfall.put("color", "#4572A7");
				rainfall.yAxis(1); // 使用列表中的第1个作为参照轴
				rainfall.data(49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6,
						148.5, 216.4, 194.1, 95.6, 54.4);
				rainfall.put("tooltip", tooltip.build()); // 添加一个后缀
				// 第二个使用曲线图 温度
				SeriesBuilder temperature = new SeriesBuilder();
				tooltip = new TooltipBuilder();
				tooltip.valueSuffix(" °C");
				temperature.name("Temperature");
				temperature.type(PlotOptionsType.SPLINE);
				temperature.put("color", "#89A54E");
				temperature.data(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5,
						23.3, 18.3, 13.9, 9.6);
				temperature.put("tooltip", tooltip.build());
				// 第三个
				SeriesBuilder pressure = new SeriesBuilder();
				tooltip = new TooltipBuilder();
				tooltip.valueSuffix(" mb");
				pressure.name("Sea-Level Pressure");
				pressure.type(PlotOptionsType.SPLINE);
				pressure.put("color", "#AA4643");
				pressure.yAxis(2); // 使用列表中的第2个作为参照轴
				pressure.data(1016, 1016, 1015.9, 1015.5, 1012.3, 1009.5,
						1009.6, 1010.2, 1013.1, 1016.9, 1018.2, 1016.);
				// plotOptions 高级特性设置
				pressure.put("tooltip", tooltip.build());
				Map<String, Object> marker = new HashMap<String, Object>();
				marker.put("enabled", false);
				pressure.put("marker", marker);
				pressure.put("dashStyle", DashStyles.SHORTDASH);
				// 添加到列表中
				list.add(rainfall.build());
				list.add(pressure.build());
				list.add(temperature.build());
				return list;
			}

			@Override
			protected Title subtitle(SubtitleBuilder subtitleBuilder) {
				subtitleBuilder.text("来源: www.unbank.info");
				return subtitleBuilder.build();
			}

			@Override
			protected Title title(TitleBuilder titleBuilder) {
				titleBuilder.text("Average Monthly Weather Data for Tokyo");
				return titleBuilder.build();
			}

			@Override
			protected XAxis xAxis(XAxisBuilder xAxisBuilder) {
				xAxisBuilder.categories("Jan", "Feb", "Mar", "Apr", "May",
						"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
				return xAxisBuilder.build();
			}

			@Override
			protected YAxis yAxis(YAxisBuilder yAxisBuilder) {
				return null;
			}

			@Override
			protected List<YAxis> yAxis() {
				List<YAxis> list = new ArrayList<YAxis>();
				// Primary yAxis,
				YAxisBuilder primary = new YAxisBuilder();
				Axis.LabelsBuilder labels = new Axis.LabelsBuilder();
				Style style = new Style();
				style.put("color", "#89A54E");
				labels.format("{value}°C");
				labels.style(style);
				Axis.TitleBuilder title = new Axis.TitleBuilder();
				title.text("Temperature");
				title.style(style);
				primary.labels(labels.build());
				primary.title(title.build());
				primary.opposite(true); // 反方向显示, 右侧
				// Secondary yAxis,
				YAxisBuilder secondary = new YAxisBuilder();
				labels = new Axis.LabelsBuilder();
				style = new Style();
				style.put("color", "#4572A7");
				labels.format("{value} mm");
				labels.style(style);
				title = new Axis.TitleBuilder();
				title.text("Rainfall");
				title.style(style);
				secondary.labels(labels.build());
				secondary.title(title.build());
				// Tertiary yAxis
				YAxisBuilder tertiary = new YAxisBuilder();
				labels = new Axis.LabelsBuilder();
				style = new Style();
				style.put("color", "#AA4643");
				labels.format("{value} mb");
				labels.style(style);
				title = new Axis.TitleBuilder();
				title.text("Sea-Level Pressure");
				title.style(style);
				tertiary.labels(labels.build());
				tertiary.title(title.build());
				tertiary.opposite(true); // 反方向显示, 右侧
				tertiary.gridLineWidth(0);
				// 构建列表
				list.add(primary.build());
				list.add(secondary.build());
				list.add(tertiary.build());
				return list;
			}
		});

	}

	public void execute() {
	}

	public static void main(String[] args) {
		Client c = new Client();
		// HcResultEntity<HighchartsConfig> resultEntity = c.lineChartTest();
		// HcResultEntity<HighchartsConfig> resultEntity = c.columnChartTest();
		// HcResultEntity<HighchartsConfig> resultEntity = c.pieChartTest();
		// HcResultEntity<HighchartsConfig> resultEntity = c.scatterChartTest();
		// HcResultEntity<HighchartsConfig> resultEntity =
		// c.combinationsChartsTest1();
		HcResultEntity<HighchartsConfig> resultEntity = c
				.combinationsChartsTest2();

		System.out.println(resultEntity.getEntity().jsonObject());
		System.out.println(resultEntity.getMs());
		System.out.println(resultEntity.getTheme().themeConfig().jsonObject());
		System.out.println(resultEntity.getTheme().themename());
		System.out.println(resultEntity.getOptions().jsonObject());
		System.out.println(resultEntity.getType());
		HighchartsConfig hc = GsonUtil.fromJson(resultEntity.toString(),
				HighchartsConfig.class);
		System.out.println(hc.jsonObject());
	}
}
