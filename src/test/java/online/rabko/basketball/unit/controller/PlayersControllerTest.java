package online.rabko.basketball.unit.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import online.rabko.basketball.controller.PlayersController;
import online.rabko.basketball.controller.mapper.PlayerMapper;
import online.rabko.basketball.entity.Player;
import online.rabko.basketball.service.impl.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Unit tests for {@link PlayersController}.
 */
@ExtendWith(MockitoExtension.class)
class PlayersControllerTest {

    @Mock
    private PlayerServiceImpl playerServiceImpl;

    @Mock
    private PlayerMapper playerMapper;

    @InjectMocks
    private PlayersController playersController;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(playersController);
    }

    @Test
    void playersGet_shouldReturnList() {
        Player p1 = Player.builder().id(1L).build();
        Player p2 = Player.builder().id(2L).build();
        when(playerServiceImpl.findAll()).thenReturn(List.of(p1, p2));
        when(playerMapper.toDto(p1)).thenReturn(new online.rabko.model.Player());
        when(playerMapper.toDto(p2)).thenReturn(new online.rabko.model.Player());

        given()
            .when()
            .get("/players")
            .then()
            .statusCode(200)
            .body("$", hasSize(2));

        verify(playerServiceImpl).findAll();
        verify(playerMapper, times(2)).toDto(any(Player.class));
    }

    @Test
    void playersIdGet_shouldReturnPlayer() {
        Long id = 42L;
        Player entity = Player.builder().id(id).build();
        when(playerServiceImpl.findById(id)).thenReturn(entity);
        when(playerMapper.toDto(entity)).thenReturn(new online.rabko.model.Player());

        given()
            .when()
            .get("/players/{id}", id.toString())
            .then()
            .statusCode(200)
            .body("$", notNullValue());

        verify(playerServiceImpl).findById(id);
        verify(playerMapper).toDto(entity);
    }

    @Test
    void playersPost_shouldCreatePlayer() {
        Player toCreate = Player.builder().build();
        Player created = Player.builder().id(10L).build();

        when(playerMapper.toEntity(any(online.rabko.model.Player.class))).thenReturn(toCreate);
        when(playerServiceImpl.create(toCreate)).thenReturn(created);
        when(playerMapper.toDto(created)).thenReturn(new online.rabko.model.Player());

        given()
            .contentType(ContentType.JSON)
            .body("{}")
            .when()
            .post("/players")
            .then()
            .statusCode(201)
            .body("$", notNullValue());

        verify(playerMapper).toEntity(any(online.rabko.model.Player.class));
        verify(playerServiceImpl).create(toCreate);
        verify(playerMapper).toDto(created);
    }

    @Test
    void playersIdPut_shouldUpdatePlayer() {
        Long id = 7L;
        Player toUpdate = Player.builder().build();
        Player updated = Player.builder().id(id).build();

        when(playerMapper.toEntity(any(online.rabko.model.Player.class))).thenReturn(toUpdate);
        when(playerServiceImpl.update(eq(id), eq(toUpdate))).thenReturn(updated);
        when(playerMapper.toDto(updated)).thenReturn(new online.rabko.model.Player());

        given()
            .contentType(ContentType.JSON)
            .body("{}")
            .when()
            .put("/players/{id}", id.toString())
            .then()
            .statusCode(200)
            .body("$", notNullValue());

        verify(playerMapper).toEntity(any(online.rabko.model.Player.class));
        verify(playerServiceImpl).update(id, toUpdate);
        verify(playerMapper).toDto(updated);
    }

    @Test
    void playersIdDelete_shouldDeletePlayer() {
        Long id = 9L;
        doNothing().when(playerServiceImpl).delete(id);

        given()
            .when()
            .delete("/players/{id}", id.toString())
            .then()
            .statusCode(204);

        verify(playerServiceImpl).delete(id);
        verifyNoInteractions(playerMapper);
    }

    @Test
    void playersIdGet_shouldReturnNotFound() {
        Long id = 404L;
        when(playerServiceImpl.findById(id))
            .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        given()
            .when()
            .get("/players/{id}", id.toString())
            .then()
            .statusCode(404);
    }

    @Test
    void playersIdDelete_shouldReturnNotFound() {
        Long id = 404L;
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
            .when(playerServiceImpl).delete(id);

        given()
            .when()
            .delete("/players/{id}", id.toString())
            .then()
            .statusCode(404);
    }

    @Test
    void playersPost_shouldReturnBadRequest() {
        when(playerMapper.toEntity(any(online.rabko.model.Player.class)))
            .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        given()
            .contentType(ContentType.JSON)
            .body("{}")
            .when()
            .post("/players")
            .then()
            .statusCode(400);
    }

    @Test
    void playersIdPut_shouldReturnBadRequest() {
        Long id = 5L;
        when(playerMapper.toEntity(any(online.rabko.model.Player.class)))
            .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        given()
            .contentType(ContentType.JSON)
            .body("{}")
            .when()
            .put("/players/{id}", id.toString())
            .then()
            .statusCode(400);
    }

    @Test
    void playersIdPut_shouldReturnConflict() {
        Long id = 6L;
        when(playerMapper.toEntity(any(online.rabko.model.Player.class)))
            .thenReturn(Player.builder().build());
        when(playerServiceImpl.update(eq(id), any(Player.class)))
            .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT));

        given()
            .contentType(ContentType.JSON)
            .body("{}")
            .when()
            .put("/players/{id}", id.toString())
            .then()
            .statusCode(409);
    }
}
