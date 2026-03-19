package dev.acobano.pcm.dto.response;

import dev.acobano.pcm.model.enumerated.ProjectStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDTO extends RepresentationModel<ProjectResponseDTO> {
    private String id;
    private String projectName;
    private String description;
    private ProjectStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private BudgetResponseDTO budget;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean isActive;

    private String customerId;
    private String assignedTeamId;

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
