package online.rabko.basketball.api.step;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import online.rabko.basketball.api.assertion.AuthAssertions;
import online.rabko.basketball.api.service.AuthService;
import online.rabko.model.SignInRequest;
import online.rabko.model.SignUpRequest;
public class AuthSteps {

    private final AuthService service = new AuthService();

    @Step("Регистрация пользователя: {request.username}")
    public void registerUser(SignUpRequest request) {
        Response response = service.signUp(request);
        AuthAssertions.assertSignUpSuccess(response);
    }

    @Step("Попытка регистрации существующего пользователя")
    public void registerExistingUser(SignUpRequest request) {
        Response response = service.signUp(request);
        AuthAssertions.assertUserAlreadyExists(response);
    }

    @Step("Регистрация с невалидными данными")
    public void registerInvalid(SignUpRequest request) {
        Response response = service.signUp(request);
        AuthAssertions.assertBadRequest(response);
    }

    @Step("Логин пользователя: {request.username}")
    public void login(SignInRequest request) {
        Response response = service.signIn(request);
        AuthAssertions.assertLoginSuccess(response);
    }

    @Step("Логин с неверным паролем")
    public void loginInvalid(SignInRequest request) {
        Response response = service.signIn(request);
        AuthAssertions.assertInvalidLogin(response);
    }

    @Step("Получение токена для пользователя: {request.username}")
    public String getToken(SignInRequest request) {
        Response response = service.signIn(request);
        AuthAssertions.assertLoginSuccess(response);
        return response.jsonPath().getString("token");
    }
}
