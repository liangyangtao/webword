package com.chart.highchart.config;

/**
 * 
 * @author zile
 * 
 */
public class XAxis extends Axis {

	public static class XAxisBuilder extends AxisBuilder {

		@Override
		public XAxis build() {
			return new XAxis(this);
		}

	}

	protected XAxis(XAxisBuilder builder) {
		super(builder);
	}

}
