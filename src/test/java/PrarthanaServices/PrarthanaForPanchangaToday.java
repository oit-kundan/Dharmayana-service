package PrarthanaServices;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PrarthanaForPanchangaToday {


    @Test
    void  prarthanaByPanchangaTodayTest(){

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/panchanga/prarthanas/today")
                .queryParam("timestamp","1740633586")
                .queryParam("masa","purnimanta")
                .queryParam("samvat","vikram")

                .when()
                .get();

        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");

        // convert response to JSON
        JSONObject jsonresponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonresponse.has("data"),"jsonresponse does not contain any data");

        Object dataObject = jsonresponse.get("data");

        if(dataObject instanceof JSONArray){

            JSONArray dataArray = (JSONArray) dataObject;

            Assert.assertTrue(dataArray.length()>0,"dataArray is empty");
            for(int i=0; i<dataArray.length();i++){

                validatePrarthana(dataArray.getJSONObject(i),i);
            }
        }
        else if (dataObject instanceof  JSONObject) {
            validatePrarthana((JSONObject)dataObject,0);

        }
        else{
            Assert.fail("'data' is neither object nor array" );
        }

    }

    private  void validatePrarthana(JSONObject prarthana, int index){


        // Extract Actual values
        String actualTitle = prarthana.optString("title", "Missing");
        String actualBannerUrl = prarthana.optString("banner_image_url", "Missing");
        String actualImageUrl = prarthana.optString("days", "Missing");
        String actualBannerImageUrl = prarthana.optString("image_url", "Missing");
        String actualIsAudioAvailable = prarthana.optString("is_audio_available", "false");
        String actualAudioUrl = prarthana.optString("audio_url","missing");

        // Fetch expected values
        String expectedTitle = getExpectedValue(prarthana, "title");
        String expectedBannerUrl = getExpectedValue(prarthana, "banner_image_url");
        String expectedImageUrl = getExpectedValue(prarthana, "days");
        String expectedBannerImageUrl = getExpectedValue(prarthana, "image_url");
        String expectedIsAudioAvailable = getExpectedValue(prarthana, "is_audio_available");
        String expectedAudioUrl =  getExpectedValue(prarthana,"audio_url");


        // Validate actual vs expected values
        Assert.assertEquals(actualTitle, expectedTitle, "Title mismatch for prayer " + index);
        Assert.assertEquals(actualBannerUrl, expectedBannerUrl, "days mismatch for prayer " + index);
        Assert.assertEquals(actualImageUrl, expectedImageUrl, "Image URL mismatch for prayer " + index);
        Assert.assertEquals(actualBannerImageUrl, expectedBannerImageUrl, "Banner Image URL mismatch for prayer " + index);
        Assert.assertEquals(actualIsAudioAvailable, expectedIsAudioAvailable, "is_audio_available mismatch for prayer " + index);
        Assert.assertEquals(actualAudioUrl,expectedAudioUrl,"Audio_url is missing");

        // Validate that values are not null
        Assert.assertNotNull(actualTitle, "Title is null for prayer " + index);
        Assert.assertNotNull(actualAudioUrl, "Audio URL is null for prayer " + index);
        Assert.assertNotNull(actualImageUrl, "Image URL is null for prayer " + index);
        Assert.assertNotNull(actualBannerImageUrl, "Banner Image URL is null for prayer " + index);
        Assert.assertNotNull(actualIsAudioAvailable, "is_audio_available is null for prayer " + index);

    }
    private String getExpectedValue(JSONObject jsonObject,String field){
        return jsonObject.optString(field,"missing");
    }


    @Test
    public void titleTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/panchanga/prarthanas/today")
                .when().get("?timestamp=1742487461&masa=purnimanta&samvat=vikram");
        response.then().log().all();
        Assert.assertEquals(response.jsonPath().getString("data[0].title"),"Thursday Prayer","Title mismatch");
    }

    @Test
    public void withoutmasa_samvatTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/panchanga/prarthanas/today")
                .when().get("?timestamp=1742487461");
        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
    }


    @Test
    public void withoutmasa_samvat_IdTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/panchanga/prarthanas/today")
                .when().get("?timestamp=1742487461");
        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data[0].id"),"8064b421-7a22-40f5-89fa-96f849102517","id mismatch");
    }

    @Test
    public void audio_availableTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/panchanga/prarthanas/today")
                .when().get("?timestamp=1742487461");
        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data[0].is_audio_available"),"true","id mismatch");
    }

    @Test
    public void audioTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/panchanga/prarthanas/today")
                .when().get("?timestamp=1742487461");
        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data[0].audio_url"),"https://d161fa2zahtt3z.cloudfront.net/audio/stitched_audio/thursday_prayer.mp3","Audio url mismatch");
    }

    @Test
    public void invalidTimestampTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/panchanga/prarthanas/today")
                .when().get("?timestamp=465767hvhgu");
        Assert.assertEquals(response.getStatusCode(),400,"Bad request");

    }

    @Test
    public void withoutTimestampTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/panchanga/prarthanas/today")
                .when().get("?masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.getStatusCode(),400,"Bad request");

    }




}
