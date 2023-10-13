import helpers.JsonDataProviderReader;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.io.InputStream;

import static io.restassured.RestAssured.given;

public class TestOne {
    private JSONObject loginData;

    @BeforeClass(groups = {"login", "manageUsers"})
    public void setup() {
        JsonDataProviderReader jsonDataProviderReader = new JsonDataProviderReader();
        loginData = jsonDataProviderReader.readJsonData("src/main/resources/userLoginData.json");
    }

    @Test(groups = {"login"})
    public void testLoginSuccess() {
        JSONObject successLoginData = (JSONObject) loginData.get("success_login");
        String response = given().contentType(ContentType.JSON)
                .body(successLoginData.toJSONString())
                .post("login")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();
    }

    @Test(groups = {"login"})
    public void testBadRequest() {
        JSONObject failLoginData = (JSONObject) loginData.get("bad_request_login");
        String response = given().contentType(ContentType.JSON)
                .body(failLoginData.toJSONString())
                .post("login")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST) // expected status code SC_BAD_REQUEST
                .extract().asString();
    }
}
