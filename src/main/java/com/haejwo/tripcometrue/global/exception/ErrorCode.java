package com.haejwo.tripcometrue.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author liyusang1
 * @implNote 에러 메시지 코드들을 한번에 관리
 */

@Getter
public enum ErrorCode {

    // Permission
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "해당 작업을 수행할 권한이 업습니다."),

    // EMAIL
    EMAIL_SENDING_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송에 실패했습니다."),
    EMAIL_TEMPLATE_LOAD_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 템플릿 로드에 실패했습니다."),
    EMAIL_VERIFY_FAILURE(HttpStatus.BAD_REQUEST, "인증번호가 일치하지 않습니다."),

    // USER
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."),
    USER_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    USER_INVALID_ACCESS(HttpStatus.BAD_REQUEST, "잘못된 유저의 접근입니다."),

    // AUTH
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다."),

    // CITY
    CITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 도시입니다."),
    EXCHANGE_RATE_API_FAIL(HttpStatus.BAD_REQUEST, "환율 Open API 호출 실패했습니다."),

    // PLACE
    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 여행지입니다."),

    // TRIP_RECORD
    TRIP_RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 여행후기입니다."),
    EXPENSE_RANGE_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 비용범위입니다."),

    // Likes
    LIKES_NOT_FOUND(HttpStatus.NOT_FOUND, "좋아요 데이터가 존재하지 않습니다."),
    LIKES_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 좋아요가 등록되어 있습니다."),

    // S3
    FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
    FILE_EMPTY(HttpStatus.BAD_REQUEST, "첨부 파일이 없습니다."),
    FILE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "삭제할 파일이 저장 공간에 존재하지 않습니다."),
    MAX_SIZE_EXCEEDED(HttpStatus.PAYLOAD_TOO_LARGE, "허용 용량을 초과한 파일입니다."),

    // 5xx
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
