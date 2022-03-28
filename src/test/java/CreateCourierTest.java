import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import other.DetailsCourier;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class CreateCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }
    @Test
    @DisplayName("Создание курьера")
    @Description("Метод должен вернуть 201 с телом {'ok': true}")
    public void createNewCourier(){
        File json = new File("src/test/resources/newCourier.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("ok",equalTo(true))
                .and()
                .statusCode(201);
        System.out.println("Создание курьера: " + response.body().asString());
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Метод должен вернуть 409 с телом {'code': 409, 'message': 'Этот логин уже используется. Попробуйте другой.'}")
    public void cannotCreateIdenticalCouriers(){
        File json = new File("src/test/resources/newCourier.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("message",equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
        System.out.println("Создание двух одинаковых курьеров: " + response.body().asString());
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Метод должен вернуть 400 с телом {'code': 400, 'message': 'Недостаточно данных для создания учетной записи'}")
    public void createCourierWithOutLogin() {
        File json = new File("src/test/resources/newCourierWithoutLogin.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
        System.out.println("Создание курьера без логина: " + response.body().asString());
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Метод должен вернуть 400 с телом {'code': 400, 'message': 'Недостаточно данных для создания учетной записи'}")
    public void createCourierWithOutPassword() {
        File json = new File("src/test/resources/newCourierWithoutPassword.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
        System.out.println("Создание курьера без пароля: " + response.body().asString());
    }

    @AfterClass
    public static void cleanCourier(){
        DetailsCourier.deleteCourier();
    }
}
