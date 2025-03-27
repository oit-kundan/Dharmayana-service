package PanchangaServices;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonTypeInfo;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ReverseGeoCoding {

    String baseurl = "https://api.stage.dharmayana.in/v1/location/reverse-geocoding";

    @Test
    void reversegeocodingTest() {


        Response  response =given()

                .baseUri("https://api.stage.dharmayana.in/v1/location/reverse-geocoding")
                .queryParam("lat", "12.954954954954955")
                .queryParam("long", "77.7076523959518")
                .queryParam("masa","purnimanta")
                .queryParam("samvat","vikram")
                .log().all()


                .when().get();
       response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200,"Status Code Mismatch");

        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"),"jsonResponse does not contain any data");

        JSONObject dataObject = jsonResponse.getJSONObject("data");


        String area_name =  dataObject.optString("area_name");
        Assert.assertEquals(area_name,"Bengaluru","area_nmae mismatch: "+ area_name);

        Assert.assertEquals(dataObject.optString("lat"),"12.9549146","latitude mismatch: "+ area_name);
        Assert.assertEquals(dataObject.optString("long"),"77.7075553","longitude mismatch:"+ area_name);
        Assert.assertEquals(dataObject.optString("timezone"),"Asia/Calcutta","TimeZone mismatch:"+ area_name);
        Assert.assertEquals(dataObject.optString("offset"),"5.5","offset mismatch: "+ area_name);


    }

    @Test
    public void  AreanameTest() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=12.954954954954955&long=77.7076523959518&masa=purnimanta&samvat=vikram");
               Assert.assertEquals(response.jsonPath().getString("data.area_name"),"Bengaluru","Area Name mismatch");

    }

    @Test
    public void  LatTest() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=12.954954954954955&long=77.7076523959518&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.lat"),"12.954915","Lattitude mismatch");

    }

    @Test
    public void  LongTest() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=12.954954954954955&long=77.7076523959518&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.long"),"77.70756","Longitude mismatch");

    }


    @Test
    public void  TimezoneTest() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=12.954954954954955&long=77.7076523959518&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.timezone"),"Asia/Calcutta","Timezone mismatch");

    }

    @Test
    public void  OffsetTest() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=12.954954954954955&long=77.7076523959518&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.offset"),"5.5","Offset mismatch");

    }

    @Test
    public void  Areaname1Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=25.6651975&long=85.8364817&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.area_name"),"Nawada","Area Name mismatch");

    }

    @Test
    public void  Lat1Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=25.6651975&long=85.8364817&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.lat"),"25.665163","Lattitude mismatch");

    }

    @Test
    public void  Long1Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=25.6651975&long=85.8364817&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.long"),"85.83662","Long mismatch");

    }

    @Test
    public void  timeZone1Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=25.6651975&long=85.8364817&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.timezone"),"Asia/Calcutta","Time zone mismatch");

    }

    @Test
    public void  offset1Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=25.6651975&long=85.8364817&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.offset"), "5.5", "offSet mismatch");
    }

    @Test
    public void  Areaname2Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=25.6651975&long=85.8364817&masa=Amanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.area_name"),"Nawada","Area Name mismatch");

    }

    @Test
    public void  Lat2Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=25.6651975&long=85.8364817&masa=Amanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.lat"),"25.665163","Lattitude mismatch");

    }

    @Test
    public void  Long2Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=25.6651975&long=85.8364817&masa=Amanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.long"),"85.83662","Long mismatch");

    }

    @Test
    public void  timeZone2Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=25.6651975&long=85.8364817&masa=Amanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.timezone"),"Asia/Calcutta","Time zone mismatch");

    }

    @Test
    public void  offset2Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=25.6651975&long=85.8364817&masa=Amanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.offset"), "5.5", "offSet mismatch");
    }

    @Test
    public void  Areaname3Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=52.9783809&long=85.8364817&masa=Amanta&samvat=kali");
        Assert.assertEquals(response.jsonPath().getString("data.area_name"),"Verkh-Yaminskoe","Area Name mismatch");

    }

    @Test
    public void  Lat3Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=52.9783809&long=85.8364817&masa=Amanta&samvat=kali");
        Assert.assertEquals(response.jsonPath().getString("data.lat"),"52.978382","Lat mismatch");

    }

    @Test
    public void  long3Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=52.9783809&long=85.8364817&masa=Amanta&samvat=kali");
        Assert.assertEquals(response.jsonPath().getString("data.long"),"85.83648","Long mismatch");

    }

    @Test
    public void  timeZone3Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=52.9783809&long=85.8364817&masa=Amanta&samvat=kali");
        Assert.assertEquals(response.jsonPath().getString("data.timezone"),"Asia/Krasnoyarsk","Time zone mismatch");

    }

    @Test
    public void  offset3Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=52.9783809&long=85.8364817&masa=Amanta&samvat=kali");

        Assert.assertEquals(response.jsonPath().getString("data.offset"), "7", "offSet mismatch");
    }

    @Test
    public void  Areaname4Test() {

        Response response = given()
                .baseUri(baseurl)



                .when().get("?lat=52.9783809&long=-0.0420111&masa=Amanta&samvat=Kali");

        Assert.assertEquals(response.jsonPath().getString("data.area_name"),"Boston","Area Name mismatch");

    }

    @Test
    public void  Lat4Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=52.9783809&long=-0.0420111&masa=Amanta&samvat=Kali");
        Assert.assertEquals(response.jsonPath().getString("data.lat"),"52.97894","Lat mismatch");

    }

    @Test
    public void  long4Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=52.9783809&long=-0.0420111&masa=Amanta&samvat=Kali");
        Assert.assertEquals(response.jsonPath().getString("data.long"),"-0.026577","Long mismatch");

    }

    @Test
    public void  timeZone4Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=52.9783809&long=-0.0420111&masa=Amanta&samvat=Kali");
        Assert.assertEquals(response.jsonPath().getString("data.timezone"),"Europe/London","Time zone mismatch");

    }
    @Test
    public void  offset4Test() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=52.9783809&long=-0.0420111&masa=Amanta&samvat=Kali");
        Assert.assertEquals(response.jsonPath().getString("data.offset"), "0", "offSet mismatch");
    }

    @Test
    public void  withoutLatTest() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?long=-0.0420111&masa=Amanta&samvat=Kali");
        Assert.assertEquals(response.getStatusCode(),400,"status code mismatch");
    }

    @Test
    public void  withoutLongTest() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=52.9783809&masa=Amanta&samvat=Kali");
        Assert.assertEquals(response.getStatusCode(),400,"status code mismatch");
    }

    @Test
    public void  withoutLatLongMasaTest() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?samvat=Kali");
        Assert.assertEquals(response.getStatusCode(),400,"status code mismatch");
    }












}
