package org.babyshark.chagok.domain.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.babyshark.chagok.domain.member.dto.SignupForm;
import org.babyshark.chagok.domain.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Member implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long memberId;

  private String name;  // 이름
  private String email;  // 이메일
  private String password;  // 비밀번호
  private String profileImageUrl;  // 프로필사진
  private String gender;  // 성별
  private String age;  // 연령대

  @ElementCollection
  @Enumerated(EnumType.STRING)
  private List<Role> role;  // 권한

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.role.stream()
        .map(role -> new SimpleGrantedAuthority(role.name()))
        .collect(Collectors.toList());
  }

  @Override
  public String getUsername() {
    return "";
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
