package dev.acobano.pcm.dto.response;

import dev.acobano.pcm.model.enumerated.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO extends RepresentationModel<TaskResponseDTO> {
    private UUID id;
    private String title;
    private String description;
    private Priority priority;
    private Float estimatedHours;
    private Float spentHours;
    private LocalDate dueDate;
    private UUID projectId;
    private UUID assigneeEmployeeId;
}
