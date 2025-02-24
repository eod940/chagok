package org.babyshark.chagok.domain.member.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.babyshark.chagok.domain.member.dto.SignupForm;
import org.babyshark.chagok.global.model.Provider;
import org.babyshark.chagok.global.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long memberId;

  private String name;  // 이름
  private String email;  // 이메일
  private String password;  // 비밀번호
  private String profileImageUrl;  // 프로필사진
  private String gender;  // 성별
  private String age;  // 연령대
  private String refreshToken;  // 리프레시 토큰(TokenService)

  @ElementCollection
  @Enumerated(EnumType.STRING)
  private List<Role> role;  // 권한

  @Enumerated(EnumType.STRING)
  private Provider provider;  // 로그인 주체
  private String providerId;  // 소셜로그인시 Id

  public void updateRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public Member update(String name, String profileUrl) {
    this.name = name;
    this.profileImageUrl = profileUrl;

    return this;
  }

  public static Member from(SignupForm signup, String password) {
    return Member.builder()
        .name(signup.name())
        .email(signup.email())
        .profileImageUrl(signup.profileImageUrl())
        .gender(signup.gender())
        .age(signup.age())
        .password(password).build();
  }
}
