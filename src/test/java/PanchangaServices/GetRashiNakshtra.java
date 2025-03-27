package PanchangaServices;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetRashiNakshtra {

    @Test
    void getRashiNakshtra(){
        String url = "https://api.stage.dharmayana.in/prediction/v1/rashi";

        given()
                .queryParam("lat","25.6727058")
                .queryParam("long","85.8258931")
               .queryParam("dob","16/04/2020")
              .queryParam("tob","15:18")
                .queryParam("offset","5.5")
//                .queryParam("timezone","Asia%2FKolkata")

                .log().all()

                .when()
                .get(url)

                .then()
//                .log().all();
             .statusCode(200);

    }




}
