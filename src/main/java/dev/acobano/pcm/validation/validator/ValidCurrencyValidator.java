package dev.acobano.pcm.validation.validator;

import dev.acobano.pcm.model.enumerated.Currency;
import dev.acobano.pcm.validation.ValidCurrency;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ValidCurrencyValidator implements ConstraintValidator<ValidCurrency, Currency> {

    private static final String ALLOWED_CURRENCIES = Arrays.stream(Currency.values())
            .map(Currency::getIsoCode)
            .collect(Collectors.joining(", "));

    private static final String MESSAGE_TEMPLATE = "Invalid currency. Must be one of: " + ALLOWED_CURRENCIES;

    @Override
    public boolean isValid(Currency value, ConstraintValidatorContext context) {
        if (Objects.isNull(value) || value.toString().isBlank()) {
            return true;
        }

        try {
            Currency.valueOf(value.getIsoCode());
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
