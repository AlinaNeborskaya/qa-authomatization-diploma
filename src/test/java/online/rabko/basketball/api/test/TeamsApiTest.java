package online.rabko.basketball.api.test;

import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import online.rabko.basketball.api.step.AuthSteps;
import online.rabko.basketball.api.step.TeamSteps;
import online.rabko.basketball.util.ConfigReader;
import online.rabko.model.SignInRequest;
import online.rabko.model.Team;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

/**
 * API тесты для управления командами.
 *
 * Проверяет:
 * - создание команды с авторизацией
 * - создание команды без авторизации
 */
@Feature("Basketball API")
@Epic("Teams Management")
@Slf4j
public class TeamsApiTest {

    private final AuthSteps authSteps = new AuthSteps();
    private final TeamSteps teamSteps = new TeamSteps();

    private String token;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = ConfigReader.getApiHost();
    }

    @BeforeEach
    void login() {
        SignInRequest request = new SignInRequest(
                ConfigReader.getApiUsername(),
                ConfigReader.getApiUserPassword()
        );

        token = authSteps.getToken(request);
    }

    @Test
    @AllureId("API007")
    @Story("Создание команды с авторизацией")
    @Severity(SeverityLevel.MINOR)
    void shouldCreateTeam() {
        Team team = new Team(
                new Random().nextLong(1_000_000),
                UUID.randomUUID().toString()
        );

        teamSteps.createTeam(team, token);
    }
    @Test
    @AllureId("API008")
    @Story("Создание команды без авторизации")
    @Severity(SeverityLevel.MINOR)
    void shouldFailCreateTeamWithoutAuth() {
        Team team = new Team(
                new Random().nextLong(1_000_000),
                UUID.randomUUID().toString()
        );

        teamSteps.createTeamWithoutAuth(team);
    }
}
