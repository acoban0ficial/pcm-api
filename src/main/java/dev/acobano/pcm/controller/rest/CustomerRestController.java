package dev.acobano.pcm.controller.rest;

import dev.acobano.pcm.dto.response.CustomerResponseDTO;
import dev.acobano.pcm.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerRestController {

    private final ICustomerService customerService;




    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<CustomerResponseDTO>> getAllCustomers(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(customerService.listCustomers(pageable));
    }
}
