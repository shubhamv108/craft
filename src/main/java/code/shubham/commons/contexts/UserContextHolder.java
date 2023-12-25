package code.shubham.commons.contexts;

import code.shubham.core.iammodels.UserDTO;

public class UserContextHolder {

	private static final ThreadLocal<UserDTO> CURRENT_USER = new ThreadLocal<>();

	public static void set(final UserDTO user) {
		CURRENT_USER.set(user);
	}

	public static UserDTO get() {
		return CURRENT_USER.get();
	}

	public static void clear() {
		CURRENT_USER.remove();
	}

}
