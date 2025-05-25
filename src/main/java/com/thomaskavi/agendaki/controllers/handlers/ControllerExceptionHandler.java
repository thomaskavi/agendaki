package com.thomaskavi.agendaki.controllers.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.thomaskavi.agendaki.dto.CustomError;
import com.thomaskavi.agendaki.dto.ValidationError;
import com.thomaskavi.agendaki.services.exceptions.DatabaseException;
import com.thomaskavi.agendaki.services.exceptions.ForbiddenException;
import com.thomaskavi.agendaki.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    CustomError err = new CustomError(Instant.now(), status.value(), "Recurso não encontrado", e.getMessage(),
        request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<CustomError> database(DatabaseException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    CustomError err = new CustomError(Instant.now(), status.value(), "Database error", e.getMessage(),
        request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<CustomError> methodArgumentNotValidException(MethodArgumentNotValidException e,
      HttpServletRequest request) {
    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    ValidationError err = new ValidationError(Instant.now(), status.value(), "Dados inválidos", e.getMessage(),
        request.getRequestURI());

    // Field-level validation errors (e.g., @NotNull, @Size, etc)
    for (FieldError f : e.getBindingResult().getFieldErrors()) {
      err.addError(f.getField(), f.getDefaultMessage());
    }

    // Class-level validation errors (e.g., @EndTimeAfterStartTime)
    e.getBindingResult().getGlobalErrors().forEach(globalError -> {
      err.addError(globalError.getObjectName(), globalError.getDefaultMessage());
    });

    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<CustomError> forbidden(ForbiddenException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.FORBIDDEN;
    CustomError err = new CustomError(Instant.now(), status.value(), "Não autorizado", e.getMessage(),
        request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }

}