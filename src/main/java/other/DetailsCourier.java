package other;

import pogo.ResponseLoginId;
import java.io.File;

import static io.restassured.RestAssured.given;

public class DetailsCourier {

    public static void deleteCourier() {
        File json = new File("src/test/resources/newCourierId.json");
        ResponseLoginId response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login")
                        .then().extract().body().as(ResponseLoginId.class);
        int id = response.getId();
        given()
                .delete("/api/v1/courier/" + id);
    }
    public static void createNewCourier(){
        File json = new File("src/test/resources/newCourier.json");
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier");
    }
}
