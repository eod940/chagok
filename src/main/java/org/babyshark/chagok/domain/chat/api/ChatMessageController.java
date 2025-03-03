package org.babyshark.chagok.domain.chat.api;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.babyshark.chagok.domain.chat.application.ChatMessageService;
import org.babyshark.chagok.domain.chat.dto.ChatMessageRequest;
import org.babyshark.chagok.domain.chat.dto.ChatMessageResponse;
import org.babyshark.chagok.domain.chat.dto.MbtiRoom;
import org.babyshark.chagok.global.auth.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatMessageController {

  private final ChatMessageService chatMessageService;
  private final TokenService tokenService;

  @PostMapping
  public ResponseEntity<String> saveMessage(@RequestHeader("Authorization") String token,
      @RequestBody ChatMessageRequest request) {
    Long memberId = tokenService.getMemberId(token);
    chatMessageService.createChat(memberId, request);
    return ResponseEntity.ok("ok");
  }

  @GetMapping
  public ResponseEntity<List<ChatMessageResponse>> getChatList(@RequestHeader("Authorization") String token,
      @RequestBody MbtiRoom request) {
    Long memberId = tokenService.getMemberId(token);
    return ResponseEntity.ok(chatMessageService.getChatList(memberId, request));
  }

}
