package com.yashodeep.vmprovisioningservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yashodeep.vmprovisioningservice.jpa.VmConfig;
import com.yashodeep.vmprovisioningservice.web.AuthenticationRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Slf4j
public class VmProvisioningServiceApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	ObjectMapper objectMapper;

	private String token;

	@Before
	public void setup() {
		RestAssured.port = this.port;
		token = given()
				.contentType(ContentType.JSON)
				.body(AuthenticationRequest.builder().username("yashodeepv").password("yash").build())
				.when().post("/auth/signin")
				.andReturn().jsonPath().getString("token");
		log.debug("Got token:" + token);
	}

	@Test
	public void getAllProvisionedVms() throws Exception {
		given()
				.header("Authorization", "Bearer "+token)
				.accept(ContentType.JSON)
				.when()
				.get("/vm-provisioning-service/provision-vm")
				.then()
				.assertThat()
				.statusCode(HttpStatus.SC_OK);
	}

	@Test
	public void testSave() throws Exception {
		given()
				.contentType(ContentType.JSON)
				.body(VmConfig.builder()
						.cpuCores(2)
						.hdd("250gb")
						.os("Win")
						.ram("12GB")
						.build())
				.when()
				.post("/vm-provisioning-service/provision-vm")
				.then()
				.statusCode(403);
	}

	@Test
	public void testSaveWithAuth() throws Exception {
		given()
				.header("Authorization", "Bearer "+token)
				.contentType(ContentType.JSON)
				.body(VmConfig.builder()
						.cpuCores(2)
						.hdd("250gb")
						.os("Win")
						.ram("12GB")
						.build())
				.when()
				.post("/vm-provisioning-service/provision-vm")
				.then()
				.statusCode(200);

	}

}