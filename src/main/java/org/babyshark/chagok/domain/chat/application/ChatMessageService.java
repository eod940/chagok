package org.babyshark.chagok.domain.chat.application;

import lombok.RequiredArgsConstructor;
import org.babyshark.chagok.domain.chat.domain.ChatMessage;
import org.babyshark.chagok.domain.chat.repository.ChaMessageRepository;
import org.babyshark.chagok.domain.member.domain.Member;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

  private final ChaMessageRepository chatRepository;

  public void createMessage(long chatId, String message, Member member) {

    chatRepository.save(ChatMessage.create(chatId, message, member));
  }
}
