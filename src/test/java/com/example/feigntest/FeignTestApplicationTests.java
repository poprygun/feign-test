package com.example.feigntest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"chachkie-client.ribbon.listOfServers=http://localhost:8888"})
class FeignTestApplicationTests {

	private static WireMockServer wireMockServer;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ChachkieClient chachkieClient;

	@BeforeAll
	static void initMockServer(){
		final int port = 8888;
		wireMockServer = new WireMockServer(port);
		wireMockServer.start();
		WireMock.configureFor("localhost", port);

	}

	@Test
	void contextLoads() throws JsonProcessingException {
		final Chachkie john_doe = Chachkie.builder().id("first-one")
				.name("John Doe")
				.when(LocalDate.now()).build();

		JsonNode node = objectMapper.convertValue(john_doe, JsonNode.class);

		System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));

		stubFor(WireMock.get(urlPathMatching("/chachkies/.*"))
				.willReturn(aResponse()
						.withStatus(HttpStatus.OK.value())
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.withJsonBody(node)));

		final Chachkie myChachkie = chachkieClient.findMyChachkie("some-id");
	}

	@AfterAll
	static void cleanUp(){
		wireMockServer.stop();
	}

}
