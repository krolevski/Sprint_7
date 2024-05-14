import courier.*;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;

public class LoginCourierTests extends CourierApi {
    CourierApi courierApi;

    @Before
    @Step("Подготовка данных перед тестом")
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
    @Step("Проверка авторизации курьера с верными данными")
    public void courierAuthorization() {
        Response response = courierApi.authorizationCourier();

        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(SC_OK);

        System.out.println(response.body().asString());
    }

    @Test
    @Step("Проверка авторизации курьера с неверными данными")
    public void courierAuthorizationWithIncorrectPassword() {
        Response response = courierApi.authorizationCourierNotValidData();

        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);

        System.out.println(response.body().asString());
    }

    @Test
    @Step("Проверка авторизации курьера с неполными данными")
    public void courierAuthorizationWithoutPassword() {
        Response response = courierApi.authorizationCourierNullData();

        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);

        System.out.println(response.body().asString());
    }
}
