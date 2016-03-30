package com.chart.highchart.config;

import com.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class DrillUpButton {

	private final Position position;
	private final String relativeTo;
	private final ThemeMap theme;

	private DrillUpButton(DrillUpButtonBuilder builder) {
		this.position = builder.position;
		this.relativeTo = builder.relativeTo;
		this.theme = builder.theme;
	}

	public static class DrillUpButtonBuilder implements Builder<DrillUpButton> {

		private Position position;
		private String relativeTo;
		private ThemeMap theme;

		public DrillUpButtonBuilder position(Position position) {
			this.position = position;
			return this;
		}

		public DrillUpButtonBuilder relativeTo(String relativeTo) {
			this.relativeTo = relativeTo;
			return this;
		}

		public DrillUpButtonBuilder theme(ThemeMap theme) {
			this.theme = theme;
			return this;
		}

		@Override
		public DrillUpButton build() {
			return new DrillUpButton(this);
		}

	}

	public Position getPosition() {
		return position;
	}

	public String getRelativeTo() {
		return relativeTo;
	}

	public ThemeMap getTheme() {
		return theme;
	}

}
