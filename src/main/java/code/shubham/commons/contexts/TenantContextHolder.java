package code.shubham.commons.contexts;

public class TenantContextHolder {

	private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

	public static void set(String id) {
		CONTEXT.set(id);
	}

	public static String get() {
		return CONTEXT.get();
	}

	public static void clear() {
		CONTEXT.remove();
	}

}
