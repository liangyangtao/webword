package com.chart.highchart.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.chart.util.GsonUtil;

public abstract class AbstractChartConfig implements ChartConfig {

	@Override
	public JsonObject jsonObject() {
		JsonElement jsonElement = GsonUtil.toJsonTree(this);
		JsonObject jsonObject = null;
		if (jsonElement instanceof JsonObject)
			jsonObject = (JsonObject) jsonElement;
		return jsonObject;
	}

}
