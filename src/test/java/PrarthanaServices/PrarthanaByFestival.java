package PrarthanaServices;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static PrarthanaServices.FestivalData.getExpectedFestivalNames;
import static io.restassured.RestAssured.given;

public class PrarthanaByFestival {

    @Test
    public void prarthanaByFestivalTest() {

        Response response = RestAssured
                .given()
                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "25.6727054")
                .queryParam("long", "85.8258931")
                .queryParam("timestamp", "1740562478")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("limit", "30")
                .queryParam("filter", "popular")
                .queryParam("masa", "purnimanta")
                .queryParam("samvat", "vikram")

                .when()
                .get();

        response.then().log().all();

        // validate  status
        Assert.assertEquals(response.getStatusCode(), 200);

        JSONObject jsonResponse = new JSONObject(response.getBody().asString());




        // Navigate to `data` -> `observances`
        JSONObject dataObject = jsonResponse.getJSONObject("data");
        Assert.assertNotNull(dataObject, "Data object is missing");

        JSONArray observancesArray = dataObject.getJSONArray("observances");
        Assert.assertNotNull(observancesArray, "Observances array is missing");


        List<String> expectedNames = getExpectedFestivalNames();



        for (int i = 0; i < observancesArray.length(); i++) {
            JSONObject observance = observancesArray.getJSONObject(i).getJSONObject("data");

            String actualName = observance.getString("name");
            System.out.println("Festival Name: " + actualName);

            // Validate name exists in expected list
            Assert.assertTrue(expectedNames.contains(actualName), "Unexpected festival name: " + actualName);

            // Check descriptions
            String description1 = observance.optString("description_1", null);
            String description2 = observance.optString("description_2", null);

            Assert.assertNotNull(description1,"description_1 is null for festival: "+ actualName);
            Assert.assertNotNull(description2, "description_2 is null for festival: " + actualName);
        }


    }
    @Test
    public void nameTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "25.6727054")
                .queryParam("long", "85.8258931")
                .queryParam("timestamp", "1740399132")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("limit", "30")
                .queryParam("filter", "popular")
                .queryParam("masa", "purnimanta")
                .queryParam("samvat", "vikram")

                .when()
                .get();



        Assert.assertEquals(response.jsonPath().getString("data.observances[0].data.name"),"Mahashivaratri","name mismatch");
        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
    }

    @Test
    public void withoutFilterTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "25.6727054")
                .queryParam("long", "85.8258931")
                .queryParam("timestamp", "1740399132")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("limit", "30")
//                .queryParam("filter", "popular")
                .queryParam("masa", "purnimanta")
                .queryParam("samvat", "vikram")

                .when()
                .get();
        response.then().log().all();



        Assert.assertEquals(response.jsonPath().getString("data.observances[0].data.key"),"ekadashi","key mismatch");
        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
    }

    @Test
    public void withoutTimestampTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "25.6727054")
                .queryParam("long", "85.8258931")
//                .queryParam("timestamp", "1740399132")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("limit", "30")
               .queryParam("filter", "popular")
                .queryParam("masa", "purnimanta")
                .queryParam("samvat", "vikram")

                .when()
                .get();
        response.then().log().all();




        Assert.assertEquals(response.getStatusCode(),400,"Invalid request passed");
    }


    @Test
    public void withoutLat_long_TimestampTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "25.6727054")
                .queryParam("long", "85.8258931")
//                .queryParam("timestamp", "1740399132")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("limit", "30")
                .queryParam("filter", "popular")
                .queryParam("masa", "purnimanta")
                .queryParam("samvat", "vikram")

                .when()
                .get();
        response.then().log().all();




        Assert.assertEquals(response.getStatusCode(),400,"Invalid request passed");
    }

    @Test
    public void name1Test() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "52.9783809")
                .queryParam("long", "-0.0420111")
                .queryParam("timestamp", "1740399132")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("limit", "30")
                .queryParam("filter", "popular")
                .queryParam("masa", "purnimanta")
                .queryParam("samvat", "vikram")

                .when()
                .get();



        Assert.assertEquals(response.jsonPath().getString("data.observances[0].data.name"),"Mahashivaratri","name mismatch");
        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
    }

    @Test
    public void withoutfilter_change_lat_longTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "52.9783809")
                .queryParam("long", "-0.0420111")
                .queryParam("timestamp", "1740399132")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("limit", "30")
//                .queryParam("filter", "popular")
                .queryParam("masa", "purnimanta")
                .queryParam("samvat", "vikram")

                .when()
                .get();



        Assert.assertEquals(response.jsonPath().getString("data.observances[0].data.name"),"Ekadashi","name mismatch");
        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
    }


    @Test
    public void nameCahnge_Lat_LonngTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "25.6727062")
                .queryParam("long", "85.83619279999999")
                .queryParam("timestamp", "1740399132")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("limit", "30")
                .queryParam("filter", "popular")
                .queryParam("masa", "purnimanta")
                .queryParam("samvat", "vikram")

                .when()
                .get();



        Assert.assertEquals(response.jsonPath().getString("data.observances[0].data.name"),"Mahashivaratri","name mismatch");
        Assert.assertEquals(response.getStatusCode(),200,"status code mismatch");
    }

}
