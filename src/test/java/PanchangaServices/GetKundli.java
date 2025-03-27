 package PanchangaServices;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetKundli {

    @Test
    void getkundli(){
        String url = "https://api.stage.dharmayana.im/v1/panchanga/details";
        given()
                .queryParam("timestamp","1740040430 ")
                .queryParam("lat","25.6727054")
                .queryParam("lon","85.8258931")
                .log().all()


                .when()
                .get(url)

                .then()
                .statusCode(200)
                .log().all();



    }
}


