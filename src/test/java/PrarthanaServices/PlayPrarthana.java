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
}
