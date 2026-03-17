package dev.acobano.pcm.controller.rest;

import dev.acobano.pcm.dto.request.ProjectPostRequestDTO;
import dev.acobano.pcm.dto.response.CustomerResponseDTO;
import dev.acobano.pcm.dto.response.ProjectResponseDTO;
import dev.acobano.pcm.service.IProjectService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectRestController {

    private final IProjectService projectService;

    @GetMapping(
            value = "/{projectId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProjectResponseDTO> getProjectById(
            @PathVariable("projectId") String projectId
    ) {
        ProjectResponseDTO output = projectService.findProject(UUID.fromString(projectId));

        // Agregar links HATEOAS:
        output.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(ProjectRestController.class)
                        .getProjectById(projectId))
                .withSelfRel());
        output.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(ProjectRestController.class)
                        .getAllProjects(Pageable.unpaged()))
                .withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.ok(output);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<ProjectResponseDTO>> getAllProjects(
            @PageableDefault Pageable pageable
    ) {
        Page<ProjectResponseDTO> outputPage = projectService.listProjects(pageable)
                .map(dto -> {
                    // Agregar links HATEOAS a cada proyecto:
                    dto.add(WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(ProjectRestController.class)
                                    .getProjectById(dto.getId()))
                            .withSelfRel());
                    dto.add(WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(ProjectRestController.class)
                                    .getAllProjects(Pageable.unpaged()))
                            .withRel(IanaLinkRelations.COLLECTION));
                    return dto;
                });

        PagedModel<ProjectResponseDTO> pagedModel = PagedModel.of(
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
    public ResponseEntity<ProjectResponseDTO> createProject(
            @Valid @RequestBody ProjectPostRequestDTO request
    ) {

    }
}
