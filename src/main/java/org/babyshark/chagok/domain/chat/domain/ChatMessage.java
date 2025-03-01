package org.babyshark.chagok.domain.chat.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.babyshark.chagok.domain.member.domain.Member;
import org.babyshark.chagok.global.auditing.BaseEntity;
import org.babyshark.chagok.global.model.Mbti;

@Entity
@SuperBuilder
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class ChatMessage extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long chatMessageId;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @Enumerated(EnumType.STRING)
  private Mbti mbti;  // AI 종류

  private String question;  // 사용자 질문
  private String answer;  // AI 답

  public static ChatMessage create(Mbti mbti, String question, String answer, Member member) {
    return ChatMessage.builder()
        .mbti(mbti)
        .question(question)
        .answer(answer)
        .member(member)
        .build();
  }
}
