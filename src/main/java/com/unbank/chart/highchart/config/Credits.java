package com.unbank.chart.highchart.config;

import com.unbank.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class Credits {

	private final Boolean enabled;
	private final String href;
	private final Position position;
	private final Style style;
	private final String text;

	private Credits(CreditsBuilder builder) {
		this.enabled = builder.enabled;
		this.href = builder.href;
		this.position = builder.position;
		this.style = builder.style;
		this.text = builder.text;
	}

	public static class CreditsBuilder implements Builder<Credits> {

		private Boolean enabled;
		private String href;
		private Position position;
		private Style style;
		private String text;

		public CreditsBuilder enabled(Boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public CreditsBuilder href(String href) {
			this.href = href;
			return this;
		}

		public CreditsBuilder position(Position position) {
			this.position = position;
			return this;
		}

		public CreditsBuilder style(Style style) {
			this.style = style;
			return this;
		}

		public CreditsBuilder text(String text) {
			this.text = text;
			return this;
		}

		@Override
		public Credits build() {
			return new Credits(this);
		}

	}

	public Boolean getEnabled() {
		return enabled;
	}

	public String getHref() {
		return href;
	}

	public Position getPosition() {
		return position;
	}

	public Style getStyle() {
		return style;
	}

	public String getText() {
		return text;
	}

}
