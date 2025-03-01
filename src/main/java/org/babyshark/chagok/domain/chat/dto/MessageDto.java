package org.babyshark.chagok.domain.chat.dto;

import java.time.LocalDateTime;
import org.babyshark.chagok.domain.member.dto.LoginMemberIdDto;

public record MessageDto(LoginMemberIdDto memberIdDto, String message, LocalDateTime created_at) {

}
