package dev.acobano.pcm.mapper;

import dev.acobano.pcm.dto.request.ProjectPostRequestDTO;
import dev.acobano.pcm.dto.response.ProjectResponseDTO;
import dev.acobano.pcm.model.entity.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IProjectMapper {

    @Mapping(target = "id", ignore = true) // Se genera automáticamente
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "budget.price", source = "budgetPrice")
    @Mapping(target = "budget.currency", source = "budgetCurrency")
    @Mapping(target = "customer.id", source = "customerId")
    @Mapping(target = "team.id", source = "assignedTeamId")
    @Mapping(target = "tasks", ignore = true)
    ProjectEntity toEntity(ProjectPostRequestDTO dto);

    @Mapping(target = "id", expression = "java(entity.getId().toString())")
    @Mapping(target = "projectName", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "budget", source = "budget")
    @Mapping(target = "links", ignore = true)
    ProjectResponseDTO toResponseDTO(ProjectEntity entity);
}
