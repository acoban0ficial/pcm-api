package dev.acobano.pcm.controller.rest;

import dev.acobano.pcm.dto.response.TaskResponseDTO;
import dev.acobano.pcm.service.ITaskService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskRestController {

    private final ITaskService taskService;

    @GetMapping(
            value = "/{taskId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TaskResponseDTO> getTaskById(
            @NotBlank(message = "This field cannot be blank")
            @Pattern(
                    regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "This field must be in UUID format"
            )
            @PathVariable("taskId") String taskId
    ) {
        TaskResponseDTO response = taskService.findTask(UUID.fromString(taskId));

        // Agregar links HATEOAS:
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(TaskRestController.class)
                        .getTaskById(taskId))
                .withSelfRel());
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(TaskRestController.class)
                        .getAllTasks(Pageable.unpaged()))
                .withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.ok(response);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<TaskResponseDTO>> getAllTasks(
            @PageableDefault Pageable pageable
    ) {
        Page<TaskResponseDTO> outputPage = taskService.listTasks(pageable)
                .map(dto -> {
                    // Agregar links HATEOAS a cada proyecto:
                    dto.add(WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(TaskRestController.class)
                                    .getTaskById(dto.getId().toString()))
                            .withSelfRel());
                    dto.add(WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(TaskRestController.class)
                                    .getAllTasks(pageable))
                            .withRel(IanaLinkRelations.COLLECTION));
                    return dto;
                });

        PagedModel<TaskResponseDTO> pagedModel = PagedModel.of(
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
                    .linkTo(WebMvcLinkBuilder.methodOn(TaskRestController.class)
                            .getAllTasks(outputPage.previousPageable()))
                    .withRel(IanaLinkRelations.PREV));
        }

        pagedModel.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(TaskRestController.class)
                        .getAllTasks(pageable))
                .withSelfRel());

        if (outputPage.hasNext()) {
            pagedModel.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(TaskRestController.class)
                            .getAllTasks(outputPage.nextPageable()))
                    .withRel(IanaLinkRelations.NEXT));
        }

        return ResponseEntity.ok(pagedModel);
    }
}
