package dev.acobano.pcm.dto.response;

import java.math.BigDecimal;

public record BudgetResponseDTO(
         BigDecimal price,
         String currencyName,
         String currencyIsoCode,
         String currencySymbol
) {
}
