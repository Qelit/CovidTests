package api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.openqa.selenium.json.Json;
import users.User;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class TestAssured {
    //private String oid = "3078943604";
    //private String url = "http://pgu-uat-fedlkapinlb.test.gosuslugi.ru";
    User user;
    String email = "aglebov@usetech.ru";
    String type = "RF_BRTH_CERT";
    String omsNumber = "3";
    String omsSeries = "0000000000000000";

    public String getToken(String url, String oid){
        Response response = RestAssured.given().baseUri(url).pathParam("oid", oid).when().get("/lk-api/internal/api/lk/v2/users/offline/token?userId={oid}");
        String token = response.getBody().jsonPath().get("token").toString();
        return token;
    }

    @Step("Получение из json файла пользователя для тестирования получения детского сертификата")
    private User getChildUser(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            user = mapper.readValue(new File("src/test/resources/childUser.json"), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void getChildCert(String url, String token){
        User user = getChildUser();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonBody = mapper.createObjectNode();
        jsonBody.put("birthDate", user.getBirthday());
        jsonBody.put("email", email);
        jsonBody.put("firstName", user.getFirstName());
        jsonBody.put("lastName", user.getSurName());
        jsonBody.put("middleName", user.getPatName());

        ObjectNode personData = mapper.createObjectNode();

        ObjectNode document = mapper.createObjectNode();
        document.put("number", user.getNumber());
        document.put("series", user.getSeries());
        document.put("type", type);
        personData.set("document", document);

        ObjectNode oms = mapper.createObjectNode();
        oms.put("number", omsNumber);
        oms.put("series", omsSeries);
        personData.set("oms", oms);

        personData.put("snils", user.getSnils());
        jsonBody.set("personData", personData);
        jsonBody.put("sendToEmail", "true");


        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("Cookie",  token);
        request.body(jsonBody.toString());
        request.baseUri(url);
        Response response = request.post("/api/covid-cert/v3/status-cert");
        Assert.assertEquals(response.getStatusCode(), 200);
    }

}
