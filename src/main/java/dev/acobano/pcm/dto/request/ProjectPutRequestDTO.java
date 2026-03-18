package dev.acobano.pcm.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.acobano.pcm.model.enumerated.Currency;
import dev.acobano.pcm.model.enumerated.ProjectStatus;
import dev.acobano.pcm.validation.ValidCurrency;
import dev.acobano.pcm.validation.ValidCustomerRegistered;
import dev.acobano.pcm.validation.ValidDateRange;
import dev.acobano.pcm.validation.ValidProjectStatus;
import dev.acobano.pcm.validation.ValidTeamAssigned;
import dev.acobano.pcm.validation.config.LocalDateDeserializer;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@ValidDateRange
public record ProjectPutRequestDTO(
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
        @ValidProjectStatus
        ProjectStatus status,

        @Nullable
        @JsonDeserialize(using = LocalDateDeserializer.class)
        LocalDate startDate,

        @Nullable
        @JsonDeserialize(using = LocalDateDeserializer.class)
        LocalDate endDate,

        @Nullable
        @Min(
                value = 0,
                message = "This field must be a positive number"
        )
        BigDecimal budgetPrice,

        @Nullable
        @ValidCurrency
        Currency budgetCurrency,

        @Nullable
        @Pattern(
                regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "This field must be in UUID format"
        )
        @ValidCustomerRegistered
        UUID customerId,

        @Nullable
        @Pattern(
                regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "This field must be in UUID format"
        )
        @ValidTeamAssigned
        UUID assignedTeamId
) {
}
