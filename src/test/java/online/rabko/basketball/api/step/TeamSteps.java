package online.rabko.basketball.api.step;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import online.rabko.basketball.api.assertion.TeamAssertions;
import online.rabko.basketball.api.service.TeamService;
import online.rabko.model.Team;
public class TeamSteps {
    private final TeamService service = new TeamService();

    @Step("Создание команды с авторизацией: {team.name}")
    public void createTeam(Team team, String token) {
        Response response = service.createTeam(team, token);
        TeamAssertions.assertTeamCreated(response, team);
    }

    @Step("Попытка создания команды без авторизации")
    public void createTeamWithoutAuth(Team team) {
        Response response = service.createTeamWithoutAuth(team);
        TeamAssertions.assertUnauthorized(response);
    }
}
