package org.babyshark.chagok.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class RefreshTokenResponse {
  private String refreshToken;
}
