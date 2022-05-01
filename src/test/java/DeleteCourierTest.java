import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import pogo.ResponseLoginId;
import utils.DetailsCourier;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class DeleteCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        DetailsCourier.createNewCourier();
    }

    @Test
    @DisplayName("Удаление курьера")
    @Description("Метод должен вернуть 200 с телом {'ok': true}")
    public void deleteCourier() {
        File json = new File("src/test/resources/newCourierId.json");
        ResponseLoginId response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login")
                        .then().extract().body().as(ResponseLoginId.class);
        int id = response.getId();
        Response delete =
                given()
                        .delete("/api/v1/courier/" + id);
        delete.then().assertThat().body("ok",equalTo(true))
                .and()
                .statusCode(200);
        System.out.println("Удаление курьера: " + delete.body().asString());
    }
}
