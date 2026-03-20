package dev.acobano.pcm.exception.handler;

import dev.acobano.pcm.dto.response.error.MainErrorResponseDTO;
import dev.acobano.pcm.dto.response.error.ValidationErrorResponseDTO;
import dev.acobano.pcm.exception.CustomerNotFoundException;
import dev.acobano.pcm.exception.ProjectNotFoundException;
import dev.acobano.pcm.exception.TeamNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MainErrorResponseDTO handleGenericException(
            @NotNull Exception exception,
            @NotNull HttpServletRequest request
    ) {
        log.error(exception.getMessage(), exception);
        return new MainErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(),
                request.getRequestURI(),
                "An unexpected error has been occurred on the server side"
        );
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponseDTO handleValidationException(
            @NotNull HandlerMethodValidationException validationExceptionHandler,
            @NotNull HttpServletRequest request
    ) {
        Map<String, String> errorsMap = validationExceptionHandler.getAllErrors().stream()
                .filter(error -> !Objects.isNull(error.getArguments()) && error.getArguments().length > 0)
                .collect(Collectors.toMap(
                        error -> ((DefaultMessageSourceResolvable) error.getArguments()[0]).getDefaultMessage(),
                        MessageSourceResolvable::getDefaultMessage,
                        (existing, replacement) -> existing // Mantener el primero si hay duplicados
                ));

        log.error(validationExceptionHandler.getMessage());
        return new ValidationErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
                request.getRequestURI(),
                "The following validation error data are been found:",
                errorsMap
        );
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MainErrorResponseDTO manageCustomerNotFoundException(
            @NotNull CustomerNotFoundException exception,
            @NotNull HttpServletRequest request
    ) {
        log.error(exception.getMessage(), exception);
        return new MainErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
                request.getRequestURI(),
                exception.getMessage()
        );
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MainErrorResponseDTO manageProjectNotFoundException(
            @NotNull ProjectNotFoundException exception,
            @NotNull HttpServletRequest request
    ) {
        log.error(exception.getMessage(), exception);
        return new MainErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
                request.getRequestURI(),
                exception.getMessage()
        );
    }

    @ExceptionHandler(TeamNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MainErrorResponseDTO manageTeamNotFoundException(
            @NotNull TeamNotFoundException exception,
            @NotNull HttpServletRequest request
    ) {
        log.error(exception.getMessage(), exception);
        return new MainErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
                request.getRequestURI(),
                exception.getMessage()
        );
    }
}
