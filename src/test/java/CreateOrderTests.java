import io.qameta.allure.Step;
import io.restassured.response.Response;
import orders.CreateOrder;
import orders.OrderApi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.*;

@RunWith(Parameterized.class)
public class CreateOrderTests extends OrderApi {
    private final List<String> color;
    OrderApi orderApi;
    Response response;

    public CreateOrderTests(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    @Step("Параметризуем параметр выбора цвета")
    public static Object[][] checkCreateOrderWithChoiceColor() {
        return new Object[][] {
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()},
        };
    }

    @Before
    public void setUp(){
        UrlApi baseURL = new UrlApi();
        baseURL.baseUrl();

        OrderApi orderApi1 = new OrderApi();
    }

    @After
    @Step("Удаление заказа после теста")
    public void deleteOrder() {
        given()
                .header("Content-type", "application/json")
                .when()
                .put("/api/v1/orders/cancel?track=" + response.jsonPath().getString("track"))
                .then().assertThat().body("ok", equalTo(true));
    }

    @Test
    @Step("Создание заказа")
    public void createNewOrder() {
        CreateOrder createOrder = new CreateOrder("Снежанна", "Снежевна", "Зимняя, 45", "4", "89123456789", 2, "2024-12-25", "Даже зимой хочется", color);

       Response response = orderApi.newOrders(createOrder);

        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(SC_CREATED);

        System.out.println(response.body().asString());
    }
}
