package in.reqres.qaTestSalsa;


import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.*;


import java.io.*;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.module.jsv.JsonSchemaValidator;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


public class APITestes {

	@BeforeClass
	public static void setup() {

		RestAssured.baseURI = "https://reqres.in/";

	}

	@Test
	public void deveListarUsuarios() {

		InputStream listUserJsonSchema = getClass().getClassLoader ()
			    .getResourceAsStream ("listuserjsonschema.json");
		
		RestAssured.given()
	    .when()
	      .get("api/users?page=2")
	    .then()
	      .statusCode(200)
	      .assertThat()
	      .body(JsonSchemaValidator.matchesJsonSchema(listUserJsonSchema));
	}
	
	
	@Test
	public void deveRealizarLogin() {

	RestAssured.given()
     .body("{\"email\": \"eve.holt@reqres.in\",\"password\": \"cityslicka\"}")
     		
     .contentType(ContentType.JSON)
        .when()
     .post("/api/login")
        .then().assertThat()
     .statusCode(200)
     .body(containsString("token"))
     .and()
	 .body("token", notNullValue());

	}

}
