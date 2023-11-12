package in.reqres.qaTestSalsa;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;

public class APITestes {

	@BeforeClass
	public static void setup() {

		RestAssured.baseURI = "https://reqres.in/";

	}

	@Test
	public void deveListarUsuarios() {

		RestAssured.given()
	    .when()
	      .get("api/users?page=2")
	    .then()
	      .statusCode(200);
	}
}
