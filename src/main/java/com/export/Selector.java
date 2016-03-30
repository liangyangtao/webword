package com.export;

/**
 * select type or checkbox type
 * 
 * @author quzile
 * 
 * @param <T>
 */
public class Selector<T> {

	private T key;

	private String value;

	public T getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public void setKey(T key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
