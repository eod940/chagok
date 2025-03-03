package org.babyshark.chagok.domain.chat.dto;

import org.babyshark.chagok.global.model.Mbti;

public record ChatMessageRequest(String userQuestion, String aiAnswer, Mbti mbti) {

}
