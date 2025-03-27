package PrarthanaServices;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SabheeDevta {

    @Test
    void prarthanaV2Dieties() {

        Response response = RestAssured
                .given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v2/deities/055d329e-eabd-403e-9f41-2912c278f978")
                .queryParam("masa", "purnimanta")
                .queryParam("samvat", "vikram")
                .when()
                .get();

        // Validate status code

        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

        // Convert response to JSON
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"), "Response does not contain 'data' field");

        // Handle 'data' as either JSONArray or JSONObject
        Object dataObject = jsonResponse.get("data");

        if (dataObject instanceof JSONArray) {
            JSONArray dataArray = (JSONArray) dataObject;
            Assert.assertTrue(dataArray.length() > 0, "Data array is empty");

            for (int i = 0; i < dataArray.length(); i++) {
                validatePrarthana(dataArray.getJSONObject(i), i);
            }
        } else if (dataObject instanceof JSONObject) {
            validatePrarthana((JSONObject) dataObject, 0);
        } else {
            Assert.fail("'data' field is neither an object nor an array");
        }
    }

    private void validatePrarthana(JSONObject prarthana, int index) {
        // Extract Actual values
        String actualTitle = prarthana.optString("title", "Missing");
        String actualAudioUrl = prarthana.optString("aliases_v1", "Missing");
        String actualImageUrl = prarthana.optString("prarthanas", "Missing");
        String actualBannerImageUrl = prarthana.optString("image_url", "Missing");
        String actualIsAudioAvailable = prarthana.optString("is_audio_available", "false");

        // Fetch expected values
        String expectedTitle = getExpectedValue(prarthana, "title");
        String expectedAudioUrl = getExpectedValue(prarthana, "aliases_v1");
        String expectedImageUrl = getExpectedValue(prarthana, "prarthanas");
        String expectedBannerImageUrl = getExpectedValue(prarthana, "image_url");
        String expectedIsAudioAvailable = getExpectedValue(prarthana, "is_audio_available");

        // Validate actual vs expected values
        Assert.assertEquals(actualTitle, expectedTitle, "Title mismatch for prayer " + index);
        Assert.assertEquals(actualAudioUrl, expectedAudioUrl, "Audio URL mismatch for prayer " + index);
        Assert.assertEquals(actualImageUrl, expectedImageUrl, "Image URL mismatch for prayer " + index);
        Assert.assertEquals(actualBannerImageUrl, expectedBannerImageUrl, "Banner Image URL mismatch for prayer " + index);
        Assert.assertEquals(actualIsAudioAvailable, expectedIsAudioAvailable, "is_audio_available mismatch for prayer " + index);

        // Validate that values are not null
        Assert.assertNotNull(actualTitle, "Title is null for prayer " + index);
        Assert.assertNotNull(actualAudioUrl, "Audio URL is null for prayer " + index);
        Assert.assertNotNull(actualImageUrl, "Image URL is null for prayer " + index);
        Assert.assertNotNull(actualBannerImageUrl, "Banner Image URL is null for prayer " + index);
        Assert.assertNotNull(actualIsAudioAvailable, "is_audio_available is null for prayer " + index);
    }

    private String getExpectedValue(JSONObject jsonObject, String field) {
        return jsonObject.optString(field, "Missing");
    }
}
