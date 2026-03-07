package dev.acobano.pcm.dto.request;

import dev.acobano.pcm.model.enumerated.StreetType;
import dev.acobano.pcm.model.enumerated.Country;
import dev.acobano.pcm.validation.UniqueCustomerEmail;
import dev.acobano.pcm.validation.UniqueCustomerTaxId;
import dev.acobano.pcm.validation.ValidSpanishTaxId;
import dev.acobano.pcm.validation.MinimumAge;
import dev.acobano.pcm.validation.ValidStreetType;
import dev.acobano.pcm.validation.ValidCountry;
import dev.acobano.pcm.validation.config.LocalDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CustomerPutRequestDTO(
        @Nullable
        @Size(
                min = 2,
                max = 20,
                message = "First name must be between 2 and 20 characters"
        )
        String firstName,

        @Nullable
        @Size(
                min = 3,
                max = 50,
                message = "Last name must be between 3 and 50 characters"
        )
        String lastName,

        @Nullable
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

        @Nullable
        @Email(message = "Email must be valid")
        @UniqueCustomerEmail
        String email,

        @Nullable
        @Size(
                min = 9,
                max = 9,
                message = "Phone number must be exactly nine digits long"
        )
        @Pattern(
                regexp = "^[96][0-9]{8}$",
                message = "Phone number must be exactly nine digits starting with 9 or 6"
        )
        String phoneNumber,

        @Nullable
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @MinimumAge
        LocalDate birthDate,

        @Nullable
        @Size(
                min = 2,
                max = 50,
                message = "Street name must be between 2 and 50 characters"
        )
        String streetName,

        @Nullable
        @Pattern(
                regexp = "^[0-9]*$",
                message = "Street number must contain only digits"
        )
        String streetNumber,

        @Nullable
        @ValidStreetType
        StreetType streetType,

        @Nullable
        @Size(
                min = 2,
                max = 50,
                message = "City must be between 2 and 50 characters"
        )
        String city,

        @Nullable
        @Pattern(
                regexp = "^[0-9]{5}$",
                message = "Zip code must be exactly 5 digits"
        )
        String zipCode,

        @Nullable
        @Size(
                min = 2,
                max = 30,
                message = "State must be between 2 and 30 characters"
        )
        String state,

        @Nullable
        @ValidCountry
        Country country
) {
}
