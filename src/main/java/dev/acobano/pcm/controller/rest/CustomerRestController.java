package dev.acobano.pcm.controller.rest;

import dev.acobano.pcm.dto.response.CustomerResponseDTO;
import dev.acobano.pcm.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerRestController {

    private final ICustomerService customerService;

    @GetMapping(
            value = "/{customerId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerResponseDTO> getCustomerById(
            @PathVariable("customerId") String customerId) {

        CustomerResponseDTO output = customerService.findCustomer(java.util.UUID.fromString(customerId));

        // Agregar links HATEOAS:
        output.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                        .getCustomerById(customerId))
                .withSelfRel());
        output.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                        .getAllCustomers(Pageable.unpaged()))
                .withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.ok(output);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<CustomerResponseDTO>> getAllCustomers(
            @PageableDefault Pageable pageable) {

        Page<CustomerResponseDTO> customerPage = customerService.listCustomers(pageable);

        for (CustomerResponseDTO customerDto : customerPage) {
            // Agregar links HATEOAS por cada elemento:
            customerDto.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                            .getCustomerById(customerDto.getId().toString()))
                    .withSelfRel());
            customerDto.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                            .getAllCustomers(Pageable.unpaged()))
                    .withRel(IanaLinkRelations.COLLECTION));
        }
        PagedModel<CustomerResponseDTO> pagedModel = PagedModel.of(
                customerPage.getContent(),
                new PagedModel.PageMetadata(
                        customerPage.getSize(),
                        customerPage.getNumber(),
                        customerPage.getTotalElements(),
                        customerPage.getTotalPages()
                )
        );

        // Agregar links HATEOAS de la lista:
        pagedModel.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                        .getAllCustomers(pageable))
                .withSelfRel());

        if (customerPage.hasNext()) {
            pagedModel.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                            .getAllCustomers(customerPage.nextPageable()))
                    .withRel("next"));
        }

        if (customerPage.hasPrevious()) {
            pagedModel.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                            .getAllCustomers(customerPage.previousPageable()))
                    .withRel("prev"));
        }

        return ResponseEntity.ok(pagedModel);
    }
}
