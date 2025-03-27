package PrarthanaServices;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PrarthanaList {

    @Test
    public  void dailyPrarthnaTest() {

        // Step 1: Call API and get response
        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas")
                .queryParam("type", "daily")
                .queryParam("masa", "purnimanta")
                .queryParam("samvat", "vikram")
                .when()
                .get();

        response.then().log().all();

        // Step 2: Validate API response status
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "API response status is not 200");

        // Step 3: Convert response to JSON
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());

        // Ensure "data" field is present and is an array
        Assert.assertTrue(jsonResponse.has("data"), "Response does not contain 'data' field");
        JSONArray dataArray = jsonResponse.getJSONArray("data");
        Assert.assertTrue(dataArray.length() > 0, "Data array is empty");

        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject prarthana = dataArray.getJSONObject(i);

            // Step 4: Extract actual values
            String actualTitle = prarthana.optString("title", "Missing");
            String actualAudioUrl = prarthana.optString("audio_url", "Missing");
            String actualImageUrl = prarthana.optString("image_url", "Missing");
            String actualBannerImageUrl = prarthana.optString("banner_image_url", "Missing");
            String actualIsAudioAvailable = prarthana.optString("is_audio_available", "false");



            // Step 5: Fetch expected values from external source (if available)
            String expectedTitle = getExpectedValue(prarthana, "title");
            String expectedAudioUrl = getExpectedValue(prarthana, "audio_url");
            String expectedImageUrl = getExpectedValue(prarthana, "image_url");
            String expectedBannerImageUrl = getExpectedValue(prarthana, "banner_image_url");
            String expectedIsAudioAvailable = getExpectedValue(prarthana, "is_audio_available");


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

            // Step 7: Validate "days" array dynamically
            Assert.assertTrue(prarthana.has("days"), "Days field is missing for prayer " + i);
            JSONArray daysArray = prarthana.optJSONArray("days");



            Assert.assertNotNull(daysArray, "Days array is null for prayer " + i);
            Assert.assertFalse(daysArray.isEmpty(), "Days array is empty for prayer " + i);



        }
    }

    // Function to fetch expected values from an external source (if needed)
    private String getExpectedValue(JSONObject jsonObject, String field) {
        return jsonObject.optString(field, "Missing");
    }


    @Test
    public void titleTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas")
                .when().get("?type=daily&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.getStatusCode(),200,"Statuscode mismatch");
        Assert.assertEquals(response.jsonPath().getString("data[0].title"),"Sunday Prayer","Title mismatch");

    }

    @Test
    public void dataLengthTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas")
                .when().get("?type=daily&masa=purnimanta&samvat=vikram");
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());

        Assert.assertTrue(jsonResponse.has("data"), "Response does not contain 'data' field");
        JSONArray dataArray = jsonResponse.getJSONArray("data");
        Assert.assertEquals(dataArray.length(),7,"data Array length mismatch");

    }

    @Test
    public void durationTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas")
                .when().get("?type=daily&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.getStatusCode(),200,"Statuscode mismatch");
        Assert.assertEquals(response.jsonPath().getString("data[1].duration"),"11 mins","Title mismatch");

    }

    @Test
    public void audioTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas")
                .when().get("?type=daily&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.getStatusCode(),200,"Statuscode mismatch");
        Assert.assertEquals(response.jsonPath().getString("data[2].is_audio_available"),"true","Title mismatch");

    }

    @Test
    public void banner_imageTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas")
                .when().get("?type=daily&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.getStatusCode(),200,"Statuscode mismatch");
        Assert.assertEquals(response.jsonPath().getString("data[6].banner_image_url"),"https://d161fa2zahtt3z.cloudfront.net/prarthanas/banner_images/saturday.png","Banner image mismatch");

    }

    @Test
    public void withoutTypeParamTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas")
                .when().get("?masa=purnimanta&samvat=vikram");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200,"Statuscode mismatch");
        Assert.assertEquals(response.jsonPath().getString("data[0].id"),"914a3950-a9e7-42fe-a33c-63a82bf14407","data id mismatch");

    }

    @Test
    public void differnetTypeParamTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas")
                .when().get("?type=weekly&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.getStatusCode(),200,"Statuscode mismatch");
        Assert.assertEquals(response.jsonPath().getString("data[0].title"),"Rahu Ashtottara Shatanamavali","Title mismatch");

    }

    @Test
    public void withoutMasa_samvatTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas")
                .when().get("?type=weekly&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.getStatusCode(),200,"Statuscode mismatch");

    }

    @Test
    public void withoutMasa_samvat_TypeParamTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas")
                .when().get();
        Assert.assertEquals(response.getStatusCode(),200,"Statuscode mismatch");
        Assert.assertEquals(response.jsonPath().getString("data[0].title"),"Rahu Ashtottara Shatanamavali","Title mismatch");

    }





}