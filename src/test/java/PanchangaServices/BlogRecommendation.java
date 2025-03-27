package PanchangaServices;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonTypeInfo;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BlogRecommendation {

    @Test
    public void blogRecommendationTest(){

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/platform/blog/v1/recommendation")
                .queryParam("masa","purnimanta")
                .queryParam("samvat","saka")

                .when().get();

        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200,"Status Code mismatch");

        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"),"jsonResponse does not contain any data");

        JSONArray dataArray = jsonResponse.getJSONArray("data");
        Assert.assertFalse(dataArray.isEmpty(),"data Array is empty");

        List<String> expectedName = BlogReCommendationData.getExpectedName();


        for(int i=0;i< dataArray.length();i++){

            JSONObject dataObject = dataArray.getJSONObject(i);

            String title = dataObject.optString("title");
            System.out.println(title);
//           Assert.assertTrue(expectedName.contains(title),"title is mismatch " +title);
            Assert.assertFalse(title.isEmpty(),"title is empty");

            JSONArray tagsArray = dataObject.getJSONArray("tags");
            Assert.assertFalse(tagsArray.isEmpty(),"tags Array is Empty");

            for(int j=0;j< tagsArray.length();j++){

                JSONObject tagsObject = tagsArray.getJSONObject(j);

                String name = tagsObject.optString("name");
                String url = tagsObject.optString("url");
                Assert.assertTrue(expectedName.contains(name),"name does not contain " + name);
                Assert.assertFalse(url.isEmpty(),"url is Empty for: " + name);

            }
            String url = dataObject.optString("url");
            String thumbnail_image = dataObject.optString("thumbnail_image");

            Assert.assertFalse(url.isEmpty(),"url is empty for:"+ dataObject);
            Assert.assertFalse(thumbnail_image.isEmpty(),"thumbnail_image is empty for: " + dataObject);
        }


    }


    @Test
    public void statusCodeTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/platform/blog/v1/recommendation?masa=purnimanta&samvat=saka")
                .when().get();

        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");
    }

    @Test
    public void titleTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/platform/blog/v1/recommendation?masa=purnimanta&samvat=saka")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data[0].title"),"Ganesha and the Paradox of Perfection: The Broken Tusk","Title mismatch");
    }

    @Test
    public void dataLengthest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/platform/blog/v1/recommendation?masa=purnimanta&samvat=saka")
                .when().get();

        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"),"jsonResponse does not contain any data");

        JSONArray dataArray = jsonResponse.getJSONArray("data");
        Assert.assertEquals(dataArray.length(),5,"data length mismatch");
    }

    @Test
    public void UrlTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/platform/blog/v1/recommendation?masa=purnimanta&samvat=saka")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data[0].url"),"https://web.stage.dharmayana.in/articles/ganesha-and-the-paradox-of-perfection-the-broken-tusk-61d8b46b-0fb9-429a-b822-aa075a38d999","data Url mismatch");
    }

    @Test
    public void thumbnail_imageTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/platform/blog/v1/recommendation?masa=purnimanta&samvat=saka")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data[1].thumbnail_image"),"https://dharmayana-strapi-staging.s3.ap-south-1.amazonaws.com/small_Saraswati_8e00e086d4.jpg","thumbnail_image mismatch");
    }

    @Test
    public void withoutMasa_samvatTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/platform/blog/v1/recommendation")
                .when().get();

        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");
    }

    @Test
    public void InvalidMasa_samvatTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/platform/blog/v1/recommendation")
                .queryParam("masa","amantafyhijovguhhh")
                .queryParam("samvat","vikrammmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm")
                .when().get();

        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");
    }


}
