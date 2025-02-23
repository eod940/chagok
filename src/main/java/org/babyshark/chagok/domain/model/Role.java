package org.babyshark.chagok.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role {

  ROLE_MEMBER("회원"),
  ROLE_ADMIN("관리자");

  private final String description;
}
