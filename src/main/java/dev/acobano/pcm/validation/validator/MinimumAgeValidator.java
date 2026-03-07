package dev.acobano.pcm.validation.validator;

import dev.acobano.pcm.validation.MinimumAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class MinimumAgeValidator implements ConstraintValidator<MinimumAge, LocalDate> {

    private int minimumAge;

    @Override
    public void initialize(MinimumAge annotation) {
        this.minimumAge = annotation.value();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are handled by @NotNull
        }

        LocalDate minimumBirthDate = LocalDate.now().minusYears(minimumAge);
        return !value.isAfter(minimumBirthDate);
    }
}

