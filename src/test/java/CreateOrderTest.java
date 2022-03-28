import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final String color;

    public CreateOrderTest(String color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{
                {"BLACK"},
                {"GREY"},
                {"BLACK\" , \"GREY"},
                {""}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Создание заказа с разными цветами")
    @Description("Метод должен вернуть 201 с телом {'track':}")
    public void createOrder() {
        String orderBody = "{\"firstName\": \"Ivan\", "
                + "\"lastName\": \"Ivanov\", "
                + "\"address\": \"Mira, 12 apt.\", "
                + "\"metroStation\": 4, "
                + "\"phone\": \"+7 988 377 99 88\", "
                + "\"rentTime\": 5, "
                + "\"deliveryDate\": \"2022-03-30\", "
                + "\"comment\": \"Лифт не работает\", "
                + "\"color\":";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(orderBody + "[\"" + color + "\"]}")
                        .when()
                        .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
        System.out.println("Создан заказ: " + response.body().asString());
    }
}
