package dev.acobano.pcm.dto.request;

import dev.acobano.pcm.validation.ValidEmployeeRegistered;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record TeamPutRequestDTO(

        @Nullable
        @Size(
                min = 3,
                max = 30,
                message = "This field must have between 3 and 30 characters"
        )
        String name,

        @Nullable
        @Size(
                min = 10,
                max = 255,
                message = "This field must have between 10 and 255 characters"
        )
        String description,

        @Nullable
        @Pattern(
                regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "This field must be in UUID format"
        )
        UUID employeeLeaderId
) {
}
