package orders;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi {
    final static String CREATE_ORDER = "/api/v1/orders";
    public static final String COURIER_ID = "/api/v1/orders?courierId=%s";


    @Step("Получение списка заказов")
    public Response getCourierOrder() {
        return given()
                .get("/api/v1/orders")
                .then()
                .extract().response();
    }
    @Step("Создание заказа")
    public Response newOrders(CreateOrder orders) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(orders)
                .when()
                .post(CREATE_ORDER);
    }
}
