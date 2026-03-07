package dev.acobano.pcm.validation.validator;

import dev.acobano.pcm.repository.CustomerJpaRepository;
import dev.acobano.pcm.validation.UniqueCustomerEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UniqueCustomerEmailValidator implements ConstraintValidator<UniqueCustomerEmail, String> {

    private final CustomerJpaRepository customerRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value) || value.isBlank()) {
            return true;
        }

        return !customerRepository.existsByEmail(value);
    }
}

