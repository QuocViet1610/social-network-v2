package org.example.notificationservice.exception;

import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        log.error("Runtime Exception: ", exception);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingGenericException(Exception exception) {
        log.error("Generic Exception: ", exception);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.internalServerError().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
        String enumKey = null;
        if (exception.getFieldError() != null) {
            enumKey = exception.getFieldError().getDefaultMessage();
        }

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map<String, Object> attributes = null;

        try {
            if (enumKey != null) {
                errorCode = ErrorCode.valueOf(enumKey);
            }

            if (!exception.getBindingResult().getAllErrors().isEmpty()) {
                var constraintViolation = exception.getBindingResult()
                        .getAllErrors()
                        .get(0)
                        .unwrap(ConstraintViolation.class);

                attributes = constraintViolation.getConstraintDescriptor().getAttributes();
                log.info("Constraint attributes: {}", attributes);
            }

        } catch (IllegalArgumentException e) {
            log.warn("Invalid error code enum key: {}", enumKey);
        } catch (Exception e) {
            log.error("Error processing validation exception", e);
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(
                Objects.nonNull(attributes)
                        ? mapAttribute(errorCode.getMessage(), attributes)
                        : errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        if (message == null || attributes == null) {
            return message;
        }

        String result = message;

        // Handle min attribute
        if (attributes.containsKey(MIN_ATTRIBUTE)) {
            String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
            result = result.replace("{" + MIN_ATTRIBUTE + "}", minValue);
        }

        // Handle max attribute
        if (attributes.containsKey("max")) {
            String maxValue = String.valueOf(attributes.get("max"));
            result = result.replace("{max}", maxValue);
        }

        // Handle other common attributes
        if (attributes.containsKey("value")) {
            String value = String.valueOf(attributes.get("value"));
            result = result.replace("{value}", value);
        }

        return result;
    }
}