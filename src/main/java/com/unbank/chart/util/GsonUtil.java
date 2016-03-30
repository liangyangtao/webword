package com.unbank.chart.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * 
 * @author zile
 * 
 */
public class GsonUtil {

	public static JsonElement toJsonTree(Object obj) {
		try {
			Gson gson = new Gson();
			return gson.toJsonTree(obj);
		} catch (Exception e) {
			return null;
		}
	}

	public static JsonElement toJsonTree(Object obj, Type typeOfSrc) {
		try {
			Gson gson = new Gson();
			return gson.toJsonTree(obj, typeOfSrc);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T fromJson(String json, Class<T> t) {
		try {
			Gson gson = new Gson();
			return gson.fromJson(json, t);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T fromJson(JsonElement json, Class<T> classOfT) {
		try {
			Gson gson = new Gson();
			return gson.fromJson(json, classOfT);
		} catch (Exception e) {
			return null;
		}
	}
}
