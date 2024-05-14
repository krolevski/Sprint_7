package courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierApi {
     final static String CREATE_COURIER = "/api/v1/courier";
     final static String LOGIN_COURIER = "/api/v1/courier/login";
     final static String DELETE_COURIER = "/api/v1/courier/";

    @Step("Создание курьера")
    public Response createCourier() {
        Courier courier = new Courier("ErikaKrolevski", "12345", "Erika");

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(CREATE_COURIER);
    }

    @Step("Создание курьера с неполными данными")
    public Response createCourierNullData() {
        Courier courier = new Courier(null, null, null);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(CREATE_COURIER);
    }

    @Step("Авторизация курьера с верными данными")
    public Response authorizationCourier() {
        Courier courier = new Courier("ErikaKrolevski", "12345", null);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN_COURIER);
    }

    @Step("Авторизация курьера с неверными данными")
    public Response authorizationCourierNotValidData() {
        Courier courier = new Courier("Erika", "12345", null);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN_COURIER);
    }

    @Step("Авторизация курьера с неполными данными")
    public Response authorizationCourierNullData() {
        Courier courier = new Courier(null, "12345", null);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN_COURIER);
    }

    @Step("Удаление курьера")
    public void deleteCourier() {
        Courier courier = new Courier("ErikaKrolevski", "12345", null);

        Response responseLogin =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post(LOGIN_COURIER);

        Response responseDelete =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .delete(DELETE_COURIER + responseLogin.jsonPath().getString("id"));
    }
}
