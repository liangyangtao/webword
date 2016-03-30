package com.unbank.model.param;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 异构容器
 * 
 * @author zile
 *
 */
public class Params implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6442594746936081836L;

	private Map<Class<?>, Object> params = new HashMap<Class<?>, Object>();

	public <T extends UnbankParam> void put(Class<T> type, T instance) {
		if (type == null)
			throw new NullPointerException("type is null!");
		params.put(type, instance);
	}

	public <T extends UnbankParam> T get(Class<T> type) {
		return type.cast(params.get(type));
	}

}
