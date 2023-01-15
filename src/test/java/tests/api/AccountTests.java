package tests.api;

import data.RegistrationData;
import data.api.AuthorizationApi;
import data.models.*;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBaseApi;

import static config.user.UserProperties.PASSWORD;
import static config.user.UserProperties.USERNAME;
import static data.specs.RequestSpecs.jsonRequestSpec;
import static data.specs.RequestSpecs.requestSpec;
import static data.specs.ResponseSpecs.responseSpec;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

@Owner("werechess")
@Epic(value = "Book Store Application")
@Feature(value = "API")
@Story(value = "Account API")
@Tag("api")
class AccountTests extends TestBaseApi {

    private final AuthorizationApi authorizationApi = new AuthorizationApi();
    private final Credentials credentials = new Credentials();
    private final RegistrationData data = new RegistrationData();

    @Severity(CRITICAL)
    @DisplayName("New user creation with success")
    @Description("post /Account/v1/User")
    @Test
    void createNewUserTest() {
        credentials.setUserName(data.firstName + data.lastName);
        credentials.setPassword(PASSWORD);

        UserNew user = authorizationApi.createUser(credentials);

        assertThat(user.getUserID()).isNotEmpty();
        assertThat(user.getUsername()).isEqualTo(credentials.getUserName());
        assertThat(user.getBooks()).isEmpty();
    }

    @Severity(NORMAL)
    @DisplayName("New user creation with error 'User exists!'")
    @Description("post /Account/v1/User")
    @Test
    void reCreateOldUserTest() {
        credentials.setUserName(USERNAME);
        credentials.setPassword(PASSWORD);

        given(jsonRequestSpec)
                .body(credentials)
                .when()
                .post("/Account/v1/User")
                .then()
                .spec(responseSpec)
                .statusCode(406)
                .body("code", is("1204"),
                        "message", is("User exists!"));
    }

    @Severity(CRITICAL)
    @DisplayName("New user token generation with success")
    @Description("post /Account/v1/GenerateToken")
    @Test
    void getTokenTest() {
        credentials.setUserName(data.firstName + data.lastName);
        credentials.setPassword(PASSWORD);

        authorizationApi.createUser(credentials);
        Token token = authorizationApi.getToken(credentials);

        assertThat(token.getToken()).isNotEmpty();
        assertThat(token.getExpires()).isNotEmpty();
        assertThat(token.getStatus()).isEqualTo("Success");
        assertThat(token.getResult()).isEqualTo("User authorized successfully.");
    }

    @Severity(NORMAL)
    @DisplayName("Old user getting info with success")
    @Description("get /Account/v1/User/{UUID}")
    @Test
    void getOldUserInfoTest() {
        credentials.setUserName(USERNAME);
        credentials.setPassword(PASSWORD);

        authorizationApi.getToken(credentials);
        Cookie cookie = authorizationApi.login(credentials);

        User user = given(requestSpec)
                .header("Authorization", "Bearer " + cookie.getToken())
                .when()
                .get("/Account/v1/User/" + cookie.getUserId())
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(User.class);

        assertThat(user.getBooks()).hasSize(1);
        assertThat(user.getBooks().get(0).getIsbn()).isEqualTo("9781449325862");
        assertThat(user.getBooks().get(0).getTitle()).isEqualTo("Git Pocket Guide");
        assertThat(user.getBooks().get(0).getSubTitle()).isEqualTo("A Working Introduction");
        assertThat(user.getBooks().get(0).getAuthor()).isEqualTo("Richard E. Silverman");
        assertThat(user.getBooks().get(0).getPublishDate()).isEqualTo("2020-06-04T08:48:39.000Z");
        assertThat(user.getBooks().get(0).getPublisher()).isEqualTo("O'Reilly Media");
        assertThat(user.getBooks().get(0).getPages()).isEqualTo(234);
        assertThat(user.getBooks().get(0).getDescription()).isEqualTo("This pocket guide is the perfect on-the-job companion to Git, " +
                "the distributed version control system. It provides a compact, readable introduction to Git for new users, " +
                "as well as a reference to common commands and procedures for those of you with Git exp");
        assertThat(user.getBooks().get(0).getWebsite()).isEqualTo("http://chimera.labs.oreilly.com/books/1230000000561/index.html");
    }

    @Severity(CRITICAL)
    @DisplayName("New user deletion with success")
    @Description("delete /Account/v1/User/{UUID}")
    @Test
    void deleteUserTest() {
        credentials.setUserName(data.firstName + data.lastName);
        credentials.setPassword(PASSWORD);

        UserNew user = authorizationApi.createUser(credentials);
        Token token = authorizationApi.getToken(credentials);

        authorizationApi.deleteUser(user, token);

        given(jsonRequestSpec)
                .body(credentials)
                .when()
                .post("/Account/v1/Authorized")
                .then()
                .spec(responseSpec)
                .statusCode(404)
                .body("code", is("1207"),
                        "message", is("User not found!"));
    }

    @Severity(NORMAL)
    @DisplayName("New user deletion with error 'User not authorized!'")
    @Description("delete /Account/v1/User/{UUID}")
    @Test
    void deleteUnauthorizedUserTest() {
        credentials.setUserName(data.firstName + data.lastName);
        credentials.setPassword(PASSWORD);

        UserNew user = authorizationApi.createUser(credentials);

        given(requestSpec)
                .when()
                .delete("/Account/v1/User/" + user.getUserID())
                .then()
                .spec(responseSpec)
                .statusCode(401)
                .body("code", is("1200"),
                        "message", is("User not authorized!"));
    }
}
