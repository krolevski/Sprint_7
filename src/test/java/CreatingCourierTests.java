import courier.*;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;
import static org.apache.http.HttpStatus.*;

public class CreatingCourierTests {
     private final static String requestCreateCourier = "/api/v1/courier";

    @Before
    @Step("Data preparation")
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @After
    @Step("Deleting data")
    public void dataDelete() {
        Courier сourier = new Courier("Rika", "12345", null);

        Response responseLogin = given()
                .header("Content-type", "application/json")
                .and()
                .body(сourier)
                .when()
                .post("/api/v1/courier/login");

        responseLogin.then().assertThat().body("id", isA(Integer.class))
                .and()
                .statusCode(SC_OK);

        String IdString = responseLogin.body().asString();
        Gson gson = new Gson();
        DeleteCourier id = gson.fromJson(IdString, DeleteCourier.class);

        Response responseDelete = given()
                .header("Content-type", "application/json")
                .when()
                .delete(String.format("/api/v1/courier/%s", id.getId()));

        responseDelete.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @Step("A method for creating a new courier without errors")
    public void createNewCourier() {
        Courier courier = new Courier("Rika", "12345", "Eri");

        Response response = given()
            .header("Content-type", "application/json")
            .and()
            .body(courier)
            .when()
            .post(requestCreateCourier);

        response.then().assertThat().body("ok", equalTo(true))
            .and().statusCode(SC_CREATED);

        System.out.println(response.body().asString());
    }

    @Test
    @Step("A method for checking that it is impossible to create a courier with the same username")
    public void createCourierWithSameUsername() {
        Courier courier = new Courier("Rika", "12345", "Eri");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(requestCreateCourier);

        response.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(SC_CREATED);

        Response response2 = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(requestCreateCourier);

        response2.then().assertThat().body("message", equalTo("Этот логин уже используется"))
                .and()
                .statusCode(SC_CONFLICT);

        System.out.println(response2.body().asString());
    }

        @Test
        @Step("A method to verify that the courier will not be created with incomplete data")
        public void createCourierWithoutLogin () {
            Courier courier = new Courier(null, "12345", "Eri");

            Response response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(courier)
                    .when()
                    .post(requestCreateCourier);

            response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                    .and()
                    .statusCode(SC_BAD_REQUEST);

            System.out.println(response.body().asString());
        }
    }
