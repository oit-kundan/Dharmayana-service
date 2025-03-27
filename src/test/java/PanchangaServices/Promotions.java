package PanchangaServices;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Promotions {

    @Test
    public  void promotonTest(){


        Response responsse = given()
                .baseUri("https://api.stage.dharmayana.in/promotion/v1/promotions")
                .queryParam("masa","purnimanta")
                .queryParam("samvat","vikram")

                .when().get();

        responsse.then().log().all();

        Assert.assertEquals(responsse.getStatusCode(),200,"Status Code mismatch");

        JSONObject jsonResponse = new JSONObject(responsse.getBody().asString());
       Assert.assertTrue(jsonResponse.has("data"),"JsonResponse does not contain any data");

        JSONArray dataArray = jsonResponse.getJSONArray("data");
        Assert.assertFalse(dataArray.isEmpty(),"dataarray is Empty");

        for(int i=0; i< dataArray.length();i++){
            JSONObject dataObject = dataArray.getJSONObject(i);

            String image_url = dataObject.optString("image_url");
            String redirection_url = dataObject.optString("redirection_url");

            Assert.assertFalse(image_url.isEmpty(),"image_url is Empty for: " + dataObject);
            Assert.assertFalse(redirection_url.isEmpty(),"redirection_url is empty "+dataObject);
        }


    }

    @Test
    public void ImageUrlTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/promotion/v1/promotions?masa=purnimanta&samvat=vikram")
                .when().get();
        response.then().log().all();
       Assert.assertEquals(response.jsonPath().getString("data[0].image_url"), "https://d161fa2zahtt3z.cloudfront.net/sponsor-banner/shri-sharda.png", "image url mismatch");
    }

    @Test
    public void SponsoredTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/promotion/v1/promotions?masa=purnimanta&samvat=vikram")
                .when().get();
        response.then().log().all();
        Assert.assertEquals(response.jsonPath().getString("data[0].is_sponsored"), "true", "Sponsored mismatch");
    }

    @Test
    public void Sponsored1Test() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/promotion/v1/promotions?masa=purnimanta&samvat=vikram")
                .when().get();
        response.then().log().all();
        Assert.assertEquals(response.jsonPath().getString("data[2].is_sponsored"), "false", "Sponsored mismatch");
    }

    @Test
    public void withoutMasaTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/promotion/v1/promotions?samvat=vikram")
                .when().get();
        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");
    }

    @Test
    public void dataLengthTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/promotion/v1/promotions?samvat=vikram")
                .when().get();
        JSONObject JsonResponse = new JSONObject(response.getBody().asString());
        JSONArray dataArray = JsonResponse.getJSONArray("data");
        Assert.assertEquals(dataArray.length(),3,"data Array length mismatch");
    }

    @Test
    public void withoutMasa_samvatTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/promotion/v1/promotions")
                .when().get();
        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");
    }

    @Test
    public void withoutmasa_samvatTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/promotion/v1/promotions?masa=purnimanta&samvat=vikram")
                .when().get();
        response.then().log().all();
        Assert.assertEquals(response.jsonPath().getString("data[0].id"), "703565ee-82f9-4c10-b5c7-0c36169aced5", "id mismatch");
    }





}
