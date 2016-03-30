package com.export;

import java.util.ArrayList;
import java.util.List;

public enum FieldType {

	TEXTAREA("文本"), RADIO("单选"), CHECKBOX("多选"), TIME("时间");

	private static final List<Selector<FieldType>> list = new ArrayList<Selector<FieldType>>();

	public static List<Selector<FieldType>> getList() {
		return list;
	}

	static {
		for (FieldType field : FieldType.values()) {
			Selector<FieldType> selector = new Selector<FieldType>();
			selector.setKey(field);
			selector.setValue(field.getName());
			list.add(selector);
		}
	}

	private final String name;

	private FieldType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
