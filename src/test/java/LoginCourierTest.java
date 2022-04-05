import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import utils.DetailsCourier;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class LoginCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        DetailsCourier.createNewCourier();
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Метод должен вернуть 200 с телом {'id':}")
    public void loginCourier() {
        File json = new File("src/test/resources/newCourierId.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
        System.out.println("Логин курьера: " + response.body().asString());
    }

    @Test
    @DisplayName("Запрос без логина")
    @Description("Метод должен вернуть 400 с телом {'code': 400, 'message': 'Недостаточно данных для входа'}")
    public void loginCourierWithOutLogin() {
        File json = new File("src/test/resources/loginCourierWithOutLogin.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().body("message",equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        System.out.println("Запрос без логина: " + response.body().asString());
    }

    @Test
    @DisplayName("Запрос без пароля")
    @Description("Метод должен вернуть 400 с телом {'code': 400, 'message': 'Недостаточно данных для входа'}")
    public void loginCourierWithOutPassword() {
        File json = new File("src/test/resources/loginCourierWithOutPassword.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().body("message",equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        System.out.println("Запрос без пароля: " + response.body().asString());
    }

    @Test
    @DisplayName("Запрос с несуществующим лого-пасом")
    @Description("Метод должен вернуть 404 с телом {'code': 400, 'message': 'Учетная запись не найдена'}}")
    public void loginCourierWithNonExistentLogoPass() {
        File json = new File("src/test/resources/loginCourierWithNonExistentLogoPass.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().body("message",equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
        System.out.println("Логин не существующего курьера: " + response.body().asString());
    }
    @AfterClass
    public static void cleanCourier(){
        DetailsCourier.deleteCourier();
    }
}
