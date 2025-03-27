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



}
