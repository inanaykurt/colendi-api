package com.colendi.assignment.colendiapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.metamodel.EmbeddableType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ColendiApiException.class)
    public ResponseEntity<ErrorResponse> globalHandler(ColendiApiException exception) {
        ErrorCodeEnum errorCode = exception.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse( errorCode.getCode(), errorCode.getMessage(), errorCode.getHttpStatus() );
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> globalValidationHandler(MethodArgumentNotValidException exception) {

        List<String> errorMessages = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMessages.add(fieldName.concat(":").concat(errorMessage));
        });


        ErrorResponse errorResponse = new ErrorResponse( ErrorCodeEnum.FIELD_VALIDATION_ERROR.getCode(), String.join(",", errorMessages),  HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

}
