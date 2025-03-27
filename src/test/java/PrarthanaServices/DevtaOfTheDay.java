package PrarthanaServices;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DevtaOfTheDay {

    @Test
    void prarthnadietiesTodays(){


        Response response = given()
                .baseUri("https://api.stage.dharmayana.in/")
               .queryParam("masa","purnimanta")
               .queryParam("samvat","vikram")

                .when()
                .get("prarthana/v1/deities/today");

        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200,"Status code mismatch");

        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertTrue(jsonResponse.has("data"),"JsonResponse does not Contain any data");

        JSONArray dataArray = jsonResponse.getJSONArray("data");
        Assert.assertFalse(dataArray.isEmpty(),"data Array is empty");

        for(int i=0; i< dataArray.length();i++){

            JSONObject dataObject = dataArray.getJSONObject(i);

            String Title = dataObject.getString("title");
//            System.out.println(Title);

            String subTitle = dataObject.optString("sub_title");
            Assert.assertFalse(subTitle.isEmpty(),"sub title is empty " + Title);

            String imageUrl = dataObject.getJSONObject("ui_info").optString("image_url");
            Assert.assertFalse(imageUrl.isEmpty(),"image url is empty for: " + Title);

            JSONArray hero_image_Array = dataObject.getJSONObject("ui_info").getJSONArray("hero_image_album");
            Assert.assertFalse(hero_image_Array.isEmpty(),"hero image Array is Empty: " + Title);

            for(int j=0; j<hero_image_Array.length();j++){

                JSONObject heroImage_Object  = hero_image_Array.getJSONObject(j);

                String thumbnailImage = heroImage_Object.optString("thumbnail_image");
                String full_image  = heroImage_Object.optString("full_image");
                String share_image = heroImage_Object.optString("share_image");

                Assert.assertFalse(thumbnailImage.isEmpty(),"thumbniail image is empty for: "+ Title);
                Assert.assertFalse(full_image.isEmpty(),"full image is empty for " + Title);
                Assert.assertFalse(share_image.isEmpty(),"share image is empty for: " + Title);

            }
        }



    }
}
