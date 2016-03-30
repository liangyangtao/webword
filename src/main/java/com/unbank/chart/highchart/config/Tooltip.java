package com.unbank.chart.highchart.config;

import java.util.ArrayList;
import java.util.List;

import com.unbank.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class Tooltip {

	public static enum DashStyles {

		SOLID("Solid"),

		SHORTDASH("ShortDash"),

		SHORTDOT("ShortDot"),

		SHORTDASHDOT("ShortDashDot"),

		SHORTDASHDOTDOT("ShortDashDotDot"),

		DOT("Dot"),

		DASH("Dash"),

		LONGDASH("LongDash"),

		DASHDOT("DashDot"),

		LONGDASHDOT("LongDashDot"),

		LONGDASHDOTDOT("LongDashDotDot");

		private final String name;

		private DashStyles(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public static class Crosshairs {

		private final Number width;
		private final Object color; // String|Color
		private final Number zIndex;
		private final String dashStyles; // DashStyles

		private Crosshairs(CrosshairsBuilder builder) {
			this.width = builder.width;
			this.color = builder.color;
			this.zIndex = builder.zIndex;
			this.dashStyles = builder.dashStyles;
		}

		public Number getWidth() {
			return width;
		}

		public Object getColor() {
			return color;
		}

		public Number getzIndex() {
			return zIndex;
		}

		public String getDashStyles() {
			return dashStyles;
		}

	}

	public static class CrosshairsBuilder implements Builder<Crosshairs> {

		private Number width;
		private Object color; // String|Color
		private Number zIndex;
		private String dashStyles; // DashStyles

		public CrosshairsBuilder width(Number width) {
			this.width = width;
			return this;
		}

		public CrosshairsBuilder color(String color) {
			this.color = color;
			return this;
		}

		public CrosshairsBuilder color(Color color) {
			this.color = color;
			return this;
		}

		public CrosshairsBuilder zIndex(Number zIndex) {
			this.zIndex = zIndex;
			return this;
		}

		public CrosshairsBuilder dashStyles(DashStyles dashStyles) {
			this.dashStyles = dashStyles.getName();
			return this;
		}

		@Override
		public Crosshairs build() {
			return new Crosshairs(this);
		}

	}

	/**
	 * 
	 * Default:
	 * 
	 * <p>
	 * millisecond:"%A, %b %e, %H:%M:%S.%L"
	 * </p>
	 * 
	 * <p>
	 * second:"%A, %b %e, %H:%M:%S",
	 * </p>
	 * 
	 * <p>
	 * minute:"%A, %b %e, %H:%M",
	 * </p>
	 * 
	 * <p>
	 * hour:"%A, %b %e, %H:%M",
	 * </p>
	 * 
	 * <p>
	 * day:"%A, %b %e, %Y",
	 * </p>
	 * 
	 * <p>
	 * week:"Week from %A, %b %e, %Y",
	 * </p>
	 * 
	 * <p>
	 * month:"%B %Y",
	 * </p>
	 * 
	 * <p>
	 * year:"%Y"
	 * </p>
	 * 
	 * @author zile
	 * 
	 */
	public static class DateFormat {

		private final String millisecond;
		private final String second;
		private final String minute;
		private final String hour;
		private final String day;
		private final String week;
		private final String month;
		private final String year;

		public DateFormat(DateFormatBuilder builder) {
			this.millisecond = builder.millisecond;
			this.second = builder.second;
			this.minute = builder.minute;
			this.hour = builder.hour;
			this.day = builder.day;
			this.week = builder.week;
			this.month = builder.month;
			this.year = builder.year;
		}

		public String getMillisecond() {
			return millisecond;
		}

		public String getSecond() {
			return second;
		}

		public String getMinute() {
			return minute;
		}

		public String getHour() {
			return hour;
		}

		public String getDay() {
			return day;
		}

		public String getWeek() {
			return week;
		}

		public String getMonth() {
			return month;
		}

		public String getYear() {
			return year;
		}

	}

	public static class DateFormatBuilder implements Builder<DateFormat> {

		private String millisecond;
		private String second;
		private String minute;
		private String hour;
		private String day;
		private String week;
		private String month;
		private String year;

		/**
		 * 
		 * 
		 * @param millisecond
		 *            default value '%A, %b %e, %H:%M:%S.%L'
		 * @return
		 */
		public DateFormatBuilder millisecond(String millisecond) {
			this.millisecond = millisecond;
			return this;
		}

		/**
		 * 
		 * @param second
		 *            default value '%A, %b %e, %H:%M:%S'
		 * @return
		 */
		public DateFormatBuilder second(String second) {
			this.second = second;
			return this;
		}

		/**
		 * 
		 * @param minute
		 *            default value '%A, %b %e, %H:%M'
		 * @return
		 */
		public DateFormatBuilder minute(String minute) {
			this.minute = minute;
			return this;
		}

		/**
		 * 
		 * @param hour
		 *            default value '%A, %b %e, %H:%M'
		 * @return
		 */
		public DateFormatBuilder hour(String hour) {
			this.hour = hour;
			return this;
		}

		/**
		 * 
		 * @param day
		 *            default value '%A, %b %e, %Y'
		 * @return
		 */
		public DateFormatBuilder day(String day) {
			this.day = day;
			return this;
		}

		/**
		 * 
		 * @param week
		 *            default value 'Week from %A, %b %e, %Y'
		 * @return
		 */
		public DateFormatBuilder week(String week) {
			this.week = week;
			return this;
		}

		/**
		 * 
		 * @param month
		 *            default value '%B %Y'
		 * @return
		 */
		public DateFormatBuilder month(String month) {
			this.month = month;
			return this;
		}

		/**
		 * 
		 * @param year
		 *            default value '%Y'
		 * @return
		 */
		public DateFormatBuilder year(String year) {
			this.year = year;
			return this;
		}

		/**
		 * 
		 */
		@Override
		public DateFormat build() {
			return new DateFormat(this);
		}

	}

	private final Object animation; // Boolean|Animation
	private final Object backgroundColor; // String|Color
	private final Object borderColor;// String|Color
	private final Number borderRadius;
	private final Number borderWidth;
	private final Object crosshairs; // Mixed:Boolean|List<Boolean>|Crosshairs|List<Crosshairs>,
	// x and y, list size 2
	private final DateFormat dateTimeLabelFormats;
	private final Boolean enabled;
	private final Boolean followPointer;
	private final Boolean followTouchMove;
	private final String footerFormat;
	// private Function formatter;
	private final String headerFormat;
	private final Number hideDelay;
	private final String pointFormat;
	// private Function positioner;
	private final Boolean shadow; // Boolean|Object?
	private final String shape;
	private final Boolean shared;
	private final Number snap;
	private final Style style;
	private final Boolean useHTML;
	private final Number valueDecimals;
	private final String valuePrefix;
	private final String valueSuffix;
	private final String xDateFormat;

	private Tooltip(TooltipBuilder builder) {
		this.animation = builder.animation;
		this.backgroundColor = builder.backgroundColor;
		this.borderColor = builder.borderColor;
		this.borderRadius = builder.borderRadius;
		this.borderWidth = builder.borderWidth;
		this.crosshairs = builder.crosshairs;
		this.dateTimeLabelFormats = builder.dateTimeLabelFormats;
		this.enabled = builder.enabled;
		this.followPointer = builder.followPointer;
		this.followTouchMove = builder.followTouchMove;
		this.footerFormat = builder.footerFormat;
		this.headerFormat = builder.headerFormat;
		this.hideDelay = builder.hideDelay;
		this.pointFormat = builder.pointFormat;
		this.shadow = builder.shadow;
		this.shape = builder.shape;
		this.shared = builder.shared;
		this.snap = builder.snap;
		this.style = builder.style;
		this.useHTML = builder.useHTML;
		this.valueDecimals = builder.valueDecimals;
		this.valuePrefix = builder.valuePrefix;
		this.valueSuffix = builder.valueSuffix;
		this.xDateFormat = builder.xDateFormat;

	}

	public static class TooltipBuilder implements Builder<Tooltip> {

		private Object animation; // Boolean|Animation
		private Object backgroundColor; // String|Color
		private Object borderColor;// String|Color
		private Number borderRadius;
		private Number borderWidth;
		private Object crosshairs; // Mixed:Boolean|List<Boolean>|Crosshairs|List<Crosshairs>,
									// x and y, list size 2
		private DateFormat dateTimeLabelFormats;
		private Boolean enabled;
		private Boolean followPointer;
		private Boolean followTouchMove;
		private String footerFormat;
		// private Function formatter;
		private String headerFormat;
		private Number hideDelay;
		private String pointFormat;
		// private Function positioner;
		private Boolean shadow; // Boolean|Object?
		private String shape;
		private Boolean shared;
		private Number snap;
		private Style style;
		private Boolean useHTML;
		private Number valueDecimals;
		private String valuePrefix;
		private String valueSuffix;
		private String xDateFormat;

		public TooltipBuilder animation(Animation animation) {
			this.animation = animation;
			return this;
		}

		public TooltipBuilder animation(Boolean animation) {
			this.animation = animation;
			return this;
		}

		public TooltipBuilder backgroundColor(String backgroundColor) {
			this.backgroundColor = backgroundColor;
			return this;
		}

		public TooltipBuilder backgroundColor(Color color) {
			this.backgroundColor = color;
			return this;
		}

		public TooltipBuilder borderColor(String borderColor) {
			this.borderColor = borderColor;
			return this;
		}

		public TooltipBuilder borderColor(Color color) {
			this.borderColor = color;
			return this;
		}

		public TooltipBuilder borderRadius(Number borderRadius) {
			this.borderRadius = borderRadius;
			return this;
		}

		public TooltipBuilder borderWidth(Number borderWidth) {
			this.borderWidth = borderWidth;
			return this;
		}

		public TooltipBuilder crosshairs(Boolean crosshairs) {
			this.crosshairs = crosshairs;
			return this;
		}

		/**
		 * 
		 * @param x
		 *            turns on the x axis crosshair
		 * @param y
		 *            turns on the y axis crosshair
		 * @return
		 */
		public TooltipBuilder crosshairs(Boolean x, Boolean y) {
			List<Boolean> e = new ArrayList<Boolean>();
			e.add(x);
			e.add(y);
			this.crosshairs = e;
			return this;
		}

		public TooltipBuilder crosshairs(Crosshairs crosshairs) {
			this.crosshairs = crosshairs;
			return this;
		}

		/**
		 * 
		 * @param x
		 *            applies to the x axis crosshair
		 * @param y
		 *            applies to the y axis crosshair
		 * @return
		 */
		public TooltipBuilder crosshairs(Crosshairs x, Crosshairs y) {
			List<Crosshairs> e = new ArrayList<Crosshairs>();
			e.add(x);
			e.add(y);
			this.crosshairs = e;
			return this;
		}

		public TooltipBuilder dateTimeLabelFormats(
				DateFormat dateTimeLabelFormats) {
			this.dateTimeLabelFormats = dateTimeLabelFormats;
			return this;
		}

		public TooltipBuilder enabled(Boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public TooltipBuilder followPointer(Boolean followPointer) {
			this.followPointer = followPointer;
			return this;
		}

		public TooltipBuilder followTouchMove(Boolean followTouchMove) {
			this.followTouchMove = followTouchMove;
			return this;
		}

		public TooltipBuilder footerFormat(String footerFormat) {
			this.footerFormat = footerFormat;
			return this;
		}

		public TooltipBuilder headerFormat(String headerFormat) {
			this.headerFormat = headerFormat;
			return this;
		}

		public TooltipBuilder hideDelay(Number hideDelay) {
			this.hideDelay = hideDelay;
			return this;
		}

		public TooltipBuilder pointFormat(String pointFormat) {
			this.pointFormat = pointFormat;
			return this;
		}

		public TooltipBuilder shadow(Boolean shadow) {
			this.shadow = shadow;
			return this;
		}

		public TooltipBuilder shape(String shape) {
			this.shape = shape;
			return this;
		}

		public TooltipBuilder shared(Boolean shared) {
			this.shared = shared;
			return this;
		}

		public TooltipBuilder snap(Number snap) {
			this.snap = snap;
			return this;
		}

		public TooltipBuilder style(Style style) {
			this.style = style;
			return this;
		}

		public TooltipBuilder useHTML(Boolean useHTML) {
			this.useHTML = useHTML;
			return this;
		}

		public TooltipBuilder valueDecimals(Number valueDecimals) {
			this.valueDecimals = valueDecimals;
			return this;
		}

		public TooltipBuilder valuePrefix(String valuePrefix) {
			this.valuePrefix = valuePrefix;
			return this;
		}

		public TooltipBuilder valueSuffix(String valueSuffix) {
			this.valueSuffix = valueSuffix;
			return this;
		}

		public TooltipBuilder xDateFormat(String xDateFormat) {
			this.xDateFormat = xDateFormat;
			return this;
		}

		@Override
		public Tooltip build() {
			return new Tooltip(this);
		}

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

	public Object getCrosshairs() {
		return crosshairs;
	}

	public DateFormat getDateTimeLabelFormats() {
		return dateTimeLabelFormats;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public Boolean getFollowPointer() {
		return followPointer;
	}

	public Boolean getFollowTouchMove() {
		return followTouchMove;
	}

	public String getFooterFormat() {
		return footerFormat;
	}

	public String getHeaderFormat() {
		return headerFormat;
	}

	public Number getHideDelay() {
		return hideDelay;
	}

	public String getPointFormat() {
		return pointFormat;
	}

	public Boolean getShadow() {
		return shadow;
	}

	public String getShape() {
		return shape;
	}

	public Boolean getShared() {
		return shared;
	}

	public Number getSnap() {
		return snap;
	}

	public Style getStyle() {
		return style;
	}

	public Boolean getUseHTML() {
		return useHTML;
	}

	public Number getValueDecimals() {
		return valueDecimals;
	}

	public String getValuePrefix() {
		return valuePrefix;
	}

	public String getValueSuffix() {
		return valueSuffix;
	}

	public String getxDateFormat() {
		return xDateFormat;
	}

}
