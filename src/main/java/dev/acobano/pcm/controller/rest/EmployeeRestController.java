package dev.acobano.pcm.controller.rest;

import dev.acobano.pcm.dto.response.EmployeeResponseDTO;
import dev.acobano.pcm.service.IEmployeeService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeRestController {

    private final IEmployeeService employeeService;


    @GetMapping(
            value = "/{employeeId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(
            @NotBlank(message = "This field cannot be blank")
            @Pattern(
                    regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "This field must be in UUID format"
            )
            @PathVariable("projectId") UUID employeeId
    ) {
        EmployeeResponseDTO response = employeeService.findEmployee(employeeId);

        // Agregar link HATEOAS al equipo:
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(EmployeeRestController.class)
                        .getEmployeeById(response.getId()))
                .withSelfRel());
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(TeamRestController.class)
                        .getAllTeams(Pageable.unpaged()))
                .withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.ok(response);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<EmployeeResponseDTO>> getAllEmployees(Pageable pageable) {
        Page<EmployeeResponseDTO> outputPage = employeeService.listEmployees(pageable)
                .map(dto -> {
                    // Agregar links HATEOAS a cada equipo:
                    dto.add(WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(EmployeeRestController.class)
                                    .getEmployeeById(dto.getId()))
                            .withSelfRel());
                    dto.add(WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(EmployeeRestController.class)
                                    .getAllEmployees(Pageable.unpaged()))
                            .withRel(IanaLinkRelations.COLLECTION));
                    return dto;
                });

        PagedModel<EmployeeResponseDTO> pagedModel = PagedModel.of(
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
                    .linkTo(WebMvcLinkBuilder.methodOn(EmployeeRestController.class)
                            .getAllEmployees(outputPage.previousPageable()))
                    .withRel(IanaLinkRelations.PREV));
        }

        pagedModel.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(EmployeeRestController.class)
                        .getAllEmployees(pageable))
                .withSelfRel());

        if (outputPage.hasNext()) {
            pagedModel.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(EmployeeRestController.class)
                            .getAllEmployees(outputPage.nextPageable()))
                    .withRel(IanaLinkRelations.NEXT));
        }

        return ResponseEntity.ok(pagedModel);
    }
}
