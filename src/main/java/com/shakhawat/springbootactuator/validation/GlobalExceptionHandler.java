package com.shakhawat.springbootactuator.validation;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause().getMessage();

        if (message.contains("Duplicate entry")) {
            String duplicateValue = extractDuplicateValue(message);
            String constraintName = extractConstraintName(message);
            String fieldName = getFieldNameFromConstraint(constraintName);
            return new ResponseEntity<>("Duplicate value '" + duplicateValue + "' for field: " + fieldName, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Data integrity violation: " + message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String fieldName = ex.getName();
        String providedValue = ex.getValue() != null ? ex.getValue().toString() : "null";
        String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown";

        String errorMessage = String.format("Failed to convert value '%s' to required type '%s' for parameter '%s'.", providedValue, requiredType, fieldName);

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private String extractConstraintName(String message) {
        if (message.contains("for key")) {
            String[] parts = message.split("for key");
            if (parts.length > 1) {
                return parts[1].trim().replace("'", "");
            }
        }
        return null;
    }

    private String getFieldNameFromConstraint(String constraintName) {
        if (constraintName == null) {
            return "unknown field";
        }

        // Assuming constraint names are of the form table_field_type, such as "table_field_unique"
        String[] parts = constraintName.split("_");
        if (parts.length >= 2) {
            return parts[1];
        }

        return "unknown field";
    }

    private String extractDuplicateValue(String message) {
        Pattern pattern = Pattern.compile("Duplicate entry '(.+?)'");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "unknown value";
    }

}
