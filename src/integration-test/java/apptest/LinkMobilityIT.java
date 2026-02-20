package apptest;

import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.TestPropertySource;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;
import se.sundsvall.smssender.Application;

@WireMockAppTestSuite(files = "classpath:/LinkMobilityIT/", classes = Application.class)
@TestPropertySource(properties = {
	"provider.telia.enabled=false",
	"provider.linkmobility.enabled=true"
})
class LinkMobilityIT extends AbstractAppTest {

	private static final String MUNICIPALITY_ID = "2281";

	private static final String SERVICE_PATH = "/" + MUNICIPALITY_ID + "/send/sms";

	@Test
	void test1_successfulRequest() {
		setupCall()
			.withServicePath(SERVICE_PATH)
			.withHttpMethod(HttpMethod.POST)
			.withRequest("request.json")
			.withExpectedResponseStatus(OK)
			.withExpectedResponse("response.json")
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test2_successfulFlashRequest() {
		setupCall()
			.withServicePath(SERVICE_PATH + "?flash=true")
			.withHttpMethod(HttpMethod.POST)
			.withRequest("request.json")
			.withExpectedResponseStatus(OK)
			.withExpectedResponse("response.json")
			.sendRequestAndVerifyResponse();
	}
}
