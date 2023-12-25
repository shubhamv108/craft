package code.shubham.commons.utils;

import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.exceptions.InvalidRequestException;

import java.util.Iterator;
import java.util.List;

public class Utils {

	public static <T> T getNextInSequence(final List<T> sequence, final T current) {
		final Iterator<T> iterator = sequence.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().equals(current)) {
				if (iterator.hasNext())
					return iterator.next();
				break;
			}
		}
		return null;
	}

	public static void validateUserOrThrowException(final String userId) {
		if (!userId.equals(UserIDContextHolder.get()) && !RoleContextHolder.isAdmin())
			throw new InvalidRequestException("userId", "User with userId: %s not allowed to perform the operation",
					userId);
	}

}
