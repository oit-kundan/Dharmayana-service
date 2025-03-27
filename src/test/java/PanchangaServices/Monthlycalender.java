package PanchangaServices;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Monthlycalender {

    @Test
    void details() {
        String url = "https://api.stage.dharmayana.in/v1/calendar/monthly?timestamp=1740029588&lat=-6.2839295&long=106.7196771";
        given()
                .header("Authorization", "Bearer YOUR_ACCESS_TOKEN")
                .header("Content-Type", "application/json")
                .when()

                .get(url)
                .then()
                .statusCode(200)
                .log().all();
    }
}


