package com.commencis.tests;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import com.commencis.utilities.ConfigurationReader;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class TestCase1 extends TestBase{

    @BeforeClass
    public void beforeClass() {
        baseURI = ConfigurationReader.get("url");
    }
    private long id;
    private String status;

    @Test (priority = 0)
    public void testName1() {
        extentLogger = report.createTest("Commencis case 1");
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("status", "status update");

        extentLogger.info("When user send status to the end point, it should return 200, etc ");
        Response response = given().queryParams(queryParam)
                .and().header("Authorization", "Bearer QXQ7kspK67IiRxbfY0T--bKNCcMGYKfh6cfcvgvm2sU")
                .and().log().all()
                .when().post("/api/v1/statuses/");
        System.out.println("response.prettyPrint() = " + response.prettyPrint());
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json; charset=utf-8");
        JsonPath jsonPath = response.jsonPath();
        status = jsonPath.getString("content");
        id = jsonPath.getLong("id");
        Assert.assertEquals("<p>" + queryParam.get("status") + "</p>", status);
        extentLogger.pass("Passed");
    }

    @Test (priority = 1)
    public void testGetFromAPI() {
        Response response = given().pathParam("id", id)
                .when().get("/api/v1/statuses/{id}");
        Assert.assertEquals(response.statusCode(),200);

        JsonPath jsonPath = response.jsonPath();

        Assert.assertEquals(id, jsonPath.getLong("id"));

        Assert.assertEquals(status, jsonPath.getString("content"));
    }
}
