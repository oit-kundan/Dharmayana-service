package PrarthanaServices;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PlayPrarthana {

    @Test
    public void playPrarthanaTest(){

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/user-action")
                .header("Content-Type","application/json")
                .body(new File("src/test/java/PrarthanaServices/Userdata.json"))

                .when().post();

        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch for post request");
       Assert.assertEquals(response.jsonPath().getString("message"),"Successful","Message value mismatch");

    }


    @Test
    public void withoutBodyTest(){

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/user-action")
                .header("Content-Type","application/json")


                .when().post();

        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),400,"status code mismatch for post request");


    }

    @Test
    public void bodyWithoutIdTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/user-action")
                .header("Content-Type", "application/json")
                .body(new File("src/test/java/PrarthanaServices/Userdata.json"))


                .when().post();

        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch for post request");


    }

    @Test
    public void withoutTokenStatusTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/user-action")
                .header("Content-Type", "application/json")
                .body(new File("src/test/java/PrarthanaServices/Userdata.json"))
                .queryParam("Authorization","fedoo43fjojc324")


                .when().post();

        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch for post request");



    }






}
