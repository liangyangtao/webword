package com.unbank.chart.highchart.type;

/**
 * 
 * @author zile
 * 
 */
public enum VerticalAlign {

	TOP("top"), MIDDLE("middle"), BOTTOM("bottom");

	private final String name;

	private VerticalAlign(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
