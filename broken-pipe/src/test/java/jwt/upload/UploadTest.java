package jwt.upload;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.*;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.RxStreamingHttpClient;
import io.micronaut.http.client.multipart.MultipartBody;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class UploadTest {

	public static final Logger logger = LoggerFactory.getLogger(UploadTest.class);

	@Test
	public void testBig() {
		int capacity = 7*1024*1024; // 7MB
		StringBuilder sb = new StringBuilder(capacity);
		for (int i=0; i<capacity; i++)
			sb.append(i % 10);
		doTest(sb.toString());
	}

	protected void doTest(String s) {
		logger.info(String.format("Beginning test with: %d bytes", s.length()));
		Map<String, Object> config = new LinkedHashMap<>();
		config.put("micronaut.http.client.read-timeout", "10m");
		config.put("micronaut.netty.event-loops.other.num-threads", 10);
		config.put("micronaut.http.client.event-loop-group", "other");

		EmbeddedServer server = ApplicationContext.build(config).run(EmbeddedServer.class);
		RxStreamingHttpClient client = (RxStreamingHttpClient) server.getApplicationContext().createBean(HttpClient.class, server.getURL());


		MultipartBody.Builder builder = MultipartBody.builder();
		builder.addPart(
			"file",
			"file.txt",
			MediaType.TEXT_PLAIN_TYPE,
			s.getBytes()
		);

		MutableHttpRequest<MultipartBody> req = HttpRequest.POST("/", builder.build()) // set body
			.contentType(MediaType.MULTIPART_FORM_DATA_TYPE);
		req.header(HttpHeaders.AUTHORIZATION, // authorize
			String.format("%s %s", HttpHeaderValues.AUTHORIZATION_PREFIX_BEARER, "abc"));

		try {
			client.toBlocking().exchange(req, String.class);
		} catch (Exception t) {
			logger.info("{}: {}", t.getClass().getSimpleName(), t.getMessage());
		}

		try {
			client.toBlocking().exchange(req, String.class);
		} catch (Exception t) {
			logger.info("{}: {}", t.getClass().getSimpleName(), t.getMessage());
		}

		client.close();
		server.close();
	}

}
