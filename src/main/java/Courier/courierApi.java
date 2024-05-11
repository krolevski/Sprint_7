package courier;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierApi {
     final static String CREATE_COURIER = "/api/v1/courier";
     final static String LOGIN_COURIER = "/api/v1/courier/login";
     final static String DELETE_COURIER = "/api/v1/courier/%s";

    @Step("Создание курьера")
    public Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(CREATE_COURIER);
    }

    @Step("Авторизация курьера")
    public Response authorizationCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN_COURIER);
    }

    @Step("Получить id курьера")
    public IdCourier getIdCourier(Courier courier) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(CourierApi.LOGIN_COURIER);

        String IdString = response.body().asString();
        Gson gson = new Gson();
        IdCourier id = gson.fromJson(IdString, IdCourier.class);
        return id;
    }

    @Step("Удаление курьера")
    public void deleteCourier(String id) {

        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .delete(String.format(DELETE_COURIER, id));
    }
}
