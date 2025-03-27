package PanchangaServices;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Notification {

    @Test
    void notificatiom() {

        String url = "https://api.stage.dharmayana.in/v1/notification/daily";
        given()
                .queryParam("timestamp", "1740042446")

                .when()
                .get(url)

                .then()
                .log().all()
                .statusCode(200);


    }
}
