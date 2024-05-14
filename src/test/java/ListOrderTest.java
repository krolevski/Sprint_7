import courier.CourierApi;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import orders.OrderApi;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.*;

public class ListOrderTest  {
    CourierApi courierApi = new CourierApi();
    OrderApi orderApi = new OrderApi();


    @Before
    public void setUp() {
        UrlApi baseURL = new UrlApi();
        baseURL.baseUrl();

        courierApi.createCourier();
    }

    @Test
    @Step("Получение списка заказов")
        public void checkGetListOfOrders() {
            Response responseListOrder = orderApi.getCourierOrder();

            responseListOrder.then().assertThat().body("orders", notNullValue())
                    .and()
                    .statusCode(SC_OK);

            System.out.println(responseListOrder.body().asString());
        }
    }
