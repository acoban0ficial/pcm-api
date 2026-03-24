package dev.acobano.pcm.dto.request;

import dev.acobano.pcm.validation.ValidEmployeeRegistered;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record TeamPostRequestDTO(

        @NotBlank(message = "This field is required")
        @Size(
                min = 3,
                max = 30,
                message = "This field must have between 3 and 30 characters"
        )
        String name,

        @NotBlank(message = "This field is required")
        @Size(
                min = 10,
                max = 255,
                message = "This field must have between 10 and 255 characters"
        )
        String description,

        @NotBlank(message = "This field is required")
        @Pattern(
                regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "This field must be in UUID format"
        )
        @ValidEmployeeRegistered
        UUID employeeLeaderId
) {
}