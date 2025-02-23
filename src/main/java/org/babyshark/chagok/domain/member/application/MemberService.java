package org.babyshark.chagok.domain.member.application;

import lombok.AllArgsConstructor;
import org.babyshark.chagok.domain.member.domain.Member;
import org.babyshark.chagok.domain.member.domain.PrincipalDetails;
import org.babyshark.chagok.domain.member.dto.SignupForm;
import org.babyshark.chagok.domain.member.repository.MemberRepository;
import org.babyshark.chagok.global.error.CustomException;
import org.babyshark.chagok.global.model.ErrorCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

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
}
