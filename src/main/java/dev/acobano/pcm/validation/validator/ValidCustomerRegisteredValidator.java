package dev.acobano.pcm.validation.validator;

import dev.acobano.pcm.repository.CustomerJpaRepository;
import dev.acobano.pcm.validation.ValidCustomerRegistered;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidCustomerRegisteredValidator implements ConstraintValidator<ValidCustomerRegistered, UUID> {

    private final CustomerJpaRepository customerRepository;

    @Override
    public boolean isValid(UUID value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }

        return customerRepository.existsById(value);
    }
}
