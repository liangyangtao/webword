package com.chart.highchart.config;

/**
 * 
 * @author zile
 * 
 */
public class FrameColorSize {

	private Color color;

	private Number size;

	public FrameColorSize() {
	}

	public FrameColorSize(Color color, Number size) {
		this.color = color;
		this.size = size;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Number getSize() {
		return size;
	}

	public void setSize(Number size) {
		this.size = size;
	}

}
