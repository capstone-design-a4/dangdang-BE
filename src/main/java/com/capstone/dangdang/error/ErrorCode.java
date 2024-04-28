package com.capstone.dangdang.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다"),
    NEGATIVE_GOAL(HttpStatus.BAD_REQUEST, "목표 당 수치는 자연수이어야 합니다"),


    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */

    /* 403 FORBIDDEN : 권한이 없는 사용자 */


    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "이미지가 존재하지 않습니다"),
    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    ALREADY_EXIST_LOGIN_ID(HttpStatus.CONFLICT, "이미 존재하는 ID입니다"),
    ALREADY_RECOMMEND_BOARD(HttpStatus.CONFLICT, "이미 추천한 게시글입니다"),
    ALREADY_BOOKMARKED_MENU(HttpStatus.CONFLICT, "이미 즐겨찾기한 메뉴입니다"),
    /* 500 INTERNAL_SERVER_ERROR : 서버오류 */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류"),
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
