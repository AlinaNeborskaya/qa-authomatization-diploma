package online.rabko.basketball.api.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import online.rabko.model.SignInRequest;
import online.rabko.model.SignUpRequest;

public class AuthService {

    public Response signUp(SignUpRequest request) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/auth/sign-up");
    }

    public Response signIn(SignInRequest request) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/auth/token");
    }
}
