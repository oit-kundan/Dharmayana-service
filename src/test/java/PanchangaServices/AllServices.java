package PanchangaServices;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class AllServices {
    @Test
    void allServicesTest() {


       Response  response =given()
               .baseUri("https://api.stage.dharmayana.in/configuration/v1/services")

                .when()
                .get();

//        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);

        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"),"JsonResponse does not contain any data");

        JSONObject dataObject = jsonResponse.getJSONObject("data");
        Assert.assertTrue(dataObject.has("home_page_service_list"),"data Object is empty");

        JSONArray home_page_serviceArray = dataObject.getJSONArray("home_page_service_list");
        Assert.assertFalse(home_page_serviceArray.isEmpty(),"Service Array is empty");

        for(int i=0; i<home_page_serviceArray.length();i++) {

            JSONObject panchangaData = home_page_serviceArray.getJSONObject(i);

            String key = panchangaData.getString("key");
            String dispalyName = panchangaData.optString("display_name");
            String icon = panchangaData.optString("icon");
            Assert.assertFalse(key.isEmpty(),"key value is empty: " + home_page_serviceArray);
            Assert.assertFalse(dispalyName.isEmpty(),"display_name is not showing: " +home_page_serviceArray);
            Assert.assertFalse(icon.isEmpty(),"icon is empty: " + home_page_serviceArray);
        }

        JSONArray  allServiceArray = dataObject.getJSONArray("all_services");
            Assert.assertFalse(allServiceArray.isEmpty(),"Service Array is Empty");
            for(int j=0;j<allServiceArray.length();j++){

                JSONObject serviceObject = allServiceArray.getJSONObject(j);

                String Header = serviceObject.optString("header");
                Assert.assertFalse(Header.isEmpty(),"header us empty: "+ allServiceArray);

                JSONArray itemsArray = serviceObject.getJSONArray("items");
                Assert.assertFalse(itemsArray.isEmpty(),"item Array is empty for: " + Header);

                for(int k=0; k<itemsArray.length();k++){

                    JSONObject itemObject = itemsArray.getJSONObject(k);

                    String key = itemObject.getString("key");
                    String displayName = itemObject.optString("display_name");
                    String description = itemObject.optString("description");
                    String icon = itemObject.optString("icon");

                    Assert.assertFalse(key.isEmpty(),"key is empty " + Header);
                    Assert.assertFalse(displayName.isEmpty(),"display name is empty: " + Header);
                    Assert.assertFalse(description.isEmpty(),"description is empty for: " + Header);
                    Assert.assertFalse(icon.isEmpty(),"icon is empty for ; " + Header);

                }


            }



    }

}
