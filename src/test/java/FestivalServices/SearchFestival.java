package FestivalServices;


import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class SearchFestival {

    private static final String base_Url = "https://api.stage.dharmayana.in/v1/observances/search";

    @Test
    public void testsearchFestival() {

        Response response = given()

                .queryParam("lat", "25.6727062")
                .queryParam("long", "85.83619279999999")
                .queryParam("timestamp", "1741350008")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("keyword", "diwali")
                .queryParam("offset", "5.5")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")

                .when()
                .get(base_Url);
       response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"), "jsonResponse does not contain any data");

        JSONObject dataObject = jsonResponse.getJSONObject("data");
        Assert.assertTrue(dataObject.has("festivals"), "dataObject is empty");

        JSONArray festivalArray = dataObject.getJSONArray("festivals");
        Assert.assertFalse(festivalArray.isEmpty(), "festival Array is empty");

        List<String> expectedName = SearchFestivalData.getExpectedFestival();


        for (int i = 0; i < festivalArray.length(); i++) {

            JSONObject FestivalData = festivalArray.getJSONObject(i);

            String name = FestivalData.getString("name");
            Assert.assertTrue(expectedName.contains(name), "name is missing: " + name);
            System.out.println(name);


            int date = FestivalData.getInt("date");
            Assert.assertFalse(date == -1, "Date is empty");

        }
    }

    @Test
    public void testValidSearch() {

        Response response = given()
                .queryParam("keyword", "diwali")

                .when().get(base_Url);

//        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "Unexpected status code");

        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"), "Response does not contain 'data'");

        JSONObject dataObject = jsonResponse.getJSONObject("data");
        Assert.assertTrue(dataObject.has("festivals"), "dataObject is empty");

        JSONArray festivalArray = dataObject.getJSONArray("festivals");
        Assert.assertFalse(festivalArray.isEmpty(), "festival Array should not be  empty");


    }


    @Test
    public void specialCharacterKeywordTest() {
        Response response = given()
                .queryParam("keyword", " $%^$%&R^")
                .when()
                .get(base_Url);

        Assert.assertEquals(response.getStatusCode(), 200, "Unexpected status code");

        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"), "Response does not contain 'data'");


        JSONObject dataObject = jsonResponse.optJSONObject("data");
        Assert.assertTrue(dataObject.has("festivals"), "dataObject is should not contain 'festivals' ");


    }


    @Test
    public void emptyKeyWord() {
        Response response = given()
                .queryParam("keyword", " ")
                .when()
                .get(base_Url);

        Assert.assertEquals(response.getStatusCode(), 500, "Unexpected status code");
    }


    @Test
    public void testInvalidKeywordSearch() {
        Response response = given()
                .queryParam("keyword", "@#$%^&*")
                .when()
                .get(base_Url);

        Assert.assertEquals(response.getStatusCode(), 200, "Expected 400 Bad Request");
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"), "Response does not contain 'data'");


        JSONObject dataObject = jsonResponse.optJSONObject("data");
        Assert.assertFalse(dataObject.isEmpty(), "dataObject is should not contain 'festival' ");


    }


    @Test
    public void testMissingKeyword() {
        Response response = given()
                .when()
                .get(base_Url);

        Assert.assertEquals(response.getStatusCode(), 500, "Expected 500 Bad Request for missing keyword");
    }
}