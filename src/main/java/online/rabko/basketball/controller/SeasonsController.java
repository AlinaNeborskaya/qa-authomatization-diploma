package online.rabko.basketball.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;
import lombok.RequiredArgsConstructor;
import online.rabko.api.SeasonsApi;
import online.rabko.basketball.controller.mapper.SeasonMapper;
import online.rabko.basketball.entity.Season;
import online.rabko.basketball.service.impl.SeasonServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing seasons.
 */
@RestController
@RequiredArgsConstructor
public class SeasonsController implements SeasonsApi {

    private final SeasonServiceImpl seasonServiceImpl;
    private final SeasonMapper seasonMapper;

    @Override
    public ResponseEntity<List<online.rabko.model.Season>> seasonsGet() {
        return ResponseEntity.ok(
            seasonServiceImpl.findAll().stream()
                .map(seasonMapper::toDto)
                .toList()
        );
    }

    @Override
    public ResponseEntity<online.rabko.model.Season> seasonsIdGet(Long id) {
        return ResponseEntity.ok(
            seasonMapper.toDto(seasonServiceImpl.findById(id))
        );
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> seasonsIdDelete(Long id) {
        seasonServiceImpl.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<online.rabko.model.Season> seasonsIdPut(
        Long id,
        online.rabko.model.Season dto
    ) {
        Season updated = seasonServiceImpl.update(id, seasonMapper.toEntity(dto));
        return ResponseEntity.ok(seasonMapper.toDto(updated));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<online.rabko.model.Season> seasonsPost(
        online.rabko.model.Season dto
    ) {
        Season created = seasonServiceImpl.create(seasonMapper.toEntity(dto));
        return ResponseEntity.status(CREATED).body(seasonMapper.toDto(created));
    }
}
