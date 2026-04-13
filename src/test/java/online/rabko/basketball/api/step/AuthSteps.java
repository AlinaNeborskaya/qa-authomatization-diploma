package online.rabko.basketball.api.step;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import online.rabko.basketball.api.assertion.AuthAssertions;
import online.rabko.basketball.api.service.AuthService;
import online.rabko.model.SignInRequest;
import online.rabko.model.SignUpRequest;

public class AuthSteps {

    private final AuthService service = new AuthService();

    public void registerUser(SignUpRequest request) {
        Allure.step("Регистрация пользователя: " + request.getUsername(), () -> {
            Response response = service.signUp(request);
            AuthAssertions.assertSignUpSuccess(response);
        });
    }

    public void registerExistingUser(SignUpRequest request) {
        Allure.step("Попытка регистрации существующего пользователя", () -> {
            Response response = service.signUp(request);
            AuthAssertions.assertUserAlreadyExists(response);
        });
    }

    public void registerInvalid(SignUpRequest request) {
        Allure.step("Регистрация с невалидными данными", () -> {
            Response response = service.signUp(request);
            AuthAssertions.assertBadRequest(response);
        });
    }

    public void login(SignInRequest request) {
        Allure.step("Логин пользователя: " + request.getUsername(), () -> {
            Response response = service.signIn(request);
            AuthAssertions.assertLoginSuccess(response);
        });
    }

    public void loginInvalid(SignInRequest request) {
        Allure.step("Логин с неверным паролем", () -> {
            Response response = service.signIn(request);
            AuthAssertions.assertInvalidLogin(response);
        });
    }

    public String getToken(SignInRequest request) {
        return Allure.step("Получение токена для пользователя: " + request.getUsername(), () -> {
            Response response = service.signIn(request);
            AuthAssertions.assertLoginSuccess(response);
            return response.jsonPath().getString("token");
        });
    }
}
