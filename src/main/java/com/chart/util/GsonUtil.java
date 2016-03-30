package com.chart.util;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author zile
 * 
 */
public class GsonUtil {
	
	static Gson gson = new Gson();

	public static JsonElement toJsonTree(Object obj) {
		try {
			return gson.toJsonTree(obj);
		} catch (Exception e) {
			return null;
		}
	}

	public static JsonElement toJsonTree(Object obj, Type typeOfSrc) {
		try {
			return gson.toJsonTree(obj, typeOfSrc);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T fromJson(String json, Class<T> t) {
		try {
			return gson.fromJson(json, t);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T fromJson(JsonElement json, Class<T> classOfT) {
		try {
			return gson.fromJson(json, classOfT);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 转换对象为json
	 * 
	 * @param obj
	 * @return
	 */
	public static String getJsonStringFromObject(Object obj) {
		return gson.toJson(obj);
	}
	
	/**
	 * 转换Json字符串为对象
	 * 
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T> T getObjectFromJsonStr(String json, Class<T> classOfT) {

		return gson.fromJson(json, classOfT);
	}

	public static List<String> getListFromJson(String json) {
		return gson.fromJson(json, new TypeToken<List<String>>() {
		}.getType());
	}
}
