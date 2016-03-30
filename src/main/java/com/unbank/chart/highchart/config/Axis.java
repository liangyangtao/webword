package com.unbank.chart.highchart.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.unbank.chart.highchart.config.Tooltip.DateFormat;
import com.unbank.chart.highchart.type.Align;
import com.unbank.chart.highchart.type.VerticalAlign;
import com.unbank.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class Axis {

	/**
	 * for axis only
	 * 
	 * @author zile
	 * 
	 */
	public static class Title {

		private final String align;
		private final Boolean enabled; // deprecated
		private final Number margin;
		private final Number offset;
		private final Number rotation;
		private final Style style;
		private final String text;

		public Title(TitleBuilder builder) {
			this.align = builder.align;
			this.enabled = builder.enabled;
			this.margin = builder.margin;
			this.offset = builder.offset;
			this.rotation = builder.rotation;
			this.style = builder.style;
			this.text = builder.text;
		}

		public String getAlign() {
			return align;
		}

		public Boolean getEnabled() {
			return enabled;
		}

		public Number getMargin() {
			return margin;
		}

		public Number getOffset() {
			return offset;
		}

		public Number getRotation() {
			return rotation;
		}

		public Style getStyle() {
			return style;
		}

		public String getText() {
			return text;
		}

	}

	public static class TitleBuilder implements Builder<Title> {

		private String align;
		private Boolean enabled; // deprecated
		private Number margin;
		private Number offset;
		private Number rotation;
		private Style style;
		private String text;

		public TitleBuilder align(Align align) {
			this.align = align.getName();
			return this;
		}

		/**
		 * 
		 * @param enabled
		 * @return
		 */
		@Deprecated
		public TitleBuilder enabled(Boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public TitleBuilder margin(Number margin) {
			this.margin = margin;
			return this;
		}

		public TitleBuilder offset(Number offset) {
			this.offset = offset;
			return this;
		}

		public TitleBuilder rotation(Number rotation) {
			this.rotation = rotation;
			return this;
		}

		public TitleBuilder style(Style style) {
			this.style = style;
			return this;
		}

		public TitleBuilder text(String text) {
			this.text = text;
			return this;
		}

		@Override
		public Title build() {
			return new Title(this);
		}

	}

	/**
	 * for plotBinds and plotLines Only
	 * 
	 * @author zile
	 * 
	 */
	public static class Label {

		private final String align; // Align
		private final Number rotation;
		private final Style style;
		private final String text;
		private final String textAlign; // Align
		private final Boolean useHTML;
		private final String verticalAlign; // VerticalAlign
		private final Number x;
		private final Number y;

		public Label(LabelBuilder builder) {
			this.align = builder.align;
			this.rotation = builder.rotation;
			this.style = builder.style;
			this.text = builder.text;
			this.textAlign = builder.textAlign;
			this.useHTML = builder.useHTML;
			this.verticalAlign = builder.verticalAlign;
			this.x = builder.x;
			this.y = builder.y;
		}

		public String getAlign() {
			return align;
		}

		public Number getRotation() {
			return rotation;
		}

		public Style getStyle() {
			return style;
		}

		public String getText() {
			return text;
		}

		public String getTextAlign() {
			return textAlign;
		}

		public Boolean getUseHTML() {
			return useHTML;
		}

		public String getVerticalAlign() {
			return verticalAlign;
		}

		public Number getX() {
			return x;
		}

		public Number getY() {
			return y;
		}

	}

	public static class LabelBuilder implements Builder<Label> {

		private String align; // Align
		private Number rotation;
		private Style style;
		private String text;
		private String textAlign; // Align
		private Boolean useHTML;
		private String verticalAlign; // VerticalAlign
		private Number x;
		private Number y;

		public LabelBuilder align(Align align) {
			this.align = align.getName();
			return this;
		}

		public LabelBuilder rotation(Number rotation) {
			this.rotation = rotation;
			return this;
		}

		public LabelBuilder style(Style style) {
			this.style = style;
			return this;
		}

		public LabelBuilder text(String text) {
			this.text = text;
			return this;
		}

		public LabelBuilder textAlign(Align textAlign) {
			this.textAlign = textAlign.getName();
			return this;
		}

		public LabelBuilder useHTML(Boolean useHTML) {
			this.useHTML = useHTML;
			return this;
		}

		public LabelBuilder verticalAlign(VerticalAlign verticalAlign) {
			this.verticalAlign = verticalAlign.getName();
			return this;
		}

		public LabelBuilder x(Number x) {
			this.x = x;
			return this;
		}

		public LabelBuilder y(Number y) {
			this.y = y;
			return this;
		}

		@Override
		public Label build() {
			return new Label(this);
		}

	}

	/**
	 * for Axis only
	 * 
	 * @author zile
	 * 
	 */
	public static class Labels {

		private final String align; // Align
		private final Number distance;
		private final Boolean enabled;
		private final String format;
		// private Function formatter;
		private final Number maxStaggerLines;
		private final String overflow;
		private final Number rotation;
		private final Number staggerLines;
		private final Number step;
		private final Style style;
		private final Boolean useHTML;
		private final Number x;
		private final Number y;
		private final Number zIndex;

		private Labels(LabelsBuilder builder) {
			this.align = builder.align;
			this.distance = builder.distance;
			this.enabled = builder.enabled;
			this.format = builder.format;
			this.maxStaggerLines = builder.maxStaggerLines;
			this.overflow = builder.overflow;
			this.rotation = builder.rotation;
			this.staggerLines = builder.staggerLines;
			this.step = builder.step;
			this.style = builder.style;
			this.useHTML = builder.useHTML;
			this.x = builder.x;
			this.y = builder.y;
			this.zIndex = builder.zIndex;
		}

		public String getAlign() {
			return align;
		}

		public Number getDistance() {
			return distance;
		}

		public Boolean getEnabled() {
			return enabled;
		}

		public String getFormat() {
			return format;
		}

		public Number getMaxStaggerLines() {
			return maxStaggerLines;
		}

		public String getOverflow() {
			return overflow;
		}

		public Number getRotation() {
			return rotation;
		}

		public Number getStaggerLines() {
			return staggerLines;
		}

		public Number getStep() {
			return step;
		}

		public Style getStyle() {
			return style;
		}

		public Boolean getUseHTML() {
			return useHTML;
		}

		public Number getX() {
			return x;
		}

		public Number getY() {
			return y;
		}

		public Number getzIndex() {
			return zIndex;
		}

	}

	public static class LabelsBuilder implements Builder<Labels> {

		private String align; // Align
		private Number distance;
		private Boolean enabled;
		private String format;
		// private Function formatter;
		private Number maxStaggerLines;
		private String overflow;
		private Number rotation;
		private Number staggerLines;
		private Number step;
		private Style style;
		private Boolean useHTML;
		private Number x;
		private Number y;
		private Number zIndex;

		public LabelsBuilder align(Align align) {
			this.align = align.getName();
			return this;
		}

		public LabelsBuilder distance(Number distance) {
			this.distance = distance;
			return this;
		}

		public LabelsBuilder enabled(Boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public LabelsBuilder format(String format) {
			this.format = format;
			return this;
		}

		public LabelsBuilder maxStaggerLines(Number maxStaggerLines) {
			this.maxStaggerLines = maxStaggerLines;
			return this;
		}

		public LabelsBuilder overflow(String overflow) {
			this.overflow = overflow;
			return this;
		}

		public LabelsBuilder rotation(Number rotation) {
			this.rotation = rotation;
			return this;
		}

		public LabelsBuilder staggerLines(Number staggerLines) {
			this.staggerLines = staggerLines;
			return this;
		}

		public LabelsBuilder step(Number step) {
			this.step = step;
			return this;
		}

		public LabelsBuilder style(Style style) {
			this.style = style;
			return this;
		}

		public LabelsBuilder useHTML(Boolean useHTML) {
			this.useHTML = useHTML;
			return this;
		}

		public LabelsBuilder x(Number x) {
			this.x = x;
			return this;
		}

		public LabelsBuilder y(Number y) {
			this.y = y;
			return this;
		}

		public LabelsBuilder zIndex(Number zIndex) {
			this.zIndex = zIndex;
			return this;
		}

		@Override
		public Labels build() {
			return new Labels(this);
		}

	}

	/**
	 * 
	 * 
	 * An array of colored bands stretching across the plot area marking an
	 * interval on the axis.
	 * 
	 * @author zile
	 * 
	 */
	public static class PlotBands {

		private final Object color;
		// private Event events;
		private final Number from;
		private final String id;
		private final Label label;
		private final Number to;
		private final Number zIndex;

		public PlotBands(PlotBandsBuilder builder) {
			this.color = builder.color;
			this.from = builder.from;
			this.id = builder.id;
			this.label = builder.label;
			this.to = builder.to;
			this.zIndex = builder.zIndex;
		}

		public Object getColor() {
			return color;
		}

		public Number getFrom() {
			return from;
		}

		public String getId() {
			return id;
		}

		public Label getLabel() {
			return label;
		}

		public Number getTo() {
			return to;
		}

		public Number getzIndex() {
			return zIndex;
		}

	}

	public static class PlotBandsBuilder implements Builder<PlotBands> {

		private Object color;
		// private Event events;
		private Number from;
		private String id;
		private Label label;
		private Number to;
		private Number zIndex;

		public PlotBandsBuilder color(String color) {
			this.color = color;
			return this;
		}

		public PlotBandsBuilder color(Color color) {
			this.color = color;
			return this;
		}

		public PlotBandsBuilder from(Number from) {
			this.from = from;
			return this;
		}

		public PlotBandsBuilder id(String id) {
			this.id = id;
			return this;
		}

		public PlotBandsBuilder label(Label label) {
			this.label = label;
			return this;
		}

		public PlotBandsBuilder to(Number to) {
			this.to = to;
			return this;
		}

		public PlotBandsBuilder zIndex(Number zIndex) {
			this.zIndex = zIndex;
			return this;
		}

		@Override
		public PlotBands build() {
			return new PlotBands(this);
		}

	}

	/**
	 * An array of lines stretching across the plot area, marking a specific
	 * value on one of the axes.
	 * 
	 * @author zile
	 * 
	 */
	public static class PlotLines {

		private final Object color;
		private final String dashStyle;
		// private Event events;
		private final String id;
		private final Label label;
		private final Number value;
		private final Number width;
		private final Number zIndex;

		public PlotLines(PlotLinesBuilder builder) {
			this.color = builder.color;
			this.dashStyle = builder.dashStyle;
			this.id = builder.id;
			this.label = builder.label;
			this.value = builder.value;
			this.width = builder.width;
			this.zIndex = builder.zIndex;
		}

		public Object getColor() {
			return color;
		}

		public String getDashStyle() {
			return dashStyle;
		}

		public String getId() {
			return id;
		}

		public Label getLabel() {
			return label;
		}

		public Number getValue() {
			return value;
		}

		public Number getWidth() {
			return width;
		}

		public Number getzIndex() {
			return zIndex;
		}

	}

	public static class PlotLinesBuilder implements Builder<PlotLines> {

		private Object color;
		private String dashStyle;
		// private Event events;
		private String id;
		private Label label;
		private Number value;
		private Number width;
		private Number zIndex;

		public PlotLinesBuilder color(String color) {
			this.color = color;
			return this;
		}

		public PlotLinesBuilder color(Color color) {
			this.color = color;
			return this;
		}

		public PlotLinesBuilder dashStyle(String dashStyle) {
			this.dashStyle = dashStyle;
			return this;
		}

		public PlotLinesBuilder id(String id) {
			this.id = id;
			return this;
		}

		public PlotLinesBuilder label(Label label) {
			this.label = label;
			return this;
		}

		public PlotLinesBuilder value(Number value) {
			this.value = value;
			return this;
		}

		public PlotLinesBuilder width(Number width) {
			this.width = width;
			return this;
		}

		public PlotLinesBuilder zIndex(Number zIndex) {
			this.zIndex = zIndex;
			return this;
		}

		@Override
		public PlotLines build() {
			return new PlotLines(this);
		}

	}

	protected static class AxisBuilder implements Builder<Axis> {

		private Boolean allowDecimals;
		private Object alternateGridColor; // String|Color
		private List<String> categories; //
		private Number ceiling;
		private DateFormat dateTimeLabelFormats;
		private Boolean endOnTick;
		// private Event events;
		private Number floor;
		private Object gridLineColor; // String|Color
		private String gridLineDashStyle;
		private Number gridLineWidth;
		private Number gridZIndex;
		private String id;
		private Labels labels;
		private Object lineColor; // String|Color
		private Number lineWidth;
		private Number linkedTo;
		private Number max;
		private Number maxPadding;
		private Number maxZoom; // deprecated
		private Number min;
		private Number minPadding;
		private Number minRange;
		private Number minTickInterval;
		private Object minorGridLineColor; // String|Color
		private String minorGridLineDashStyle;
		private Number minorGridLineWidth;
		private Object minorTickColor; // String|Color
		private Number minorTickInterval;
		private Number minorTickLength;
		private String minorTickPosition;
		private Number minorTickWidth;
		private Number offset;
		private Boolean opposite;
		private List<PlotBands> plotBands;
		private List<PlotLines> plotLines;
		private Boolean reversed;
		private Boolean showEmpty;
		private Boolean showFirstLabel;
		private Boolean showLastLabel;
		private Boolean startOfWeek;
		private Boolean startOnTick;
		private Object tickColor; // String|Color
		private Number tickInterval;
		private Number tickLength;
		private Number tickPixelInterval;
		private String tickPosition;
		// private Function tickPositioner;
		private List<Number> tickPositions; // [0, 1, 2, 4, 8]
		private Number tickWidth;
		private String tickmarkPlacement;
		private Title title;
		private String type;

		public AxisBuilder allowDecimals(Boolean allowDecimals) {
			this.allowDecimals = allowDecimals;
			return this;
		}

		public AxisBuilder alternateGridColor(String alternateGridColor) {
			this.alternateGridColor = alternateGridColor;
			return this;
		}

		public AxisBuilder alternateGridColor(Color color) {
			this.alternateGridColor = color;
			return this;
		}

		public AxisBuilder categories(List<String> categories) {
			this.categories = categories;
			return this;
		}

		public AxisBuilder categories(String... categories) {
			this.categories = new ArrayList<String>();
			for (String e : categories) {
				this.categories.add(e);
			}
			return this;
		}

		public AxisBuilder ceiling(Number ceiling) {
			this.ceiling = ceiling;
			return this;
		}

		public AxisBuilder dateTimeLabelFormats(DateFormat dateTimeLabelFormats) {
			this.dateTimeLabelFormats = dateTimeLabelFormats;
			return this;
		}

		public AxisBuilder endOnTick(Boolean endOnTick) {
			this.endOnTick = endOnTick;
			return this;
		}

		public AxisBuilder floor(Number floor) {
			this.floor = floor;
			return this;
		}

		public AxisBuilder gridLineColor(String gridLineColor) {
			this.gridLineColor = gridLineColor;
			return this;
		}

		public AxisBuilder gridLineColor(Color color) {
			this.gridLineColor = color;
			return this;
		}

		public AxisBuilder gridLineDashStyle(String gridLineDashStyle) {
			this.gridLineDashStyle = gridLineDashStyle;
			return this;
		}

		public AxisBuilder gridLineWidth(Number gridLineWidth) {
			this.gridLineWidth = gridLineWidth;
			return this;
		}

		public AxisBuilder gridZIndex(Number gridZIndex) {
			this.gridZIndex = gridZIndex;
			return this;
		}

		public AxisBuilder id(String id) {
			this.id = id;
			return this;
		}

		public AxisBuilder labels(Labels labels) {
			this.labels = labels;
			return this;
		}

		public AxisBuilder lineColor(String lineColor) {
			this.lineColor = lineColor;
			return this;
		}

		public AxisBuilder lineColor(Color color) {
			this.lineColor = color;
			return this;
		}

		public AxisBuilder lineWidth(Number lineWidth) {
			this.lineWidth = lineWidth;
			return this;
		}

		public AxisBuilder linkedTo(Number linkedTo) {
			this.linkedTo = linkedTo;
			return this;
		}

		public AxisBuilder max(Number max) {
			this.max = max;
			return this;
		}

		public AxisBuilder maxPadding(Number maxPadding) {
			this.maxPadding = maxPadding;
			return this;
		}

		public AxisBuilder maxZoom(Number maxZoom) {
			this.maxZoom = maxZoom;
			return this;
		}

		public AxisBuilder min(Number min) {
			this.min = min;
			return this;
		}

		public AxisBuilder minPadding(Number minPadding) {
			this.minPadding = minPadding;
			return this;
		}

		public AxisBuilder minRange(Number minRange) {
			this.minRange = minRange;
			return this;
		}

		public AxisBuilder minTickInterval(Number minTickInterval) {
			this.minTickInterval = minTickInterval;
			return this;
		}

		public AxisBuilder minorGridLineColor(Object minorGridLineColor) {
			this.minorGridLineColor = minorGridLineColor;
			return this;
		}

		public AxisBuilder minorGridLineDashStyle(String minorGridLineDashStyle) {
			this.minorGridLineDashStyle = minorGridLineDashStyle;
			return this;
		}

		public AxisBuilder minorGridLineWidth(Number minorGridLineWidth) {
			this.minorGridLineWidth = minorGridLineWidth;
			return this;
		}

		public AxisBuilder minorTickColor(String minorTickColor) {
			this.minorTickColor = minorTickColor;
			return this;
		}

		public AxisBuilder minorTickColor(Color color) {
			this.minorTickColor = color;
			return this;
		}

		public AxisBuilder minorTickInterval(Number minorTickInterval) {
			this.minorTickInterval = minorTickInterval;
			return this;
		}

		public AxisBuilder minorTickLength(Number minorTickLength) {
			this.minorTickLength = minorTickLength;
			return this;
		}

		public AxisBuilder minorTickPosition(String minorTickPosition) {
			this.minorTickPosition = minorTickPosition;
			return this;
		}

		public AxisBuilder minorTickWidth(Number minorTickWidth) {
			this.minorTickWidth = minorTickWidth;
			return this;
		}

		public AxisBuilder offset(Number offset) {
			this.offset = offset;
			return this;
		}

		public AxisBuilder opposite(Boolean opposite) {
			this.opposite = opposite;
			return this;
		}

		public AxisBuilder plotBands(PlotBands... plotBands) {
			plotBands(Arrays.asList(plotBands));
			return this;
		}

		public AxisBuilder plotBands(List<PlotBands> plotBands) {
			this.plotBands = plotBands;
			return this;
		}

		public AxisBuilder plotLines(PlotLines... plotLines) {
			plotLines(Arrays.asList(plotLines));
			return this;
		}

		public AxisBuilder plotLines(List<PlotLines> plotLines) {
			this.plotLines = plotLines;
			return this;
		}

		public AxisBuilder reversed(Boolean reversed) {
			this.reversed = reversed;
			return this;
		}

		public AxisBuilder showEmpty(Boolean showEmpty) {
			this.showEmpty = showEmpty;
			return this;
		}

		public AxisBuilder showFirstLabel(Boolean showFirstLabel) {
			this.showFirstLabel = showFirstLabel;
			return this;
		}

		public AxisBuilder showLastLabel(Boolean showLastLabel) {
			this.showLastLabel = showLastLabel;
			return this;
		}

		public AxisBuilder startOfWeek(Boolean startOfWeek) {
			this.startOfWeek = startOfWeek;
			return this;
		}

		public AxisBuilder startOnTick(Boolean startOnTick) {
			this.startOnTick = startOnTick;
			return this;
		}

		public AxisBuilder tickColor(String tickColor) {
			this.tickColor = tickColor;
			return this;
		}

		public AxisBuilder tickColor(Color color) {
			this.tickColor = color;
			return this;
		}

		public AxisBuilder tickInterval(Number tickInterval) {
			this.tickInterval = tickInterval;
			return this;
		}

		public AxisBuilder tickLength(Number tickLength) {
			this.tickLength = tickLength;
			return this;
		}

		public AxisBuilder tickPixelInterval(Number tickPixelInterval) {
			this.tickPixelInterval = tickPixelInterval;
			return this;
		}

		public AxisBuilder tickPosition(String tickPosition) {
			this.tickPosition = tickPosition;
			return this;
		}

		public AxisBuilder tickPositions(List<Number> tickPositions) {
			this.tickPositions = tickPositions;
			return this;
		}

		public AxisBuilder tickPositions(Number... tickPositions) {
			this.tickPositions = new ArrayList<Number>();
			for (Number e : tickPositions)
				this.tickPositions.add(e);
			return this;
		}

		public AxisBuilder tickWidth(Number tickWidth) {
			this.tickWidth = tickWidth;
			return this;
		}

		public AxisBuilder tickmarkPlacement(String tickmarkPlacement) {
			this.tickmarkPlacement = tickmarkPlacement;
			return this;
		}

		public AxisBuilder title(Title title) {
			this.title = title;
			return this;
		}

		public AxisBuilder type(String type) {
			this.type = type;
			return this;
		}

		@Override
		public Axis build() {
			return new Axis(this);
		}

	}

	private final Boolean allowDecimals;
	private final Object alternateGridColor; // String|Color
	private final List<String> categories; //
	private final Number ceiling;
	private final DateFormat dateTimeLabelFormats;
	private final Boolean endOnTick;
	// private Event events;
	private final Number floor;
	private final Object gridLineColor; // String|Color
	private final String gridLineDashStyle;
	private final Number gridLineWidth;
	private final Number gridZIndex;
	private final String id;
	private final Labels labels;
	private final Object lineColor; // String|Color
	private final Number lineWidth;
	private final Number linkedTo;
	private final Number max;
	private final Number maxPadding;
	private final Number maxZoom; // deprecated
	private final Number min;
	private final Number minPadding;
	private final Number minRange;
	private final Number minTickInterval;
	private final Object minorGridLineColor; // String|Color
	private final String minorGridLineDashStyle;
	private final Number minorGridLineWidth;
	private final Object minorTickColor; // String|Color
	private final Number minorTickInterval;
	private final Number minorTickLength;
	private final String minorTickPosition;
	private final Number minorTickWidth;
	private final Number offset;
	private final Boolean opposite;
	private final List<PlotBands> plotBands;
	private final List<PlotLines> plotLines;
	private final Boolean reversed;
	private final Boolean showEmpty;
	private final Boolean showFirstLabel;
	private final Boolean showLastLabel;
	private final Boolean startOfWeek;
	private final Boolean startOnTick;
	private final Object tickColor; // String|Color
	private final Number tickInterval;
	private final Number tickLength;
	private final Number tickPixelInterval;
	private final String tickPosition;
	// private Function tickPositioner;
	private final List<Number> tickPositions; // [0, 1, 2, 4, 8]
	private final Number tickWidth;
	private final String tickmarkPlacement;
	private final Title title;
	private final String type;

	protected Axis(AxisBuilder builder) {
		this.allowDecimals = builder.allowDecimals;

		this.alternateGridColor = builder.alternateGridColor;
		this.categories = builder.categories;
		this.ceiling = builder.ceiling;
		this.dateTimeLabelFormats = builder.dateTimeLabelFormats;
		this.endOnTick = builder.endOnTick;
		this.floor = builder.floor;
		this.gridLineColor = builder.gridLineColor;
		this.gridLineDashStyle = builder.gridLineDashStyle;
		this.gridLineWidth = builder.gridLineWidth;
		this.gridZIndex = builder.gridZIndex;

		this.id = builder.id;
		this.labels = builder.labels;
		this.lineColor = builder.lineColor;
		this.lineWidth = builder.lineWidth;
		this.linkedTo = builder.linkedTo;
		this.max = builder.max;
		this.maxPadding = builder.maxPadding;
		this.maxZoom = builder.maxZoom;
		this.min = builder.min;
		this.minPadding = builder.minPadding;

		this.minRange = builder.minRange;
		this.minTickInterval = builder.minTickInterval;
		this.minorGridLineColor = builder.minorGridLineColor;
		this.minorGridLineDashStyle = builder.minorGridLineDashStyle;
		this.minorGridLineWidth = builder.minorGridLineWidth;
		this.minorTickColor = builder.minorTickColor;
		this.minorTickInterval = builder.minorTickInterval;
		this.minorTickLength = builder.minorTickLength;
		this.minorTickPosition = builder.minorTickPosition;
		this.minorTickWidth = builder.minorTickWidth;

		this.offset = builder.offset;
		this.opposite = builder.opposite;
		this.plotBands = builder.plotBands;
		this.plotLines = builder.plotLines;
		this.reversed = builder.reversed;
		this.showEmpty = builder.showEmpty;
		this.showFirstLabel = builder.showFirstLabel;
		this.showLastLabel = builder.showLastLabel;
		this.startOfWeek = builder.startOfWeek;
		this.startOnTick = builder.startOnTick;

		this.tickColor = builder.tickColor;
		this.tickInterval = builder.tickInterval;
		this.tickLength = builder.tickLength;
		this.tickPixelInterval = builder.tickPixelInterval;
		this.tickPosition = builder.tickPosition;
		this.tickPositions = builder.tickPositions;
		this.tickWidth = builder.tickWidth;
		this.tickmarkPlacement = builder.tickmarkPlacement;
		this.title = builder.title;
		this.type = builder.type;
	}

	public Boolean getAllowDecimals() {
		return allowDecimals;
	}

	public Object getAlternateGridColor() {
		return alternateGridColor;
	}

	public List<String> getCategories() {
		return categories;
	}

	public Number getCeiling() {
		return ceiling;
	}

	public DateFormat getDateTimeLabelFormats() {
		return dateTimeLabelFormats;
	}

	public Boolean getEndOnTick() {
		return endOnTick;
	}

	public Number getFloor() {
		return floor;
	}

	public Object getGridLineColor() {
		return gridLineColor;
	}

	public String getGridLineDashStyle() {
		return gridLineDashStyle;
	}

	public Number getGridLineWidth() {
		return gridLineWidth;
	}

	public Number getGridZIndex() {
		return gridZIndex;
	}

	public String getId() {
		return id;
	}

	public Labels getLabels() {
		return labels;
	}

	public Object getLineColor() {
		return lineColor;
	}

	public Number getLineWidth() {
		return lineWidth;
	}

	public Number getLinkedTo() {
		return linkedTo;
	}

	public Number getMax() {
		return max;
	}

	public Number getMaxPadding() {
		return maxPadding;
	}

	public Number getMaxZoom() {
		return maxZoom;
	}

	public Number getMin() {
		return min;
	}

	public Number getMinPadding() {
		return minPadding;
	}

	public Number getMinRange() {
		return minRange;
	}

	public Number getMinTickInterval() {
		return minTickInterval;
	}

	public Object getMinorGridLineColor() {
		return minorGridLineColor;
	}

	public String getMinorGridLineDashStyle() {
		return minorGridLineDashStyle;
	}

	public Number getMinorGridLineWidth() {
		return minorGridLineWidth;
	}

	public Object getMinorTickColor() {
		return minorTickColor;
	}

	public Number getMinorTickInterval() {
		return minorTickInterval;
	}

	public Number getMinorTickLength() {
		return minorTickLength;
	}

	public String getMinorTickPosition() {
		return minorTickPosition;
	}

	public Number getMinorTickWidth() {
		return minorTickWidth;
	}

	public Number getOffset() {
		return offset;
	}

	public Boolean getOpposite() {
		return opposite;
	}

	public List<PlotBands> getPlotBands() {
		return plotBands;
	}

	public List<PlotLines> getPlotLines() {
		return plotLines;
	}

	public Boolean getReversed() {
		return reversed;
	}

	public Boolean getShowEmpty() {
		return showEmpty;
	}

	public Boolean getShowFirstLabel() {
		return showFirstLabel;
	}

	public Boolean getShowLastLabel() {
		return showLastLabel;
	}

	public Boolean getStartOfWeek() {
		return startOfWeek;
	}

	public Boolean getStartOnTick() {
		return startOnTick;
	}

	public Object getTickColor() {
		return tickColor;
	}

	public Number getTickInterval() {
		return tickInterval;
	}

	public Number getTickLength() {
		return tickLength;
	}

	public Number getTickPixelInterval() {
		return tickPixelInterval;
	}

	public String getTickPosition() {
		return tickPosition;
	}

	public List<Number> getTickPositions() {
		return tickPositions;
	}

	public Number getTickWidth() {
		return tickWidth;
	}

	public String getTickmarkPlacement() {
		return tickmarkPlacement;
	}

	public Title getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

}
