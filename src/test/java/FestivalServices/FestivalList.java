package FestivalServices;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class FestivalList {

    @Test
    public  void FestivalListTest(){

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat","25.6727062")
                .queryParam("long","85.83619279999999")
                .queryParam("timestamp","1741263132")
                .queryParam("timezone","Asia%2FCalcutta")
                .queryParam("offset","5.5")
                .queryParam("direction","forward")
                .queryParam("limit","30")
                .queryParam("region","br")
                .queryParam("masa","purnimanata")
                .queryParam("samvat","vikram")

                .when()
                .get();

       response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);  //validate status code

        JSONObject jsonResponse = new JSONObject(response.getBody().asString());  // converting response to JSON
        Assert.assertTrue(jsonResponse.has("data"),"JsonResponse does not contain any data");

        JSONObject dataObject = jsonResponse.getJSONObject("data");

        JSONArray observancesArray = dataObject.getJSONArray("observances");
        Assert.assertTrue(observancesArray.length()>0,"Array is empty");

        List<String>expectedName = FestivalData.getExpectedFestivals();
        int count =1;
        for(int i=0; i<observancesArray.length();i++){

            JSONObject FestivalData = observancesArray.getJSONObject(i).getJSONObject("data");
            System.out.println(count);
            String name = FestivalData.getString("name");
            count++;
//            System.out.println("festival Name: " + name);
            Assert.assertTrue(expectedName.contains(name),"Name is missing" + name);


            String description_1 = FestivalData.optString( "description_1");
            String description_2 = FestivalData.optString( "description_2");
            Assert.assertFalse(description_1.isEmpty(),"description_1 is empty " +name);
            Assert.assertFalse(description_2.isEmpty(),"description_2 is empty "+ name);



            JSONObject uiInfoObject = FestivalData.getJSONObject( "ui_info");


            String bg_image = uiInfoObject.getString( "bg_image");
            String custom_share_image = uiInfoObject.optString("custom_share_image");
            String bg_image_v1 = uiInfoObject.optString("bg_image_v1");
            Assert.assertFalse(bg_image.isEmpty(),"image is empty " + name);
            Assert.assertFalse(custom_share_image.isEmpty(),"sharable image is empty " +name);
            Assert.assertFalse(bg_image_v1.isEmpty(),"bg_image_v1 is empty " + name);



            JSONObject panchanga = observancesArray.getJSONObject(i).getJSONObject("panchanga");
            Assert.assertTrue(panchanga.has("tithi"),"panchanga is empty "+ name);



            JSONArray tithiArray = panchanga.getJSONArray("tithi");
            Assert.assertTrue(tithiArray.length()>0,"tithiArray is empty " + name);

            for(int j=0; j<tithiArray.length();j++){

                JSONObject tithiDetail = tithiArray.getJSONObject(j);



                String name1 = tithiDetail.getString("name");
                String key = tithiDetail.getString("key");
                int  endTime = tithiDetail.getInt("end_time");
                int  startTime = tithiDetail.getInt("start_time");
                Assert.assertFalse(name1.isEmpty(),"name is empty for: " + name);
                Assert.assertFalse(key.isEmpty(),"key is empty for: " + name);
                Assert.assertFalse(endTime== -1,"end_time is empty for" + name);
                Assert.assertFalse(startTime == -1,"start_time is empty for :" +name);
            }

            String paksha = panchanga.optString("paksha");
            Assert.assertFalse(paksha.isEmpty(),"paksha is empty for: " + name);


            JSONObject masaObject = panchanga.getJSONObject("masa");
            String amanta = masaObject.optString("amanta");
            String purnima = masaObject.optString("purnima");
            Assert.assertFalse(amanta.isEmpty(),"amanta is empty for: "+ name +count);
            Assert.assertFalse(purnima.isEmpty(),"purnima value is empty for: " + name);


            JSONObject paksha_v1 = panchanga.getJSONObject("paksha_v1");
            String name2 = paksha_v1.optString("name");
            String key2 = paksha_v1.optString("key");
            Assert.assertFalse(name2.isEmpty(),"nameof paksha is empty: " +name);
            Assert.assertFalse(key2.isEmpty(),"key is emptyn for: " + name);

        }


    }

    @Test
    public void dataKeyTest() {

        Response response = given()

                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat","25.6727062")
                .queryParam("long","85.83619279999999")
                .queryParam("timestamp","1741263132")
                .queryParam("timezone","Asia%2FCalcutta")
                .queryParam("offset","5.5")
                .queryParam("direction","forward")
                .queryParam("limit","30")
                .queryParam("region","br")
                .queryParam("masa","purnimanata")
                .queryParam("samvat","vikram")

                        .when().get();





        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.observances[0].data.key"),"ekadashi","key value mismatch");

    }

    @Test
    public void withoutregionTest() {

        Response response = given()

                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "25.6727062")
                .queryParam("long", "85.83619279999999")
                .queryParam("timestamp", "1741263132")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("limit", "30")

                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")

                .when().get();


        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.observances[0].data.name"), "Melukote Vairamudi Utsava", "name value mismatch");

    }

    @Test
    public void changelat_longkeyTest() {

        Response response = given()

                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "52.9783773")
                .queryParam("long", "-0.0420111")
                .queryParam("timestamp", "1741263132")


                .when().get();


        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.observances[0].data.name"), "Melukote Vairamudi Utsava", "name value mismatch");

    }


    @Test
    public void timestampTest() {

        Response response = given()

                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "52.9783773")
                .queryParam("long", "-0.0420111")
                .queryParam("timestamp", "1333016005")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "7.0")
                .queryParam("direction", "forward")
                .queryParam("region","br")

                .queryParam("limit", "30")

                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")




                .when().get();


        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.observances"), null, "observance mismatch");

    }

    @Test
    public void timestamp1Test() {

        Response response = given()

                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "52.9783773")
                .queryParam("long", "-0.0420111")
                .queryParam("timestamp", "1333016005")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "7.0")
                .queryParam("direction", "forward")
                .queryParam("region","br")

                .queryParam("limit", "30")

                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")




                .when().get();
        response.then().log().all();


        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.can_fetch_next"), "false", "observance mismatch");

    }













}
