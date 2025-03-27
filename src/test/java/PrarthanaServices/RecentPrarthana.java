package PrarthanaServices;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonTypeInfo;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RecentPrarthana {

    @Test
    public void recentPrarthanaTest() {

        Response response = RestAssured
                .given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/83461502-6723-4166-818f-160eaab968dc")
                .queryParam("masa", "purnimanta")
                .queryParam("samvat", "vikram")
                .queryParam("prayer_language", "hindi")
                .when()
                .get();

        // Validate status code
        Assert.assertEquals(response.getStatusCode(), 200);

        // Convert response to JSON
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"), "Response does not contain 'data'");

        // Handle 'data' as either a JSONArray or JSONObject
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
        String actualAudioInfo = prarthana.optString("audio_info","missing");
        String actualIsChapter = prarthana.optString("chapters","missing");
        String actualIsAudioAvailable = prarthana.optString("is_audio_available", "false");
        String actualAudioUrl = prarthana.optString("audio_url", "Missing");
        String actualImageUrl = prarthana.optString("m3u8_url", "Missing");
        String actualBannerImageUrl = prarthana.optString("banner_image_url", "Missing");

        String actualIsAlbumArt = prarthana.optString("album_art", "false");
        String actualIsAvailLan = prarthana.optString("available_languages", "Missing");

        // Fetch expected values
        String expectedTitle = getExpectedValue(prarthana, "title");
        String expectedAudioInfo = getExpectedValue(prarthana,"audio_info");
        String expectedIsChapter = getExpectedValue(prarthana,"chapters");
        String expectedIsAudioAvailable = getExpectedValue(prarthana, "is_audio_available");
        String expectedAudioUrl = getExpectedValue(prarthana, "audio_url");
        String expectedImageUrl = getExpectedValue(prarthana, "m3u8_url");
        String expectedBannerImageUrl = getExpectedValue(prarthana, "banner_image_url");

        String expectedIsAlbumArt = getExpectedValue(prarthana, "album_art");
        String expectedIsAvailLan = getExpectedValue(prarthana, "available_languages");

        // Validate actual vs expected values
        Assert.assertEquals(actualTitle, expectedTitle, "Title mismatch for prayer " + index);
        Assert.assertEquals(actualAudioInfo,expectedAudioInfo,"Audio information mismatch"+index);
        Assert.assertEquals(actualIsChapter,expectedIsChapter,"chapter mismatch"+index);
//        Assert.assertEquals(actualIsAudioAvailable, expectedIsAudioAvailable, "is_audio_available mismatch for prayer " + index);
        Assert.assertEquals(actualAudioUrl, expectedAudioUrl, "Audio URL mismatch for prayer " + index);
        Assert.assertEquals(actualImageUrl, expectedImageUrl, "m3u8_url URL mismatch for prayer " + index);
        Assert.assertEquals(actualBannerImageUrl, expectedBannerImageUrl, "Banner Image URL mismatch for prayer " + index);

        Assert.assertEquals(actualIsAlbumArt, expectedIsAlbumArt, "album_art mismatch for prayer " + index);
        Assert.assertEquals(actualIsAvailLan, expectedIsAvailLan, "Language mismatch for prayer " + index);

        // Validate that values are not null
        Assert.assertNotNull(actualTitle, "Title is null for prayer " + index);
        Assert.assertNotNull(actualIsAudioAvailable, "is_audio_available is null for prayer " + index);
        Assert.assertNotNull(actualAudioUrl, "Audio URL is null for prayer " + index);
        Assert.assertNotNull(actualImageUrl, "Image URL is null for prayer " + index);
        Assert.assertNotNull(actualBannerImageUrl, "Banner Image URL is null for prayer " + index);

        Assert.assertNotNull(actualIsAlbumArt, "album_art is not available for prayer " + index);
        Assert.assertNotNull(actualIsAvailLan, "available_languages is not available for prayer " + index);
    }

    private String getExpectedValue(JSONObject jsonObject, String field) {
        return jsonObject.optString(field, "Missing");
    }


    @Test
    public void statusTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/83461502-6723-4166-818f-160eaab968dc")
                .when().get("?prayer_language=hindi&masa=purnimanta&samvat=vikram");
        response.then().log().all();

       Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
    }

    @Test
    public void without_languageTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/83461502-6723-4166-818f-160eaab968dc")
                .when().get("?masa=purnimanta&samvat=vikram");
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
    }

    @Test
    public void shlokTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/83461502-6723-4166-818f-160eaab968dc")
                .when().get("?masa=purnimanta&samvat=vikram");
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.chapters[0].stotras[0].shloks[0].shlok"),"नमस्ते शारदे देवी काश्मीरपुरवासिनि  त्वामहं प्रार्थये नित्यं विद्यादानं च देहि मे ॥ १ ॥","shlok mismatch");
    }

    @Test
    public void shlokexplanationTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/83461502-6723-4166-818f-160eaab968dc")
                .when().get("?prayer_language=hindi&masa=purnimanta&samvat=vikram");
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.chapters[0].stotras[0].shloks[0].explanation"),"नमस्ते शारदा देवी, काश्मीरपुर निवासिनी, मैं आपसे सदा विद्या का दान देने की प्रार्थना करता हूँ।","shlok mismatch");
    }

    @Test
    public void shlok_explanationTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/83461502-6723-4166-818f-160eaab968dc")
                .when().get("?prayer_language=english&masa=purnimanta&samvat=vikram");
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.chapters[0].stotras[0].shloks[0].explanation"),"Salutations to you, Goddess Sharada, residing in the city of Kashmir. I constantly pray to you to grant me the gift of knowledge.","shlok mismatch");
    }

    @Test
    public void shlok1Test() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/83461502-6723-4166-818f-160eaab968dc")
                .when().get("?prayer_language=english&masa=purnimanta&samvat=vikram");
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.chapters[0].stotras[0].shloks[0].shlok"),"Namaste Sharade Devi Kashmirpuravasini  Tvamaham prarthaye nityam vidyadanam cha dehi me ॥ 1 ॥","shlok mismatch");
    }

    @Test
    public void intPrayer_lan_shlokTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/83461502-6723-4166-818f-160eaab968dc")
                .when().get("?prayer_language=12&masa=purnimanta&samvat=vikram");
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.chapters[0].stotras[0].shloks[0].shlok"),"नमस्ते शारदे देवी काश्मीरपुरवासिनि  त्वामहं प्रार्थये नित्यं विद्यादानं च देहि मे ॥ १ ॥","shlok mismatch");
    }

    @Test
    public void intLanguageshlok_explanationTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/83461502-6723-4166-818f-160eaab968dc")
                .when().get("?prayer_language=english&masa=purnimanta&samvat=vikram");
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.chapters[0].stotras[0].shloks[0].explanation"),"Salutations to you, Goddess Sharada, residing in the city of Kashmir. I constantly pray to you to grant me the gift of knowledge.","shlok mismatch");
    }

    @Test
    public void invalid_lan_shlokTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/83461502-6723-4166-818f-160eaab968dc")
                .when().get("?prayer_language=$^%&%#%#&masa=purnimanta&samvat=vikram");
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.chapters[0].stotras[0].shloks[0].shlok"),"नमस्ते शारदे देवी काश्मीरपुरवासिनि  त्वामहं प्रार्थये नित्यं विद्यादानं च देहि मे ॥ १ ॥","shlok mismatch");
    }

    @Test
    public void invalid_language_shlok_explanationTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/83461502-6723-4166-818f-160eaab968dc")
                .when().get("?prayer_language=english&masa=purnimanta&samvat=vikram");
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.chapters[0].stotras[0].shloks[0].explanation"),"Salutations to you, Goddess Sharada, residing in the city of Kashmir. I constantly pray to you to grant me the gift of knowledge.","shlok mismatch");
    }







}
