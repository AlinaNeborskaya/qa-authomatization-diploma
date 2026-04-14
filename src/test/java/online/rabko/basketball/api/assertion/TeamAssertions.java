package online.rabko.basketball.api.assertion;

import io.restassured.response.Response;
import online.rabko.model.Team;

import static org.hamcrest.Matchers.equalTo;

public class TeamAssertions {

    public static void assertTeamCreated(Response response, Team team) {
        response.then()
                .statusCode(201)
                .body("name", equalTo(team.getName()));
    }

    public static void assertUnauthorized(Response response) {
        response.then()
                .statusCode(403);
    }
}
