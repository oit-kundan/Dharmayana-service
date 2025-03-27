package Shubhkarya;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PrarthanaApiTest {




        private final String BASE_URL = "https://api.stage.dharmayana.in/prarthana/v1/prarthanas/";
        private final String VALID_ID = "83461502-6723-4166-818f-160eaab968dc";

        @BeforeClass
        public void setup() {
            RestAssured.baseURI = BASE_URL;
        }

        /** ✅ Positive Test Cases **/

//        @Test
        public void testValidRequest() {
            Response response = RestAssured.get(VALID_ID + "?prayer_language=hindi&masa=purnimanta&samvat=vikram");
            Assert.assertEquals(response.statusCode(), 200);
            Assert.assertTrue(response.getBody().asString().contains("prarthana_id"));
        }

        @Test
        public void testValidRequestWithDifferentLanguage() {
            Response response = RestAssured.get(VALID_ID + "?prayer_language=english&masa=purnimanta&samvat=vikram");
            Assert.assertEquals(response.statusCode(), 200);
        }

        @Test
        public void testValidRequestWithDifferentMasaAndSamvat() {
            Response response = RestAssured.get(VALID_ID + "?prayer_language=hindi&masa=amanta&samvat=saka");
            Assert.assertEquals(response.statusCode(), 200);
        }

        @Test
        public void testCaseSensitivity() {
            Response response = RestAssured.get(VALID_ID + "?prayer_language=HINDI&masa=PURNIMANTA&samvat=VIKRAM");
            Assert.assertEquals(response.statusCode(), 200);
        }

        @Test
        public void testWhitespaceHandling() {
            Response response = RestAssured.get(VALID_ID + "?prayer_language= hindi &masa=purnimanta&samvat=vikram");
            Assert.assertEquals(response.statusCode(), 200);
        }

        /** ❌ Negative Test Cases **/

        @Test
        public void testInvalidPrarthanaID() {
            Response response = RestAssured.get("random-uuid?prayer_language=hindi&masa=purnimanta&samvat=vikram");
            Assert.assertEquals(response.statusCode(), 500);
        }

        @Test
        public void testInvalidLanguage() {
            Response response = RestAssured.get(VALID_ID + "?prayer_language=xyz&masa=purnimanta&samvat=vikram");
            Assert.assertEquals(response.statusCode(), 200);
        }

        @Test
        public void testInvalidMasaAndSamvat() {
            Response response = RestAssured.get(VALID_ID + "?prayer_language=hindi&masa=random&samvat=wrong");
            Assert.assertEquals(response.statusCode(), 200);
        }

        @Test
        public void testMissingParametersOnlyPrayerLanguage() {
            Response response = RestAssured.get(VALID_ID + "?prayer_language=hindi");
            Assert.assertEquals(response.statusCode(), 200);
        }

        @Test
        public void testEmptyQueryParams() {
            Response response = RestAssured.get(VALID_ID + "?prayer_language=&masa=&samvat=");
            Assert.assertEquals(response.statusCode(), 200);
        }

        @Test
        public void testExtraUnknownParameter() {
            Response response = RestAssured.get(VALID_ID + "?prayer_language=hindi&masa=purnimanta&samvat=vikram&extra_param=test");
            Assert.assertEquals(response.statusCode(), 200); // API should ignore unknown parameters
        }

        @Test
        public void testIncorrectHTTPMethod() {
            Response response = RestAssured.given().post(VALID_ID + "?prayer_language=hindi&masa=purnimanta&samvat=vikram");
            Assert.assertEquals(response.statusCode(), 404);
        }

        @Test
        public void testInvalidDataTypes() {
            Response response = RestAssured.get(VALID_ID + "?prayer_language=123&masa=true&samvat=false");
            Assert.assertEquals(response.statusCode(), 200);
        }

        @Test
        public void testMalformedURL() {
            Response response = RestAssured.get("/v1/prarthanas/");
            Assert.assertEquals(response.statusCode(), 404);
        }

        @Test
        public void testSpecialCharactersInQuery() {
            Response response = RestAssured.get(VALID_ID + "?prayer_language=*&masa=#1234&samvat=!@#$");
            Assert.assertEquals(response.statusCode(), 200);
        }

        /** ⚡ Edge Cases **/

        @Test
        public void testVeryLongInput() {
            String longValue = "hindihindihindi".repeat(100);
            Response response = RestAssured.get(VALID_ID + "?prayer_language=" + longValue + "&masa=purnimanta&samvat=vikram");
            Assert.assertEquals(response.statusCode(), 200);
        }

        @Test
        public void testMultipleValuesForParameters() {
            Response response = RestAssured.get(VALID_ID + "?prayer_language=hindi,english,tamil&masa=purnimanta&samvat=vikram");
            Assert.assertEquals(response.statusCode(), 200);
        }

        @Test
        public void testEmptyResponseHandling() {
            Response response = RestAssured.get(VALID_ID + "?prayer_language=xyz&masa=purnimanta&samvat=vikram");
            Assert.assertEquals(response.statusCode(), 200);

        }

        @Test
        public void testAPIDowntimeSimulation() {
            Response response = RestAssured.get("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/fake_id");
            Assert.assertTrue(response.statusCode() == 500 || response.statusCode() == 404); // Expecting 500 if server down
        }

        @Test
        public void testSlowResponseHandling() {
            long startTime = System.currentTimeMillis();
            Response response = RestAssured.get(VALID_ID + "?prayer_language=hindi&masa=purnimanta&samvat=vikram");
            long endTime = System.currentTimeMillis();
            Assert.assertTrue((endTime - startTime) < 5000, "API response time is too slow");
        }

    @Test
    public void testNoMasaProvided() {
        Response response = RestAssured.get(BASE_URL + VALID_ID + "?prayer_language=hindi&samvat=vikram");
        Assert.assertEquals(response.statusCode(), 400, "API should return 400 if masa is missing");
    }

    @Test
    public void testNoSamvatProvided() {
        Response response = RestAssured.get(BASE_URL + VALID_ID + "?prayer_language=hindi&masa=purnimanta");
        Assert.assertEquals(response.statusCode(), 400, "API should return 400 if samvat is missing");
    }

    @Test
    public void testNoParamsProvided() {
        Response response = RestAssured.get(BASE_URL + VALID_ID);
        Assert.assertEquals(response.statusCode(), 400, "API should return 400 if no query params are provided");
    }

////    @Test
//    public void testAPIDowntimeSimulation() {
//        Response response = RestAssured.get("https://api.stage.dharmayana.in/prarthana/v1/prarthanas/fake_id");
//        Assert.assertTrue(response.statusCode() == 500 || response.statusCode() == 404, "API should return 500 if server is down");
//    }

//    @Test
//    public void testSlowResponseHandling() {
//        long startTime = System.currentTimeMillis();
//        Response response = RestAssured.get(BASE_URL + VALID_ID + "?prayer_language=hindi&masa=purnimanta&samvat=vikram");
//        long endTime = System.currentTimeMillis();
//        Assert.assertTrue((endTime - startTime) < 5000, "API response time should be less than 5 seconds");
//    }
    }


