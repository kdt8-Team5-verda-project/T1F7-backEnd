package com.verda.BE.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommonResponse<T> {
    private int statusCode;
    private Result result;
    private T data;
    private String message;

    public static <T> CommonResponse<T> success() {
        return (CommonResponse<T>) CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .result(Result.SUCCESS)
                .build();
    }

    public static <T> CommonResponse<T> createSuccess() {
        return (CommonResponse<T>) CommonResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .result(Result.SUCCESS)
                .build();
    }

    public static <T> CommonResponse<T> success(T data) {
        return success(data, null);
    }

    public static <T> CommonResponse<T> success(T data, String message) {
        return (CommonResponse<T>) CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .result(Result.SUCCESS)
                .data(data)
                .message(message)
                .build();
    }

    public static CommonResponse fail(ErrorCode errorCode) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .statusCode(errorCode.getStatus())
                .message(errorCode.getMessage())
                .build();
    }

    public static CommonResponse fail(String message, Integer statusCode) {
        return CommonResponse.builder()
                .statusCode(statusCode)
                .result(Result.FAIL)
                .message(message)
                .build();
    }

    public enum Result {
        SUCCESS, FAIL
    }
}
