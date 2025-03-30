package PanchangaServices;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ArticleRecommendation {

    @Test
    public void articleRecommendationTest(){

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/article/v1/recommendation")
                .queryParam("masa","purnimanta")
                .queryParam("samvat","vikram")

                .when().get();

//        response.then().log().all();

        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"),"jsonResponse does not contain any data");

        JSONArray dataArray = jsonResponse.getJSONArray("data");
        Assert.assertFalse(dataArray.isEmpty(),"data Array is Empty");

        List<String> expectedData = ArticleRecommendationData.getExpectedName();

        for(int i=0;i< dataArray.length();i++){

            JSONObject dataObject = dataArray.getJSONObject(i);

            String title = dataObject.optString("title");
            String description = dataObject.optString("description");
            String url = dataObject.optString("url");


            Assert.assertTrue(expectedData.contains(title));
            Assert.assertTrue(expectedData.contains(url));
            Assert.assertFalse(description.isEmpty(),"description is empty: " + title);


        }



    }


    @Test
    public void titleTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/article/v1/recommendation?masa=purnimanta&samvat=vikram")
                .when().get();
        response.then().log().all();
            Assert.assertEquals(response.jsonPath().getString("data[0].title"), "Understanding Masa (Lunar Month )", "Title mismatch");
    }


    @Test
    public void dataLengthTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/article/v1/recommendation?masa=purnimanta&samvat=vikram")
                .when().get();
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"),"jsonResponse does not contain any data");

        JSONArray dataArray = jsonResponse.getJSONArray("data");
        Assert.assertEquals(dataArray.length(), 3, "data length mismatch");
    }

    @Test
    public void url2Test() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/article/v1/recommendation?masa=purnimanta&samvat=vikram")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data[2].url"), "https://dharmayana.in/learn-more-mobile/5/#Introduction", "Url mismatch");
    }

    @Test
    public void idTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/article/v1/recommendation?masa=purnimanta&samvat=vikram")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data[1].id"), "6638ccf3e051abb58d94c0b8", "id mismatch");
    }


    @Test
    public void url1Test() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/article/v1/recommendation?masa=purnimanta&samvat=Kali")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data[1].url"), "https://dharmayana.in/learn-more-mobile/4/#Introduction", "Url mismatch");
    }

    @Test
    public void withoutmasa_samvatTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/article/v1/recommendation")
                .when().get();
        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");
    }









}
