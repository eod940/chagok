package org.babyshark.chagok.global.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.babyshark.chagok.domain.member.domain.Member;
import org.babyshark.chagok.domain.member.domain.PrincipalDetails;
import org.babyshark.chagok.domain.member.dto.LoginMemberIdDto;
import org.babyshark.chagok.domain.member.dto.TokensAndMemberId;
import org.babyshark.chagok.domain.member.repository.MemberRepository;
import org.babyshark.chagok.global.auth.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final static String CONTENT_TYPE = "application/json";
  private final static String CHARACTER_ENCODING = "UTF-8";

  private final TokenService tokenService;
  private final MemberRepository memberRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    String email = getMemberEmail(authentication);

    Member member = memberRepository.findByEmail(email).orElseThrow(
        () -> new UsernameNotFoundException(email + "는(은) 없는 회원입니다."));
    String memberEmail = member.getEmail();

    String accessToken = tokenService.createAccessToken(memberEmail, member.getMemberId());
    String refreshToken = tokenService.createRefreshToken();

    // response header에 토큰 추가
    tokenService.sendAccessTokenAndRefreshToken(response, accessToken, refreshToken);

    // 로그인한 회원의 RefreshToken 업데이트
    member.updateRefreshToken(refreshToken);
    memberRepository.saveAndFlush(member);

    // 멤버 식별자 보내주기
    LoginMemberIdDto memberData = LoginMemberIdDto.from(member);
    TokensAndMemberId memberIdData = TokensAndMemberId.from(accessToken, refreshToken, memberData);

    String memberJson = new ObjectMapper().writeValueAsString(memberIdData);

    response.setContentType(CONTENT_TYPE);
    response.setCharacterEncoding(CHARACTER_ENCODING);
    response.setStatus(HttpServletResponse.SC_OK);
    response.getWriter().write(memberJson);
  }

  private String getMemberEmail(Authentication authentication) {
    PrincipalDetails memberDetails = (PrincipalDetails) authentication.getPrincipal();
    return memberDetails.getUsername();
  }
}
