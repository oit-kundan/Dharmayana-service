package PanchangaServices;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class AppConfig {


    @Test
    public void AppConfigTest(){

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/app-config")
                .queryParam("masa","purnimanta")
                .queryParam("samvat","vikram")

                .when().get();

      response.then().log().all();

        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"),"Jsonresponse does not contain any data");

        JSONObject dataObject = jsonResponse.getJSONObject("data");

        JSONArray approx_timeArray = dataObject.getJSONArray("approx_time");
        Assert.assertFalse(approx_timeArray.isEmpty(),"Approx_time array is empty");


        List<String> expectedTime = AppConfigData.getExpectedName();

        for(int i=0; i<approx_timeArray.length();i++){

            JSONObject approx_timeObject = approx_timeArray.getJSONObject(i);

            String key = approx_timeObject.optString("key");
            String display = approx_timeObject.optString("display");
            Assert.assertTrue(expectedTime.contains(key));
            Assert.assertTrue(expectedTime.contains(display));
        }

        JSONArray consultaion_approx_timeArray = dataObject.getJSONArray("consultation_approx_time");
        Assert.assertFalse(consultaion_approx_timeArray.isEmpty(),"consultation Approx time Array is Empty");

        for(int i=0; i<consultaion_approx_timeArray.length();i++){

            JSONObject consultation_Approx_timeObject = consultaion_approx_timeArray.getJSONObject(i);

            String key = consultation_Approx_timeObject.optString("key");
            String display = consultation_Approx_timeObject.optString("display");
            int end_time = consultation_Approx_timeObject.optInt("end_time");

            Assert.assertTrue(expectedTime.contains(key));
            Assert.assertTrue(expectedTime.contains(display));
//            Assert.assertFalse(end_time == -1,"end time value is missing");
        }

        JSONArray deity_details_promotionArray = dataObject.getJSONArray("deity_details_promotion");
        Assert.assertFalse(deity_details_promotionArray.isEmpty(),"Array is empty "+ deity_details_promotionArray);
        for( int i=0 ;i<deity_details_promotionArray.length();i++){

            JSONObject deity_detail_promotionObject = deity_details_promotionArray.getJSONObject(i);

            String icon_url = deity_detail_promotionObject.optString("icon_url");
            String title = deity_detail_promotionObject.optString("title");
            String subtitle = deity_detail_promotionObject.optString("sub_title");

           Assert.assertFalse(title.isEmpty(),"Title is empty " + deity_details_promotionArray);
           Assert.assertFalse(icon_url.isEmpty(),"icon_url is missing "+ title);
//           Assert.assertFalse(subtitle.isEmpty(),"subTitle is missing : "+ title);

           JSONArray featuresArray = deity_detail_promotionObject.getJSONArray("features");
           Assert.assertFalse(featuresArray.isEmpty(),"features Array is Empty");

           for(int j=0; j< featuresArray.length();j++){

               JSONObject featuresObject = featuresArray.getJSONObject(j);

              String text = featuresObject.optString("text");
              Assert.assertFalse(text.isEmpty(),"text is empty for: " +featuresArray);

           }
        }

       JSONArray festival_details_promotionArray = dataObject.getJSONArray("festival_details_promotion");
        Assert.assertFalse(festival_details_promotionArray.isEmpty(),"festival_details_promotion Array is Empty "+festival_details_promotionArray);

        for( int i=0 ;i<festival_details_promotionArray.length();i++){

            JSONObject festival_detail_promotionObject = festival_details_promotionArray.getJSONObject(i);

            String icon_url = festival_detail_promotionObject.optString("icon_url");
            String title = festival_detail_promotionObject.optString("title");
            String subtitle = festival_detail_promotionObject.optString("sub_title");

            Assert.assertFalse(title.isEmpty(),"Title is empty " + deity_details_promotionArray);
            Assert.assertFalse(icon_url.isEmpty(),"icon_url is missing "+ title);
            Assert.assertFalse(subtitle.isEmpty(),"subTitle is missing : "+ title);

            JSONArray featuresArray = festival_detail_promotionObject.getJSONArray("features");
            Assert.assertFalse(featuresArray.isEmpty(),"features Array is Empty");

            for(int j=0; j< featuresArray.length();j++){

                JSONObject featuresObject = featuresArray.getJSONObject(j);

                String text = featuresObject.optString("text");
                Assert.assertFalse(text.isEmpty(),"text is empty for: " +featuresArray);

            }
        }

        JSONObject home_page_layoutObject = dataObject.getJSONObject("home_page_layout");
        JSONArray sectionsArray = home_page_layoutObject.getJSONArray("sections");

        Assert.assertFalse(sectionsArray.isEmpty(),"Section Array is Empty " + sectionsArray);

        for(int i=0;i< sectionsArray.length();i++){

            JSONObject sectionObject = sectionsArray.getJSONObject(i);
            Assert.assertFalse(sectionObject.isEmpty(),"section object is Empty");

            String type = sectionObject.optString("type");
            Assert.assertFalse(type.isEmpty(),"Type value is missing " +sectionsArray);


            if(sectionObject.has("items") && !sectionObject.isNull("items")){

                JSONArray itemsArray = sectionObject.getJSONArray("items");
                Assert.assertFalse(itemsArray.isEmpty(),"item Array is Empty");

                for( int j=0; j<itemsArray.length();j++){

                    JSONObject itemObject = itemsArray.getJSONObject(j);

                    String type1 = itemObject.optString("type");
                    Assert.assertFalse(type1.isEmpty(),"type is empty " + type1);
                }
            }
            else{
                if (type.equals("main_banner") || type.equals("some_other_section_that_should_have_items")) {
                    System.out.println("Warning: 'items' key is missing in section: " + sectionObject);
                }
           }


        }

        JSONArray kundali_matching_promtionArray = dataObject.getJSONArray("kundali_matching_promotion");
        Assert.assertFalse(kundali_matching_promtionArray.isEmpty(),"kundali_matching_promotion is Empty");

        for(int i=0; i< kundali_matching_promtionArray.length();i++){

            JSONObject kundali_matching_promtionObject = kundali_matching_promtionArray.getJSONObject(i);

            String title = kundali_matching_promtionObject.optString("title");
            String icon_url = kundali_matching_promtionObject.optString("icon_url");
            String sub_title = kundali_matching_promtionObject.optString("sub_title");


            Assert.assertFalse(title.isEmpty(),"Title is Empty " + kundali_matching_promtionArray);
            Assert.assertFalse(icon_url.isEmpty(),"icon_url is empty: "+ title);
            Assert.assertFalse(sub_title.isEmpty(),"sub_title is empty: " +title);

            JSONArray featuresArray = kundali_matching_promtionObject.getJSONArray("features");
            Assert.assertFalse(featuresArray.isEmpty(),"features array is empty");

            for(int j=0; j< featuresArray.length();j++){

                JSONObject featuresObject = featuresArray.getJSONObject(j);

                String text = featuresObject.optString("text");
                Assert.assertFalse(text.isEmpty(),"tect is empty for: " + featuresArray);

            }
        }

        JSONArray kundali_promtionArray = dataObject.getJSONArray("kundali_promotion");
        Assert.assertFalse(kundali_promtionArray.isEmpty(),"kundali_promotion is Empty");

        for(int i=0; i< kundali_promtionArray.length();i++){

            JSONObject kundali_promtionObject = kundali_promtionArray.getJSONObject(i);

            String title = kundali_promtionObject.optString("title");
            String icon_url = kundali_promtionObject.optString("icon_url");
            String sub_title = kundali_promtionObject.optString("sub_title");


            Assert.assertFalse(title.isEmpty(),"Title is Empty " + kundali_promtionArray);
            Assert.assertFalse(icon_url.isEmpty(),"icon_url is empty: "+ title);
            Assert.assertFalse(sub_title.isEmpty(),"sub_title is empty: " +title);

            JSONArray featuresArray = kundali_promtionObject.getJSONArray("features");
            Assert.assertFalse(featuresArray.isEmpty(),"features array is empty");

            for(int j=0; j< featuresArray.length();j++){

                JSONObject featuresObject = featuresArray.getJSONObject(j);

                String text = featuresObject.optString("text");
                Assert.assertFalse(text.isEmpty(),"tect is empty for: " + featuresArray);

            }
        }

        JSONObject notification_configObject = dataObject.getJSONObject("notification_config");

        String daily_panchanga_topic = notification_configObject.optString("daily_panchanga_topic");
       Assert.assertEquals(daily_panchanga_topic,"daily_panchanga","daily panchnga topic mismtach " + notification_configObject);
       Assert.assertEquals(notification_configObject.optString("daily_prediction_topic"),"daily_prediction%{{rashi_key}}","daily prediction topic mismatch");
       Assert.assertEquals(notification_configObject.optString("default_daily_prediction_topic"),"daily_prediction_default","default daily prediction mismatch");
       Assert.assertEquals(notification_configObject.optString("festival_topic"),"festival_reminder%{{observance_day_id}}%minus%{{day_before}}d","festival Topic mismatch");
       Assert.assertEquals(notification_configObject.optString("festival_wishes_topic"),"festival_wishes","festival wishes topic mismatch");
       Assert.assertEquals(notification_configObject.optString( "region_topic"),"region%{{region_key}}","region_topic mismatch");
       Assert.assertEquals(notification_configObject.optString("timezone_topic"),"timezone%{{offset}}","Time zone topic mismatch");


       JSONObject observances_filterObject = dataObject.getJSONObject("observances_filter");
       Assert.assertTrue(observances_filterObject.has("filters"),"observances filter is Empty");

       JSONArray filtersArray = observances_filterObject.getJSONArray("filters");
       Assert.assertFalse(filtersArray.isEmpty(),"filters Array is Empty");


       for(int i=0; i< filtersArray.length();i++){

           JSONObject filtersObject = filtersArray.getJSONObject(i);


           String key = filtersObject.optString("Key");
           String name = filtersObject.optString("Name");


           Assert.assertFalse(key.isEmpty(),"key value is Empty: "+ name);
           Assert.assertFalse(name.isEmpty(),"name is empty for " + filtersArray);
       }


       JSONArray orderdetails_promotionArray = dataObject.getJSONArray("order_details_promotion");
       Assert.assertFalse(orderdetails_promotionArray.isEmpty(),"orerdetails_promotion is Empty");

       for(int i=0; i< orderdetails_promotionArray.length();i++){

           JSONObject order_details_promotionbject = orderdetails_promotionArray.getJSONObject(i);

           String title = order_details_promotionbject.optString("title");
           String icon_url = order_details_promotionbject.optString("icon_url");
           String sub_title = order_details_promotionbject.optString("sub_title");
           String cta_text = order_details_promotionbject.optString("cta_text");
           String cta_deeplink = order_details_promotionbject.optString("cta_deeplink");


           Assert.assertFalse(title.isEmpty(),"Title is Empty for " + orderdetails_promotionArray);
           Assert.assertFalse(icon_url.isEmpty(),"icon_url is Empty for " +title);
           Assert.assertFalse(sub_title.isEmpty(),"sub_title is Empty for " +title);
           Assert.assertFalse(cta_text.isEmpty(),"cta_text is Empty for " +title);
           Assert.assertFalse(cta_deeplink.isEmpty(),"cta_deeplink is Empty for " +title);


       }

       JSONObject prediction_configuration = dataObject.getJSONObject("prediction_configuration");
       Assert.assertTrue(prediction_configuration.has("rashi_list"),"prediction configuration is Empty");

       JSONArray rashi_listArray = prediction_configuration.getJSONArray("rashi_list");
       Assert.assertFalse(rashi_listArray.isEmpty(),"Rashi List is Empty");

       List<String>expetedRashi = RashiListData.getExpectedRshi();

       for(int i=0; i< rashi_listArray.length();i++){

           JSONObject rashi_listObject = rashi_listArray.getJSONObject(i);

           String text = rashi_listObject.optString("text");
           String sub_text = rashi_listObject.optString("sub_text");
           String key = rashi_listObject.optString("key");

           Assert.assertTrue(expetedRashi.contains(text));
           Assert.assertTrue(expetedRashi.contains(sub_text));
           Assert.assertTrue(expetedRashi.contains(key));


           String home_banner_url = rashi_listObject.getJSONObject("ui_info").optString("home_banner_url");
           String icon_url = rashi_listObject.getJSONObject("ui_info").optString("icon_url");

           Assert.assertFalse(home_banner_url.isEmpty(),"home-banner_url is Empty for:" +text);
           Assert.assertFalse(icon_url.isEmpty(),"icon_url is Empty for:" + text);
       }


            JSONArray rashifal_details_promotionArray =  dataObject.getJSONArray("rashifal_details_promotion");
            Assert.assertFalse(rashifal_details_promotionArray.isEmpty(),"Rashifal Promotion Array is empty");

            for(int i=0 ; i< rashifal_details_promotionArray.length();i++){

                JSONObject rashifal_details_promotionObject = rashifal_details_promotionArray.getJSONObject(i);

                String title = rashifal_details_promotionObject.optString("title");
                String icon_url = rashifal_details_promotionObject.optString("icon_url");
                String sub_title = rashifal_details_promotionObject.optString("sub_title");


                Assert.assertFalse(title.isEmpty(),"Title is Empty " + kundali_promtionArray);
                Assert.assertFalse(icon_url.isEmpty(),"icon_url is empty: "+ title);
                Assert.assertFalse(sub_title.isEmpty(),"sub_title is empty: " +title);

                JSONArray featuresArray = rashifal_details_promotionObject.getJSONArray("features");
                Assert.assertFalse(featuresArray.isEmpty(),"features array is empty");

                for(int j=0; j< featuresArray.length();j++){

                    JSONObject featuresObject = featuresArray.getJSONObject(j);

                    String text = featuresObject.optString("text");
                    Assert.assertFalse(text.isEmpty(),"tect is empty for: " + featuresArray);

                }

            }

            JSONArray shubh_karya_categoriesArray = dataObject.getJSONArray("shubh_karya_categories");
            Assert.assertFalse(shubh_karya_categoriesArray.isEmpty(),"shubh_karya_categories Array is Empty");

            for(int i=0;i< shubh_karya_categoriesArray.length();i++){

                JSONObject shubh_karya_categoriesObject = shubh_karya_categoriesArray.getJSONObject(i);

                String key = shubh_karya_categoriesObject.optString("key");
                String value = shubh_karya_categoriesObject.optString("value");

                Assert.assertFalse(key.isEmpty(),"key value is empty " +shubh_karya_categoriesArray);
                Assert.assertFalse(value.isEmpty(),"value is Empty for: " + shubh_karya_categoriesArray);
            }


        JSONArray shubh_karya_filtersArray = dataObject.getJSONArray("shubh_karya_filters");
        Assert.assertFalse(shubh_karya_filtersArray.isEmpty(),"shubh_karya_filters Array is Empty");

        for(int i=0;i< shubh_karya_filtersArray.length();i++){

            JSONObject shubh_karya_filtersObject = shubh_karya_filtersArray.getJSONObject(i);

            String key = shubh_karya_filtersObject.optString("key");
            String display_name = shubh_karya_filtersObject.optString("display_name");
            String applied_display_name = shubh_karya_filtersObject.optString("applied_display_name");
            String description = shubh_karya_filtersObject.optString("description");
            String icon_url = shubh_karya_filtersObject.optString("icon_url");

            Assert.assertFalse(key.isEmpty(),"key value is empty " +shubh_karya_filtersArray);
            Assert.assertFalse(display_name.isEmpty(),"display_name is Empty for: " + shubh_karya_filtersArray);
            Assert.assertFalse(applied_display_name.isEmpty(),"applied_display_name is Empty for: " + shubh_karya_filtersArray);
            Assert.assertFalse(description.isEmpty(),"description is Empty for: " + shubh_karya_filtersArray);
            Assert.assertFalse(icon_url.isEmpty(),"icon_url is Empty for: " + shubh_karya_filtersArray);
        }


        JSONObject suppoerted_country_code_configuration = dataObject.getJSONObject("supported_country_code_configuration");
        Assert.assertTrue(suppoerted_country_code_configuration.has("country_list"),"suppoerted_country_code_configuration has Empty data");

        JSONArray country_listArray = suppoerted_country_code_configuration.getJSONArray( "country_list");
        Assert.assertFalse(country_listArray.isEmpty(),"Country List Array is Empty");

        List<String>expectedData = CountryCodeConfigurationData.getExpectedData();

        for(int i=0;i<country_listArray.length();i++){

            JSONObject country_listObject = country_listArray.getJSONObject(i);

            String country_name = country_listObject.optString("country_name");
            String country_code = country_listObject.optString("country_code");
            String key = country_listObject.optString("key");
            String alpha2_code = country_listObject.optString("alpha2_code");

            Assert.assertTrue(expectedData.contains(country_name));
            Assert.assertTrue(expectedData.contains(country_code));
            Assert.assertTrue(expectedData.contains(key));
            Assert.assertTrue(expectedData.contains(alpha2_code));
        }

        JSONObject web_url = dataObject.getJSONObject("web_urls");

        String  daily_prediction = web_url.optString("daily_prediction");
        Assert.assertEquals(daily_prediction,"/learn-more-mobile/On-Kundali#on-predictions-bhavishya","daily prediction url mismatch");
        Assert.assertEquals(web_url.optString("facebook"),"https://www.facebook.com/profile.php?id=61567306302222","Facebook url mismatcg");
        Assert.assertEquals(web_url.optString("instagram"),"https://www.instagram.com/dharmayana_app/","instagram url mismatch");
        Assert.assertEquals(web_url.optString("jyotisha"),"/learn-more-mobile/On-Kundali#on-predictions-bhavishya","jyotisha url mismatch");
        Assert.assertEquals(web_url.optString("kundali"),"/learn-more-mobile/On-Kundali#Introduction","kndali url is mismatch");
        Assert.assertEquals(web_url.optString("kundali_matching"),"/learn-more-mobile/On-Kundali#Kundali-Matching","kundali_matching url is mismatch");
        Assert.assertEquals(web_url.optString("linked_in"),"https://www.linkedin.com/company/oit-innovations-pvt-ltd/","Linkedin url is mismatch");Assert.assertEquals(web_url.optString("muhurta"),"/learn-more-mobile/13/","Muhurta url is mismatch");
        Assert.assertEquals(web_url.optString("panchanga"),"/learn-more-mobile/11/","panchanga url is mismatch");
        Assert.assertEquals(web_url.optString("policy"),"/policy","plicy url is mismatch");
        Assert.assertEquals(web_url.optString("sutak"),"/learn-more-mobile/14/#Sutak","sutak url is mismatch");
        Assert.assertEquals(web_url.optString("table_of_content"),"/learn-more-mobile/","table of content url is mismatch");
        Assert.assertEquals(web_url.optString("terms_and_conditions"),"/terms-and-conditions","Terms & condition url is mismatch");
        Assert.assertEquals(web_url.optString("tithi"),"/learn-more-mobile/4/","Tithi url is mismatch");
        Assert.assertEquals(web_url.optString("twitter"),"https://twitter.com/Dharmayana_in","twitter url is mismatch");
        Assert.assertEquals(web_url.optString("view_all_blogs"), "/articles","view all bloga url mismatch");
        Assert.assertEquals(web_url.optString("whatsapp"),"https://wa.me/918746884747?text=","whatsapp url is mismatch");
        Assert.assertEquals(web_url.optString("youtube"),"https://youtube.com/@Dharmayana_in","youtube url is mismatch");





    }


    @Test
    public void ApproxTimeTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/app-config?masa=purnimanta&samvat=vikram")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data.approx_time[2].display"), "Evening", "Approx time mismatch");
    }

    @Test
    public void consultaion_ApproxTimeTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/app-config?masa=purnimanta&samvat=vikram")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data.consultation_approx_time[2].end_time"), "20:00", " Consultation_Approx time mismatch");
    }

    @Test
    public void  deity_details_promotionTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/app-config?masa=purnimanta&samvat=vikram")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data.deity_details_promotion[0].icon_url"), "https://d161fa2zahtt3z.cloudfront.net/icons/service_icons/online_pooja.png", " url time mismatch");
    }

    @Test
    public void  deity_details_promotion_TitleTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/app-config?masa=purnimanta&samvat=vikram")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data.deity_details_promotion[0].title"), "Invoke {deity_name}’s Blessings", "Title mismatch");
    }


    @Test
    public void  deity_details_promotion_FeatureTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/app-config?masa=purnimanta&samvat=Kali")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data.deity_details_promotion[0].features[0].text"), "Pandit Pooja at our partner temples", "features mismatch");
    }

    @Test
    public void  deity_details_promotion_Feature1Test() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/app-config?masa=purnimanta&samvat=Kali")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data.deity_details_promotion[0].features[1].text"), "Personalised Sankalpa calling out your Nakshatra, Rashi & Gotra", "features mismatch");
    }

    @Test
    public void  festivalTypeTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/app-config?masa=purnimanta&samvat=Kali")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data.home_page_layout.sections[0].items[0].type"), "tier1_festival", "festival  mismatch");
    }

    @Test
    public void  home_pageDailyPredictionTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/app-config?masa=purnimanta&samvat=Kali")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data.home_page_layout.sections[0].items[3].type"), "daily_prediction", "home_page daily-prediction  mismatch");
    }

    @Test
    public void  ObservanceFilterTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/app-config?masa=purnimanta&samvat=Kali")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data.observances_filter.filters[2].Name"), "Vrat", "Observance Filter  mismatch");
    }

    @Test
    public void  supportedCountryCodeTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/app-config?masa=purnimanta&samvat=Kali")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data.supported_country_code_configuration.country_list[0].country_code"), "+93", "Country code  mismatch");
    }

    @Test
    public void  supportedCountry_Alfa_CodeTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/app-config?masa=purnimanta&samvat=Kali")
                .when().get();
        Assert.assertEquals(response.jsonPath().getString("data.supported_country_code_configuration.country_list[0].alpha2_code"), "AF", "Country Alfa_code  mismatch");
    }


    @Test
    public void  withoutMasaTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/app-config?samvat=Kali")
                .when().get();
        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");
    }

    @Test
    public void  withoutSamvatTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/app-config?masa=purnimanta")
                .when().get();
        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");
    }

    @Test
    public void  withoutMasa_SamvatTest() {

        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/v1/app-config")
                .when().get();
        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");
    }


}
