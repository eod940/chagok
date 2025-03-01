package org.babyshark.chagok.domain.chat.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.minidev.json.annotate.JsonIgnore;
import org.babyshark.chagok.domain.member.domain.Member;
import org.babyshark.chagok.global.auditing.BaseEntity;

@Entity
@SuperBuilder
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class ChatMessage extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long chatMessageId;

  private long chatRoomId;  // entj, istj, enfp, isfp (1, 2, 3, 4 순)
  private String message;  // 메시지

  @JsonIgnore
  @ManyToOne
  private Member member;  // 사용자

  // 생성 메서드
  public static ChatMessage create(long chatRoomId, String message, Member member) {
    return ChatMessage.builder()
        .chatRoomId(chatRoomId)
        .message(message)
        .member(member)
        .build();
  }
}
