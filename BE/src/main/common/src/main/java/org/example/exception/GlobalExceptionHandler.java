package org.example.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity apiException(ApiException apiException) {
        return handleExceptionInternal(apiException);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity apiException(Exception e) {
        return handleExceptionInternal(e.getMessage());
    }

    private ResponseEntity<Object> handleExceptionInternal(String cause) {
        ErrorCode errorCode = ErrorCode.SYSTEM_EXCEPTION;
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(buildResponse(errorCode));
    }

    private ResponseEntity<Object> handleExceptionInternal(ApiException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(buildResponse(errorCode));
    }
    private CommonResponse buildResponse(ErrorCode errorCode) {
        return CommonResponse.fail(errorCode);
    }
}
