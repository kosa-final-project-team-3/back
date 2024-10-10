package com.kosa.jungdoin.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionCode {
	/*
	 * 400
	 */
	NOT_MATCH_WRITER(HttpStatus.BAD_REQUEST, "작성자가 아닙니다."),
	ALREADY_LOGOUT_USER(HttpStatus.BAD_REQUEST, "다시 로그인해 주시기 바랍니다."),
	IMAGE_NOT_PROVIDED_OR_EMPTY(HttpStatus.BAD_REQUEST, "이미지 파일이 제공되지 않았거나 비어있습니다."),
	IMAGE_AMOUNT_OVER(HttpStatus.BAD_REQUEST, "이미지 개수가 허용된 크기를 초과했습니다.(최대 5개)"),
	IMAGE_SIZE_OVER(HttpStatus.BAD_REQUEST, "이미지 크기가 허용된 크기를 초과했습니다.(개당 5mb 이하)"),
	INVALID_IMAGE_URL(HttpStatus.BAD_REQUEST, "유효하지 않은 이미지 url입니다."),
	/*
	 * 401
	 */
	NULL_REFRESH(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 존재하지 않습니다."),
	EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
	UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "지원되지 않는 토큰입니다."),
	INVALID_JWT(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
	ILLEGAL_ARGUMENT_JWT(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),
	NEED_EMAIL_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "이메일 인증이 필요합니다."),
	/*
	 * 403
	 */
	UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "잘못된 접근입니다."),
	/*
	 * 404
	 */
	NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
	USER_ROLE_NOT_EXIST(HttpStatus.NOT_FOUND, "권한이 존재하지 않습니다."),
	USER_ROLE_INVALID(HttpStatus.NOT_FOUND, "옳은 권한이 아닙니다."),
	REFRESH_TOKEN_NOT_EXISTS(HttpStatus.NOT_FOUND, "다시 로그인 해주시기 바랍니다."),
	ALREADY_DELETED_ELEMENT(HttpStatus.NOT_FOUND, "이미 삭제된 요소입니다."),
	NOT_FOUND_ARTICLE(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다."),
	NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
	ALREADY_EXIST_USER(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
	REISSUE_FAILED(HttpStatus.CONFLICT, "다시 로그인하여 주시기 바랍니다."),
	NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."),
	ALREADY_EXIST_CATEGORY(HttpStatus.NOT_FOUND, "이미 존재하는 카테고리입니다."),
	NOT_FOUND_ARTICLETYPE(HttpStatus.NOT_FOUND, "존재하지 않는 게시글 종류 입니다."),
	NOT_FOUND_IMAGE(HttpStatus.NOT_FOUND, "해당 이미지를 찾을 수 없습니다."),
	NOT_FOUND_RESOURCE(HttpStatus.NOT_FOUND, "해당 자원을 찾을 수 없습니다."),
	NOT_FOUND_CHATROOM(HttpStatus.NOT_FOUND, "채팅방이 존재하지 않습니다."),
	/*
	 * 415
	 */
	UNSUPPORTED_IMAGE_FORMAT(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 이미지 파일 형식입니다."),
	/*
	 * 500
	 */
	INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 문제가 발생했습니다."),
	FAILED_TO_REMOVE(HttpStatus.INTERNAL_SERVER_ERROR, "삭제에 실패했습니다.");

	private final HttpStatus httpStatus;
	private final String message;
}