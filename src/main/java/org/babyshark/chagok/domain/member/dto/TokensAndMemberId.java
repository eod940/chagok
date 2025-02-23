package org.babyshark.chagok.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokensAndMemberId {

  private String accessToken;
  private String refreshToken;
  private LoginMemberIdDto loginMemberIdDto;

  public static TokensAndMemberId from(String accessToken, String refreshToken, LoginMemberIdDto idDto) {
    return TokensAndMemberId.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .loginMemberIdDto(idDto)
        .build();
  }
}
