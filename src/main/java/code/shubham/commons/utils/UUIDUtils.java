package code.shubham.commons.utils;

import java.util.UUID;

public class UUIDUtils {

	public static String uuid4(final String key) {
		return UUID.nameUUIDFromBytes(key.getBytes()).toString();
	}

	public static String generate() {
		return UUID.randomUUID().toString();
	}

}
