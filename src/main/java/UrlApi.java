import io.restassured.RestAssured;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UrlApi {
    private String url;

    public void baseUrl() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }
}
