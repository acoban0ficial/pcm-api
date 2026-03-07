package dev.acobano.pcm.validation.validator;

import dev.acobano.pcm.validation.ValidSpanishTaxId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class ValidSpanishTaxIdValidator implements ConstraintValidator<ValidSpanishTaxId, String> {

    private static final String DNI_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";
    private static final String DNI_PATTERN = "^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]$";
    private static final String NIF_PATTERN = "^[XYZ][0-9]{7}[A-Z]$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value) || value.isBlank()) {
            return true;
        }

        return isValidDNI(value.toUpperCase().trim()) || isValidNIF(value.toUpperCase().trim());
    }

    private boolean isValidDNI(String value) {
        if (!value.matches(DNI_PATTERN)) {
            return false;
        }

        String numberSubstring = value.substring(0, 8);
        int number = Integer.parseInt(numberSubstring);
        char controlLetter = value.charAt(8);
        char expectedLetter = DNI_LETTERS.charAt(number % 23);

        return controlLetter == expectedLetter;
    }

    private boolean isValidNIF(String value) {
        if (!value.matches(NIF_PATTERN)) {
            return false;
        }

        char firstLetter = value.charAt(0);
        String numberPart = value.substring(1, 8);
        char controlLetter = value.charAt(8);

        int number = switch (firstLetter) {
            case 'X' -> Integer.parseInt("0" + numberPart);
            case 'Y' -> Integer.parseInt("1" + numberPart);
            case 'Z' -> Integer.parseInt("2" + numberPart);
            default -> -1;
        };

        if (number == -1) {
            return false;
        }

        char expectedLetter = DNI_LETTERS.charAt(number % 23);

        return controlLetter == expectedLetter;
    }
}

