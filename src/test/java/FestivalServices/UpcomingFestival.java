package FestivalServices;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class UpcomingFestival {

    @Test
    public void upcomingFestivalTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "25.6727062")
                .queryParam("long", "85.83619279999999")
                .queryParam("timestamp", "1741761732")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("limit", "30")
                .queryParam("filter", "all")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")
//                .log().all()


                .when()
                .get();

//        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200); // validating status code

        JSONObject jsonResponse = new JSONObject(response.getBody().asString());

        JSONObject dataObject = jsonResponse.getJSONObject("data");
        Assert.assertTrue(dataObject.has("observances"), "dataObject is empty");

        JSONArray observancesArray = dataObject.getJSONArray("observances");
        Assert.assertTrue(observancesArray.length() > 0, "observances Array is empty");


       List<String>expectedName = UpcomingFestivalData.getExpectedFestivals();


        for (int i = 0; i < observancesArray.length(); i++) {

            JSONObject festivalData = observancesArray.getJSONObject(i).getJSONObject("data");

            String name = festivalData.getString("name");
            System.out.println(name);
            Assert.assertTrue(expectedName.contains(name),"name mismatch: " + name);



            String description1 = festivalData.optString("description_1");
            String description2 = festivalData.optString("description_2");
            Assert.assertFalse(description1.isEmpty(), "description_1 is empty for: " + name);
            Assert.assertFalse(description2.isEmpty(), "description_2 is empty for: " + name);


            JSONObject uiInfoObject = festivalData.getJSONObject("ui_info");  //  object ui_info

            String bg_image = uiInfoObject.optString("bg_image");
            String share_image = uiInfoObject.optString("share_image");
            String banner_image = uiInfoObject.optString("banner_image");
            String custom_share_image = uiInfoObject.optString("custom_share_image");
            String desktop_web_banner_image = uiInfoObject.optString("desktop_web_banner_image");
            String mobile_web_banner_image = uiInfoObject.optString("mobile_web_banner_image");
            String bg_image_v1 = uiInfoObject.optString("bg_image_v1");
            String share_image_v1 = uiInfoObject.optString("share_image_v1");
            String banner_image_v1 = uiInfoObject.optString("banner_image_v1");


            Assert.assertFalse(bg_image.isEmpty(), "bg_image is empty: " + name);
//           Assert.assertFalse(share_image.isEmpty(), "share_image is empty: " + name);
//            Assert.assertFalse(banner_image.isEmpty(), "banner_image is empty: " + name);
            Assert.assertFalse(custom_share_image.isEmpty(), "custom_share_image is empty: " + name);
//            Assert.assertFalse(desktop_web_banner_image.isEmpty(), "desktop_web_banner_image is empty: " + name);
//            Assert.assertFalse(mobile_web_banner_image.isEmpty(), "Mobile web_banner_image is empty: " + name);
            Assert.assertFalse(bg_image_v1.isEmpty(), "bg_image_v1 is empty: " + name);
//            Assert.assertFalse(share_image_v1.isEmpty(), "share_image_v1 is empty: " + name);
//            Assert.assertFalse(banner_image_v1.isEmpty(), "banner_image_v1 is empty: " + name);


           String observanceName = festivalData.optString("observance_name");
           Assert.assertFalse(observanceName.isEmpty(),"observane Name is empty: " + name);


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
            Assert.assertFalse(amanta.isEmpty(),"amanta is empty for: "+ name );
            Assert.assertFalse(purnima.isEmpty(),"purnima value is empty for: " + name);


            JSONObject paksha_v1 = panchanga.getJSONObject("paksha_v1");
            String name2 = paksha_v1.optString("name");
            String key2 = paksha_v1.optString("key");
            Assert.assertFalse(name2.isEmpty(),"nameof paksha is empty: " +name);
            Assert.assertFalse(key2.isEmpty(),"key is emptyn for: " + name);


        }


    }

    @Test
    public void dataNameTest() {

        Response response = given()

                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "25.6727062")
                .queryParam("long", "85.83619279999999")
                .queryParam("timestamp", "1742544529")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("limit", "10")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")

                .when().get();
        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.observances[0].data.name"), "Sheetal Ashtami", "observance name mismatch");

    }

    @Test
    public void tithiTest() {

        Response response = given()

                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "25.6727062")
                .queryParam("long", "85.83619279999999")
                .queryParam("timestamp", "1742544529")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("limit", "10")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")
                .when().get();

        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.observances[0].panchanga.tithi[1].name"), "Navami", "observance name mismatch");

    }


    @Test
    public void masaTest() {

        Response response = given()

                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "25.6727062")
                .queryParam("long", "85.83619279999999")
                .queryParam("timestamp", "1742544529")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("limit", "10")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")

                .when().get();
        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.observances[0].panchanga.masa.amanta"), "Phalguna", "masa name mismatch");

    }


    @Test
    public void paksha_v1Test() {

        Response response = given()

                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "25.6727062")
                .queryParam("long", "85.83619279999999")
                .queryParam("timestamp", "1742544529")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("limit", "10")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")

                .when().get();
        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.observances[0].panchanga.paksha_v1.name"), "Krishna Paksha", "paksha_v1 name mismatch");

    }


    @Test
    public void withoutlat_long_timestampTest() {

        Response response = given()

                .baseUri("https://api.stage.dharmayana.in/v1/observances")

                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("limit", "10")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "vikram")

                .when().get();
        Assert.assertEquals(response.getStatusCode(), 400, "status code mismatch");


    }

    @Test
    public void panchangaTithiTest() {

        Response response = given()

                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "25.6727062")
                .queryParam("long", "85.83619279999999")
                .queryParam("timestamp", "1742544529")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("filter","all")
                .queryParam("limit", "10")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "Kali")


                .when().get();
        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.observances[0].panchanga.tithi[0].end_time"), "1742687636", "paksha_v1 name mismatch");

    }

    @Test
    public void masaPurnimantaTest() {

        Response response = given()

                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "25.6727062")
                .queryParam("long", "85.83619279999999")
                .queryParam("timestamp", "1742544529")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("filter","all")
                .queryParam("limit", "10")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "Kali")


                .when().get();
        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.observances[0].panchanga.masa.purnima"), "Chaitra", "Masa purnimanta name mismatch");

    }

    @Test
    public void panchanga1TithiTest() {

        Response response = given()

                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "52.9786256")
                .queryParam("long", "-0.0407279")
                .queryParam("timestamp", "1742544529")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("filter","all")
                .queryParam("limit", "10")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "Kali")


                .when().get();
        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.observances[1].panchanga.tithi[1].name"), "Dwadashi", "panchanga tithi name mismatch");

    }

    @Test
    public void observanceNameTest() {

        Response response = given()

                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .baseUri("https://api.stage.dharmayana.in/v1/observances")
                .queryParam("lat", "52.9786256")
                .queryParam("long", "-0.0407279")
                .queryParam("timestamp", "1742544529")
                .queryParam("timezone", "Asia%2FCalcutta")
                .queryParam("offset", "5.5")
                .queryParam("direction", "forward")
                .queryParam("filter","all")
                .queryParam("limit", "10")
                .queryParam("masa", "purnimanata")
                .queryParam("samvat", "Kali")


                .when().get();
        Assert.assertEquals(response.getStatusCode(), 200, "status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.observances[0].data.name"), "Sheetal Ashtami", "panchanga tithi name mismatch");

    }


}
