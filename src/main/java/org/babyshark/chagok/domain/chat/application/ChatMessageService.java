package org.babyshark.chagok.domain.chat.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.babyshark.chagok.domain.chat.domain.ChatMessage;
import org.babyshark.chagok.domain.chat.repository.ChatMessageRepository;
import org.babyshark.chagok.domain.clova.dto.MessageOnly;
import org.babyshark.chagok.domain.member.domain.Member;
import org.babyshark.chagok.domain.member.repository.MemberRepository;
import org.babyshark.chagok.global.error.CustomException;
import org.babyshark.chagok.global.model.ErrorCode;
import org.babyshark.chagok.global.model.Mbti;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

  private final ChatMessageRepository chatMessageRepository;
  private final MemberRepository memberRepository;
  private final ObjectMapper objectMapper;

  public void createChat(Long memberId, MessageOnly message, Flux<String> isfpPersona, Mbti mbti) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new CustomException(ErrorCode.INVALID_JWT_I));

    // 질문, AI 답 저장
    isfpPersona.subscribe(response -> {
      String answer = parseMessageFromJson(response);
    });
//    chatMessageRepository.save(ChatMessage.create(mbti, message.message(), answer, member));
  }

  /// AI 응답으로 받은 json 파싱하는 메서드
  private String parseMessageFromJson(String json) {
    try {
      JsonNode root = objectMapper.readTree(json);
      return root.path("result").path("message").path("content").asText();
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to parse JSON content", e);
    }
  }
}
