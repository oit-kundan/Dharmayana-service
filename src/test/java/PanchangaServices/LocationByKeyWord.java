package PanchangaServices;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonTypeInfo;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class LocationByKeyWord {

    @Test
    void locationByKeyWord() {

        String url = "https://api.stage.dharmayana.in/v1/location/suggestions";
        given()
                .queryParam("keyword", "pune")
                .header("Authorization", "Bearer_TOKEN")
                .log().all()

                .when()
                .get(url)
                .then()
                .statusCode(200)
                .log().all();


    }


    @Test
    public void dataLengthTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/location/suggestions")
                .queryParam("keyword","delhi")
                .queryParam("masa","purnimanta")
                .queryParam("samvta","vikram")

                .when().get();
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        JSONArray data = jsonResponse.getJSONArray("data");
        Assert.assertEquals(data.length(),5,"data length mismatch");
    }

    @Test
    public void dataLabelTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/location/suggestions")
                .queryParam("keyword","delhi")
                .queryParam("masa","purnimanta")
                .queryParam("samvta","vikram")

                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data[0].label"),"Delhi, India","data label mismatch");
    }

    @Test
    public void placeIdTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/location/suggestions")
                .queryParam("keyword","delhi")
                .queryParam("masa","purnimanta")
                .queryParam("samvta","vikram")

                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data[0].place_id"),"ChIJL_P_CXMEDTkRw0ZdG-0GVvw","place id mismatch");
    }

    @Test
    public void searchByKeywordFygugihoTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/location/suggestions")
                .queryParam("keyword","gyguhjug")
                .queryParam("masa","purnimanta")
                .queryParam("samvta","vikram")

                .when().get();
        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");
    }

    @Test
    public void withoutKeywordTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/location/suggestions")

                .queryParam("masa","purnimanta")
                .queryParam("samvta","vikram")

                .when().get();
        Assert.assertEquals(response.getStatusCode(),400,"Invalid requeest passed");
    }

    @Test
    public void searchByKeyword_dTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/location/suggestions")
                .queryParam("keyword","d")
                .queryParam("masa","purnimanta")
                .queryParam("samvta","vikram")

                .when().get();
        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");
    }

    @Test
    public void withoutKeyword_masa_samvatTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/location/suggestions")



                .when().get();
        Assert.assertEquals(response.getStatusCode(),400,"Invalid requeest passed");
    }

    @Test
    public void searchByKeywordIntager_vlueTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/location/suggestions")
                .queryParam("keyword","24345")
                .queryParam("masa","purnimanta")
                .queryParam("samvta","vikram")

                .when().get();
        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");
    }

    @Test
    public void searchBySpecialCharacterTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/location/suggestions")
                .queryParam("keyword","$^%&&%$")
                .queryParam("masa","purnimanta")
                .queryParam("samvta","vikram")

                .when().get();
        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");
    }

    @Test
    public void searchByAnyletter_SpecialCharacterTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/location/suggestions")
                .queryParam("keyword","gan$^%&&%$")
                .queryParam("masa","purnimanta")
                .queryParam("samvta","vikram")

                .when().get();
        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");
    }

    @Test
    public void searchBykeywordUndercoreTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/location/suggestions")
                .queryParam("keyword","_")
                .queryParam("masa","purnimanta")
                .queryParam("samvta","vikram")

                .when().get();
        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");
    }
}
