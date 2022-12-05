package io.dsub.sweatboys.opendiscogs.api.exception;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class, ItemNotFoundException.class, ValidationException.class})
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.badRequest().body(e.getLocalizedMessage());
    }
}
