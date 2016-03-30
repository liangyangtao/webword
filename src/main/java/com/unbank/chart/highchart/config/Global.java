package com.unbank.chart.highchart.config;

import com.unbank.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class Global {

	private final String VMLRadialGradientURL;
	private final String canvasToolsURL;
	private final Number timezoneOffset;
	private final Boolean useUTC;

	private Global(GlobalBuilder builder) {
		this.VMLRadialGradientURL = builder.VMLRadialGradientURL;
		this.canvasToolsURL = builder.canvasToolsURL;
		this.timezoneOffset = builder.timezoneOffset;
		this.useUTC = builder.useUTC;
	}

	public static class GlobalBuilder implements Builder<Global> {

		private String VMLRadialGradientURL;
		private String canvasToolsURL;
		private Number timezoneOffset;
		private Boolean useUTC;

		/**
		 * <p>
		 * Path to the pattern image required by VML browsers in order to draw
		 * radial gradients. Defaults to
		 * http://code.highcharts.com/{version}/gfx/vml-radial-gradient.png.
		 * </p>
		 * 
		 * @param VMLRadialGradientURL
		 * @return
		 */
		public GlobalBuilder VMLRadialGradientURL(String VMLRadialGradientURL) {
			this.VMLRadialGradientURL = VMLRadialGradientURL;
			return this;
		}

		/**
		 * <p>
		 * The URL to the additional file to lazy load for Android 2.x devices.
		 * These devices don't support SVG, so we download a helper file that
		 * contains canvg, its dependency rbcolor, and our own CanVG Renderer
		 * class. To avoid hotlinking to our site, you can install
		 * canvas-tools.js on your own server and change this option
		 * accordingly. Defaults to
		 * http://code.highcharts.com/{version}/modules/canvas-tools.js.
		 * </p>
		 * 
		 * @param canvasToolsURL
		 * @return
		 */
		public GlobalBuilder canvasToolsURL(String canvasToolsURL) {
			this.canvasToolsURL = canvasToolsURL;
			return this;
		}

		/**
		 * <p>
		 * The timezone offset in minutes. Positive values are west, negative
		 * values are east of UTC, as in the ECMAScript getTimezoneOffset
		 * method. Use this to display UTC based data in a predefined time zone.
		 * Defaults to 0.
		 * </p>
		 * 
		 * @param timezoneOffset
		 * @return
		 */
		public GlobalBuilder timezoneOffset(int timezoneOffset) {
			this.timezoneOffset = timezoneOffset;
			return this;
		}

		/**
		 * <p>
		 * Whether to use UTC time for axis scaling, tickmark placement and time
		 * display in Highcharts.dateFormat. Advantages of using UTC is that the
		 * time displays equally regardless of the user agent's time zone
		 * settings. Local time can be used when the data is loaded in real time
		 * or when correct Daylight Saving Time transitions are required.
		 * Defaults to true.
		 * </p>
		 * 
		 * @param useUTC
		 * @return
		 */
		public GlobalBuilder useUTC(boolean useUTC) {
			this.useUTC = useUTC;
			return this;
		}

		@Override
		public Global build() {
			return new Global(this);
		}

	}

	public String getVMLRadialGradientURL() {
		return VMLRadialGradientURL;
	}

	public String getCanvasToolsURL() {
		return canvasToolsURL;
	}

	public Number getTimezoneOffset() {
		return timezoneOffset;
	}

	public Boolean getUseUTC() {
		return useUTC;
	}

}
