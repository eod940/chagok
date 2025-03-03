package org.babyshark.chagok.domain.chat.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.babyshark.chagok.domain.chat.domain.ChatMessage;
import org.babyshark.chagok.domain.chat.dto.ChatMessageRequest;
import org.babyshark.chagok.domain.chat.dto.ChatMessageResponse;
import org.babyshark.chagok.domain.chat.dto.MbtiRoom;
import org.babyshark.chagok.domain.chat.repository.ChatMessageRepository;
import org.babyshark.chagok.domain.member.domain.Member;
import org.babyshark.chagok.domain.member.repository.MemberRepository;
import org.babyshark.chagok.global.error.CustomException;
import org.babyshark.chagok.global.model.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

  private final ChatMessageRepository chatMessageRepository;
  private final MemberRepository memberRepository;

  public void createChat(Long memberId, ChatMessageRequest inputMessage) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new CustomException(ErrorCode.INVALID_JWT_I));
    // 질문, AI 답 저장
    chatMessageRepository.save(ChatMessage.create(inputMessage.mbti(), inputMessage.userQuestion(),
        inputMessage.aiAnswer(), member));
  }

  public List<ChatMessageResponse> getChatList(long memberId, MbtiRoom mbti) {
    return chatMessageRepository.getChatMessagesByMember_MemberIdAndMbti(
            memberId, mbti.mbti()).stream()
        .map(ChatMessageResponse::of)
        .toList();
  }
}
