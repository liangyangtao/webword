package com.chart.highchart.type;

/**
 * 
 * @author zile
 * 
 */
public enum Align {

	LEFT("left"), CENTER("center"), RIGHT("right");

	private final String name;

	private Align(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
