package dev.acobano.pcm.mapper;


import dev.acobano.pcm.dto.response.EmployeeResponseDTO;
import dev.acobano.pcm.model.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IEmployeeMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "entryDate", source = "entryDate")
    @Mapping(target = "isEnabled", source = "enabled")
    EmployeeResponseDTO toResponseDTO(EmployeeEntity entity);
}
