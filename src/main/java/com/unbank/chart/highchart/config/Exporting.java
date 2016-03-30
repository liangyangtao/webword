package com.unbank.chart.highchart.config;

import com.unbank.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class Exporting {

	private final Buttons buttons; // FIXME
	private final Object chartOptions;
	private final Boolean enabled;
	private final String filename;
	private final Object formAttributes;
	private final Number scale;
	private final Number sourceHeight;
	private final Number sourceWidth;
	private final String type;
	private final String url;
	private final Number width;

	private Exporting(ExportingBuilder builder) {
		this.buttons = builder.buttons;
		this.chartOptions = builder.chartOptions;
		this.enabled = builder.enabled;
		this.filename = builder.filename;
		this.formAttributes = builder.formAttributes;
		this.scale = builder.scale;
		this.sourceHeight = builder.sourceHeight;
		this.sourceWidth = builder.sourceWidth;
		this.type = builder.type;
		this.url = builder.url;
		this.width = builder.width;
	}

	public static class ExportingBuilder implements Builder<Exporting> {

		private Buttons buttons; // FIXME
		private Object chartOptions;
		private Boolean enabled;
		private String filename;
		private Object formAttributes;
		private Number scale;
		private Number sourceHeight;
		private Number sourceWidth;
		private String type;
		private String url;
		private Number width;

		public ExportingBuilder chartOptions(Object chartOptions) {
			this.chartOptions = chartOptions;
			return this;
		}

		public ExportingBuilder enabled(Boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public ExportingBuilder filename(String filename) {
			this.filename = filename;
			return this;
		}

		public ExportingBuilder formAttributes(Object formAttributes) {
			this.formAttributes = formAttributes;
			return this;
		}

		public ExportingBuilder scale(Number scale) {
			this.scale = scale;
			return this;
		}

		public ExportingBuilder sourceHeight(Number sourceHeight) {
			this.sourceHeight = sourceHeight;
			return this;
		}

		public ExportingBuilder sourceWidth(Number sourceWidth) {
			this.sourceWidth = sourceWidth;
			return this;
		}

		public ExportingBuilder type(String type) {
			this.type = type;
			return this;
		}

		public ExportingBuilder url(String url) {
			this.url = url;
			return this;
		}

		public ExportingBuilder width(Number width) {
			this.width = width;
			return this;
		}

		@Override
		public Exporting build() {
			return new Exporting(this);
		}

	}

	public Buttons getButtons() {
		return buttons;
	}

	public Object getChartOptions() {
		return chartOptions;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public String getFilename() {
		return filename;
	}

	public Object getFormAttributes() {
		return formAttributes;
	}

	public Number getScale() {
		return scale;
	}

	public Number getSourceHeight() {
		return sourceHeight;
	}

	public Number getSourceWidth() {
		return sourceWidth;
	}

	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public Number getWidth() {
		return width;
	}

}
