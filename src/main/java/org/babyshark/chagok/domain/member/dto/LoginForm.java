package org.babyshark.chagok.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginForm {
  private String email;
  private String password;
}
