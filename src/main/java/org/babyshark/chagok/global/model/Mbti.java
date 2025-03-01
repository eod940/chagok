package org.babyshark.chagok.global.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Mbti {

  ENTJ,
  ISTJ,
  ENFP,
  ISFP
}
