package org.babyshark.chagok.domain.chat.dto;

public record ChatMessageRequest(long chatId, long memberId, String message) {

}
