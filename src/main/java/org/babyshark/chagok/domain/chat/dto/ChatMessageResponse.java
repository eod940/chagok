package org.babyshark.chagok.domain.chat.dto;

import java.time.LocalDateTime;
import org.babyshark.chagok.domain.chat.domain.ChatMessage;
import org.babyshark.chagok.global.model.Mbti;

public record ChatMessageResponse(String userQuestion, String aiAnswer, Mbti mbti, LocalDateTime created_at) {

  public static ChatMessageResponse of(ChatMessage request) {
    return new ChatMessageResponse(request.getQuestion(), request.getAnswer(), request.getMbti(), request.getCreatedDateTime());
  }
}
