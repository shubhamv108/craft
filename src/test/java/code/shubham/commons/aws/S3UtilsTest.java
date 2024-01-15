package code.shubham.commons.aws;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class S3UtilsTest {

	@Test
	void createPresignedUrl() {
		final String url = S3Utils.createPresignedUrl("ap-south-1", "bucket", "key", new HashMap<>());
		System.out.println(url);
		Assertions.assertTrue(url
			.startsWith("https://bucket.s3.ap-south-1.amazonaws.com/key?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date="));
		Assertions.assertTrue(url.contains("&X-Amz-SignedHeaders=host&X-Amz-Credential="));
		Assertions.assertTrue(url.contains("ap-south-1%2Fs3%2Faws4_request&X-Amz-Expires=600&X-Amz-Signature="));
	}

}