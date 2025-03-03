package org.babyshark.chagok.domain.member.application;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babyshark.chagok.domain.member.domain.Member;
import org.babyshark.chagok.domain.member.domain.PrincipalDetails;
import org.babyshark.chagok.domain.member.dto.LoginMemberIdDto;
import org.babyshark.chagok.domain.member.dto.RefreshTokenRequest;
import org.babyshark.chagok.domain.member.dto.SignupForm;
import org.babyshark.chagok.domain.member.dto.TokensAndMemberId;
import org.babyshark.chagok.domain.member.repository.MemberRepository;
import org.babyshark.chagok.global.auth.TokenService;
import org.babyshark.chagok.global.error.CustomException;
import org.babyshark.chagok.global.model.ErrorCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

  public TokensAndMemberId reissueTwoTokens(HttpServletResponse response,
      RefreshTokenRequest refreshTokenRequest) {

    String refreshToken = refreshTokenRequest.refreshToken();

    Optional<Member> optionalMember = memberRepository.findByRefreshToken(refreshToken);

    if (optionalMember.isEmpty()) {
      log.warn("MemberService reissue tokens: Empty Member!");
      throw new CustomException(ErrorCode.INVALID_JWT_M);
//      return null;
    }
    Member member = optionalMember.get();
    String newRefreshToken = reissueRefreshToken(member);
    log.info("MemberService reissue tokens: newRefreshToken: {}", newRefreshToken);
    String newAccessToken = tokenService.createAccessToken(member.getEmail(), member.getMemberId());
    log.info("MemberService reissue tokens: newAccessToken: {}", newAccessToken);

    tokenService.sendAccessToken(response, newAccessToken);
    tokenService.sendAccessTokenAndRefreshToken(response, newAccessToken, newRefreshToken);

    LoginMemberIdDto loginMember = LoginMemberIdDto.from(member);
    return TokensAndMemberId.from(newAccessToken, newRefreshToken, loginMember);
  }

  private String reissueRefreshToken(Member member) {
    String newRefreshToken = tokenService.createRefreshToken();
    member.updateRefreshToken(newRefreshToken);
    memberRepository.saveAndFlush(member);
    return newRefreshToken;
  }

  @Override
  public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
    Member member = this.memberRepository.findByEmail(userEmail)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userEmail));

    return new PrincipalDetails(member);
  }

  public Member registerMember(SignupForm signup) {
    boolean isEmailRegistered = this.memberRepository.existsByEmail(signup.email());
    if (isEmailRegistered) {
      throw new CustomException(ErrorCode.EXIST_EMAIL);
    }
    // 비밀번호 인코딩하여 저장
    return memberRepository.save(Member.from(signup, passwordEncoder.encode(signup.password())));
  }

  public Member getMember(long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new CustomException(ErrorCode.INVALID_JWT_M));
  }

  @Transactional
  public void deleteMember(long memberId) {
    memberRepository.deleteById(memberId);
  }
}
