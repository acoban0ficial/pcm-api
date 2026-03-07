package dev.acobano.pcm.mapper;

import dev.acobano.pcm.dto.request.CustomerPostRequestDTO;
import dev.acobano.pcm.dto.response.CustomerResponseDTO;
import dev.acobano.pcm.model.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "taxId", source = "taxId")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "address.streetName", source = "streetName")
    @Mapping(target = "address.streetNumber", source = "streetNumber")
    @Mapping(target = "address.streetType", source = "streetType")
    @Mapping(target = "address.city", source = "city")
    @Mapping(target = "address.zipCode", source = "zipCode")
    @Mapping(target = "address.state", source = "state")
    @Mapping(target = "address.country", source = "country")
    @Mapping(target = "projects", ignore = true)
    CustomerEntity toEntity(CustomerPostRequestDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "taxId", source = "taxId")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "isActive", source = "active")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "links", ignore = true)
    CustomerResponseDTO toResponseDTO(CustomerEntity entity);
}
