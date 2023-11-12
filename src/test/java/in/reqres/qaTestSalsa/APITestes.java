package in.reqres.qaTestSalsa;


import static org.hamcrest.Matchers.*;
import java.io.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.json.simple.JSONObject;
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
		 .param("page", "2")
	    	.when()
	     .get("api/users")
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
        	.then()
        .statusCode(200)
        .assertThat()
        .body(containsString("token"))
        .and()
        .body("token", notNullValue());

	}

	
	@Test
	public void deveCriarNovoUsuario() {
		
		JSONObject requestParams = new JSONObject();
        requestParams.put("name", "morpheus");
        requestParams.put("job", "leader");
		
		RestAssured.given()
	     .body(requestParams.toJSONString())
	     		
	     .contentType(ContentType.JSON)
	        .when()
	     .post("/api/users")
	        .then()
	     .statusCode(201)
		 .assertThat()
		 .body(containsString("name"))
		 .and()
		 .body(containsString("job"))
		 .and()
		 .body(containsString("id"))
		 .and()
		 .body(containsString("createdAt"));
	}
	
	
}
