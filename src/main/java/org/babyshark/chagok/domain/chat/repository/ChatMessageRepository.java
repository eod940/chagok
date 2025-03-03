package org.babyshark.chagok.domain.chat.repository;

import java.util.List;
import org.babyshark.chagok.domain.chat.domain.ChatMessage;
import org.babyshark.chagok.global.model.Mbti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

  List<ChatMessage> getChatMessagesByMember_MemberIdAndMbti(long memberMemberId, Mbti mbti);
}
