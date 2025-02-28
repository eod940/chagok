package org.babyshark.chagok.domain.chat.api;


import lombok.RequiredArgsConstructor;
import org.babyshark.chagok.domain.chat.application.ChatMessageService;
import org.babyshark.chagok.domain.chat.dto.ChatMessageRequest;
import org.babyshark.chagok.domain.chat.dto.MessageDto;
import org.babyshark.chagok.domain.member.application.MemberService;
import org.babyshark.chagok.domain.member.domain.Member;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
//
//  private final ChatMessageService chatMessageService;
//  private final MemberService memberService;
//  private final SimpMessageSendingOperations simpMessageSendingOperations;
//
//
//  @MessageMapping("/message/{chatRoomId}")
//  public void sendMessage(@Payload ChatMessageRequest message,
//      @DestinationVariable("chatRoomId") long chatRoomId) {
//
//    // 멤버 확인
//    Member member = memberService.getMember(message.memberId());
//
//    // 메시지 저장
//    MessageDto chatMessage = chatMessageService.createMessage(message.chatId(), message.message(),
//        member);
//    simpMessageSendingOperations.convertAndSend("/sub/chat/room" + chatRoomId, chatMessage);
//  }
}
