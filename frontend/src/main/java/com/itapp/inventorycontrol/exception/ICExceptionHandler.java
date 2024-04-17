package com.itapp.inventorycontrol.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@AllArgsConstructor
@ControllerAdvice
public class ICExceptionHandler extends ResponseEntityExceptionHandler {
    private ProblemDetail getProblemDetailForErrorType(ICErrorType errorType) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                errorType.getStatus(),
                errorType.getDescription());
        problemDetail.setTitle(errorType.toString());

        return problemDetail;
    }

    @ExceptionHandler(ICException.class)
    ProblemDetail handleRebalanceException(ICException e) {
        return getProblemDetailForErrorType(e.getErrorType());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ICErrorType errorType = ICErrorType.IC_998;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                errorType.getStatus(),
                errorType.getDescription() + errors);
        problemDetail.setTitle(errorType.toString());

        return ResponseEntity.status(errorType.getStatus()).body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handleRuntimeException(Exception e) {
        return getProblemDetailForErrorType(ICErrorType.IC_999);
    }
}
