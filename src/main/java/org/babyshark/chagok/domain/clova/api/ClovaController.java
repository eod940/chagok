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

  @PostMapping("/entj")
  public ResponseEntity<Flux<String>> getENTJAnswer(@RequestBody MessageOnly message,
      @RequestHeader("Authorization") String token) {
    tokenService.getMemberId(token);
    return ResponseEntity.ok(clovaService.createENTJPersona(message.message()));
  }

  @PostMapping("/istj")
  public ResponseEntity<Flux<String>> getISTJAnswer(@RequestBody MessageOnly message,
      @RequestHeader("Authorization") String token) {
    tokenService.getMemberId(token);
    return ResponseEntity.ok(clovaService.createISTJPersona(message.message()));
  }

  @PostMapping("/enfp")
  public ResponseEntity<Flux<String>> getENFPAnswer(@RequestBody MessageOnly message,
      @RequestHeader("Authorization") String token) {
    tokenService.getMemberId(token);
    return ResponseEntity.ok(clovaService.createENFPPersona(message.message()));
  }

  @PostMapping("/isfp")
  public ResponseEntity<Flux<String>> getISFPAnswer(@RequestBody MessageOnly message,
      @RequestHeader("Authorization") String token) {
    tokenService.getMemberId(token);
    return ResponseEntity.ok(clovaService.createISFPPersona(message.message()));
  }

  @PostMapping("/assetAssist")
  public ResponseEntity<Flux<String>> getAssetAnswer(@RequestBody MessageOnly message,
      @RequestHeader("Authorization") String token) {
    tokenService.getMemberId(token);
    return ResponseEntity.ok(clovaService.createAssetAssist(message.message()));
  }
}
