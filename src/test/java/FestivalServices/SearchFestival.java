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
    public void testValidSearchTest() {

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
    public void emptyKeyWordTest() {
        Response response = given()
                .queryParam("keyword", " ")
                .when()
                .get(base_Url);

        Assert.assertEquals(response.getStatusCode(), 500, "Unexpected status code");
    }


    @Test
    public void testInvalidKeywordSearchTest() {
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
    public void name_KeywordTest() {
        Response response = given()
                .when()
                .get(base_Url);

        Assert.assertEquals(response.getStatusCode(), 500, "expected 500 Bad Request for missing keyword");
    }

    @Test
    public void testMissingKeywordTest() {
        Response response = given()

                .queryParam("lat", "25.6727062")
                .queryParam("long", "85.83619279999999")
                .queryParam("timestamp", "1741349532")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("keyword", "diwali")
                .queryParam("offset", "5.5")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")
                .when()
                .get(base_Url);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.festivals[0].name"),"Naraka Chaturdashi","festival name mismatch");
    }

    @Test
    public void festivalIdTest() {
        Response response = given()

                .queryParam("lat", "25.6727062")
                .queryParam("long", "85.83619279999999")
                .queryParam("timestamp", "1741349532")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("keyword", "diwali")
                .queryParam("offset", "5.5")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")
                .when()
                .get(base_Url);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.festivals[1].id"),"ef0dbfba-2e6a-4eff-a446-2e85786e7417","festival id mismatch");
    }

    @Test
    public void festivalDateTest() {
        Response response = given()

                .queryParam("lat", "42.3555076")
                .queryParam("long", "-71.0565364")
                .queryParam("timestamp", "1741349532")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("keyword", "Chhath")
                .queryParam("offset", "5.5")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")
                .when()
                .get(base_Url);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.festivals[1].date"),"1761503400","festival date mismatch");
    }

    @Test
    public void festivalDate1Test() {
        Response response = given()

                .queryParam("lat", "42.3555076")
                .queryParam("long", "-71.0565364")
                .queryParam("timestamp", "2153470405")
                .queryParam("timezone", "America%2FNew_York")
                .queryParam("keyword", "Chhath")
                .queryParam("offset", "5.5")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")
                .when()
                .get(base_Url);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.festivals[1].date"),"1761503400","festival date mismatch");
    }

    @Test
    public void invalidKeywordTest() {
        Response response = given()

                .queryParam("lat", "42.3555076")
                .queryParam("long", "-71.0565364")
                .queryParam("timestamp", "2153470405")
                .queryParam("timezone", "America%2FNew_York")
                .queryParam("keyword", "afdgdf")
                .queryParam("offset", "5.5")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")
                .when()
                .get(base_Url);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.observances"),null,"festival date mismatch");
    }

    @Test
    public void intKeywordTest() {
        Response response = given()

                .queryParam("lat", "42.3555076")
                .queryParam("long", "-71.0565364")
                .queryParam("timestamp", "2153470405")
                .queryParam("timezone", "America%2FNew_York")
                .queryParam("keyword", "afdgdf")
                .queryParam("offset", "5.5")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")
                .when()
                .get(base_Url);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.observances"),null,"festival date mismatch");
    }

    @Test
    public void specialCharKeywordTest() {
        Response response = given()

                .queryParam("lat", "42.3555076")
                .queryParam("long", "-71.0565364")
                .queryParam("timestamp", "2153470405")
                .queryParam("timezone", "America%2FNew_York")
                .queryParam("keyword", "afdgdf")
                .queryParam("offset", "5.5")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")
                .when()
                .get(base_Url);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.observances"),null,"festival date mismatch");
    }

    @Test
    public void dataLengthTest() {
        Response response = given()

                .queryParam("lat", "42.3555076")
                .queryParam("long", "-71.0565364")
                .queryParam("timestamp", "2153470405")
                .queryParam("timezone", "America%2FNew_York")
                .queryParam("keyword", "Chhath")
                .queryParam("offset", "5.5")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")
                .when()
                .get(base_Url);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        JSONArray dataArray = jsonResponse.getJSONObject("data").getJSONArray("festivals");
        Assert.assertEquals(dataArray.length(),3,"data length mismtach");
    }
}