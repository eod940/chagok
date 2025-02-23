package org.babyshark.chagok.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public record SignupForm(String name, String email, String password, String profileImageUrl, String gender, String age) {

}
