package org.babyshark.chagok.global.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.babyshark.chagok.global.model.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

  private HttpStatus status;
  private int statusCode;
  private String message;
  private ErrorCode errorCode;

  public static ErrorResponse from(ErrorCode errorCode) {
    return ErrorResponse.builder()
        .message(errorCode.getDescription())
        .status(errorCode.getStatusCode())
        .statusCode(errorCode.getStatusCode().value())
        .errorCode(errorCode)
        .build();
  }
}
