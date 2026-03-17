package dev.acobano.pcm.validation.validator;

import dev.acobano.pcm.repository.TeamJpaRepository;
import dev.acobano.pcm.validation.ValidTeamAssigned;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidTeamAssignedValidator implements ConstraintValidator<ValidTeamAssigned, UUID> {

    private final TeamJpaRepository teamRepository;

    @Override
    public boolean isValid(UUID value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }

        return teamRepository.existsById(value);
    }
}
