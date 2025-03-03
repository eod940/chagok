package org.babyshark.chagok.domain.member.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.babyshark.chagok.domain.asset.domain.Asset;
import org.babyshark.chagok.domain.chat.domain.ChatMessage;
import org.babyshark.chagok.domain.member.dto.SignupForm;
import org.babyshark.chagok.global.auditing.BaseEntity;
import org.babyshark.chagok.global.model.Provider;
import org.babyshark.chagok.global.model.Role;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Member extends BaseEntity {

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

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Asset> assets = new ArrayList<>();

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ChatMessage> chatMessages = new ArrayList<>();

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
