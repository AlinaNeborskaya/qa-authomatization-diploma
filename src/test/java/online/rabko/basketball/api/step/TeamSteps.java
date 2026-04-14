package online.rabko.basketball.api.step;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import online.rabko.basketball.api.assertion.TeamAssertions;
import online.rabko.basketball.api.service.TeamService;
import online.rabko.model.Team;

public class TeamSteps {

    private final TeamService service = new TeamService();

    public void createTeam(Team team, String token) {
        Allure.step("Создание команды с авторизацией: " + team.getName(), () -> {
            Response response = service.createTeam(team, token);
            TeamAssertions.assertTeamCreated(response, team);
        });
    }

    public void createTeamWithoutAuth(Team team) {
        Allure.step("Попытка создания команды без авторизации", () -> {
            Response response = service.createTeamWithoutAuth(team);
            TeamAssertions.assertUnauthorized(response);
        });
    }
}
