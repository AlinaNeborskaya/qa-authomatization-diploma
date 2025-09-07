package online.rabko.basketball.controller.mapper;

import online.rabko.basketball.entity.Match;
import online.rabko.basketball.service.impl.SeasonServiceImpl;
import online.rabko.basketball.service.impl.TeamServiceImpl;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * MapStruct mapper for converting between Match entity and DTO. Uses expression-based lookups to
 * resolve relations from IDs.
 */
@Mapper(config = CentralMapperConfig.class)
public interface MatchMapper {

    /**
     * Converts a Match entity to a Match DTO.
     */
    @Mappings({
        @Mapping(target = "seasonId", source = "season.id"),
        @Mapping(target = "homeTeamId", source = "homeTeam.id"),
        @Mapping(target = "awayTeamId", source = "awayTeam.id")
    })
    online.rabko.model.Match toDto(Match source);

    /**
     * Converts a Match DTO to a Match entity.
     */
    @InheritInverseConfiguration(name = "toDto")
    @Mapping(target = "id", ignore = true)
    @Mapping(
        target = "season",
        expression =
            "java(dto.getSeasonId() == null ? null : seasonService.findById(dto.getSeasonId()))"
    )
    @Mapping(
        target = "homeTeam",
        expression =
            "java(dto.getHomeTeamId() == null ? null : teamService.findById(dto.getHomeTeamId()))"
    )
    @Mapping(
        target = "awayTeam",
        expression =
            "java(dto.getAwayTeamId() == null ? null : teamService.findById(dto.getAwayTeamId()))"
    )
    Match toEntity(online.rabko.model.Match dto,
        @Context SeasonServiceImpl seasonService,
        @Context TeamServiceImpl teamService);
}
