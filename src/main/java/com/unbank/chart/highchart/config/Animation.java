package com.unbank.chart.highchart.config;

import com.unbank.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class Animation {

	private final Number duration;
	private final String easing;

	private Animation(AnimationBuilder builder) {
		this.duration = builder.duration;
		this.easing = builder.easing;
	}

	public static class AnimationBuilder implements Builder<Animation> {

		private Number duration;
		private String easing;

		public AnimationBuilder duration(Number duration) {
			this.duration = duration;
			return this;
		}

		public AnimationBuilder easing(String easing) {
			this.easing = easing;
			return this;
		}

		@Override
		public Animation build() {
			return new Animation(this);
		}

	}

	public Number getDuration() {
		return duration;
	}

	public String getEasing() {
		return easing;
	}

}
