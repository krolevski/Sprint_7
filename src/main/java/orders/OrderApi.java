package orders;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi {
    final static String CREATE_ORDER = "/api/v1/orders";
    public static final String ORDER_TRACK = "/api/v1/orders/cancel?track=";
    public Response response;

    @Step("Получение списка заказов")
    public Response getCourierOrder() {
        return given()
                .get("/api/v1/orders")
                .then()
                .extract().response();
    }
    @Step("Создание заказа")
    public Response newOrders(CreateOrder orders) {
        return response = given()
                .header("Content-type", "application/json")
                .and()
                .body(orders)
                .when()
                .post(CREATE_ORDER);
    }

    @Step("Удаление заказа")
    public void deleteOrder() {
        given()
                .header("Content-type", "application/json")
                .when()
                .put(ORDER_TRACK + response.jsonPath().getString("track"));
    }
}
