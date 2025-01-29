package com.qikserve.checkout_api.exception.handler;

import com.qikserve.checkout_api.exception.ExceptionResponse;
import feign.FeignException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
import java.util.SequencedCollection;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request
    ) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ExceptionResponse> handleFeignException(
            FeignException ex, WebRequest request
    ) {
        String message;

        try {
            SequencedCollection<String> classNameParts = List.of(ex.getClass().getName().split("\\$"));

            String statusName = classNameParts.getLast();

            String normalizedStatusName = statusName.replaceAll("([A-Z])", "_$1").toUpperCase().substring(1);

            HttpStatus status = HttpStatus.valueOf(normalizedStatusName);

            message = ex.getMessage();

            ExceptionResponse response = new ExceptionResponse(
                    new Date(),
                    message,
                    request.getDescription(false)
            );

            return ResponseEntity.status(status).body(response);

        } catch (IllegalArgumentException e) {
            message = messageSource.getMessage(
                    "error.feign.unavailable",
                    null,
                    LocaleContextHolder.getLocale()
            );

            ExceptionResponse response = new ExceptionResponse(
                    new Date(),
                    message,
                    request.getDescription(false)
            );

            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);

        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, WebRequest request
    ) {
        String message = messageSource.getMessage(
                "error.data.invalid_structure",
                null,
                LocaleContextHolder.getLocale()
        );

        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                message,
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(
            RuntimeException ex, WebRequest request
    ) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(
            Exception ex, WebRequest request
    ) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
