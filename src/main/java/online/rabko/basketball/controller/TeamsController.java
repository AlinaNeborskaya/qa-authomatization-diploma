package online.rabko.basketball.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;
import lombok.RequiredArgsConstructor;
import online.rabko.api.TeamsApi;
import online.rabko.basketball.controller.mapper.TeamMapper;
import online.rabko.basketball.entity.Team;
import online.rabko.basketball.service.impl.TeamServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing teams.
 */
@RestController
@RequiredArgsConstructor
public class TeamsController implements TeamsApi {

    private final TeamServiceImpl teamServiceImpl;
    private final TeamMapper teamMapper;

    @Override
    public ResponseEntity<List<online.rabko.model.Team>> teamsGet() {
        return ResponseEntity.ok(
            teamServiceImpl.findAll().stream()
                .map(teamMapper::toDto)
                .toList()
        );
    }

    @Override
    public ResponseEntity<online.rabko.model.Team> teamsIdGet(Long id) {
        return ResponseEntity.ok(
            teamMapper.toDto(teamServiceImpl.findById(id))
        );
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> teamsIdDelete(Long id) {
        teamServiceImpl.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<online.rabko.model.Team> teamsIdPut(Long id,
        online.rabko.model.Team dto) {
        Team updated = teamServiceImpl.update(id, teamMapper.toEntity(dto));
        return ResponseEntity.ok(teamMapper.toDto(updated));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<online.rabko.model.Team> teamsPost(online.rabko.model.Team dto) {
        Team created = teamServiceImpl.create(teamMapper.toEntity(dto));
        return ResponseEntity.status(CREATED).body(teamMapper.toDto(created));
    }
}
