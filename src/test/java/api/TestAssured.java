package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TestAssured {
    //private String oid = "3078943604";
    //private String url = "http://pgu-uat-fedlkapinlb.test.gosuslugi.ru";

    public String getToken(String url, String oid){
        Response response = RestAssured.given().baseUri(url).pathParam("oid", oid).when().get("/lk-api/internal/api/lk/v2/users/offline/token?userId={oid}");
        String token = response.getBody().jsonPath().get("token").toString();
        return token;
    }


}
