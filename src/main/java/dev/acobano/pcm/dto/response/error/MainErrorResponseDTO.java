package dev.acobano.pcm.dto.response.error;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record MainErrorResponseDTO(
        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = "dd/MM/YYYY HH:mm:ss"
        )
        LocalDateTime timestamp,
        Integer httpCode,
        String httpStatus,
        String path,
        String message
) {
}
