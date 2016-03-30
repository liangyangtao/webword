package com.common.editor;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;

/**
 * BigDecimal类型属性编辑转换
 * 
 * @author
 * 
 */
public class CustomBigDecimalEditor extends PropertyEditorSupport {

	@Override
	public String getAsText() {
		String value = null;
		if (getValue() != null) {
			BigDecimal bigDecimal = (BigDecimal) getValue();
			value = bigDecimal.toString();
		}
		return value;
	}

	@Override
	public void setAsText(String value) throws IllegalArgumentException {
		try {
			if (value != null && !value.equals("")) {
				String[] _v = value.split(",");
				this.setValue(new BigDecimal(_v[0]));
			}
		} catch (Exception e) {
			this.setValue(new BigDecimal(0));
		}
	}

}
