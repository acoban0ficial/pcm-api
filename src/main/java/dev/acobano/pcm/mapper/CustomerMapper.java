package dev.acobano.pcm.mapper;

import dev.acobano.pcm.dto.response.CustomerResponseDTO;
import dev.acobano.pcm.model.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "taxId", source = "taxId")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "isActive", source = "active")
    @Mapping(target = "address", source = "address")
    CustomerResponseDTO toResponseDTO(CustomerEntity entity);


}
