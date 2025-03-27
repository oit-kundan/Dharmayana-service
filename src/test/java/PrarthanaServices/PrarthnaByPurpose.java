package PrarthanaServices;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PrarthnaByPurpose {

    @Test
  public void prarthnaBYPurposeTest(){

        Response response = RestAssured
                .given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/collection/purpose?masa=purnimanta&samvat=vikram")
                .queryParam("masa","purnimanta")
                .queryParam("samvat","vikram")

                .when()
                .get();
        // response status
        response.then().log().all();

        // validate response
        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");


        // convert respoinse to JSON
        JSONObject jsoResponse = new JSONObject(response.getBody().asString());


        //Ensure 'data' field is present in array
        Assert.assertTrue(jsoResponse.has("data"),"response does not contain data field");
        JSONArray dataArray = jsoResponse.getJSONArray("data");

        Assert.assertTrue(dataArray.length()>0,"data array is empty");

        for(int i=0;i< dataArray.length();i++){

                JSONObject prarthana =  dataArray.optJSONObject(i);

                // Extract Actual value
            String actualTitle = prarthana.optString("title","missing");
            String actualAudioUrl = prarthana.optString("audio_url", "Missing");
            String actualImageUrl = prarthana.optString("image_url", "Missing");
            String actualBannerImageUrl = prarthana.optString("banner_image_url", "Missing");
            String actualIsAudioAvailable = prarthana.optString("is_audio_available", "false");


            //Fetch expected value from external source
            String expectedTitle = getExpectedValue(prarthana,"title");
            String expectedAudioUrl= getExpectedValue(prarthana,"audio_url");
            String  expectedImageUrl = getExpectedValue(prarthana,"image_url");
            String  expectedBannerImageUrl = getExpectedValue(prarthana,"banner_image_url");
            String  expectedIsAudioAvailable = getExpectedValue(prarthana,"is_audio_available");


            // Step 6: Validate actual vs expected values
            Assert.assertEquals(actualTitle, expectedTitle, "Title mismatch for prayer " + i);
            Assert.assertEquals(actualAudioUrl, expectedAudioUrl, "Audio URL mismatch for prayer " + i);
            Assert.assertEquals(actualImageUrl, expectedImageUrl, "Image URL mismatch for prayer " + i);
            Assert.assertEquals(actualBannerImageUrl, expectedBannerImageUrl, "Banner Image URL mismatch for prayer " + i);
            Assert.assertEquals(actualIsAudioAvailable, expectedIsAudioAvailable, "is_audio_available mismatch for prayer " + i);



            // validate null value
            Assert.assertNotNull(actualTitle, "Title is null for prayer " + i);
            Assert.assertNotNull(actualAudioUrl, "Audio URL is null for prayer " + i);
            Assert.assertNotNull(actualImageUrl, "Image URL is null for prayer " + i);
            Assert.assertNotNull(actualBannerImageUrl, "Banner Image URL is null for prayer " + i);
            Assert.assertNotNull(actualIsAudioAvailable, "is_audio_available is null for prayer " + i);

        }
    }
    private String getExpectedValue(JSONObject jsonObject, String field) {
        return jsonObject.optString(field, "Missing");

    }


    @Test
    public void durationTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/collection/purpose")
                .when().get("?masa=purnimanta&samvat=vikram");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200,"Statuscode mismatch");
        Assert.assertEquals(response.jsonPath().getString("data[1].duration"),"1 min","Title mismatch");

    }

    @Test
    public void dataLengthTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/collection/purpose")
                .when().get("?masa=purnimanta&samvat=vikram");

        JSONObject jsoResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsoResponse.has("data"),"response does not contain data field");
        JSONArray dataArray = jsoResponse.getJSONArray("data");
        Assert.assertEquals(dataArray.length(),15,"data length mismatch");


    }

    @Test
    public void titleTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/collection/purpose")
                .when().get("?masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data[0].title"),"While doing Pradaksina","Title mismatch");

    }

    @Test
    public void duration1Test() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/collection/purpose")
                .when().get("?masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data[1].duration"),"1 min","duration mismatch");

    }

    @Test
    public void title1Test() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/collection/purpose")
                .when().get("?masa=purnimanta&samvat=Kali");
        Assert.assertEquals(response.jsonPath().getString("data[0].title"),"While doing Pradaksina","Title mismatch");


    }

    @Test
    public void dataLength_changesParamTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/collection/purpose")
                .when().get("?masa=purnimanta&samvat=Kali");
        Assert.assertEquals(response.jsonPath().getString("data[14].slug"),"before-taking-medicine","Slug mismatch");

    }


}
