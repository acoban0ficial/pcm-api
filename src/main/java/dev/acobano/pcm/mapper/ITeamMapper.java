package dev.acobano.pcm.mapper;

import dev.acobano.pcm.dto.response.TeamResponseDTO;
import dev.acobano.pcm.model.entity.TeamEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITeamMapper {

    @Mapping(target = "id", source = "id")
    TeamResponseDTO toResponseDTO(TeamEntity entity);
}
