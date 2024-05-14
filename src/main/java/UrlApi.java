import io.restassured.RestAssured;
import lombok.*;


public class UrlApi {
    private String url;

    public void baseUrl() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }
}