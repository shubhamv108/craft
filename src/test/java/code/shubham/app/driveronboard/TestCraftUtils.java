package code.shubham.app.driveronboard;

import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.contexts.UserContextHolder;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.core.iammodels.UserDTO;

public class TestCraftUtils {

	public static void setContext() {
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
		UserContextHolder.set(new UserDTO(TestCommonConstants.USER_ID, TestCommonConstants.USER_EMAIL));
	}

}
