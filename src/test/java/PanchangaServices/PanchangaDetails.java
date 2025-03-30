package PanchangaServices;


import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PanchangaDetails {

    String baseurl = "https://api.stage.dharmayana.in/v1/panchanga/details";

    @Test
    public void panchangaDetailsTest(){

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/panchanga/details")
                .queryParam("lat","25.6727062")
                .queryParam("long","85.83619279999999")
                .queryParam("timestamp","1741954332")
                .queryParam("offset","5.5")
                .queryParam("masa","purnimanta")
                .queryParam("samavat","vikram")


                .when().get();

//        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");


        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"),"jsonResponse does not contain any data");

        JSONObject dataObject =  jsonResponse.getJSONObject("data");

        JSONArray tithiArray = dataObject.getJSONArray("tithi");
        Assert.assertFalse(tithiArray.isEmpty(),"tithi Array is empty");

        for(int i=0; i< tithiArray.length();i++){

            JSONObject tithiObject = tithiArray.getJSONObject(i);

            String name = tithiObject.optString("name");
            String key =  tithiObject.optString("key");
            int end_time = tithiObject.optInt("end_time");
            int start_time = tithiObject.optInt("start_time");


            Assert.assertFalse(name.isEmpty(),"name is missing for " + tithiArray);
            Assert.assertFalse(key.isEmpty(),"key is missing for " + tithiArray);
            Assert.assertFalse(end_time ==-1,"end_time is missing for name " + name);
            Assert.assertFalse(start_time==-1,"Start time is missing for " + name);
        }

        JSONArray nakshatraArray = dataObject.getJSONArray("nakshatra");
        Assert.assertFalse(tithiArray.isEmpty(),"nakshatra Array is empty");

        for(int i=0; i< nakshatraArray.length();i++){

            JSONObject nakshatraObject = tithiArray.getJSONObject(i);

            String name = nakshatraObject.optString("name");
            String key =  nakshatraObject.optString("key");
            int end_time = nakshatraObject.optInt("end_time");
            int start_time = nakshatraObject.optInt("start_time");


            Assert.assertFalse(name.isEmpty(),"name is missing for " +nakshatraArray );
            Assert.assertFalse(key.isEmpty(),"key is missing for " + nakshatraArray);
            Assert.assertFalse(end_time ==-1,"end_time is missing for name " + name);
            Assert.assertFalse(start_time==-1,"Start time is missing for " + name);
        }

        JSONObject ayana = dataObject.getJSONObject("ayana");

        String name = ayana.optString("name");
        String key = ayana.optString("key");

        Assert.assertFalse(name.isEmpty(),"name is Empty for: " + ayana);
        Assert.assertFalse(key.isEmpty(),"key is empty: " + ayana);


        JSONObject paksha_v1 = dataObject.getJSONObject("paksha_v1");
        String name1 = paksha_v1.optString("name");
        String key1 = paksha_v1.optString("key");


        Assert.assertFalse(name1.isEmpty(),"name is Empty for: " + ayana);
        Assert.assertFalse(key1.isEmpty(),"key is empty: " + ayana);


        String paksha = dataObject.optString("paksha");
        Assert.assertFalse(paksha.isEmpty(),"paksha value is empty for " + paksha);

        JSONObject sun = dataObject.getJSONObject("sun");
        int rise = sun.optInt("end_time");
        int set = sun.optInt("start_time");

        Assert.assertFalse( rise ==-1,"rise time is missing for name " + sun);
        Assert.assertFalse(set ==-1,"Set time is missing for " + sun);


        JSONObject moon = dataObject.getJSONObject("moon");
        int rise1 = moon.optInt("end_time");
        int set1 = moon.optInt("start_time");

        Assert.assertFalse( rise1 ==-1,"rise time is missing for name " + moon);
        Assert.assertFalse(set1 ==-1,"Set time is missing for " + moon);



        JSONObject masa = dataObject.getJSONObject("masa");
        String amantha = masa.optString("amanta");
        String purnima = masa.optString("purnima");

        Assert.assertFalse(amantha.isEmpty(),"amanta value is empty for:" + masa);
        Assert.assertFalse(purnima.isEmpty(),"purnima value is empty for " + masa);


        JSONObject year = dataObject.getJSONObject("years");

        JSONObject vikram = year.getJSONObject("vikram");
        String name2 = vikram.optString("name");
        int year1 = vikram.getInt("year");

        Assert.assertFalse(name2.isEmpty(),"name is missing for " + vikram);
        Assert.assertFalse(year1 == -1,"year is missing for " + vikram);


        JSONObject saka = year.getJSONObject("saka");
        String name3 = vikram.optString("name");
        int year2 = vikram.getInt("year");

        Assert.assertFalse(name3.isEmpty(),"name is missing for " + saka);
        Assert.assertFalse(year2 == -1,"year is missing for " + saka);



        JSONObject kali = year.getJSONObject("kali");
        String name4 = vikram.optString("name");
        int year3 = vikram.getInt("year");

        Assert.assertFalse(name4.isEmpty(),"name is missing for " + kali);
        Assert.assertFalse(year3 == -1,"year is missing for " + kali);



        JSONObject muhurat = dataObject.getJSONObject("muhurat");
        JSONArray auspiciousArray = muhurat.getJSONArray("auspicious");
        Assert.assertFalse(auspiciousArray.isEmpty(),"Array is empty :" + auspiciousArray);

        for(int i=0; i<auspiciousArray.length(); i++) {

            JSONObject auspiciousObject = auspiciousArray.getJSONObject(i);

            String name5 = auspiciousObject.optString("name");
            String key2 = auspiciousObject.optString("key");
            int end_time = auspiciousObject.optInt("end_time");
            int start_time = auspiciousObject.optInt("start_time");


            Assert.assertFalse(name5.isEmpty(), "name is missing for " + auspiciousArray);
            Assert.assertFalse(key2.isEmpty(), "key is missing for " + auspiciousArray);
            Assert.assertFalse(end_time == -1, "end_time is missing for name " + name5);
            Assert.assertFalse(start_time == -1, "Start time is missing for " + name5);
        }


            JSONArray inauspiciousArray = muhurat.getJSONArray("inauspicious");
            Assert.assertFalse(inauspiciousArray.isEmpty(),"Array is empty :" + inauspiciousArray);

            for(int i=0; i<auspiciousArray.length(); i++) {

                JSONObject inauspiciousObject = inauspiciousArray.getJSONObject(i);

                String name5 = inauspiciousObject.optString("name");
                String key2 = inauspiciousObject.optString("key");
                int end_time = inauspiciousObject.optInt("end_time");
                int start_time = inauspiciousObject.optInt("start_time");


                Assert.assertFalse(name5.isEmpty(), "name is missing for " + inauspiciousArray);
                Assert.assertFalse(key2.isEmpty(), "key is missing for " + inauspiciousArray);
                Assert.assertFalse(end_time == -1, "end_time is missing for name " + name5);
                Assert.assertFalse(start_time == -1, "Start time is missing for " + name5);


            }

            JSONObject ritu = dataObject.getJSONObject("ritu");

            String name6 = ritu.optString("name");
            String key3 = ritu.optString("key");
            String sub_text = ritu.optString("sub_text");

        Assert.assertFalse(name6.isEmpty(), "name is missing for " + ritu);
        Assert.assertFalse(key3.isEmpty(), "key is missing for " + ritu);
        Assert.assertFalse(sub_text.isEmpty(),"sub_text is empty :" + ritu);


        JSONObject vaara = dataObject.getJSONObject("vaara");

        String name7 = ritu.optString("name");
        String key4 = ritu.optString("key");
        String sub_text1 = ritu.optString("sub_text");

        Assert.assertFalse(name7.isEmpty(), "name is missing for " + vaara);
        Assert.assertFalse(key4.isEmpty(), "key is missing for " + vaara);
        Assert.assertFalse(sub_text1.isEmpty(),"sub_text is empty :" + vaara);

        JSONArray karanaArray = dataObject.getJSONArray("karana");
        Assert.assertFalse(karanaArray.isEmpty(),"Array is empty :" + karanaArray);

        for(int i=0; i<karanaArray.length(); i++) {

            JSONObject karanaObject = karanaArray.getJSONObject(i);

            String name8 = karanaObject.optString("name");
            String key5 = karanaObject.optString("key");
            int end_time = karanaObject.optInt("end_time");
            int start_time = karanaObject.optInt("start_time");


            Assert.assertFalse(name.isEmpty(), "name is missing for " + karanaArray);
            Assert.assertFalse(key.isEmpty(), "key is missing for " + karanaArray);
            Assert.assertFalse(end_time == -1, "end_time is missing for name " + name8);
            Assert.assertFalse(start_time == -1, "Start time is missing for " + name8);
        }

        JSONArray yogaArray = dataObject.getJSONArray("yoga");
        Assert.assertFalse(yogaArray.isEmpty(),"Array is empty :" + yogaArray);

        for(int i=0; i<yogaArray.length(); i++) {

            JSONObject yogaObject = yogaArray.getJSONObject(i);

            String name9 = yogaObject.optString("name");
            String key6 = yogaObject.optString("key");
            int end_time = yogaObject.optInt("end_time");
            int start_time = yogaObject.optInt("start_time");


            Assert.assertFalse(name9.isEmpty(), "name is missing for " + karanaArray);
            Assert.assertFalse(key6.isEmpty(), "key is missing for " + karanaArray);
            Assert.assertFalse(end_time == -1, "end_time is missing for name " + name9);
            Assert.assertFalse(start_time == -1, "Start time is missing for " + name9);
        }

        JSONArray observancesArray = dataObject.getJSONArray("observances");
        Assert.assertFalse(observancesArray.isEmpty(),"observance Array is empty " + observancesArray);

        for(int i=0; i< observancesArray.length();i++){

            JSONObject observanceObject = observancesArray.getJSONObject(i);

            String name10 = observanceObject.getString("name");
            String type = observanceObject.optString("type");
            String description_1 = observanceObject.optString("description_1");
            String description_2 = observanceObject.optString("description_2");


            Assert.assertFalse(name10.isEmpty(),"Name is empty for: " + observancesArray);
            Assert.assertFalse(type.isEmpty(),"Name is empty for: " + name10);
            Assert.assertFalse(description_1.isEmpty(),"Name is empty for: " + name10);
            Assert.assertFalse(description_2.isEmpty(),"Name is empty for: " + name10);


            JSONObject UiInfoObject = observanceObject.getJSONObject("ui_info");

            String bg_image = UiInfoObject.optString("bg_image");
            String share_image = UiInfoObject.optString("share_image");
            String banner_image = UiInfoObject.optString("banner_image");
            String custom_share_image = UiInfoObject.optString("custom_share_image");
            String desktop_web_banner_image = UiInfoObject.optString("desktop_web_banner_image");
            String mobile_web_banner_image = UiInfoObject.optString("mobile_web_banner_image");
            String bg_image_v1 = UiInfoObject.optString("bg_image_v1");
            String share_image_v1 = UiInfoObject.optString("share_image_v1");
            String banner_image_v1 = UiInfoObject.optString("banner_image_v1");


            Assert.assertFalse(bg_image.isEmpty(),"bg_image is empty for: " + name10);
//            Assert.assertFalse(share_image.isEmpty(),"share_image is empty for: " + name10);
//            Assert.assertFalse(banner_image.isEmpty(),"banner_image is empty for: " + name10);
            Assert.assertFalse(custom_share_image.isEmpty(),"custom_share_image is empty for: " + name10);
//            Assert.assertFalse(desktop_web_banner_image.isEmpty(),"desktop_web_banner_image is empty for: " + name10);
//            Assert.assertFalse(mobile_web_banner_image.isEmpty(),"mobile_web_banner_image is empty for: " + name10);
            Assert.assertFalse(bg_image_v1.isEmpty(),"bg_image_v1 is empty for: " + name10);
//            Assert.assertFalse(share_image_v1.isEmpty(),"share_image_v1 is empty for: " + name10);
//            Assert.assertFalse(banner_image_v1.isEmpty(),"banner_image_v1 is empty for: " + name10);





            JSONArray prarthanasArray = observanceObject.getJSONArray("prarthanas");
//            System.out.println(prarthanasArray.toString());
//            Assert.assertFalse(prarthanasArray.isEmpty(),"prarthana array is empty for:"+ prarthanasArray);


            for(int j=0; j<prarthanasArray.length();j++){

                JSONObject prarthanaObject = prarthanasArray.getJSONObject(j);

                String title = prarthanaObject.optString("title");
                String imageUrl = prarthanaObject.optString("image_url");

                Assert.assertFalse(title.isEmpty(),"Title is empty for: " + prarthanasArray);
                Assert.assertFalse(imageUrl.isEmpty(),"Image Url is empty for: " + title);

                String  image_url = prarthanaObject.getJSONObject("ui_info").optString("image_url");
                Assert.assertFalse(image_url.isEmpty(),"image url is empty for "+ title);



            }


                JSONArray deitiesArray = observanceObject.getJSONArray("deities");
//           Assert.assertFalse(deitiesArray.isEmpty(),"deites Array is empty");


                for (int j = 0; j < deitiesArray.length(); j++) {

                    JSONObject deitiesObject = deitiesArray.getJSONObject(j);

                    String title = deitiesObject.getString("title");
                    String description = deitiesObject.optString("description");
                    String image_url = deitiesObject.optString("image_url");

                    Assert.assertFalse(title.isEmpty(), "Tilte is empty for " + deitiesArray);
                    Assert.assertFalse(description.isEmpty(), "Tilte is empty for " + deitiesArray);
                    Assert.assertFalse(image_url.isEmpty(), "Tilte is empty for " + deitiesArray);


                    String Image_url = deitiesObject.getJSONObject("ui_info").optString("image_url");
                    Assert.assertFalse(Image_url.isEmpty(), "Tilte is empty for " + deitiesArray);

                }


        }

        JSONArray prarthanaArrays=  dataObject.getJSONArray("prarthanas");
        Assert.assertFalse(prarthanaArrays.isEmpty(),"prarthana Array is Empty");

        for(int i=0;i<prarthanaArrays.length();i++){

            JSONObject prarthanaObject = prarthanaArrays.getJSONObject(i);

            String title = prarthanaObject.getString("title");
            String image_url = prarthanaObject.optString("image_url");

            Assert.assertFalse(title.isEmpty(),"Title is empty for " + prarthanaArrays);
            Assert.assertFalse(image_url.isEmpty(),"Image_url is empty for :"+ title);


        }

        JSONObject rashiObject = dataObject.getJSONObject("rashi");
        JSONArray moonArray = rashiObject.getJSONArray("moon");
        Assert.assertFalse(moonArray.isEmpty(),"Moon Array is Empty");

        for(int i=0; i< moonArray.length();i++){

            JSONObject moonObject = moonArray.getJSONObject(i);

            String name11 = moonObject.optString("name");
            String key7 =  moonObject.optString("key");
            int end_time = moonObject.optInt("end_time");
            int start_time = moonObject.optInt("start_time");


            Assert.assertFalse(name11.isEmpty(),"name is missing for " + moonArray );
            Assert.assertFalse(key7.isEmpty(),"key is missing for " + moonArray);
            Assert.assertFalse(end_time ==-1,"end_time is missing for name " + name11);
            Assert.assertFalse(start_time==-1,"Start time is missing for " + name11);


        }


    }
  @Test
    public void tithilengthTest(){

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=25.6727062&long=85.83619279999999&timestamp=1741954332&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram\n");

           JSONObject jsonResponse  = new JSONObject(response.getBody().asString());
           JSONArray tithi = jsonResponse.getJSONObject("data").getJSONArray("tithi");
           Assert.assertEquals(tithi.length(),2,"Tithi lenghth value does not match");


    }
    @Test
    public void nakshtraDetailsTest(){

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=25.6727062&long=85.83619279999999&timestamp=1741954332&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");
                Assert.assertEquals(response.jsonPath().getString("data.nakshatra[0].name"),"Uttara Phalguni","Nakshtra name mismatch");
                Assert.assertEquals(response.jsonPath().getString("data.nakshatra[0].start_time"),"1741913300","Start time mismatch");
                Assert.assertEquals(response.jsonPath().getString("data.nakshatra[0].end_time"),"1742008979","end time mismatch");
    }

    @Test
    public void AynaDetailsTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=25.6727062&long=85.83619279999999&timestamp=1741954332&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.ayana.key"), "uttrayana", "Ayna key mismatch");
    }

    @Test
    public void Paksha_v1DetailsTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=25.6727062&long=85.83619279999999&timestamp=1741954332&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.paksha_v1.name"), "Krishna Paksha", "Paksha_v1 mismatch");
    }
    @Test
    public void masaDetailsTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=25.6727062&long=85.83619279999999&timestamp=1741954332&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.masa.amanta"), "Phalguna", "masa mismatch");
    }

    @Test
    public void MuhuratAuspiciousDetailsTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=25.6727062&long=85.83619279999999&timestamp=1741954332&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.muhurat.auspicious[0].name"), "Abhijit Muhurta", "Muhurat mismatch");
    }

    @Test
    public void MuhuraInAuspiciousDetailsTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=25.6727062&long=85.83619279999999&timestamp=1741954332&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.muhurat.inauspicious[1].name"), "Gulika Kala", "Muhurat  inAuspicious name mismatch");
    }

    @Test
    public void RituDetailsTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=25.6727062&long=85.83619279999999&timestamp=1741954332&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.ritu.name"), "Vasanta", "Ritu mismatch");
        Assert.assertEquals(response.jsonPath().getString("data.ritu.sub_text"), "Spring", "Ritu sub_text mismatch");

    }

    @Test
    public void VaaraDetailsTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=25.6727062&long=85.83619279999999&timestamp=1741954332&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.vaara.name"), "Shukravaara", "Vaara mismatch");
    }

    @Test
    public void KaranaDetailsTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=25.6727062&long=85.83619279999999&timestamp=1741954332&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.karana[0].name"), "Bava", "Karana mismatch");
    }

    @Test
    public void ObservancesDetailsTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=25.6727062&long=85.83619279999999&timestamp=1741954332&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.observances[0].name"), "Holi", "Observances mismatch");
    }

    @Test
    public void YogaDetailsTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=25.6727062&long=85.83619279999999&timestamp=1741954332&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.yoga[0].name"), "Shoola", "Yoga mismatch");
    }

    @Test
    public void PrarthanaDetailsTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=25.6727062&long=85.83619279999999&timestamp=1741954332&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");
        Assert.assertEquals(response.jsonPath().getString("data.prarthanas[2].title"), "Friday Prayer", "Prarthana Title mismatch");
    }

    @Test
    public void MoonArrayLengthTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=25.6727062&long=85.83619279999999&timestamp=1741954332&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");
        JSONObject jsonResponse  = new JSONObject(response.getBody().asString());
        JSONArray Moon = jsonResponse.getJSONObject("data").getJSONObject("rashi").getJSONArray("moon");
        Assert.assertEquals(Moon.length(),2,"Tithi lenghth value does not match");

    }

    @Test
    public void TithinameTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=12.959744&long=77.7060352&timestamp=1742471587&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");

               Assert.assertEquals(response.jsonPath().getString("data.tithi[0].name"),"Shashti","Tithi name mismatch");

    }

    @Test
    public void NakshtranameTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=12.959744&long=77.7060352&timestamp=1742471587&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");

        Assert.assertEquals(response.jsonPath().getString("data.nakshatra[0].name"),"Anuradha","Nakshatra name mismatch");

    }

    @Test
    public void Paksha_v1NameTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=12.959744&long=77.7060352&timestamp=1742471587&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");

        Assert.assertEquals(response.jsonPath().getString("data.paksha_v1.name"),"Krishna Paksha","Paksha_v1 name mismatch");

    }

    @Test
    public void MoonRiseTimeTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=12.959744&long=77.7060352&timestamp=1742471587&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.moon.rise"),"1742493204","moon rise Time mismatch");

    }

    @Test
    public void MasaNameTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=12.959744&long=77.7060352&timestamp=1742471587&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");

        Assert.assertEquals(response.jsonPath().getString("data.masa.purnima"),"Chaitra","Masa purnima Name mismatch");

    }

    @Test
    public void  SakaNameTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=12.959744&long=77.7060352&timestamp=1742471587&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");

        Assert.assertEquals(response.jsonPath().getString("data.years.saka.name"),"Vishvavasu","saka Name mismatch");

    }

    @Test
    public void  MuhuratAusNameTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=12.959744&long=77.7060352&timestamp=1742471587&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");

        Assert.assertEquals(response.jsonPath().getString("data.muhurat.auspicious[1].name"),"Amrit Kala","Muhurat Auspicious Name mismatch");

    }

    @Test
    public void  RituSub_textTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=12.959744&long=77.7060352&timestamp=1742471587&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");

        Assert.assertEquals(response.jsonPath().getString("data.ritu.sub_text"),"Summer","Ritu sub_text mismatch");

    }

    @Test
    public void  vaaraSub_textTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=12.959744&long=77.7060352&timestamp=1742471587&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");

        Assert.assertEquals(response.jsonPath().getString("data.vaara.sub_text"),"Thursday","Vaara sub_text mismatch");

    }

    @Test
    public void  karanaNameTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=12.959744&long=77.7060352&timestamp=1742471587&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");

        Assert.assertEquals(response.jsonPath().getString("data.karana[0].name"),"Gara","Karana name mismatch");


    }

    @Test
    public void  YogaKeyTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=12.959744&long=77.7060352&timestamp=1742471587&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");

        Assert.assertEquals(response.jsonPath().getString("data.yoga[0].key"),"vajra","Yoga key mismatch");


    }

    @Test
    public void  PrarthanaTitleTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=12.959744&long=77.7060352&timestamp=1742471587&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");

        Assert.assertEquals(response.jsonPath().getString("data.prarthanas[0].title"),"Thursday Prayer","Prarthana Title mismatch");


    }

    @Test
    public void  rashiMoonNameTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=12.959744&long=77.7060352&timestamp=1742471587&timezone=Asia%2FCalcutta&offset=5.5&masa=purnimanta&samvat=vikram");

        Assert.assertEquals(response.jsonPath().getString("data.rashi.moon[0].name"),"Vrishchika","rashi Moon Name: mismatch");


    }

    @Test
    public void  NakshtraNameTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1742472732&timezone=America%2FNew_York&offset=-4.0&masa=purnimanta&samvat=vikram");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.nakshatra[0].name"),"Anuradha","Nakshtra  Name: mismatch");


    }

    @Test
    public void  SunRiseTimeTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1742472732&timezone=America%2FNew_York&offset=-4.0&masa=purnimanta&samvat=vikram");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.sun.rise"),"1742467889","Sun Rise Time mismatch");


    }

    @Test
    public void  MasaPurnimaTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1742472732&timezone=America%2FNew_York&offset=-4.0&masa=purnimanta&samvat=vikram");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.masa.purnima"),"Chaitra","Masa Purnima  mismatch");


    }

    @Test
    public void  YearTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1742472732&timezone=America%2FNew_York&offset=-4.0&masa=purnimanta&samvat=vikram");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.years.vikram.year"),"2082","vikram Year mismatch");

    }

    @Test
    public void  MuhuratAus1NameTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1742472732&timezone=America%2FNew_York&offset=-4.0&masa=purnimanta&samvat=vikram");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.muhurat.auspicious[1].name"),"Amrit Kala","Muhurat Aus1 name mismatch");

    }

    @Test
    public void  VaaraNameTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1742472732&timezone=America%2FNew_York&offset=-4.0&masa=purnimanta&samvat=vikram");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.vaara.name"),"Guruvaara","Vaara name mismatch");

    }

    @Test
    public void  Prarthana0TitleTest() {

        Response response = given()
                .baseUri(baseurl)

                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1742472732&timezone=America%2FNew_York&offset=-4.0&masa=purnimanta&samvat=vikram");

        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.prarthanas[0].title"),"Thursday Prayer","Prarthana0 Title mismatch");

    }

    @Test
    public void  Rashi0MonNameTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1742472732&timezone=America%2FNew_York&offset=-4.0&masa=purnimanta&samvat=vikram");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.rashi.moon[0].name"),"Vrishchika","Rashi moon name mismatch");

    }


    @Test
    public void  NakshtraName1Test() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1333188805&timezone=America%252FNew_York&offset=5.5&masa=Amanta&samvat=Kali");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.nakshatra[0].name"),"Punarvasu","Nakshtra name mismatch");

    }

    @Test
    public void  TithiName1Test() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1333188805&timezone=America%252FNew_York&offset=5.5&masa=Amanta&samvat=Kali");


        Assert.assertEquals(response.jsonPath().getString("data.tithi[0].name"),"Navami","tithi name mismatch");


    }

    @Test
    public void  Paksha_v1Name1Test() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1333188805&timezone=America%252FNew_York&offset=5.5&masa=Amanta&samvat=Kali");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.paksha_v1.name"), "Shukla Paksha", "Paksha_v1 name mismatch");


    }

    @Test
    public void  MoonSetTimeTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1333188805&timezone=America%252FNew_York&offset=5.5&masa=Amanta&samvat=Kali");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.moon.set"),"1333265334","moon set time mismatch");


    }

    @Test
    public void  MasaName1Test() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1333188805&timezone=America%252FNew_York&offset=5.5&masa=Amanta&samvat=Kali");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.masa.purnima"),"Chaitra","Masa purnima name mismatch");


    }

    @Test
    public void  yearsNameTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1333188805&timezone=America%252FNew_York&offset=5.5&masa=Amanta&samvat=Kali");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.years.vikram.name"),"Vishvavasu","years Name mismatch");


    }

    @Test
    public void  MuhuratInAusNameTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1333188805&timezone=America%252FNew_York&offset=5.5&masa=Amanta&samvat=Kali");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.muhurat.inauspicious[1].name"),"Gulika Kala","years Name mismatch");

    }

    @Test
    public void  RituSub_text1Test() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1333188805&timezone=America%252FNew_York&offset=5.5&masa=Amanta&samvat=Kali");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.ritu.sub_text"),"Summer","Ritu sub_text mismatch");

    }

    @Test
    public void  VaaraName1Test() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1333188805&timezone=America%252FNew_York&offset=5.5&masa=Amanta&samvat=Kali");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.vaara.name"),"Shanivaara","Vaaara Name mismatch");

    }

    @Test
    public void  KaranaKeyTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1333188805&timezone=America%252FNew_York&offset=5.5&masa=Amanta&samvat=Kali");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.karana[0].key"),"balava","Karana key mismatch");

    }

    @Test
    public void  PrarthanaTitle0Test() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1333188805&timezone=America%252FNew_York&offset=5.5&masa=Amanta&samvat=Kali");
        response.then().log().all();

        Assert.assertEquals(response.jsonPath().getString("data.prarthanas[0].title"),"Saturday Prayer","Prarthana Title mismatch");

    }

    @Test
    public void  RashiMoonNameTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1333188805&timezone=America%252FNew_York&offset=5.5&masa=Amanta&samvat=Kali");


            Assert.assertEquals(response.jsonPath().getString("data.rashi.moon[0].name"),"Mithuna","Rashi Moon Name mismatch");

    }

    @Test
    public void  TithiNameTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=12.9729385&long=12.9729385&timestamp=1333188805&timezone=America%2FNew_York&offset=5.5&masa=Amanta&samvat=kali");

        Assert.assertEquals(response.jsonPath().getString("data.tithi[0].name"),"Ashtami","Tithi Name mismatch");

    }

    @Test
    public void  MasaAmantaNameTest() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=12.9729385&long=12.9729385&timestamp=1333188805&timezone=America%2FNew_York&offset=5.5&masa=Amanta&samvat=kali");

        Assert.assertEquals(response.jsonPath().getString("data.masa.amanta"),"Chaitra","Masa Amanta Name mismatch");

    }

    @Test
    public void  PrarthanaTitle1Test() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=12.9729385&long=12.9729385&timestamp=1333188805&timezone=America%2FNew_York&offset=5.5&masa=Amanta&samvat=kali");

        Assert.assertEquals(response.jsonPath().getString("data.prarthanas[0].title"),"Saturday Prayer","Prarthana Title mismatch");

    }


    @Test
    public void  RashiMoonName1Test() {

        Response response = given()
                .baseUri(baseurl)
                .when().get("?lat=12.9729385&long=12.9729385&timestamp=1333188805&timezone=America%2FNew_York&offset=5.5&masa=Amanta&samvat=kali");

        Assert.assertEquals(response.jsonPath().getString("data.rashi.moon[1].name"),"Kark","Rashi Moon Name mismatch");

    }

    @Test
    public void  TithiName2Test() {

        Response response = given()
                .baseUri(baseurl)
                .header("Accept-Language","hi")
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1742818332&timezone=America%2FNew_York&offset=-4.0&masa=purnimanta&samvat=vikram");


        Assert.assertEquals(response.jsonPath().getString("data.tithi[0].name"),"दशमी","Tithi Name mismatch");

    }

    @Test
    public void  MasaName2Test() {

        Response response = given()
                .baseUri(baseurl)
                .header("Accept-Language","hi")
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1742818332&timezone=America%2FNew_York&offset=-4.0&masa=purnimanta&samvat=vikram");

        Assert.assertEquals(response.jsonPath().getString("data.masa.amanta"),"फाल्गुन","Masa Ananta Name mismatch");

    }


    @Test
    public void  YearsKaliNameTest() {

        Response response = given()
                .baseUri(baseurl)
                .header("Accept-Language","hi")
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1742818332&timezone=America%2FNew_York&offset=-4.0&masa=purnimanta&samvat=vikram");

        Assert.assertEquals(response.jsonPath().getString("data.years.kali.name"),"क्रोधी","years.kali.name mismatch");

    }

    @Test
    public void  VaaraName2Test() {

        Response response = given()
                .baseUri(baseurl)
                .header("Accept-Language","hi")
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1742818332&timezone=America%2FNew_York&offset=-4.0&masa=purnimanta&samvat=vikram");

        Assert.assertEquals(response.jsonPath().getString("data.vaara.name"),"सोमवार","years.kali.name mismatch");

    }

    @Test
    public void  KaranaNameTest() {

        Response response = given()
                .baseUri(baseurl)
                .header("Accept-Language","hi")
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1742818332&timezone=America%2FNew_York&offset=-4.0&masa=purnimanta&samvat=vikram");

        Assert.assertEquals(response.jsonPath().getString("data.karana[0].name"),"वणिज","Karana Name mismatch");

    }
    @Test
    public void  PrarthanaTitle2Test() {

        Response response = given()
                .baseUri(baseurl)
                .header("Accept-Language","hi")
                .when().get("?lat=42.3555076&long=-71.0565364&timestamp=1742818332&timezone=America%2FNew_York&offset=-4.0&masa=purnimanta&samvat=vikram");


        Assert.assertEquals(response.jsonPath().getString("data.prarthanas[0].title"),"सोमवार प्रार्थना","Prarthana title mismatch");

    }

































}
