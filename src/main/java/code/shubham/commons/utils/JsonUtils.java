package code.shubham.commons.utils;

import com.google.gson.Gson;

public class JsonUtils {

	private static Gson GSON = new Gson();

	public static <T> String get(final T object) {
		return GSON.toJson(object);
	}

	public static <T> T as(final String json, final Class<T> clazz) {
		return GSON.fromJson(json, clazz);
	}

}
