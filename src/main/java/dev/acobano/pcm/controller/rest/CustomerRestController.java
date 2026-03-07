package dev.acobano.pcm.controller.rest;

import dev.acobano.pcm.dto.request.CustomerPostRequestDTO;
import dev.acobano.pcm.dto.request.CustomerPutRequestDTO;
import dev.acobano.pcm.dto.response.CustomerResponseDTO;
import dev.acobano.pcm.service.ICustomerService;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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
            @PathVariable("customerId") String customerId
    ) {
        CustomerResponseDTO output = customerService.findCustomer(UUID.fromString(customerId));

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
            @PageableDefault Pageable pageable
    ) {
        Page<CustomerResponseDTO> outputPage = customerService.listCustomers(pageable)
                .map(dto -> {
                    // Agregar links HATEOAS a cada cliente:
                    dto.add(WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                                    .getCustomerById(dto.getId()))
                            .withSelfRel());
                    dto.add(WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                                    .getAllCustomers(Pageable.unpaged()))
                            .withRel(IanaLinkRelations.COLLECTION));
                    return dto;
                });

        PagedModel<CustomerResponseDTO> pagedModel = PagedModel.of(
                outputPage.getContent(),
                new PagedModel.PageMetadata(
                        outputPage.getSize(),
                        outputPage.getNumber(),
                        outputPage.getTotalElements(),
                        outputPage.getTotalPages()
                )
        );

        // Agregar links HATEOAS de la lista:
        if (outputPage.hasPrevious()) {
            pagedModel.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                            .getAllCustomers(outputPage.previousPageable()))
                    .withRel(IanaLinkRelations.PREV));
        }

        pagedModel.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                        .getAllCustomers(pageable))
                .withSelfRel());

        if (outputPage.hasNext()) {
            pagedModel.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                            .getAllCustomers(outputPage.nextPageable()))
                    .withRel(IanaLinkRelations.NEXT));
        }

        return ResponseEntity.ok(pagedModel);
    }


    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerResponseDTO> createCustomer(
            @Valid @RequestBody CustomerPostRequestDTO input
    ) {
        CustomerResponseDTO output = customerService.saveCustomer(input);

        // Agregar links HATEOAS:
        output.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                        .getCustomerById(output.getId()))
                .withSelfRel());
        output.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                        .getAllCustomers(Pageable.unpaged()))
                .withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.ok(output);
    }

    @PutMapping(
            value = "/{customerId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @PathVariable("customerId") String customerId,
            @Valid @RequestBody CustomerPutRequestDTO input
    ) {
        CustomerResponseDTO output = customerService.updateCustomer(UUID.fromString(customerId), input);

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
}
