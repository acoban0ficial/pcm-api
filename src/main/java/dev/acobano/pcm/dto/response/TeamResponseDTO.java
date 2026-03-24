package dev.acobano.pcm.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponseDTO extends RepresentationModel<TeamResponseDTO> {
    private UUID id;
    private String teamName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean isActive;

    private UUID employeeLeaderId;
}
