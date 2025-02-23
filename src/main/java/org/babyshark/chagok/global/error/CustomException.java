package org.babyshark.chagok.global.error;

import lombok.Getter;
import org.babyshark.chagok.global.model.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;
  private final String message;
  private final HttpStatus httpStatus;

  public CustomException(ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.message = errorCode.getDescription();
    this.httpStatus = errorCode.getStatusCode();
  }
}
