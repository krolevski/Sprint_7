import courier.*;
import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginCourierTests {
    @Before
    @Step("Data preparation. Creating a courier")
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";

        Courier courier = new Courier("Rika", "12345", "Eri");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(201);
    }

    @After
    @Step("Deleting data")
    public void dataDelete() {
        Courier courier = new Courier("Rika", "12345", null);

        Response responseLogin = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");

        responseLogin.then().assertThat().body("id", isA(Integer.class))
                .and()
                .statusCode(200);

        String IdString = responseLogin.body().asString();
        Gson gson = new Gson();
        DeleteCourier id = gson.fromJson(IdString, DeleteCourier.class);

        Response responseDelete = given()
                .header("Content-type", "application/json")
                .when()
                .delete(String.format("/api/v1/courier/%s", id.getId()));

        responseDelete.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @Step("Authorization with the correct data")
    public void courierAuthorization() {
        Courier courier = new Courier("Rika", "12345", null);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");

        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);

        System.out.println(response.body().asString());
    }

    @Test
    @Step("Authorization with an invalid password")
    public void courierAuthorizationWithIncorrectPassword() {
        Courier courier = new Courier("Rika", "неверный пароль", null);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");

        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);

        System.out.println(response.body().asString());
    }

    @Test
    @Step("Authorization with incomplete data")
    public void courierAuthorizationWithoutPassword() {
        Courier courier = new Courier("Rika", null, null);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");

        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);

        System.out.println(response.body().asString());
    }
}
