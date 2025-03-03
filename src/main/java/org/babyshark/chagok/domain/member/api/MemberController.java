package org.babyshark.chagok.domain.member.api;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.babyshark.chagok.domain.member.application.MemberService;
import org.babyshark.chagok.domain.member.dto.RefreshTokenRequest;
import org.babyshark.chagok.domain.member.dto.TokensAndMemberId;
import org.babyshark.chagok.global.auth.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

  private final MemberService memberService;
  private final TokenService tokenService;

  @PostMapping("/refresh")
  public ResponseEntity<TokensAndMemberId> reissueAccessToken(HttpServletResponse response,
      @RequestBody RefreshTokenRequest refreshToken) {

    TokensAndMemberId memberData = memberService.reissueTwoTokens(response, refreshToken);

    return ResponseEntity.ok(memberData);
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteMember(@RequestHeader("Authorization") String token) {
    Long memberId = tokenService.getMemberId(token);
    memberService.deleteMember(memberId);
    return ResponseEntity.noContent().build();
  }

}
