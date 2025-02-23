package org.babyshark.chagok.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import org.babyshark.chagok.domain.member.domain.Member;

@Getter
@Builder
public class LoginMemberIdDto {

  private long memberId;
  private String memberName;
  private String memberEmail;
  private String profile;

  public static LoginMemberIdDto from(Member member) {
    return LoginMemberIdDto.builder()
        .memberId(member.getMemberId())
        .memberName(member.getName())
        .memberEmail(member.getEmail())
        .profile(member.getProfileImageUrl())
        .build();
  }
}
