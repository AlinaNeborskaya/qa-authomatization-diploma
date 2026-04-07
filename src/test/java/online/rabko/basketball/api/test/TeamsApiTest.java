package online.rabko.basketball.api.test;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import online.rabko.basketball.util.ConfigReader;
import online.rabko.model.SignInRequest;
import online.rabko.model.Team;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

/**
 * API тесты для управления командами через endpoint <code>/teams</code>.
 * <p>
 * Класс проверяет следующие сценарии:
 * <ul>
 *     <li>Создание новой команды с корректной авторизацией</li>
 *     <li>Попытка создания команды без авторизации</li>
 * </ul>
 * <p>
 * Все тесты используют {@link RestAssured} для отправки HTTP-запросов и {@link Allure} для шагов и отчетности.
 * Авторизация выполняется перед каждым тестом через endpoint <code>/auth/token</code>.
 * Тестовые данные берутся из {@link ConfigReader}.
 * </p>
 *
 * <p><b>Пример запуска тестов:</b></p>
 * <pre>{@code
 * // Выполнить все тесты через JUnit 5:
 * mvn test -Dtest=TeamsApiTest
 * }</pre>
 */
@Feature("Basketball API")
@Epic("Teams Management")
@Slf4j
public class TeamsApiTest {

    /**
     * Токен авторизации, получаемый перед каждым тестом.
     */
    private String authToken;

    /**
     * Установка базового URI для API перед всеми тестами.
     */
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = ConfigReader.getApiHost();
    }

    /**
     * Авторизация перед каждым тестом для получения токена.
     * <p>
     * Токен используется для создания команд с авторизацией.
     * </p>
     */
    @BeforeEach
    public void loginAndGetToken() {
        String username = ConfigReader.getApiUsername();
        String password = ConfigReader.getApiUserPassword();

        SignInRequest signInRequest = new SignInRequest()
            .username(username)
            .password(password);

        authToken = RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(signInRequest)
            .when()
            .post("/auth/token")
            .then()
            .statusCode(200)
            .extract()
            .path("token");

        Allure.step("Получен токен авторизации для пользователя: " + username);
    }

    /**
     * Тест проверки успешного создания команды с авторизацией.
     * <p>
     * Ожидается HTTP 201 Created и совпадение имени созданной команды с отправленным.
     * </p>
     */
    @Test
    @AllureId("API007")
    @Story("Создание команды с авторизацией")
    @Severity(SeverityLevel.MINOR)
    @Description("Создание новой команды с использованием токена авторизации. Проверка, что возвращённое имя совпадает с отправленным")
    void shouldCreateTeamWithAuthorization() {
        long id = new Random().nextInt(1_000_000) + 1;
        String name = UUID.randomUUID().toString();
        Team team = new Team(id, name);

        Allure.step("Создаём команду с авторизацией с name: " + name);

        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + authToken)
            .body(team)
            .when()
            .post("/teams")
            .then()
            .statusCode(201)
            .body("name", Matchers.equalTo(team.getName()));
    }

    /**
     * Тест проверки создания команды без авторизации.
     * <p>
     * Ожидается HTTP 401 Unauthorized.
     * </p>
     */
    @Test
    @AllureId("API008")
    @Story("Создание команды без авторизации")
    @Severity(SeverityLevel.MINOR)
    @Description("Попытка создать команду без токена авторизации должна завершиться ошибкой 403 Unauthorized")
    void shouldFailToCreateTeamWithoutAuthorization() {
        long id = new Random().nextInt(1_000_000) + 1;
        String name = UUID.randomUUID().toString();
        Team team = new Team(id, name);

        Allure.step("Пробуем создать команду без авторизации с name: " + name);

        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(team)
            .when()
            .post("/teams")
            .then()
            .statusCode(403);
    }
}
