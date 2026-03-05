package dev.acobano.pcm.service;

import dev.acobano.pcm.dto.request.CustomerPostRequestDTO;
import dev.acobano.pcm.dto.response.CustomerResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface ICustomerService {
    CustomerResponseDTO getCustomer(UUID customerId);
    PagedModel<CustomerResponseDTO> listCustomers(Pageable pageable);
    CustomerResponseDTO createCustomer(CustomerPostRequestDTO input);
}
