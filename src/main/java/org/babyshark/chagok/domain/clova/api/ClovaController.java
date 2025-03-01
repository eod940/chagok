package org.babyshark.chagok.domain.clova.api;

import lombok.RequiredArgsConstructor;
import org.babyshark.chagok.domain.chat.application.ChatMessageService;
import org.babyshark.chagok.domain.clova.application.ClovaService;
import org.babyshark.chagok.domain.clova.dto.MessageOnly;
import org.babyshark.chagok.global.auth.TokenService;
import org.babyshark.chagok.global.model.Mbti;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/clova")
@RequiredArgsConstructor
public class ClovaController {

  private final ClovaService clovaService;
  private final TokenService tokenService;
  private final ChatMessageService chatMessageService;

  @PostMapping("/entj")
  public ResponseEntity<Flux<String>> getENTJAnswer(@RequestBody MessageOnly message) {
    return ResponseEntity.ok(clovaService.createENTJPersona(message.message()));
  }

  @PostMapping("/istj")
  public ResponseEntity<Flux<String>> getISTJAnswer(@RequestBody MessageOnly message) {
    return ResponseEntity.ok(clovaService.createISTJPersona(message.message()));
  }

  @PostMapping("/enfp")
  public ResponseEntity<Flux<String>> getENFPAnswer(@RequestBody MessageOnly message) {
    return ResponseEntity.ok(clovaService.createENFPPersona(message.message()));
  }

  @PostMapping("/isfp")
  public ResponseEntity<Flux<String>> getISFPAnswer(@RequestBody MessageOnly message,
      @RequestHeader("Authorization") String token) {
    // 사용자 인증
    Long memberId = tokenService.getMemberId(token);
    // AI 답변 요청
    Flux<String> isfpPersona = clovaService.createISFPPersona(message.message());
    // 사용자의 메시지와 답변을 DB에 저장
    chatMessageService.createChat(memberId, message, isfpPersona, Mbti.ISFP);
    return ResponseEntity.ok(isfpPersona);
  }

  @PostMapping("/assetAssist")
  public ResponseEntity<Flux<String>> getAssetAnswer(@RequestBody MessageOnly message,
      @RequestHeader("Authorization") String token) {
    tokenService.getMemberId(token);
    return ResponseEntity.ok(clovaService.createAssetAssist(message.message()));
  }
}
