package dev.acobano.pcm.controller.rest;

import dev.acobano.pcm.dto.request.ProjectPostRequestDTO;
import dev.acobano.pcm.dto.request.ProjectPutRequestDTO;
import dev.acobano.pcm.dto.response.CustomerResponseDTO;
import dev.acobano.pcm.dto.response.ProjectResponseDTO;
import dev.acobano.pcm.dto.response.TeamResponseDTO;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
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
        ProjectResponseDTO response = projectService.findProject(UUID.fromString(projectId));

        // Agregar links HATEOAS:
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(ProjectRestController.class)
                        .getProjectById(projectId))
                .withSelfRel());
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(ProjectRestController.class)
                        .getAllProjects(Pageable.unpaged()))
                .withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.ok(response);
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
                    .linkTo(WebMvcLinkBuilder.methodOn(ProjectRestController.class)
                            .getAllProjects(outputPage.previousPageable()))
                    .withRel(IanaLinkRelations.PREV));
        }

        pagedModel.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(ProjectRestController.class)
                        .getAllProjects(pageable))
                .withSelfRel());

        if (outputPage.hasNext()) {
            pagedModel.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(ProjectRestController.class)
                            .getAllProjects(outputPage.nextPageable()))
                    .withRel(IanaLinkRelations.NEXT));
        }

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping(
            value = "/{projectId}/customer",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerResponseDTO> getProjectCustomer(
            @PathVariable("projectId") UUID projectId
    ) {
        CustomerResponseDTO response = projectService.getProjectCustomer(projectId);

        // Agregar link HATEOAS al cliente:
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                        .getCustomerById(response.getId()))
                .withSelfRel());

        return ResponseEntity.ok(response);
    }

    @GetMapping(
            value = "/{projectId}/team",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<TeamResponseDTO> getProjectAssignedTeam(
            @PathVariable("projectId") UUID projectId
    ) {
        TeamResponseDTO response = projectService.getProjectTeam(projectId);

        // Agregar link HATEOAS al equipo:
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(TeamRestController.class)
                        .getTeamById(UUID.fromString(response.getId())))
                .withSelfRel());

        return ResponseEntity.ok(response);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProjectResponseDTO> createProject(
            @Valid @RequestBody ProjectPostRequestDTO request
    ) {
        ProjectResponseDTO response = projectService.saveProject(request);

        // Agregar links HATEOAS:
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(ProjectRestController.class)
                        .getProjectById(response.getId()))
                .withSelfRel());
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(ProjectRestController.class)
                        .getAllProjects(Pageable.unpaged()))
                .withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.created(
                    URI.create("/api/v1/projects/" + response.getId())
                ).body(response);
    }

    @PutMapping(
            value = "/{projectId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable("projectId") UUID projectId,
            @Valid @RequestBody ProjectPutRequestDTO request
    ) {
        ProjectResponseDTO response = projectService.updateProject(projectId, request);

        // Agregar links HATEOAS:
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(ProjectRestController.class)
                        .getProjectById(response.getId()))
                .withSelfRel());
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(ProjectRestController.class)
                        .getAllProjects(Pageable.unpaged()))
                .withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable("customerId") UUID projectId
    ) {
        projectService.logicalDeleteProject(projectId);
        return ResponseEntity.noContent().build();
    }
}
