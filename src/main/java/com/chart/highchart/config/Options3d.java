package com.chart.highchart.config;

import com.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class Options3d {

	private final Number alpha;
	private final Number beta;
	private final Number depth;
	private final Boolean enabled;
	private final Frame frame;
	private final Number viewDistance;

	private Options3d(Options3dBuilder builder) {
		this.alpha = builder.alpha;
		this.beta = builder.beta;
		this.depth = builder.depth;
		this.enabled = builder.enabled;
		this.frame = builder.frame;
		this.viewDistance = builder.viewDistance;
	}

	public static class Options3dBuilder implements Builder<Options3d> {

		private Number alpha;
		private Number beta;
		private Number depth;
		private Boolean enabled;
		private Frame frame;
		private Number viewDistance;

		public Options3dBuilder alpha(Number alpha) {
			this.alpha = alpha;
			return this;
		}

		public Options3dBuilder beta(Number beta) {
			this.beta = beta;
			return this;
		}

		public Options3dBuilder depth(Number depth) {
			this.depth = depth;
			return this;
		}

		public Options3dBuilder enabled(Boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public Options3dBuilder frame(Frame frame) {
			this.frame = frame;
			return this;
		}

		public Options3dBuilder viewDistance(Number viewDistance) {
			this.viewDistance = viewDistance;
			return this;
		}

		@Override
		public Options3d build() {
			return new Options3d(this);
		}

	}

	public Number getAlpha() {
		return alpha;
	}

	public Number getBeta() {
		return beta;
	}

	public Number getDepth() {
		return depth;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public Frame getFrame() {
		return frame;
	}

	public Number getViewDistance() {
		return viewDistance;
	}

}
