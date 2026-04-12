package online.rabko.basketball.api.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import online.rabko.model.Team;

public class TeamService {

    public Response createTeam(Team team, String token) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(team)
                .post("/teams");
    }

    public Response createTeamWithoutAuth(Team team) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(team)
                .post("/teams");
    }
}
