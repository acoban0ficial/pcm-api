package dev.acobano.pcm.validation.validator;

import dev.acobano.pcm.repository.EmployeeJpaRepository;
import dev.acobano.pcm.validation.ValidEmployeeRegistered;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidEmployeeRegisteredValidator implements ConstraintValidator<ValidEmployeeRegistered, UUID> {

    private final EmployeeJpaRepository employeeRepository;

    @Override
    public boolean isValid(UUID value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }

        return employeeRepository.existsById(value);
    }
}
