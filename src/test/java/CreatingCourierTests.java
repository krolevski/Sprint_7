import courier.*;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.Step;

import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.*;

public class CreatingCourierTests extends CourierApi {
    CourierApi courierApi;

    @Before
    @Step("Подготовка данных для теста")
    public void setUp() {
        UrlApi baseURL = new UrlApi();
        baseURL.baseUrl();

        CourierApi courierApi = new CourierApi();
    }

    @After
    @Step("Удаление данных после теста")
    public void dataDelete() {
        Courier courier = new Courier("Rika", "1234", "Eri");

        IdCourier id = courierApi.getIdCourier(courier);
        courierApi.deleteCourier(id.getId());
    }

    @Test
    @Step("Проверка возможности создания курьера")
    public void createNewCourier() {
        Courier courier = new Courier("Rika", "1234", "Eri");

        Response response = courierApi.createCourier(courier);

        response.then().assertThat().body("ok", equalTo(true))
            .and().statusCode(SC_CREATED);

        System.out.println(response.body().asString());
    }

    @Test
    @Step("Проверка возможности создания двух одинаковых курьеров")
    public void createCourierWithSameUsername() {
        Courier courier = new Courier("Rika", "12345", "Eri");

        Response response = courierApi.createCourier(courier);

        response.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(SC_CREATED);

        Response response2 = courierApi.createCourier(courier);

        response2.then().assertThat().body("message", equalTo("Этот логин уже используется"))
                .and()
                .statusCode(SC_CONFLICT);

        System.out.println(response2.body().asString());
    }

        @Test
        @Step("Проверка возможности создания курьера с неполными данными")
        public void createCourierWithoutLogin () {
            Courier courier = new Courier(null, "12345", "Eri");

            Response response = courierApi.createCourier(courier);

            response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                    .and()
                    .statusCode(SC_BAD_REQUEST);

            System.out.println(response.body().asString());
        }
    }
