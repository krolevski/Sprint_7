import courier.*;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.Step;

import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.*;

public class CreateCourierTests extends CourierApi {
    CourierApi courierApi;

    @Before
    @Step("Подготовка данных для теста")
    public void setUp() {
        UrlApi baseURL = new UrlApi();
        baseURL.baseUrl();

        courierApi = new CourierApi();
    }

    @After
    @Step("Удаление данных после теста")
    public void dataDelete() {
        courierApi.deleteCourier();
    }

    @Test
    @Step("Проверка возможности создания курьера")
    public void createNewCourier() {
        Response responseCreate = courierApi.createCourier();

        responseCreate.then().assertThat().body("ok", equalTo(true))
                        .and().statusCode(SC_CREATED);

        System.out.println(responseCreate.body().asString());
    }

    @Test
    @Step("Проверка возможности создания двух одинаковых курьеров")
    public void createCourierWithSameUsername() {

        Response responseOne = courierApi.createCourier();

        responseOne.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(SC_CREATED);

        Response responseTwo = courierApi.createCourier();

        responseTwo.then().assertThat().body("message", equalTo("Этот логин уже используется"))
                .and()
                .statusCode(SC_CONFLICT);

        System.out.println(responseTwo.body().asString());
    }

        @Test
        @Step("Проверка возможности создания курьера с неполными данными")
        public void createCourierWithoutLogin () {
            Response response = courierApi.createCourierNullData();

            response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                    .and()
                    .statusCode(SC_BAD_REQUEST);

            System.out.println(response.body().asString());
        }
    }
