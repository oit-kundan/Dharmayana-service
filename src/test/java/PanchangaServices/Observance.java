package PanchangaServices;


import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Observance {

    @Test
    void observance(){
        Response response  = given()
                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat","25.6727054")
                .queryParam("long","85.8258931")
                .queryParam("timestamp","1740222242")

                .when()
                .get();
//        System.out.println(response.getBody().asPrettyString());

        Assert.assertEquals(response.getStatusCode(),200);
        String paksha = response.jsonPath().getString("data.observances[10].data.name");
        Assert.assertEquals(paksha,"Rangabhari Ekadashi","not matched");
        Assert.assertEquals(response.jsonPath().getString("data.observances[16].panchanga.paksha"),"Shukla Paksha","value do not match");
        Assert.assertEquals(response.jsonPath().getString("data.observances[10].data.key"),"ekadashi","value do not match");

    }

}
