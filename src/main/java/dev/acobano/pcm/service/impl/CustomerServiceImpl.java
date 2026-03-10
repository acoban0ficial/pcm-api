package dev.acobano.pcm.service.impl;

import dev.acobano.pcm.dto.request.CustomerPostRequestDTO;
import dev.acobano.pcm.dto.request.CustomerPutRequestDTO;
import dev.acobano.pcm.dto.response.CustomerResponseDTO;
import dev.acobano.pcm.dto.response.ProjectResponseDTO;
import dev.acobano.pcm.exception.CustomerNotFoundException;
import dev.acobano.pcm.mapper.ICustomerMapper;
import dev.acobano.pcm.mapper.IProjectMapper;
import dev.acobano.pcm.model.entity.CustomerEntity;
import dev.acobano.pcm.model.entity.ProjectEntity;
import dev.acobano.pcm.repository.CustomerJpaRepository;
import dev.acobano.pcm.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerJpaRepository customerRepository;
    private final ICustomerMapper customerMapper;
    private final IProjectMapper projectMapper;


    @Override
    public CustomerResponseDTO findCustomer(UUID customerId) {
        Optional<CustomerEntity> customerOpt = customerRepository.findById(customerId);

        if (customerOpt.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }

        return customerMapper.toResponseDTO(customerOpt.get());
    }

    @Override
    public Page<CustomerResponseDTO> listCustomers(Pageable pageable) {
        if (customerRepository.findAll().isEmpty()) {
            throw new CustomerNotFoundException("No customers found in the system.");
        }

        return customerRepository.findAll(pageable)
                .map(customerMapper::toResponseDTO);
    }

    @Override
    public Page<ProjectResponseDTO> getCustomerProjects(Pageable pageable, UUID customerId) {
        Optional<CustomerEntity> customerOpt = customerRepository.findById(customerId);

        if (customerOpt.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }

        Set<ProjectEntity> projects = customerOpt.get().getProjects();

        // Mapear ProjectEntity a ProjectResponseDTO
        java.util.List<ProjectResponseDTO> projectResponses = projects.stream()
                .map(projectMapper::toResponseDTO)
                .collect(Collectors.toList());

        // Calcular el rango de elementos para la página actual
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), projectResponses.size());

        // Extraer los elementos de la página actual
        java.util.List<ProjectResponseDTO> pageContent = projectResponses.subList(start, end);

        // Crear y retornar el Page con los datos de paginación
        return new PageImpl<>(pageContent, pageable, projectResponses.size());
    }

    @Override
    @Transactional
    public CustomerResponseDTO saveCustomer(CustomerPostRequestDTO input) {
        return customerMapper.toResponseDTO(
                customerRepository.save(
                        customerMapper.toEntity(input)
                )
        );
    }

    @Override
    @Transactional
    public CustomerResponseDTO updateCustomer(UUID customerId, CustomerPutRequestDTO input) {
        Optional<CustomerEntity> customerOpt = customerRepository.findById(customerId);

        if (customerOpt.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }

        CustomerEntity customer = customerOpt.get();
        updateCustomerFields(input, customer);

        return customerMapper.toResponseDTO(
                customerRepository.save(customer)
        );
    }

    private void updateCustomerFields(CustomerPutRequestDTO dto, CustomerEntity entity) {
        if (!Objects.isNull(dto)) {
            if (!Objects.isNull(dto.firstName())) {
                entity.setFirstName(dto.firstName());
            }
            if (!Objects.isNull(dto.lastName())) {
                entity.setLastName(dto.lastName());
            }
            if (!Objects.isNull(dto.taxId())) {
                entity.setTaxId(dto.taxId());
            }
            if (!Objects.isNull(dto.email())) {
                entity.setEmail(dto.email());
            }
            if (!Objects.isNull(dto.phoneNumber())) {
                entity.setPhoneNumber(dto.phoneNumber());
            }
            if (!Objects.isNull(dto.birthDate())) {
                entity.setBirthDate(dto.birthDate());
            }
            if (!Objects.isNull(dto.streetName())) {
                entity.getAddress().setStreetName(dto.streetName());
            }
            if (!Objects.isNull(dto.streetNumber())) {
                entity.getAddress().setStreetNumber(dto.streetNumber());
            }
            if (!Objects.isNull(dto.streetType())) {
                entity.getAddress().setStreetType(dto.streetType());
            }
            if (!Objects.isNull(dto.city())) {
                entity.getAddress().setCity(dto.city());
            }
            if (!Objects.isNull(dto.zipCode())) {
                entity.getAddress().setZipCode(dto.zipCode());
            }
            if (!Objects.isNull(dto.state())) {
                entity.getAddress().setState(dto.state());
            }
            if (!Objects.isNull(dto.country())) {
                entity.getAddress().setCountry(dto.country());
            }
        }
    }

    @Override
    public void logicalDeleteCustomer(UUID customerId) {
        Optional<CustomerEntity> customerOpt = customerRepository.findById(customerId);

        if (customerOpt.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }

        CustomerEntity customer = customerOpt.get();
        customer.setActive(Boolean.FALSE);
        customerRepository.save(customer);
    }
}
