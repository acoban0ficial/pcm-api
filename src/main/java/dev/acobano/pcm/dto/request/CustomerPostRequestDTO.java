package dev.acobano.pcm.dto.request;

import dev.acobano.pcm.model.enumerated.StreetType;
import dev.acobano.pcm.model.enumerated.Country;
import dev.acobano.pcm.validation.UniqueCustomerTaxId;
import dev.acobano.pcm.validation.UniqueCustomerEmail;
import dev.acobano.pcm.validation.ValidSpanishTaxId;
import dev.acobano.pcm.validation.MinimumAge;
import dev.acobano.pcm.validation.ValidStreetType;
import dev.acobano.pcm.validation.ValidCountry;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.acobano.pcm.validation.config.LocalDateDeserializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CustomerPostRequestDTO(
        @NotBlank(message = "First name is required")
        @Size(
                min = 2,
                max = 20,
                message = "First name must be between 2 and 20 characters"
        )
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(
                min = 3,
                max = 50,
                message = "Last name must be between 3 and 50 characters"
        )
        String lastName,

        @NotBlank(message = "Tax ID is required")
        @Size(
                min = 9,
                max = 9,
                message = "Tax ID must be exactly 9 characters long"
        )
        @Pattern(
                regexp = "^([0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]|[XYZ][0-9]{7}[A-Z])$",
                message = "Tax ID must be in the format of a valid Spanish identifier"
        )
        @ValidSpanishTaxId
        @UniqueCustomerTaxId
        String taxId,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @UniqueCustomerEmail
        String email,

        @Size(
                min = 9,
                max = 9,
                message = "Phone number must be exactly 9 digits long"
        )
        @Pattern(
                regexp = "^[96][0-9]{8}$",
                message = "Phone number must be exactly 9 digits starting with 9 or 6"
        )
        String phoneNumber,

        @NotNull(message = "Birth date is required")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @MinimumAge(value = 18)
        LocalDate birthDate,

        @NotBlank(message = "Street name is required")
        @Size(
                min = 3,
                max = 50,
                message = "Street name must be between 3 and 50 characters"
        )
        String streetName,

        @Pattern(
                regexp = "^[0-9]*$",
                message = "Street number must contain only digits"
        )
        String streetNumber,

        @NotBlank(message = "Street type is required")
        @ValidStreetType
        StreetType streetType,

        @NotBlank(message = "City is required")
        @Size(
                min = 2,
                max = 30,
                message = "City must be between 2 and 30 characters"
        )
        String city,

        @NotBlank(message = "Zip code is required")
        @Size(
                min = 5,
                max = 5,
                message = "Zip code must be exactly 5 digits long"
        )
        @Pattern(
                regexp = "^[0-9]{5}$",
                message = "Zip code must be exactly 5 digits long"
        )
        String zipCode,

        @NotBlank(message = "State is required")
        @Size(
                min = 2,
                max = 30,
                message = "State must be between 2 and 30 characters"
        )
        String state,

        @NotBlank(message = "Country is required")
        @ValidCountry
        Country country
) {
}
