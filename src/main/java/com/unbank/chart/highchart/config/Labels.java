package com.unbank.chart.highchart.config;

import java.util.ArrayList;
import java.util.List;

import com.unbank.chart.util.Builder;

/**
 * 
 * @author zile
 * 
 */
public class Labels {

	private final List<Item> items;
	private final Style style;

	private Labels(LabelsBuilder builder) {
		this.items = builder.items;
		this.style = builder.style;
	}

	public static class LabelsBuilder implements Builder<Labels> {

		private List<Item> items;
		private Style style;

		public LabelsBuilder items(Item... items) {
			this.items = new ArrayList<Item>();
			for (Item item : items)
				this.items.add(item);
			return this;
		}

		public LabelsBuilder items(List<Item> items) {
			this.items = items;
			return this;
		}

		public LabelsBuilder style(Style style) {
			this.style = style;
			return this;
		}

		@Override
		public Labels build() {
			return new Labels(this);
		}

	}

	public List<Item> getItems() {
		return items;
	}

	public Style getStyle() {
		return style;
	}

}
