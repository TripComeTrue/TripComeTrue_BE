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

    // myPage
    CURRENT_PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "현재 비밀번호가 일치하지 않습니다."),
    NEW_PASSWORD_SAME_AS_OLD(HttpStatus.BAD_REQUEST, "새 비밀번호가 기존 비밀번호와 동일합니다."),
    NEW_PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "변경을 위해 입력하신 비밀번호와 다릅니다."),
    EMAIL_NOT_MATCH(HttpStatus.BAD_REQUEST, "이메일이 일치하지 않습니다"),
    NICKNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,"중복된 닉네임입니다."),

    // CITY
    CITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 도시입니다."),
    EXCHANGE_RATE_API_FAIL(HttpStatus.BAD_REQUEST, "환율 Open API 호출 실패했습니다."),

    // PLACE
    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 여행지입니다."),

    // TRIP_RECORD
    TRIP_RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 여행후기입니다."),
    EXPENSE_RANGE_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 비용범위입니다."),

    //TRIP_RECORD_SCHEDULE_VIDEO
    TRIP_RECORD_SCHEDULE_VIDEO_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 여행스케쥴 쇼츠입니다."),

    // STORE
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "보관 데이터가 존재하지 않습니다."),
    STORE_ALREADY_EXIST(HttpStatus.CONFLICT, "보관 데이터가 이미 존재합니다."),

    // TRIP_PLAN
    TRIP_PLAN_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 여행계획입니다."),

    // Likes
    LIKES_NOT_FOUND(HttpStatus.NOT_FOUND, "좋아요 데이터가 존재하지 않습니다."),
    LIKES_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 좋아요가 등록되어 있습니다."),

    // S3
    FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
    FILE_EMPTY(HttpStatus.BAD_REQUEST, "첨부 파일이 없습니다."),
    FILE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "삭제할 파일이 저장 공간에 존재하지 않습니다."),
    MAX_SIZE_EXCEEDED(HttpStatus.PAYLOAD_TOO_LARGE, "허용 용량을 초과한 파일입니다."),

    // PLACE_REVIEW
    PLACE_REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 여행지 리뷰입니다."),
    PLACE_REVIEW_ALREADY_EXISTS(HttpStatus.CONFLICT, "여행지 리뷰를 중복 작성할 수 없습니다."),
    PLACE_REVIEW_DELETE_ALL_FAILURE(HttpStatus.BAD_REQUEST, "요청한 여행지 리뷰가 모두 존재하지 않아서 삭제에 실패했습니다."),

    // TRIP_RECORD_REVIEW
    TRIP_RECORD_REVIEW_ALREADY_EXISTS(HttpStatus.CONFLICT, "중복해서 여행 후기 별점을 매길 수 없습니다."),
    TRIP_RECORD_REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 여행 후기 리뷰입니다."),
    REGISTERING_DUPLICATE_TRIP_RECORD_REVIEW(HttpStatus.BAD_REQUEST, "본문과 이미지 등록은 한 번만 가능합니다."),
    CAN_NOT_MODIFYING_WITHOUT_CONTENT(HttpStatus.BAD_REQUEST, "리뷰 내용 작성 이전에 본문을 수정할 수 없습니다."),
    TRIP_RECORD_REVIEW_DELETE_ALL_FAILURE(HttpStatus.BAD_REQUEST, "요청한 여행 후기 리뷰가 모두 존재하지 않아서 삭제에 실패했습니다."),

    // 5xx
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
