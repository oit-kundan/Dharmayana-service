package PanchangaServices;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SolarEclipse {


    @Test
    void solareclipse(){

        String url = "https://aa.usno.navy.mil/api/eclipses/solar/date?date=2024-4-8";

          given()
                  .queryParam("date","2024-4-8")
                  .queryParam("coords","12.5183554,-69.9672889")
                  .queryParam("height","15")
                  .log().all()

                  .when()
                  .get(url)
                  .then()

                  .log().all()
                  .statusCode(200);

    }
}
