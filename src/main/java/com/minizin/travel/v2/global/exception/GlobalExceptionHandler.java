package com.minizin.travel.v2.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(e.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<ValidationErrorResponse> errors = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.add(
                    new ValidationErrorResponse(
                            fieldError.getField(), fieldError.getDefaultMessage()
                    )
            );
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
