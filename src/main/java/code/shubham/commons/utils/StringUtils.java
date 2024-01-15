package code.shubham.commons.utils;

public class StringUtils {

	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		}
		catch (NumberFormatException ex) {
			return false;
		}
	}

	public static boolean isEmpty(String input) {
		return input == null || "".equals(input);
	}

}
