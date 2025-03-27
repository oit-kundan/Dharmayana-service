package PrarthanaServices;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PrarthanaDetails {

    @Test
    public void responseBodyTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in")
                .queryParam("prayer_language", "hindi")
                .queryParam("masa", "purnimanta")
                .queryParam("samvat", "vikram")

                .when()
                .get("/prarthana/v1/prarthanas/59d4d5b6-6bfd-4ff0-aa6c-bdbab88f1875");

        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        JSONObject jsoResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsoResponse.has("data"), "JsoResponse does not contain any data");

        JSONObject dataObject = jsoResponse.getJSONObject("data");
        String Title = dataObject.getString("title");
        Assert.assertEquals(Title, "Monday Prayer", "Title mismatch ");
        System.out.println(Title);


        JSONObject audioInfo = dataObject.getJSONObject("audio_info");
        System.out.println(audioInfo.toString(4));
        String AudioUrl = audioInfo.getString("audio_url");
        String m3u8Url = audioInfo.getString("m3u8_url");
        Assert.assertEquals(AudioUrl, "https://d161fa2zahtt3z.cloudfront.net/audio/stitched_audio/monday_prayer.mp3", "Audioo mismatch " + Title);
        Assert.assertEquals(m3u8Url, "https://d161fa2zahtt3z.cloudfront.net/audio/transcoded_audio/monday_prayer/monday_prayer.m3u8", "m3u8Url mismatch " + Title);

        String description = dataObject.optString("description");
        Assert.assertFalse(description.isEmpty(),"description is empty "+ Title);


    }


    @Test
    public void withoutSamvatTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/59d4d5b6-6bfd-4ff0-aa6c-bdbab88f1875")
                .queryParam("prayer_language","hindi")
                .queryParam("masa","purnimanta")
                .queryParam("samvat","vikram")
                .when().get();


        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
    }

    @Test
    public void titleTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/59d4d5b6-6bfd-4ff0-aa6c-bdbab88f1875")
                .queryParam("prayer_language","hindi")
                .queryParam("masa","purnimanta")
                .queryParam("samvat","vikram")
                .when().get();


        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.title"),"Monday Prayer","Title mismatch");
    }

    @Test
    public void shlokTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/59d4d5b6-6bfd-4ff0-aa6c-bdbab88f1875")
                .queryParam("prayer_language","marathi")
                .queryParam("masa","purnimanta")
                .queryParam("samvat","vikram")
                .when().get();


        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.chapters[0].stotras[0].shloks[0].shlok"),"शुक्लाम्बरधरं विष्णुं शशिवर्णं चतुर्भुजम् |  प्रसन्नवदनं ध्यायेत् सर्व विघ्नोपशान्तये ॥","shloks mismatch");
    }

    @Test
    public void aval_languageTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/59d4d5b6-6bfd-4ff0-aa6c-bdbab88f1875")
                .queryParam("prayer_language","marathi")
                .queryParam("masa","purnimanta")
                .queryParam("samvat","vikram")
                .when().get();


        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.available_languages[2].value"),"ಕನ್ನಡ","available language value mismatch");
    }




}
