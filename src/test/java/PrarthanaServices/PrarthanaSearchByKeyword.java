package PrarthanaServices;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static PrarthanaServices.KeywordDeitiesData.getExpectedName;
import static io.restassured.RestAssured.given;

public class PrarthanaSearchByKeyword {

    @Test
    public void prarthanaSearchByKeywordTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities/search")
                .queryParam("keyword", "shiva")
                .queryParam("masa", "purnimanta")
                .queryParam("samvat", "vikram")

                .when()
                .get();

//        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        JSONObject jsoResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsoResponse.has("data"), "jsonResponse does not contain any data");

        JSONArray dataArray = jsoResponse.getJSONArray("data");
        Assert.assertTrue(dataArray.length() > 0, "dataArray is empty");


        List<String> expectedTitle = getExpectedName();

        for (int i = 0; i < dataArray.length(); i++) {

            JSONObject dataObject = dataArray.getJSONObject(i);
            String ActualTitle = dataObject.getString("title");

//            System.out.println( ActualTitle);
            Assert.assertTrue(expectedTitle.contains(ActualTitle), "unexpected Title " + ActualTitle);

//            String description = dataObject.optString("description");
//            Assert.assertFalse(description.isEmpty(),"description is empty for "+ ActualTitle);


            String imageUrl = dataObject.optString("image_url");
            Assert.assertFalse(imageUrl.isEmpty(), "image url is empty " + ActualTitle);

        }
    }


    @Test
    public void idTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities/search?keyword=ganesha&masa=purnimanta&samvat=vikram")
                .queryParam("keyword","ganesha")
                .when().get("?masa=purnimanta&samvat=vikram");

        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data[0].id"),"055d329e-eabd-403e-9f41-2912c278f978","id mismatch");
        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
    }

    @Test
    public void titleTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities/search?keyword=ganesha&masa=purnimanta&samvat=vikram")
                .queryParam("keyword","ganesha")
                .when().get("?masa=purnimanta&samvat=vikram");

        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data[0].title"),"Ganesha","title mismatch");
        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
    }

    @Test
    public void invalid_keyword_Test() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities/search?keyword=ganesha&masa=purnimanta&samvat=vikram")
                .queryParam("keyword","ga")
                .when().get("?masa=purnimanta&samvat=vikram");

        response.then().log().all();


        Assert.assertEquals(response.getStatusCode(),400,"Invalid request passed");
    }

    @Test
    public void integer_keyword_Test() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities/search?keyword=ganesha&masa=purnimanta&samvat=vikram")
                .queryParam("keyword","234")
                .when().get("?masa=purnimanta&samvat=vikram");

        response.then().log().all();


        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
    }

    @Test
    public void special_character_keyword_Test() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities/search?keyword=ganesha&masa=purnimanta&samvat=vikram")
                .queryParam("keyword","$%^&^%$")
                .when().get("?masa=purnimanta&samvat=vikram");

        response.then().log().all();


        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
    }


    @Test
    public void invalid1_keyword_Test() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities/search?keyword=ganesha&masa=purnimanta&samvat=vikram")
                .queryParam("keyword","ggjvgdtfjgxtfujyfyhkby")
                .when().get("?masa=purnimanta&samvat=vikram");

        response.then().log().all();


        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data[0].title"),null,"title mismatch");
    }

    @Test
    public void without_keyword_Test() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities/search?keyword=ganesha&masa=purnimanta&samvat=vikram")
                .queryParam("keyword","ggjvgdtfjgxtfujyfyhkby")
                .when().get("?masa=purnimanta&samvat=vikram");

        response.then().log().all();


        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data[0].title"),null,"title mismatch");
    }

    @Test
    public void validNameWith_specialchar_keyword_Test() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities/search?keyword=ganesha&masa=purnimanta&samvat=vikram")
                .queryParam("keyword","g$^&%&%")
                .when().get("?masa=purnimanta&samvat=vikram");

        response.then().log().all();


        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data[0].title"),null,"title mismatch");
    }


    @Test
    public void valid_keyword_Test() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities/search?keyword=ganesha&masa=purnimanta&samvat=vikram")
                .queryParam("keyword","bholenath")
                .when().get("?masa=purnimanta&samvat=vikram");

        response.then().log().all();


        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data[0].title"),"Shiva","title mismatch");
    }







}
