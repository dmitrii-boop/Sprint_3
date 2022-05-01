import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class GettingOrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Метод должен вернуть 200 с телом {'orders':}")

    public void getOrders() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .get("/api/v1/orders?limit=10&page=0");
        response.then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
        System.out.println("Список заказов: " + response.body().asString());
    }
}
