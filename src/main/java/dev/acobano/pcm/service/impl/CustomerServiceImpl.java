package dev.acobano.pcm.service.impl;

import dev.acobano.pcm.dto.request.CustomerPostRequestDTO;
import dev.acobano.pcm.dto.response.CustomerResponseDTO;
import dev.acobano.pcm.exception.CustomerNotFoundException;
import dev.acobano.pcm.mapper.CustomerMapper;
import dev.acobano.pcm.model.entity.CustomerEntity;
import dev.acobano.pcm.repository.CustomerJpaRepository;
import dev.acobano.pcm.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerJpaRepository customerRepository;


    @Override
    public CustomerResponseDTO findCustomer(UUID customerId) {
        Optional<CustomerEntity> customerOpt = customerRepository.findById(customerId);

        if (customerOpt.isEmpty()) {
            throw new CustomerNotFoundException("User not found with id: " + customerId);
        }

        return CustomerMapper.INSTANCE.toResponseDTO(customerOpt.get());
    }

    @Override
    public Page<CustomerResponseDTO> listCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(CustomerMapper.INSTANCE::toResponseDTO);
    }

    @Override
    public CustomerResponseDTO createCustomer(CustomerPostRequestDTO input) {
        return null;
    }
}
