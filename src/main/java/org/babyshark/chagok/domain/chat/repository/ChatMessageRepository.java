package org.babyshark.chagok.domain.chat.repository;

import org.babyshark.chagok.domain.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

}
