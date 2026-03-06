package dev.acobano.pcm.service;

import dev.acobano.pcm.dto.request.CustomerPostRequestDTO;
import dev.acobano.pcm.dto.response.CustomerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ICustomerService {
    CustomerResponseDTO findCustomer(UUID customerId);
    Page<CustomerResponseDTO> listCustomers(Pageable pageable);
    CustomerResponseDTO createCustomer(CustomerPostRequestDTO input);
}
