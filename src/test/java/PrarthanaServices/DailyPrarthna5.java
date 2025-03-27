package PrarthanaServices;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.nio.file.Paths;
import static io.restassured.RestAssured.given;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;


public class DailyPrarthna5 {


    public class ExpectedValuesLoader {
        private static final Map<Integer, Map<String, String>> expectedValues = new HashMap<>();

        static {
            try {
                String content = Files.readString(Paths.get("src/test/resources/expected_values.json"));
                JSONArray dataArray = new JSONObject(content).optJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject prayer = dataArray.getJSONObject(i);
                    Map<String, String> prayerData = new HashMap<>();
                    prayer.keys().forEachRemaining(key -> prayerData.put(key, prayer.optString(key, "Missing")));
                    expectedValues.put(i, prayerData);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error loading expected values", e);
            }
        }

        public static String getExpectedValue(int index, String key) {
            return expectedValues.getOrDefault(index, Map.of()).getOrDefault(key, "Missing");
        }
    }

    @Test
    void dailyPrarthna() {
        String baseUrl = System.getProperty("base.url", "https://api.stage.dharmayana.in");

        Response response = given()
                .baseUri(baseUrl + "/prarthana/v1/prarthanas")
                .queryParam("type", System.getProperty("prarthana.type", "daily"))
                .queryParam("masa", System.getProperty("prarthana.masa", "purnimanta"))
                .queryParam("samvat", System.getProperty("prarthana.samvat", "vikram"))
                .when()
                .get();
        response.then().log().all();

        // Validate API response status
        Assert.assertEquals(response.getStatusCode(), 200, "API response status is not 200");

        // Convert API response to JSON
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"), "Response does not contain 'data' field");

        JSONArray dataArray = jsonResponse.getJSONArray("data");
        Assert.assertTrue(dataArray.length() > 0, "Data array is empty");

        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject prarthana = dataArray.getJSONObject(i);

            // Extract actual values
            String actualTitle = prarthana.optString("title", "Missing");
            String actualAudioUrl = prarthana.optString("audio_url", "Missing");
            String actualImageUrl = prarthana.optString("image_url", "Missing");

            // Fetch expected values dynamically
            String expectedTitle = ExpectedValuesLoader.getExpectedValue(i, "title");
            String expectedAudioUrl = ExpectedValuesLoader.getExpectedValue(i, "audio_url");
            String expectedImageUrl = ExpectedValuesLoader.getExpectedValue(i, "image_url");

            // Validate actual vs expected values
            Assert.assertEquals(actualTitle, expectedTitle, "Title mismatch for prayer " + i);
            Assert.assertEquals(actualAudioUrl, expectedAudioUrl, "Audio URL mismatch for prayer " + i);
            Assert.assertEquals(actualImageUrl, expectedImageUrl, "Image URL mismatch for prayer " + i);
        }
    }
}
