package org.babyshark.chagok.global.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Provider {

  NORMAL("일반"),
  NAVER("네이버"),
  KAKAO("카카오");

  private final String description;
}
