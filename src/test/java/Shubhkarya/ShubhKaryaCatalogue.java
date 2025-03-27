package Shubhkarya;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ShubhKaryaCatalogue {



    private static final String base_url = "https://api.stage.dharmayana.in/platform/shubhkarya/v1/catalogue?masa=purnimanta&samvat=vikram";


    @Test
    public void catalogueTest(){

        Response response = given()

                .when().get(base_url);

//        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200,"status code does not match");


        JSONObject jsonResponse = new JSONObject(response.getBody().asString());

        JSONArray dataArray = jsonResponse.getJSONArray("data");
        Assert.assertFalse(dataArray.isEmpty(),"data Array is empty " + jsonResponse);

        List<String>expectedTitles = CatalogueData.getExpectedTitles();

        for(int i=0; i< dataArray.length();i++){

            JSONObject dataObject = dataArray.getJSONObject(i);

            String title = dataObject.getString("title");
            Assert.assertTrue(expectedTitles.contains(title),"Title mismatch: " + title);
//            System.out.println(title);

            String iconUrl = dataObject.getString("icon_url");
            Assert.assertFalse(iconUrl.isEmpty()," icon Url is empty for: " +title);

            String price = dataObject.getString("price");
            Assert.assertFalse(iconUrl.isEmpty()," price  is empty for: " +title);

            JSONArray featuresArray = dataObject.getJSONArray("features");
            Assert.assertFalse(featuresArray.isEmpty(),"feature array is empty " + title);

            for(int j=0;j< featuresArray.length();j++){

                JSONObject featureObject = featuresArray.getJSONObject(j);

                String value = featureObject.optString("value");
            }
        }


    }
}
