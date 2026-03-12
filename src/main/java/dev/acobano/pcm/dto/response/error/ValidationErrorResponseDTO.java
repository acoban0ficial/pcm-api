package dev.acobano.pcm.dto.response.error;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponseDTO(
        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = "dd/MM/YYYY HH:mm:ss"
        )
        LocalDateTime timestamp,
        Integer httpCode,
        String httpStatus,
        String path,
        String message,
        Map<String, String> fieldErrors
        ) {
}
