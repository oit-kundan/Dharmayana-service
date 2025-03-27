package PanchangaServices;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class HomePageService {

    @Test
    public void homePageServiceTest(){

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/configuration/v1/services")
                .queryParam("masa","purnimanta")
                .queryParam("samvat","vikram")

                .when().get();

//        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200,"Statuscode mismatch");


        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"),"jsonResponse does not contain any data");

        JSONObject dataObject = jsonResponse.getJSONObject("data");
        Assert.assertTrue(dataObject.has("home_page_service_list"),"dataObject does notcontain any data");

        JSONArray home_page_service_listArray = dataObject.getJSONArray("home_page_service_list");
        Assert.assertFalse(home_page_service_listArray.isEmpty(),"home_page_service_list is empty");

        List<String>expectedData = HomePageServiceData.getExpectedData();

        for(int i=0; i< home_page_service_listArray.length();i++){

            JSONObject home_page_service_listObject = home_page_service_listArray.getJSONObject(i);

            String key = home_page_service_listObject.optString("key");
            String display_name = home_page_service_listObject.optString("display_name");
            String icon = home_page_service_listObject.optString("icon");


            Assert.assertTrue(expectedData.contains(key));
            Assert.assertTrue(expectedData.contains(display_name));
            Assert.assertTrue(expectedData.contains(icon));
        }

        JSONArray all_servicesArray = dataObject.getJSONArray("all_services");
        Assert.assertFalse(all_servicesArray.isEmpty(),"All services Array is Empty");
        for(int i=0; i< all_servicesArray.length();i++){

            JSONObject all_serviceObject = all_servicesArray.getJSONObject(i);

            String header = all_serviceObject.optString("header");
            Assert.assertTrue(expectedData.contains(header));

            JSONArray itemsArray = all_serviceObject.getJSONArray("items");
            Assert.assertFalse(itemsArray.isEmpty(),"item Array is Empty");

            for(int j=0; j<itemsArray.length();j++){

                JSONObject itemsObject = itemsArray.getJSONObject(j);

                String key = itemsObject.optString("key");
                String display_name = itemsObject.optString("display_name");
                String icon = itemsObject.optString("icon");

                Assert.assertTrue(expectedData.contains(key));
                Assert.assertTrue(expectedData.contains(display_name));
                Assert.assertTrue(expectedData.contains(icon));


            }
        }


    }

    @Test
    public void displayNameTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/configuration/v1/services?masa=purnimanta&samvat=vikram")
                .when().get();
       Assert.assertEquals(response.jsonPath().getString("data.home_page_service_list[0].display_name"),"Panchanga","display Name mismatch");
    }

    @Test
    public void weblinkTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/configuration/v1/services?masa=purnimanta&samvat=vikram")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("home_page_service_list[0].web_link"),null,"display Name mismatch");
    }

    @Test
    public void headerTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/configuration/v1/services?masa=purnimanta&samvat=vikram")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data.all_services[0].header"),"PANCHANGA & MUHURTA","display Name mismatch");
    }

    @Test
    public void lengthOfAllServicesTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/configuration/v1/services?masa=purnimanta&samvat=vikram")
                .when().get();
        JSONObject jsonresponse = new JSONObject(response.getBody().asString());
        JSONArray allServiceArray = jsonresponse.getJSONObject("data").getJSONArray("all_services");

        Assert.assertEquals(allServiceArray.length(),4,"all service length mismatch");
    }

    @Test
    public void lengthOfHomePage_service_list_Test() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/configuration/v1/services?masa=purnimanta&samvat=vikram")
                .when().get();
        JSONObject jsonresponse = new JSONObject(response.getBody().asString());
        JSONArray allServiceArray = jsonresponse.getJSONObject("data").getJSONArray("home_page_service_list");

        Assert.assertEquals(allServiceArray.length(),8,"home page service list  length mismatch");
    }


    @Test
    public void withoutMasa_samvatTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/configuration/v1/services?masa=purnimanta&samvat=vikram")
                .when().get();

        Assert.assertEquals(response.getStatusCode(),200,"Statuscode mismatch");

    }






}
