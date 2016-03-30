package com.chart.highchart.type;

/**
 * The layout of the legend items. Can be one of "horizontal" or "vertical".
 * Defaults to horizontal.
 * 
 * @author zile
 * 
 */
public enum Layout {

	HORIZONTAL("horizontal"), VERTICAL("vertical");

	private final String name;

	private Layout(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
