package org.babyshark.chagok.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FixedExpensesCategory {

  PHONE("통신비"),
  EDUCATION("학원비, 참고서"),
  SUBSCRIPTION("정기 구독"),
  OTHER("기타 고정 지출");

  private final String description;
}
