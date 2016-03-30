package com.unbank.chart.highchart.config;

import java.util.ArrayList;
import java.util.List;

import com.unbank.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class Pane {

	public static class Background {

		private final Object backgroundColor; // String|color
		private final Number innerWidth;
		private final Number outerWidth;
		private final Number borderWidth;
		private final Object borderColor;// String|color

		private Background(BackgroundBuilder builder) {
			this.backgroundColor = builder.backgroundColor;
			this.innerWidth = builder.innerWidth;
			this.outerWidth = builder.outerWidth;
			this.borderWidth = builder.borderWidth;
			this.borderColor = builder.borderColor;
		}

		public Object getBackgroundColor() {
			return backgroundColor;
		}

		public Number getInnerWidth() {
			return innerWidth;
		}

		public Number getOuterWidth() {
			return outerWidth;
		}

		public Number getBorderWidth() {
			return borderWidth;
		}

		public Object getBorderColor() {
			return borderColor;
		}

	}

	public static class BackgroundBuilder implements Builder<Background> {

		private Object backgroundColor; // String|color
		private Number innerWidth;
		private Number outerWidth;
		private Number borderWidth;
		private Object borderColor;// String|color

		public BackgroundBuilder backgroundColor(String backgroundColor) {
			this.backgroundColor = backgroundColor;
			return this;
		}

		public BackgroundBuilder backgroundColor(Color color) {
			this.backgroundColor = color;
			return this;
		}

		public BackgroundBuilder innerWidth(Number innerWidth) {
			this.innerWidth = innerWidth;
			return this;
		}

		public BackgroundBuilder outerWidth(Number outerWidth) {
			this.outerWidth = outerWidth;
			return this;
		}

		public BackgroundBuilder borderWidth(Number borderWidth) {
			this.borderWidth = borderWidth;
			return this;
		}

		public BackgroundBuilder borderColor(String borderColor) {
			this.borderColor = borderColor;
			return this;
		}

		public BackgroundBuilder borderColor(Color color) {
			this.borderColor = color;
			return this;
		}

		@Override
		public Background build() {
			return new Background(this);
		}

	}

	public static class PaneBuilder implements Builder<Pane> {

		private List<Background> background;
		private List<Object> center; // Array<String|Number>
		private Number endAngle;
		private Number startAngle;

		public PaneBuilder background(Background... backgrounds) {
			this.background = new ArrayList<Background>();
			for (Background b : backgrounds)
				this.background.add(b);
			return this;
		}

		public PaneBuilder background(List<Background> background) {
			this.background = background;
			return this;
		}

		public PaneBuilder center(String... centers) {
			this.center = new ArrayList<Object>();
			for (String c : centers)
				this.center.add(c);
			return this;
		}

		public PaneBuilder center(Number... numbers) {
			this.center = new ArrayList<Object>();
			for (Number n : numbers)
				this.center.add(n);
			return this;
		}

		public PaneBuilder center(List<Number> numbers) {
			this.center = new ArrayList<Object>();
			for (Number n : numbers)
				this.center.add(n);
			return this;
		}

		public PaneBuilder endAngle(Number endAngle) {
			this.endAngle = endAngle;
			return this;
		}

		public PaneBuilder startAngle(Number startAngle) {
			this.startAngle = startAngle;
			return this;
		}

		@Override
		public Pane build() {
			return new Pane(this);
		}

	}

	private final List<Background> background;
	private final List<Object> center; // Array<String|Number>
	private final Number endAngle;
	private final Number startAngle;

	private Pane(PaneBuilder builder) {
		this.background = builder.background;
		this.center = builder.center;
		this.endAngle = builder.endAngle;
		this.startAngle = builder.startAngle;
	}

	public List<Background> getBackground() {
		return background;
	}

	public List<Object> getCenter() {
		return center;
	}

	public Number getEndAngle() {
		return endAngle;
	}

	public Number getStartAngle() {
		return startAngle;
	}

}
