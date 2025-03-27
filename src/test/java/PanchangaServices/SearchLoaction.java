package PanchangaServices;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SearchLoaction {

    @Test
    void location() {

        String url = "https://api.stage.dharmayana.in/v1/location/suggestions?keyword=dalsing";
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
