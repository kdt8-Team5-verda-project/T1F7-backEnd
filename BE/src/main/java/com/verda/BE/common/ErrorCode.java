package com.verda.BE.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SYSTEM_EXCEPTION(500, "서버 에러가 발생했습니다."),
    INVALID_INPUT_VALUE(400, "유효하지 않은 입력값입니다."),
    NOT_FOUND_HANDLER(404, "404 NOT FOUND."),
    
    /* 인증 관련 */
    UNAUTHORIZED(401, "권한이 없습니다."),
    HANDLE_ACCESS_DENIED(403, "접근이 거부되었습니다."),

    /* 채팅 관련 */
    NOT_FOUND_CHAT(404, "조회되는 채팅방이없습니다."),
    NOT_FOUND_TARGET(404,"상대방의 정보가 존재하지 않습니다.")
    ;

    private Integer status;
    private String message;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
}
