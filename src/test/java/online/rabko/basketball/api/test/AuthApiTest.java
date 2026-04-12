package online.rabko.basketball.api.test;

import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import lombok.extern.slf4j.Slf4j;
import online.rabko.basketball.api.service.AuthService;
import online.rabko.basketball.api.step.AuthSteps;
import online.rabko.model.SignInRequest;
import online.rabko.model.SignUpRequest;
import online.rabko.basketball.util.ConfigReader;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Feature("Basketball API")
@Epic("Auth")
@Slf4j
public class AuthApiTest {

    private static final AuthSteps steps = new AuthSteps();
    private static final AuthService authService = new AuthService();

    @BeforeAll
    static void setup() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = ConfigReader.getApiHost();

        SignUpRequest request = new SignUpRequest(
            ConfigReader.getApiUsername(),
            ConfigReader.getApiUserPassword()
        );

        try {
            authService.signUp(request);
        } catch (Exception ignored) {
        }
    }

    @Test
    @AllureId("API001")
    @Story("Регистрация нового пользователя")
    @Severity(SeverityLevel.NORMAL)
    void shouldRegisterUser() {
        SignUpRequest request = new SignUpRequest(
            "user-" + UUID.randomUUID(),
            "testPassword"
        );

        steps.registerUser(request);
    }

    @Test
    @AllureId("API002")
    @Story("Регистрация существующего пользователя")
    void shouldFailRegisterExistingUser() {
        SignUpRequest request = new SignUpRequest(
            ConfigReader.getApiUsername(),
            ConfigReader.getApiUserPassword()
        );

        steps.registerExistingUser(request);
    }

    @Test
    @AllureId("API003")
    @Story("Регистрация без username")
    void shouldFailRegisterWithoutUsername() {
        SignUpRequest request = new SignUpRequest(
            null,
            "testPassword"
        );

        steps.registerInvalid(request);
    }

    @Test
    @AllureId("API004")
    @Story("Успешный логин")
    @Severity(SeverityLevel.CRITICAL)
    void shouldLogin() {
        SignInRequest request = new SignInRequest(
            ConfigReader.getApiUsername(),
            ConfigReader.getApiUserPassword()
        );

        steps.login(request);
    }

    @Test
    @AllureId("API005")
    @Story("Логин с неверным паролем")
    @Severity(SeverityLevel.CRITICAL)
    void shouldFailLogin() {
        SignInRequest request = new SignInRequest(
            ConfigReader.getApiUsername(),
            "wrongPassword"
        );

        steps.loginInvalid(request);
    }
}
