package dev.acobano.pcm.dto.response;

public record AddressResponseDTO(
        String streetName,
        String streetType,
        String city,
        String zipCode,
        String state,
        String country
) {
}
