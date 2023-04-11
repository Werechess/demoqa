package data.specs;

import io.restassured.specification.RequestSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class RequestSpecs {

    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .log().uri();

    public static RequestSpecification jsonRequestSpec = requestSpec
            .log().body()
            .contentType(JSON);
}
