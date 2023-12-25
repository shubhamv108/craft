package code.shubham.commons.contexts;

public class UserIDContextHolder {

	private static final ThreadLocal<String> CURRENT_USER = new ThreadLocal<>();

	public static void set(final String id) {
		CURRENT_USER.set(id);
	}

	public static String get() {
		return CURRENT_USER.get();
	}

	public static void clear() {
		CURRENT_USER.remove();
	}

}
