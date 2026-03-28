package dev.acobano.pcm.mapper;


import dev.acobano.pcm.dto.response.TaskResponseDTO;
import dev.acobano.pcm.model.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITaskMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "priority", source = "priority")
    @Mapping(target = "estimatedHours", source = "estimatedHours")
    @Mapping(target = "spentHours", source = "spentHours")
    @Mapping(target = "dueDate", source = "dueDate")
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "assigneeEmployeeId", source = "assignee.id")
    TaskResponseDTO toResponseDTO(TaskEntity entity);
}
