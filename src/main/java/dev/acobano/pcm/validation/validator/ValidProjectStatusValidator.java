package dev.acobano.pcm.validation.validator;

import dev.acobano.pcm.model.enumerated.ProjectStatus;
import dev.acobano.pcm.validation.ValidProjectStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ValidProjectStatusValidator implements ConstraintValidator<ValidProjectStatus, ProjectStatus> {

    private static final String ALLOWED_STATUSES = Arrays.stream(ProjectStatus.values())
            .map(Enum::name)
            .collect(Collectors.joining(", "));

    private static final String MESSAGE_TEMPLATE = "Invalid project status. Must be one of: " + ALLOWED_STATUSES;

    @Override
    public void initialize(ValidProjectStatus annotation) {
        // No es necesario inicializar nada
    }

    @Override
    public boolean isValid(ProjectStatus value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true; // null values are allowed
        }

        try {
            ProjectStatus.valueOf(value.toString());
            return true;
        } catch (IllegalArgumentException e) {
            // Personalizar el mensaje de error con la lista dinámica de valores permitidos
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(MESSAGE_TEMPLATE)
                    .addConstraintViolation();
            return false;
        }
    }
}

