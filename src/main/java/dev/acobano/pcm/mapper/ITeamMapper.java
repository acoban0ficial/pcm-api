package dev.acobano.pcm.mapper;

import dev.acobano.pcm.dto.request.TeamPostRequestDTO;
import dev.acobano.pcm.dto.response.TeamResponseDTO;
import dev.acobano.pcm.model.entity.TeamEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITeamMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "leader.id", source = "employeeLeaderId")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "creationDate", ignore = true)     // Se asigna en servicio
    @Mapping(target = "lastUpdateDate", ignore = true)   // Se asigna en servicio
    TeamEntity toEntity(TeamPostRequestDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "teamName", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "createdAt", source = "creationDate")
    @Mapping(target = "updatedAt", source = "lastUpdateDate")
    @Mapping(target = "isActive", source = "active")
    @Mapping(target = "employeeLeaderId", source = "leader.id")
    TeamResponseDTO toResponseDTO(TeamEntity entity);
}
