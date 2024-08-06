package apptest;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;
import se.sundsvall.smssender.Application;

@WireMockAppTestSuite(files = "classpath:/TeliaIT/", classes = Application.class)
@TestPropertySource(properties = {
	"provider.telia.enabled=true",
	"provider.linkmobility.enabled=false",
})
class TeliaIT extends AbstractAppTest {

	private static final String MUNICIPALITY_ID = "2281";

	private static final String SERVICE_PATH = "/" + MUNICIPALITY_ID + "/send/sms";

	@Test
	void test1_successfulRequest() {
		setupCall()
			.withServicePath(SERVICE_PATH)
			.withHttpMethod(HttpMethod.POST)
			.withRequest("request.json")
			.withExpectedResponseStatus(HttpStatus.OK)
			.withExpectedResponse("response.json")
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test2_successfulFlashRequest() {
		setupCall()
			.withServicePath(SERVICE_PATH + "?flash=true")
			.withHttpMethod(HttpMethod.POST)
			.withRequest("request.json")
			.withExpectedResponseStatus(HttpStatus.OK)
			.withExpectedResponse("response.json")
			.sendRequestAndVerifyResponse();
	}

}
