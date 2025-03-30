package PanchangaServices;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DailyPredction {
    @Test
    void dailyPredction() {


        Response response = RestAssured
                .given()
                .baseUri("https://api.stage.dharmayana.in")
                .queryParam("date", "30/03/2025")
                .queryParam("rashi", "aries")
                .log().all()

                .when()
                .get("prediction/v1/daily");

                 response.then().log().all();
                 Assert.assertEquals(response.getStatusCode(), 200, "status code does not match");

        JSONObject jsonResponse = new JSONObject(response.asString());
        JSONObject dataObject = jsonResponse.getJSONObject("data");

        String date = dataObject.getString("date");



        String rashifal = response.jsonPath().getString("data.rashi");

        Assert.assertEquals(rashifal, "Mesha Rashi", "Value is incorrect");


        Assert.assertEquals(date, "30 March 2025", "date not matched");
        Assert.assertNotNull(response.jsonPath().getString("data.description"), "Description is Null");
        Assert.assertNotNull(response.jsonPath().getString("data.banner_image_url"), "Description is Null");

    }
}
