package code.shubham.commons.contexts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TenantContextHolderTest {

	@Test
	void test_set_get_clear_Tenant() {
		TenantContextHolder.set("id");

		Assertions.assertEquals("id", TenantContextHolder.get());

		TenantContextHolder.clear();

		Assertions.assertNull(TenantContextHolder.get());
	}

}