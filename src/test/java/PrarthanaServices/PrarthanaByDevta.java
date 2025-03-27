package PrarthanaServices;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static PrarthanaServices.DeitiesData.getExpectedDeities;
import static io.restassured.RestAssured.given;


public  class PrarthanaByDevta {

    @Test
    void prathnaByDietisTest(){


        Response response = RestAssured
                .given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities")
                .queryParam("masa","purnimanta")
                .queryParam("samvat","vikram")

                .when()
                .get();

//         response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200,"status mismatch");


        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"),"jsonResponse does not contain any data");

        JSONArray dataArray = jsonResponse.getJSONArray("data");
        Assert.assertTrue(dataArray.length()>0 ,"'dataArray is empty");

        List<String> expectedTitles = getExpectedDeities();

        for(int i=0; i< dataArray.length(); i++){
            JSONObject dataObject = dataArray.getJSONObject(i);

            String actualTitle = dataObject.getString("title");
//            System.out.println( actualTitle);
            Assert.assertTrue(expectedTitles.contains(actualTitle),"unexpected Title name "+ actualTitle);

            String description = dataObject.optString("description",null);
            Assert.assertNotNull(description,"description is null for " + actualTitle);

            String imageUrl = dataObject.optString("image_url",null);
            Assert.assertNotNull(imageUrl,"imageUrl is missing for " + actualTitle);




        }

    }


    @Test
    public void dataLength_changesParamTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities")
                .when().get("?masa=purnimanta&samvat=Kali");

        Assert.assertEquals(response.jsonPath().getString("data[0].title"),"Shiva","Title mismatch");

    }

    @Test
    public void PrarthanaCountTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities")
                .when().get("?masa=purnimanta&samvat=Kali");

        Assert.assertEquals(response.jsonPath().getString("data[1].prarthana_count"),"8","Prarthana Count mismatch");

    }


    @Test
    public void ui_infoImageTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities")
                .when().get("?masa=purnimanta&samvat=Kali");

        Assert.assertEquals(response.jsonPath().getString("data[2].ui_info.image_url"),"https://d161fa2zahtt3z.cloudfront.net/prarthanas/deities/list-image/ganesha.png","Ui_info image mismatch");

    }

    @Test
    public void withoutMasa_samvatTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities")
                .when().get();

        Assert.assertEquals(response.jsonPath().getString("data[2].ui_info.image_url"),"https://d161fa2zahtt3z.cloudfront.net/prarthanas/deities/list-image/ganesha.png","Ui_info image mismatch");
       Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
    }



    @Test
    public void withoutMasa_samvatPrarthna_countTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities")
                .when().get();

        Assert.assertEquals(response.jsonPath().getString("data[1].prarthana_count"),"8","Prarthana Count mismatch");
        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
    }


    @Test
    public void changingparamPrarthana_countTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities")
                .when().get("?masa=purnimanta&samvat=kali");

        Assert.assertEquals(response.jsonPath().getString("data[1].prarthana_count"),"8","Prarthana Count mismatch");
        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
    }



    @Test
    public void withoutSamvatTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/prarthana/v1/deities")
                .when().get("?masa=purnimanta");


        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
    }

}

