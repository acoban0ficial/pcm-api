package dev.acobano.pcm.dto.response;

import java.time.LocalDate;

public record CustomerResponseDTO(
        String firstName,
        String lastName,
        String taxId,
        String email,
        String phoneNumber,
        LocalDate birthDate,
        boolean isActive,
        AddressResponseDTO address
) {
}
