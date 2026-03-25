package dev.acobano.pcm.controller.rest;

import dev.acobano.pcm.dto.request.TeamPostRequestDTO;
import dev.acobano.pcm.dto.request.TeamPutRequestDTO;
import dev.acobano.pcm.dto.response.EmployeeResponseDTO;
import dev.acobano.pcm.dto.response.TeamResponseDTO;
import dev.acobano.pcm.service.ITeamService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamRestController {

    private final ITeamService teamService;

    @GetMapping(
            value = "/{teamId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TeamResponseDTO> getTeamById(
            @Pattern(
                    regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "This field must be in UUID format"
            )
            @PathVariable("projectId") UUID teamId
    ) {
        TeamResponseDTO response = teamService.findTeam(teamId);

        // Agregar links HATEOAS al equipo:
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(TeamRestController.class)
                        .getTeamById(response.getId()))
                .withSelfRel());
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(TeamRestController.class)
                        .getAllTeams(Pageable.unpaged()))
                .withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.ok(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<TeamResponseDTO>> getAllTeams(Pageable pageable) {
        Page<TeamResponseDTO> outputPage = teamService.listTeams(pageable)
                .map(dto -> {
                    // Agregar links HATEOAS a cada equipo:
                    dto.add(WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(TeamRestController.class)
                                    .getTeamById(dto.getId()))
                            .withSelfRel());
                    dto.add(WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(TeamRestController.class)
                                    .getAllTeams(Pageable.unpaged()))
                            .withRel(IanaLinkRelations.COLLECTION));
                    return dto;
                });

        PagedModel<TeamResponseDTO> pagedModel = PagedModel.of(
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
                    .linkTo(WebMvcLinkBuilder.methodOn(TeamRestController.class)
                            .getAllTeams(outputPage.previousPageable()))
                    .withRel(IanaLinkRelations.PREV));
        }

        pagedModel.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(TeamRestController.class)
                        .getAllTeams(pageable))
                .withSelfRel());

        if (outputPage.hasNext()) {
            pagedModel.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(TeamRestController.class)
                            .getAllTeams(outputPage.nextPageable()))
                    .withRel(IanaLinkRelations.NEXT));
        }
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping(
            value = "/{teamId}/leader",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EmployeeResponseDTO> getTeamLeader(
            @Pattern(
                    regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "This field must be in UUID format"
            )
            @PathVariable("teamId") UUID teamId
    ) {
        EmployeeResponseDTO response = teamService.getTeamLeader(teamId);

        // Agregar link HATEOAS al equipo:
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(EmployeeRestController.class)
                        .getEmployeeById(response.getId()))
                .withSelfRel());

        return ResponseEntity.ok(response);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TeamResponseDTO> createTeam(
            @Valid @RequestBody TeamPostRequestDTO request
            ) {
        TeamResponseDTO response = teamService.saveTeam(request);

        // Agregar link HATEOAS al equipo creado:
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(TeamRestController.class)
                        .getTeamById(response.getId()))
                .withSelfRel());
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(TeamRestController.class)
                        .getAllTeams(Pageable.unpaged()))
                .withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.created(
                URI.create("/api/v1/teams/".concat(response.getId().toString()))
        ).body(response);
    }

    @PutMapping(
            value = "/{teamId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TeamResponseDTO> updateTeam(
            @Pattern(
                    regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "This field must be in UUID format"
            )
            @PathVariable("teamId") UUID teamId,
            @Valid @RequestBody TeamPutRequestDTO request
    ) {
        TeamResponseDTO response = teamService.updateTeam(teamId, request);

        // Agregar link HATEOAS al equipo actualizado:
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(TeamRestController.class)
                        .getTeamById(response.getId()))
                .withSelfRel());
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(TeamRestController.class)
                        .getAllTeams(Pageable.unpaged()))
                .withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{teamId}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable("teamId") UUID teamId
    ) {
        teamService.logicalDeleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }
}
