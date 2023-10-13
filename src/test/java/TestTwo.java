import helpers.JsonDataProviderReader;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.InputStream;

import static io.restassured.RestAssured.given;

public class TestTwo {
    private JSONObject userData;
    @BeforeClass(groups = {"login", "manageUsers"})
    public void setup() {
        JsonDataProviderReader jsonDataProviderReader = new JsonDataProviderReader();
        userData = jsonDataProviderReader.readJsonData("src/main/resources/users.json");
    }
    @Test(groups = {"manageUsers"})
    public void testGetUser() {
        Response response = given()
                .contentType(ContentType.JSON)
                .param("id","2")
                .when()
                .get("users")
                .then()
                .extract().response();

        //Assertions.assertEquals(200, response.statusCode());
        System.out.println(response);
    }

    @Test(groups = {"manageUsers"})
    public void getUser() {
        InputStream getUserSchemaResponse = getClass().getClassLoader().getResourceAsStream("responseSchemas/getUserSchemaResponse.json");
        given()
                .when()
                .get("users/2")
                .then()
                .statusCode(HttpStatus.SC_OK).and().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(getUserSchemaResponse));
    }

    @Test(groups = {"manageUsers"})
    public void getUserList() {
        InputStream userListSchemaResponse = getClass().getClassLoader().getResourceAsStream("responseSchemas/userListSchemaResponse.json");
        given()
                .when()
                .get("unknown")
                .then()
                .statusCode(HttpStatus.SC_OK).and().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(userListSchemaResponse));
    }

    @Test(groups = {"manageUsers"})
    public void getSingleUser() {
        InputStream singleUserSchemaResponse = getClass().getClassLoader().getResourceAsStream("responseSchemas/singleUserSchemaResponse.json");
        given()
                .when()
                .get("unknown/2")
                .then()
                .statusCode(HttpStatus.SC_OK).and().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(singleUserSchemaResponse));
    }

    @Test(groups = {"manageUsers"})
    public void deleteUser() {
        given()
                .when()
                .delete("users/2")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test(groups = {"manageUsers"})
    public void createUser() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(userData.get("create").toString())
                .post("users")
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test(groups = {"manageUsers"})
    public void updateUser() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(userData.get("update").toString())
                .put("users/2")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
