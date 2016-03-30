package com.chart.highchart.type;

import com.chart.highchart.config.PlotOptions;
import com.chart.highchart.config.PlotOptions.Shape;

/**
 * 
 * @author zile
 * 
 */
public enum PlotOptionsType {

	AREA("area") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Area();
		}
	},

	AREARANGE("arearange") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Arearange();
		}
	},

	AREASPLINE("areaspline") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Areaspline();
		}
	},

	AREASPLINERANGE("areasplinerange") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Areasplinerange();
		}
	},

	BAR("bar") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Bar();
		}
	},

	BOXPLOT("boxplot") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Boxplot();
		}
	},

	BUBBLE("bubble") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Bubble();
		}
	},

	COLUMN("column") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Column();
		}
	},

	COLUMNRANGE("columnrange") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Columnrange();
		}
	},

	ERRORBAR("errorbar") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Errorbar();
		}
	},

	FUNNEL("funnel") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Funnel();
		}
	},

	GAUGE("gauge") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Gauge();
		}
	},

	HEATMAP("heatmap") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Heatmap();
		}
	},

	LINE("line") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Line();
		}
	},

	PIE("pie") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Pie();
		}
	},

	PYRAMID("pyramid") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Pyramid();
		}
	},

	SCATTER("scatter") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Scatter();
		}
	},

	SERIES("series") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Series();
		}
	},

	SOLIDGAUGE("solidgauge") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Solidgauge();
		}
	},

	/**
	 * 曲线图
	 */
	SPLINE("spline") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Spline();
		}
	},

	WATERFALL("waterfall") {
		@Override
		public Shape newInstance() {
			return new PlotOptions.Waterfall();
		}
	},

	/**
	 * 混合图形, 特殊图形
	 */
	COMBINATIONS("combinations") {
		@Override
		public Shape newInstance() {
			return null;
		}
	};

	private final String name;

	private PlotOptionsType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract Shape newInstance();

}
