package online.rabko.basketball.api.test;

import io.qameta.allure.AllureId;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import online.rabko.basketball.util.ConfigReader;
import online.rabko.model.SignInRequest;
import online.rabko.model.SignUpRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;

/**
 * API тесты для регистрации и авторизации пользователей через endpoint <code>/auth</code>.
 * <p>
 * Класс проверяет следующие сценарии:
 * <ul>
 *     <li>Регистрация нового пользователя с уникальным username</li>
 *     <li>Попытка регистрации уже существующего пользователя</li>
 *     <li>Регистрация пользователя без обязательного username</li>
 *     <li>Авторизация существующего пользователя с корректными данными</li>
 *     <li>Попытка авторизации с некорректным паролем</li>
 * </ul>
 * <p>
 * Все тесты используют {@link RestAssured} для отправки HTTP-запросов и {@link Allure} для шагов и отчетности.
 * Тестовые данные берутся из {@link ConfigReader}, который обеспечивает безопасное получение email, username и пароля.
 * </p>
 *
 * <p><b>Пример запуска тестов:</b></p>
 * <pre>{@code
 * // Выполнить все тесты через JUnit 5:
 * mvn test -Dtest=AuthApiTest
 * }</pre>
 */
@Feature("Basketball API")
@Epic("Auth")
@Slf4j
public class AuthApiTest {

    /**
     * Установка базового URI для API перед всеми тестами.
     * <p>
     * Также создается тестовый пользователь через endpoint /auth/sign-up.
     * </p>
     */
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = ConfigReader.getApiHost();
        createTestUser(ConfigReader.getApiUsername(), ConfigReader.getApiUserPassword());
    }

    /**
     * Вспомогательный метод для предварительного создания тестового пользователя.
     *
     * @param username уникальное имя пользователя
     * @param password пароль пользователя
     */
    private static void createTestUser(String username, String password) {
        SignUpRequest request = new SignUpRequest(username, password);
        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/auth/sign-up");
    }

    /**
     * Тест проверки успешной регистрации нового пользователя с уникальным username.
     * Ожидается статус HTTP 200.
     */
    @Test
    @AllureId("API001")
    @Story("Регистрация нового пользователя")
    @Severity(SeverityLevel.NORMAL)
    @Description("Успешная регистрация нового пользователя с уникальным username должна вернуть статус 200")
    void shouldRegisterUserWithUniqueUsername() {
        String username = "user-" + UUID.randomUUID();
        String password = "testPassword";

        SignUpRequest requestBody = new SignUpRequest()
            .username(username)
            .password(password);

        Allure.step("Отправляем POST /auth/sign-up с уникальным username: " + username);

        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/auth/sign-up")
            .then()
            .statusCode(200);
    }

    /**
     * Тест проверки регистрации пользователя, который уже существует.
     * Ожидается HTTP 409 Conflict и сообщение о существующем пользователе.
     */
    @Test
    @AllureId("API002")
    @Story("Регистрация уже существующего пользователя")
    @Severity(SeverityLevel.NORMAL)
    @Description("Попытка зарегистрировать существующего пользователя должна вернуть 409 Conflict с соответствующим сообщением")
    void shouldReturnConflictForExistingUser() {
        String username = ConfigReader.getApiUsername();
        String password = ConfigReader.getApiUserPassword();

        SignUpRequest requestBody = new SignUpRequest()
            .username(username)
            .password(password);

        Allure.step("Отправляем POST /auth/sign-up с существующим username: " + username);

        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/auth/sign-up")
            .then()
            .statusCode(409)
            .body("error", containsString("already exists"));
    }

    /**
     * Тест проверки регистрации пользователя без обязательного username.
     * Ожидается HTTP 409 (или 400) и сообщение об ошибке целостности данных.
     */
    @Test
    @AllureId("API003")
    @Story("Регистрация пользователя без обязательного username")
    @Severity(SeverityLevel.NORMAL)
    @Description("Попытка зарегистрировать пользователя без username должна вернуть 409 Bad Request с сообщением об ошибке")
    void shouldReturnBadRequestWhenUsernameMissing() {
        String password = "testPassword123";

        SignUpRequest requestBody = new SignUpRequest()
            .username(null)
            .password(password);

        Allure.step("Отправляем POST /auth/sign-up без username");

        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/auth/sign-up")
            .then()
            .statusCode(409)
            .body("error", containsString("Data integrity violation"));
    }

    /**
     * Тест проверки успешной авторизации существующего пользователя.
     * Ожидается HTTP 200 и выдача токена авторизации.
     */
    @Test
    @AllureId("API004")
    @Story("Авторизация существующего пользователя")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Существующий пользователь с корректными данными должен успешно получить токен авторизации (HTTP 200)")
    void shouldLoginWithValidCredentials() {
        String username = ConfigReader.getApiUsername();
        String password = ConfigReader.getApiUserPassword();

        SignInRequest signInRequest = new SignInRequest()
            .username(username)
            .password(password);

        Allure.step("Авторизуем существующего пользователя: " + username);

        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(signInRequest)
            .when()
            .post("/auth/token")
            .then()
            .statusCode(200);
    }

    /**
     * Тест проверки авторизации с неверным паролем.
     * Ожидается HTTP 401 Unauthorized и сообщение об ошибке.
     */
    @Test
    @AllureId("API005")
    @Story("Авторизация существующего пользователя с некорректным паролем")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Существующий пользователь с неверным паролем не должен пройти авторизацию (HTTP 401)")
    void shouldFailLoginWithInvalidPassword() {
        String username = ConfigReader.getApiUsername();
        String invalidPassword = "wrongPassword123";

        SignInRequest signInRequest = new SignInRequest()
            .username(username)
            .password(invalidPassword);

        Allure.step("Пробуем авторизовать существующего пользователя с неверным паролем: " + username);

        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(signInRequest)
            .when()
            .post("/auth/token")
            .then()
            .statusCode(401)
            .body("error", containsString("Invalid username or password"));
    }
}
