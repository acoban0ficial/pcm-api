package dev.acobano.pcm.validation.validator;

import dev.acobano.pcm.dto.request.ProjectPostRequestDTO;
import dev.acobano.pcm.validation.ValidDateRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class ValidDateRangeValidator implements ConstraintValidator<ValidDateRange, ProjectPostRequestDTO> {

    @Override
    public boolean isValid(ProjectPostRequestDTO dto, ConstraintValidatorContext context) {
        if (Objects.isNull(dto.startDate()) || Objects.isNull(dto.endDate())) {
            return true; // Allow null values, use @NotNull for required fields
        }

        return dto.startDate().isBefore(dto.endDate());
    }
}
