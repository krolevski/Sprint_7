import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.*;

public class ListOrderTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Step("Получение списка заказов")
    public void checkGetListOfOrders() {
        Response responseListOrder = given()
                .get("/api/v1/orders")
                .then()
                .extract().response();

        responseListOrder.then().assertThat().body("orders", notNullValue())
                        .and()
                        .statusCode(SC_OK);

        System.out.println(responseListOrder.body().asString());
    }
}
