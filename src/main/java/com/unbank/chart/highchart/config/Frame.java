package com.unbank.chart.highchart.config;

import com.unbank.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class Frame {

	private final FrameColorSize back;
	private final FrameColorSize bottom;
	private final FrameColorSize side;

	private Frame(FrameBuilder builder) {
		this.back = builder.back;
		this.bottom = builder.bottom;
		this.side = builder.side;
	}

	public static class FrameBuilder implements Builder<Frame> {

		private FrameColorSize back;
		private FrameColorSize bottom;
		private FrameColorSize side;

		public FrameBuilder back(FrameColorSize back) {
			this.back = back;
			return this;
		}

		public FrameBuilder bottom(FrameColorSize bottom) {
			this.bottom = bottom;
			return this;
		}

		public FrameBuilder side(FrameColorSize side) {
			this.side = side;
			return this;
		}

		@Override
		public Frame build() {
			return new Frame(this);
		}

	}

	public FrameColorSize getBack() {
		return back;
	}

	public FrameColorSize getBottom() {
		return bottom;
	}

	public FrameColorSize getSide() {
		return side;
	}

}
