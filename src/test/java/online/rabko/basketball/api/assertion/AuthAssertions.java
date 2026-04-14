package online.rabko.basketball.api.assertion;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.containsString;

public class AuthAssertions {

    public static void assertSignUpSuccess(Response response) {
        response.then()
                .statusCode(200);
    }

    public static void assertUserAlreadyExists(Response response) {
        response.then()
                .statusCode(409)
                .body("error", containsString("already exists"));
    }

    public static void assertBadRequest(Response response) {
        response.then()
                .statusCode(409)
                .body("error", containsString("Data integrity violation"));
    }

    public static void assertLoginSuccess(Response response) {
        response.then()
                .statusCode(200);
    }

    public static void assertInvalidLogin(Response response) {
        response.then()
                .statusCode(401)
                .body("error", containsString("Invalid username or password"));
    }
}
