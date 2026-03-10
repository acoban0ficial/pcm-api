package dev.acobano.pcm.mapper;

import dev.acobano.pcm.dto.response.ProjectResponseDTO;
import dev.acobano.pcm.model.entity.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IProjectMapper {

    @Mapping(target = "links", ignore = true)
    ProjectResponseDTO toResponseDTO(ProjectEntity entity);
}
