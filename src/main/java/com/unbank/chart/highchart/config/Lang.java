package com.unbank.chart.highchart.config;

import java.util.ArrayList;
import java.util.List;

import com.unbank.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class Lang {

	private final String contextButtonTitle;
	private final String decimalPoint;
	private final String downloadJPEG;
	private final String downloadPDF;
	private final String downloadPNG;
	private final String downloadSVG;
	private final String drillUpText;
	private final String loading;
	private final List<String> months;
	private final String noData;
	private final List<String> numericSymbols;
	private final String printChart;
	private final String resetZoom;
	private final String resetZoomTitle;
	private final List<String> shortMonths;
	private final String thousandsSep;
	private final List<String> weekdays;

	private Lang(LangBuilder builder) {
		this.contextButtonTitle = builder.contextButtonTitle;
		this.decimalPoint = builder.decimalPoint;
		this.downloadJPEG = builder.downloadJPEG;
		this.downloadPDF = builder.downloadPDF;
		this.downloadPNG = builder.downloadPNG;
		this.downloadSVG = builder.downloadSVG;
		this.drillUpText = builder.drillUpText;
		this.loading = builder.loading;
		this.months = builder.months;
		this.noData = builder.noData;
		this.numericSymbols = builder.numericSymbols;
		this.printChart = builder.printChart;
		this.resetZoom = builder.resetZoom;
		this.resetZoomTitle = builder.resetZoomTitle;
		this.shortMonths = builder.shortMonths;
		this.thousandsSep = builder.thousandsSep;
		this.weekdays = builder.weekdays;
	}

	public static class LangBuilder implements Builder<Lang> {

		private String contextButtonTitle;
		private String decimalPoint;
		private String downloadJPEG;
		private String downloadPDF;
		private String downloadPNG;
		private String downloadSVG;
		private String drillUpText;
		private String loading;
		private List<String> months;
		private String noData;
		private List<String> numericSymbols;
		private String printChart;
		private String resetZoom;
		private String resetZoomTitle;
		private List<String> shortMonths;
		private String thousandsSep;
		private List<String> weekdays;

		public LangBuilder contextButtonTitle(String contextButtonTitle) {
			this.contextButtonTitle = contextButtonTitle;
			return this;
		}

		public LangBuilder decimalPoint(String decimalPoint) {
			this.decimalPoint = decimalPoint;
			return this;
		}

		public LangBuilder downloadJPEG(String downloadJPEG) {
			this.downloadJPEG = downloadJPEG;
			return this;
		}

		public LangBuilder downloadPDF(String downloadPDF) {
			this.downloadPDF = downloadPDF;
			return this;
		}

		public LangBuilder downloadPNG(String downloadPNG) {
			this.downloadPNG = downloadPNG;
			return this;
		}

		public LangBuilder downloadSVG(String downloadSVG) {
			this.downloadSVG = downloadSVG;
			return this;
		}

		public LangBuilder drillUpText(String drillUpText) {
			this.drillUpText = drillUpText;
			return this;
		}

		public LangBuilder loading(String loading) {
			this.loading = loading;
			return this;
		}

		public LangBuilder months(String... months) {
			if (months != null && months.length == 12) {
				this.months = new ArrayList<String>();
				for (String month : months)
					this.months.add(month);
			}
			return this;
		}

		public LangBuilder noData(String noData) {
			this.noData = noData;
			return this;
		}

		public LangBuilder numericSymbols(String... numericSymbols) {
			if (numericSymbols != null) {
				this.numericSymbols = new ArrayList<String>();
				for (String numericSymbol : numericSymbols)
					this.numericSymbols.add(numericSymbol);
			}
			return this;
		}

		public LangBuilder printChart(String printChart) {
			this.printChart = printChart;
			return this;
		}

		public LangBuilder resetZoom(String resetZoom) {
			this.resetZoom = resetZoom;
			return this;
		}

		public LangBuilder resetZoomTitle(String resetZoomTitle) {
			this.resetZoomTitle = resetZoomTitle;
			return this;
		}

		public LangBuilder shortMonths(String... shortMonths) {
			if (shortMonths != null && shortMonths.length == 12) {
				this.shortMonths = new ArrayList<String>();
				for (String shortMonth : shortMonths)
					this.shortMonths.add(shortMonth);
			}
			return this;
		}

		public LangBuilder thousandsSep(String thousandsSep) {
			this.thousandsSep = thousandsSep;
			return this;
		}

		public LangBuilder weekdays(String... weekdays) {
			if (weekdays != null && weekdays.length == 7) {
				this.weekdays = new ArrayList<String>();
				for (String weekday : weekdays)
					this.weekdays.add(weekday);
			}
			return this;
		}

		@Override
		public Lang build() {
			return new Lang(this);
		}

	}

	public String getContextButtonTitle() {
		return contextButtonTitle;
	}

	public String getDecimalPoint() {
		return decimalPoint;
	}

	public String getDownloadJPEG() {
		return downloadJPEG;
	}

	public String getDownloadPDF() {
		return downloadPDF;
	}

	public String getDownloadPNG() {
		return downloadPNG;
	}

	public String getDownloadSVG() {
		return downloadSVG;
	}

	public String getDrillUpText() {
		return drillUpText;
	}

	public String getLoading() {
		return loading;
	}

	public List<String> getMonths() {
		return months;
	}

	public String getNoData() {
		return noData;
	}

	public List<String> getNumericSymbols() {
		return numericSymbols;
	}

	public String getPrintChart() {
		return printChart;
	}

	public String getResetZoom() {
		return resetZoom;
	}

	public String getResetZoomTitle() {
		return resetZoomTitle;
	}

	public List<String> getShortMonths() {
		return shortMonths;
	}

	public String getThousandsSep() {
		return thousandsSep;
	}

	public List<String> getWeekdays() {
		return weekdays;
	}

}
