package org.babyshark.chagok.global.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = Shape.STRING)
public enum Mbti {

  ENTJ,
  ISTJ,
  ENFP,
  ISFP
}
