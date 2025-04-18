package com.riya.bankingtransactionsAPI.config;

import com.riya.bankingtransactionsAPI.dto.ErrorResponse;
import com.riya.bankingtransactionsAPI.exception.AccountNotFoundException;
import com.riya.bankingtransactionsAPI.exception.InsufficientBalanceException;
import com.riya.bankingtransactionsAPI.exception.SameAccountTransferException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AccountNotFoundException.class, InsufficientBalanceException.class, SameAccountTransferException.class})
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleCustomExceptions(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            // ✅ Convert field name to a structured key (e.g., "initialBalanceError" instead of "initialBalance")
            errors.put(fieldName + "Error", errorMessage);
        });

        return ResponseEntity.badRequest().body(errors); // ✅ Properly returning ResponseEntity
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleParsingExceptions(HttpMessageNotReadableException ex) {
        String errorMessage = "Malformed JSON request";
        String field = null;
        if (ex.getCause() instanceof JsonMappingException jsonMappingException) {
            field = jsonMappingException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .findFirst()
                    .orElse(null);
        }

        if (field != null) {
            errorMessage = String.format("Malformed JSON request: Error in field '%s'", field);
        }

        ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
