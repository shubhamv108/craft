package code.shubham.commons.contexts;

import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.utils.UUIDUtils;

import java.util.UUID;

public class CorrelationIDContext {

	private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

	public static void set(final String id) {
		CONTEXT.set(id);
	}

	public static String get() {
		if (StringUtils.isEmpty(CONTEXT.get()))
			CorrelationIDContext.set(UUIDUtils.generate());
		return CONTEXT.get();
	}

	public static void clear() {
		CONTEXT.remove();
	}

}
