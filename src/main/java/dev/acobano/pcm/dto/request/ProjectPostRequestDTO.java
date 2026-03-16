package dev.acobano.pcm.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.acobano.pcm.model.enumerated.Currency;
import dev.acobano.pcm.model.enumerated.ProjectStatus;
import dev.acobano.pcm.validation.ValidDateRange;
import dev.acobano.pcm.validation.ValidProjectStatus;
import dev.acobano.pcm.validation.config.LocalDateDeserializer;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@ValidDateRange
public record ProjectPostRequestDTO(
        @NotBlank(message = "This field is required")
        @Size(
                min = 3,
                max = 30,
                message = "This field must have between 3 and 30 characters"
        )
        String name,

        @Size(
                min = 10,
                max = 255,
                message = "This field must have between 10 and 255 characters"
        )
        String description,

        @Nullable
        @ValidProjectStatus
        ProjectStatus status,

        @NotNull(message = "This field is required")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        LocalDate startDate,

        @NotNull(message = "This field is required")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        LocalDate endDate,
        BigDecimal budgetPrice,
        Currency budgetCurrency,
        UUID customerId,
        UUID assignedTeamId
) {
}
