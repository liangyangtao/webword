package com.unbank.chart.highchart.config;

import java.util.ArrayList;
import java.util.List;

import com.unbank.chart.highchart.type.PlotOptionsType;
import com.unbank.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class Highchart {

	private final Boolean alignTicks;
	private final Object animation;// Boolean|Animation
	private final Object backgroundColor; // String|Color
	private final Object borderColor;// String|Color
	private final Number borderRadius;
	private final Number borderWidth;
	private final String className;
	private final String defaultSeriesType;
	// private final Object events;
	private final Number height;
	private final Boolean ignoreHiddenSeries;
	private final Boolean inverted;
	private final List<Number> margin;
	private final Number marginBottom;
	private final Number marginLeft;
	private final Number marginRight;
	private final Number marginTop;
	private final Options3d options3d;
	private final String panKey;
	private final Boolean panning;
	private final String pinchType;
	private final Object plotBackgroundColor;// String|Color
	private final String plotBackgroundImage;
	private final Object plotBorderColor;// String|Color
	private final Number plotBorderWidth;
	private final Object plotShadow;// Boolean|Object
	private final Boolean polar;
	private final Boolean reflow;
	private final Object renderTo;// String|Object
	private final ResetZoomButton resetZoomButton;
	private final Object selectionMarkerFill;// String|Color
	private final Object shadow;// String|Object
	private final Boolean showAxes;
	private final List<Number> spacing;
	private final Number spacingBottom;
	private final Number spacingLeft;
	private final Number spacingRight;
	private final Number spacingTop;
	private final Style style;// css object
	private final String type;
	private final Number width;
	private final String zoomType;

	/**
	 * 
	 * @param builder
	 */
	private Highchart(HighchartBuilder builder) {
		this.alignTicks = builder.alignTicks;
		this.animation = builder.animation;
		this.backgroundColor = builder.backgroundColor;
		this.borderColor = builder.borderColor;
		this.borderRadius = builder.borderRadius;
		this.borderWidth = builder.borderWidth;
		this.className = builder.className;
		this.defaultSeriesType = builder.defaultSeriesType;
		this.height = builder.height;
		this.ignoreHiddenSeries = builder.ignoreHiddenSeries;
		this.inverted = builder.inverted;
		this.margin = builder.margin;
		this.marginBottom = builder.marginBottom;
		this.marginLeft = builder.marginLeft;
		this.marginRight = builder.marginRight;
		this.marginTop = builder.marginTop;
		this.options3d = builder.options3d;
		this.panKey = builder.panKey;
		this.panning = builder.panning;
		this.pinchType = builder.pinchType;
		this.plotBackgroundColor = builder.plotBackgroundColor;
		this.plotBackgroundImage = builder.plotBackgroundImage;
		this.plotBorderColor = builder.plotBorderColor;
		this.plotBorderWidth = builder.plotBorderWidth;
		this.plotShadow = builder.plotShadow;
		this.polar = builder.polar;
		this.reflow = builder.reflow;
		this.renderTo = builder.renderTo;
		this.resetZoomButton = builder.resetZoomButton;
		this.selectionMarkerFill = builder.selectionMarkerFill;
		this.shadow = builder.shadow;
		this.showAxes = builder.showAxes;
		this.spacing = builder.spacing;
		this.spacingBottom = builder.spacingBottom;
		this.spacingLeft = builder.spacingLeft;
		this.spacingRight = builder.spacingRight;
		this.spacingTop = builder.spacingTop;
		this.style = builder.style;
		this.type = builder.type;
		this.width = builder.width;
		this.zoomType = builder.zoomType;
	}

	public static class HighchartBuilder implements Builder<Highchart> {

		private Boolean alignTicks;
		private Object animation;// Boolean|Animation
		private Object backgroundColor; // String|Color
		private Object borderColor;// String|Color
		private Number borderRadius;
		private Number borderWidth;
		private String className;
		private String defaultSeriesType;
		private Number height;
		private Boolean ignoreHiddenSeries;
		private Boolean inverted;
		private List<Number> margin;
		private Number marginBottom;
		private Number marginLeft;
		private Number marginRight;
		private Number marginTop;
		private Options3d options3d;
		private String panKey;
		private Boolean panning;
		private String pinchType;
		private Object plotBackgroundColor;
		private String plotBackgroundImage;
		private Object plotBorderColor;
		private Number plotBorderWidth;
		private Object plotShadow;//
		private Boolean polar;
		private Boolean reflow;
		private Object renderTo;
		private ResetZoomButton resetZoomButton;
		private Object selectionMarkerFill;
		private Object shadow;//
		private Boolean showAxes;
		private List<Number> spacing;
		private Number spacingBottom;
		private Number spacingLeft;
		private Number spacingRight;
		private Number spacingTop;
		private Style style;// css object
		private String type;
		private Number width;
		private String zoomType;

		public HighchartBuilder(PlotOptionsType type) {
			if (type != null) {
				this.type = type.getName();
			}
		}

		public HighchartBuilder alignTicks(Boolean alignTicks) {
			this.alignTicks = alignTicks;
			return this;
		}

		public HighchartBuilder animation(Animation animation) {
			this.animation = animation;
			return this;
		}

		public HighchartBuilder animation(Boolean animation) {
			this.animation = animation;
			return this;
		}

		public HighchartBuilder backgroundColor(String backgroundColor) {
			this.backgroundColor = backgroundColor;
			return this;
		}

		public HighchartBuilder backgroundColor(Color color) {
			this.backgroundColor = color;
			return this;
		}

		public HighchartBuilder borderColor(String borderColor) {
			this.borderColor = borderColor;
			return this;
		}

		public HighchartBuilder borderColor(Color color) {
			this.borderColor = color;
			return this;
		}

		public HighchartBuilder borderRadius(Number borderRadius) {
			this.borderRadius = borderRadius;
			return this;
		}

		public HighchartBuilder borderWidth(Number borderWidth) {
			this.borderWidth = borderWidth;
			return this;
		}

		public HighchartBuilder className(String className) {
			this.className = className;
			return this;
		}

		@Deprecated
		public HighchartBuilder defaultSeriesType(String defaultSeriesType) {
			this.defaultSeriesType = defaultSeriesType;
			return this;
		}

		public HighchartBuilder height(Number height) {
			this.height = height;
			return this;
		}

		public HighchartBuilder ignoreHiddenSeries(Boolean ignoreHiddenSeries) {
			this.ignoreHiddenSeries = ignoreHiddenSeries;
			return this;
		}

		public HighchartBuilder inverted(Boolean inverted) {
			this.inverted = inverted;
			return this;
		}

		public HighchartBuilder margin(Number top, Number right, Number bottom,
				Number left) {
			this.margin = new ArrayList<Number>();
			margin.add(top);
			margin.add(right);
			margin.add(bottom);
			margin.add(left);
			return this;
		}

		public HighchartBuilder marginBottom(Number marginBottom) {
			this.marginBottom = marginBottom;
			return this;
		}

		public HighchartBuilder marginLeft(Number marginLeft) {
			this.marginLeft = marginLeft;
			return this;
		}

		public HighchartBuilder marginRight(Number marginRight) {
			this.marginRight = marginRight;
			return this;
		}

		public HighchartBuilder marginTop(Number marginTop) {
			this.marginTop = marginTop;
			return this;
		}

		public HighchartBuilder options3d(Options3d options3d) {
			this.options3d = options3d;
			return this;
		}

		public HighchartBuilder panKey(String panKey) {
			this.panKey = panKey;
			return this;
		}

		public HighchartBuilder panning(Boolean panning) {
			this.panning = panning;
			return this;
		}

		public HighchartBuilder pinchType(String pinchType) {
			this.pinchType = pinchType;
			return this;
		}

		public HighchartBuilder plotBackgroundColor(String plotBackgroundColor) {
			this.plotBackgroundColor = plotBackgroundColor;
			return this;
		}

		public HighchartBuilder plotBackgroundColor(Color color) {
			this.plotBackgroundColor = color;
			return this;
		}

		public HighchartBuilder plotBackgroundImage(String plotBackgroundImage) {
			this.plotBackgroundImage = plotBackgroundImage;
			return this;
		}

		public HighchartBuilder plotBorderColor(String plotBorderColor) {
			this.plotBorderColor = plotBorderColor;
			return this;
		}

		public HighchartBuilder plotBorderColor(Color color) {
			this.plotBorderColor = color;
			return this;
		}

		public HighchartBuilder plotBorderWidth(Number plotBorderWidth) {
			this.plotBorderWidth = plotBorderWidth;
			return this;
		}

		public HighchartBuilder plotShadow(Boolean plotShadow) {
			this.plotShadow = plotShadow;
			return this;
		}

		/**
		 * <p>
		 * Whether to apply a drop shadow to the plot area. Requires that
		 * plotBackgroundColor be set. Since 2.3 the shadow can be an object
		 * configuration containing color, offsetX, offsetY, opacity and width.
		 * Defaults to false.
		 * </p>
		 * 
		 * <p>
		 * 未实现
		 * </p>
		 * 
		 * @param object
		 * @return
		 */
		@Deprecated
		public HighchartBuilder plotShadow(Object object) {
			// this.plotShadow = object;
			// TODO
			return this;
		}

		public HighchartBuilder polar(Boolean polar) {
			this.polar = polar;
			return this;
		}

		public HighchartBuilder reflow(Boolean reflow) {
			this.reflow = reflow;
			return this;
		}

		public HighchartBuilder renderTo(String renderTo) {
			this.renderTo = renderTo;
			return this;
		}

		/**
		 * 未实现
		 * 
		 * @param resetZoomButton
		 * @return
		 */
		@Deprecated
		public HighchartBuilder resetZoomButton(ResetZoomButton resetZoomButton) {
			this.resetZoomButton = resetZoomButton;
			// TODO resetZoomButton
			return this;
		}

		public HighchartBuilder selectionMarkerFill(String selectionMarkerFill) {
			this.selectionMarkerFill = selectionMarkerFill;
			return this;
		}

		public HighchartBuilder selectionMarkerFill(Color color) {
			this.selectionMarkerFill = color;
			return this;
		}

		public HighchartBuilder shadow(Boolean shadow) {
			this.shadow = shadow;
			return this;
		}

		/**
		 * Whether to apply a drop shadow to the outer chart area. Requires that
		 * backgroundColor be set. Since 2.3 the shadow can be an object
		 * configuration containing color, offsetX, offsetY, opacity and width.
		 * Defaults to false.
		 * 
		 * <p>
		 * 未实现
		 * </p>
		 * 
		 * @param object
		 * @return
		 */
		@Deprecated
		public HighchartBuilder shadow(Object object) {
			// this.shadow = object;
			// TODO
			return this;
		}

		public HighchartBuilder showAxes(Boolean showAxes) {
			this.showAxes = showAxes;
			return this;
		}

		public HighchartBuilder spacing(Number top, Number right,
				Number bottom, Number left) {
			this.spacing = new ArrayList<Number>();
			spacing.add(top);
			spacing.add(right);
			spacing.add(bottom);
			spacing.add(left);
			return this;
		}

		public HighchartBuilder spacingBottom(Number spacingBottom) {
			this.spacingBottom = spacingBottom;
			return this;
		}

		public HighchartBuilder spacingLeft(Number spacingLeft) {
			this.spacingLeft = spacingLeft;
			return this;
		}

		public HighchartBuilder spacingRight(Number spacingRight) {
			this.spacingRight = spacingRight;
			return this;
		}

		public HighchartBuilder spacingTop(Number spacingTop) {
			this.spacingTop = spacingTop;
			return this;
		}

		public HighchartBuilder style(Style style) {
			this.style = style;
			return this;
		}

		/**
		 * 一般情况下不需要手动调用这个方法，除非你使用的是抽象层次更高的图形类，如xxxCharts.
		 * 
		 * @param type
		 * @return
		 */
		protected HighchartBuilder type(PlotOptionsType type) {
			if (type != null) {
				this.type = type.getName();
			}
			return this;
		}

		public HighchartBuilder width(Number width) {
			this.width = width;
			return this;
		}

		public HighchartBuilder zoomType(String zoomType) {
			this.zoomType = zoomType;
			return this;
		}

		@Override
		public Highchart build() {
			return new Highchart(this);
		}

	}

	public Boolean getAlignTicks() {
		return alignTicks;
	}

	public Object getAnimation() {
		return animation;
	}

	public Object getBackgroundColor() {
		return backgroundColor;
	}

	public Object getBorderColor() {
		return borderColor;
	}

	public Number getBorderRadius() {
		return borderRadius;
	}

	public Number getBorderWidth() {
		return borderWidth;
	}

	public String getClassName() {
		return className;
	}

	public String getDefaultSeriesType() {
		return defaultSeriesType;
	}

	public Number getHeight() {
		return height;
	}

	public Boolean getIgnoreHiddenSeries() {
		return ignoreHiddenSeries;
	}

	public Boolean getInverted() {
		return inverted;
	}

	public List<Number> getMargin() {
		return margin;
	}

	public Number getMarginBottom() {
		return marginBottom;
	}

	public Number getMarginLeft() {
		return marginLeft;
	}

	public Number getMarginRight() {
		return marginRight;
	}

	public Number getMarginTop() {
		return marginTop;
	}

	public Options3d getOptions3d() {
		return options3d;
	}

	public String getPanKey() {
		return panKey;
	}

	public Boolean getPanning() {
		return panning;
	}

	public String getPinchType() {
		return pinchType;
	}

	public Object getPlotBackgroundColor() {
		return plotBackgroundColor;
	}

	public String getPlotBackgroundImage() {
		return plotBackgroundImage;
	}

	public Object getPlotBorderColor() {
		return plotBorderColor;
	}

	public Number getPlotBorderWidth() {
		return plotBorderWidth;
	}

	public Object getPlotShadow() {
		return plotShadow;
	}

	public Boolean getPolar() {
		return polar;
	}

	public Boolean getReflow() {
		return reflow;
	}

	public Object getRenderTo() {
		return renderTo;
	}

	public ResetZoomButton getResetZoomButton() {
		return resetZoomButton;
	}

	public Object getSelectionMarkerFill() {
		return selectionMarkerFill;
	}

	public Object getShadow() {
		return shadow;
	}

	public Boolean getShowAxes() {
		return showAxes;
	}

	public List<Number> getSpacing() {
		return spacing;
	}

	public Number getSpacingBottom() {
		return spacingBottom;
	}

	public Number getSpacingLeft() {
		return spacingLeft;
	}

	public Number getSpacingRight() {
		return spacingRight;
	}

	public Number getSpacingTop() {
		return spacingTop;
	}

	public Object getStyle() {
		return style;
	}

	public String getType() {
		return type;
	}

	public Number getWidth() {
		return width;
	}

	public String getZoomType() {
		return zoomType;
	}

}
