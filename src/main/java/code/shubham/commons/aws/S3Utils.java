package code.shubham.commons.aws;

import code.shubham.commons.exceptions.InternalServerException;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.Map;

@Slf4j
public class S3Utils {

	public static String createPresignedUrl(final String region, final String bucketName, final String keyName,
			final Map<String, String> metadata) {
		try (final S3Presigner presigner = S3Presigner.builder().region(Region.of(region)).build()) {

			final PutObjectRequest objectRequest = PutObjectRequest.builder()
				.bucket(bucketName)
				.key(keyName)
				.metadata(metadata)
				.build();

			final PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
				.signatureDuration(Duration.ofMinutes(10)) // The URL expires in 10
															// minutes.
				.putObjectRequest(objectRequest)
				.build();

			final PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
			final String myURL = presignedRequest.url().toString();
			log.info("Presigned URL to upload a file to: [{}]", myURL);
			log.info("HTTP method: [{}]", presignedRequest.httpRequest().method());

			return presignedRequest.url().toExternalForm();
		}
	}

	/* Create a pre-signed URL to download an object in a subsequent GET request. */
	// public static String createPresignedGetUrl(String bucketName, String keyName) {
	// try (S3Presigner presigner = S3Presigner.create()) {
	//
	// GetObjectRequest objectRequest =
	// GetObjectRequest.builder().bucket(bucketName).key(keyName).build();
	//
	// GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
	// .signatureDuration(Duration.ofMinutes(10)) // The URL will expire in 10
	// // minutes.
	// .getObjectRequest(objectRequest)
	// .build();
	//
	// PresignedGetObjectRequest presignedRequest =
	// presigner.presignGetObject(presignRequest);
	// log.info("Presigned URL: [{}]", presignedRequest.url().toString());
	// log.info("HTTP method: [{}]", presignedRequest.httpRequest().method());
	//
	// return presignedRequest.url().toExternalForm();
	// }
	// }

	// public static boolean doesObjectExist(final String region, final String bucket,
	// final String key) {
	// try {
	// final S3Client s3 = S3Client.builder().region(Region.of(region)).build();
	//
	// final GetObjectAttributesResponse response = s3
	// .getObjectAttributes(GetObjectAttributesRequest.builder().bucket(bucket).key(key).build());
	// }
	// catch (NoSuchKeyException exception) {
	// log.error("No such key!!!", exception);
	// return false;
	// }
	// catch (SdkException exception) {
	// log.error("Exception while invoking S3::getObjectAttributes", exception);
	// throw new InternalServerException("Something went wrong");
	// }
	// return true;
	// }

}
