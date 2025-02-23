package org.babyshark.chagok.global.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  // 401
  INVALID_CODE("만료된 코드 입니다.", HttpStatus.UNAUTHORIZED),
  INCORRECT_CODE("인증코드가 다릅니다.", HttpStatus.UNAUTHORIZED),
  INVALID_JWT_M("[Malformed] 잘못된 인증 정보입니다.", HttpStatus.UNAUTHORIZED),
  INVALID_JWT_S("[Signature] 잘못된 접근 입니다.", HttpStatus.UNAUTHORIZED),
  INVALID_JWT_E("[Expired] 만료된 접근 입니다.", HttpStatus.UNAUTHORIZED),
  INVALID_JWT_U("[Unsupported] 잘못된 접근 입니다.", HttpStatus.UNAUTHORIZED),
  INVALID_JWT_I("[Illegal] 잘못된 접근 입니다.", HttpStatus.UNAUTHORIZED),
  EXIST_EMAIL("이미 존재하는 이메일입니다.", HttpStatus.UNAUTHORIZED),
  UNVALIDATED_REDIRECT_URI("인증에 실패하였습니다.\n(Unauthorized uri)", HttpStatus.UNAUTHORIZED);

  private final String description;
  private final HttpStatus statusCode;
}
