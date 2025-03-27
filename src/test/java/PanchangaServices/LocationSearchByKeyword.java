package PanchangaServices;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class    LocationSearchByKeyword {


    String url = "https://api.stage.dharmayana.in/v1/location/suggestions";


    @Test
    void validlocationSearchTest(){
        Response response =  given()
                .queryParam("masa","purnimanta")
                .queryParam("samvat","vikram")
                .queryParam("keyword","dalsinghsarai")

                .when().get(url);

        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");

        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"),"jsonResponse does not contain any data");

        JSONArray dataArray = jsonResponse.getJSONArray("data");
        Assert.assertFalse(dataArray.isEmpty(),"Data Array is Empty");

        for(int i=0;i< dataArray.length();i++){

            JSONObject dataObject = dataArray.getJSONObject(i);

            String label = dataObject.optString("label");
            String place_id = dataObject.optString("place_id");

           Assert.assertFalse(label.isEmpty(),"label is empty:"+ dataArray);
           Assert.assertFalse(place_id.isEmpty(),"place id is empty: " + label);
        }

    }

     @Test
     public void keywordSearchSuggestionTest(){
         Response response =  given()
                 .queryParam("masa","purnimanta")
                 .queryParam("samvat","vikram")
                 .queryParam("keyword","dal")

                 .when().get(url);

         response.then().log().all();
         Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");

         JSONObject jsonResponse = new JSONObject(response.getBody().asString());
         Assert.assertTrue(jsonResponse.has("data"),"jsonResponse does not contain any data");

         JSONArray dataArray = jsonResponse.getJSONArray("data");
         Assert.assertFalse(dataArray.isEmpty(),"Data Array is Empty");

         for(int i=0;i< dataArray.length();i++){

             JSONObject dataObject = dataArray.getJSONObject(i);

             String label = dataObject.optString("label");
             String place_id = dataObject.optString("place_id");

             Assert.assertFalse(label.isEmpty(),"label is empty:"+ dataArray);
             Assert.assertFalse(place_id.isEmpty(),"place id is empty: " + label);
         }
    }


    @Test
    public void  invalidSearchTest(){
        Response response =  given()
                .queryParam("masa","purnimanta")
                .queryParam("samvat","vikram")
                .queryParam("keyword"," ")

                .when().get(url);

        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),400,"Status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("message"),"Invalid request passed","Message should invalid request passed");
    }


    @Test
    public void  SearchBySpecialCharacterTest(){
        Response response =  given()
                .queryParam("masa","purnimanta")
                .queryParam("samvat","vikram")
                .queryParam("keyword","465757@%#^^% ")

                .when().get(url);

        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("message"),"Successful","Message should Successful");
    }


    @Test
    public void  SearchByWithoutKewordTest(){
        Response response =  given()
                .queryParam("masa","purnimanta")
                .queryParam("samvat","vikram")

                .when().get(url);

        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),400,"Status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("message"),"Invalid request passed","Message should invalid request passed");
    }







}
