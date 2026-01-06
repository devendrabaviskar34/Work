package Assignment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Assignment_02 {

    @Test
    public void PostReq() {

        JSONObject Data = new JSONObject();
        Data.put("name", "Lina");
        Data.put("job", "leader");
        Data.put("age", 25);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("x-api-key", "reqres_4908ad6b88794dbd8e4ca6155e4758f9")
                .body(Data.toJSONString())

                .when()
                .post("https://reqres.in/api/users");

        response.then()
                .log().all();

        Assert.assertEquals(response.statusCode(), 201, "Check the Status code");

    }
}
