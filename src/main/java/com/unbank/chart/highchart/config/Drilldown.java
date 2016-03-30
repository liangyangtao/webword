package com.unbank.chart.highchart.config;

import java.util.ArrayList;
import java.util.List;

import com.unbank.chart.util.Builder;

/**
 * <p>
 * Options for drill down, the concept of inspecting increasingly high
 * resolution data through clicking on chart items like columns or pie slices.
 * </p>
 * 
 * <p>
 * The drilldown feature requires the drilldown.js file to be loaded, found in
 * the modules directory of the download package, or online at
 * code.highcharts.com/modules/drilldown.js.
 * </p>
 * 
 * @author zile
 * 
 */
public class Drilldown {

	private final Style activeAxisLabelStyle;
	private final Style activeDataLabelStyle;
	private final Object animation;
	private final DrillUpButton drillUpButton;
	private final List<Object> series;

	private Drilldown(DrilldownBuilder builder) {
		this.activeAxisLabelStyle = builder.activeAxisLabelStyle;
		this.activeDataLabelStyle = builder.activeDataLabelStyle;
		this.animation = builder.animation;
		this.drillUpButton = builder.drillUpButton;
		this.series = builder.series;
	}

	public static class DrilldownBuilder implements Builder<Drilldown> {

		private Style activeAxisLabelStyle;
		private Style activeDataLabelStyle;
		private Object animation;
		private DrillUpButton drillUpButton;
		private List<Object> series;

		public DrilldownBuilder activeAxisLabelStyle(Style activeAxisLabelStyle) {
			this.activeAxisLabelStyle = activeAxisLabelStyle;
			return this;
		}

		public DrilldownBuilder activeDataLabelStyle(Style activeDataLabelStyle) {
			this.activeDataLabelStyle = activeDataLabelStyle;
			return this;
		}

		public DrilldownBuilder animation(Animation animation) {
			this.animation = animation;
			return this;
		}

		public DrilldownBuilder animation(Boolean animation) {
			this.animation = animation;
			return this;
		}

		public DrilldownBuilder drillUpButton(DrillUpButton drillUpButton) {
			this.drillUpButton = drillUpButton;
			return this;
		}

		/**
		 * do not call more than once. no effective.
		 * 
		 * @param numbers
		 * @return
		 */
		public DrilldownBuilder series(Number... numbers) {
			this.series = new ArrayList<Object>();
			for (Number number : numbers)
				series.add(number);
			return this;
		}

		/**
		 * do not call more than once. no effective.
		 * 
		 * @param array
		 * @return
		 */
		public DrilldownBuilder series(List<List<Object>> array) {
			this.series = new ArrayList<Object>();
			for (List<Object> list : array)
				series.add(list);
			return this;
		}

		/**
		 * 
		 * @param array
		 * @return
		 */
		public DrilldownBuilder series(Series... array) {
			this.series = new ArrayList<Object>();
			for (Series o : array)
				series.add(o);
			return this;
		}

		@Override
		public Drilldown build() {
			return new Drilldown(this);
		}

	}

	public Style getActiveAxisLabelStyle() {
		return activeAxisLabelStyle;
	}

	public Style getActiveDataLabelStyle() {
		return activeDataLabelStyle;
	}

	public Object getAnimation() {
		return animation;
	}

	public DrillUpButton getDrillUpButton() {
		return drillUpButton;
	}

	public List<Object> getSeries() {
		return series;
	}

}
