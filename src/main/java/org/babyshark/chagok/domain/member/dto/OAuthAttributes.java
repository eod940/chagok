package org.babyshark.chagok.domain.member.dto;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import org.babyshark.chagok.domain.member.domain.Member;
import org.babyshark.chagok.domain.member.info.NaverOAuth2UserInfo;
import org.babyshark.chagok.domain.member.info.OAuth2UserInfo;
import org.babyshark.chagok.global.model.Provider;

@Getter
public class OAuthAttributes {

  private final String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
  private final OAuth2UserInfo oauth2UserInfo; // 소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)

  @Builder
  public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
    this.nameAttributeKey = nameAttributeKey;
    this.oauth2UserInfo = oauth2UserInfo;
  }

  public static OAuthAttributes of(String userNameAttributeName, Map<String, Object> attributes) {
    return OAuthAttributes.builder()
        .nameAttributeKey(userNameAttributeName)
        .oauth2UserInfo(new NaverOAuth2UserInfo(attributes))
        .build();
  }

  public Member toEntity(Provider provider, String refreshToken, OAuth2UserInfo oauth2UserInfo) {
    return Member.builder()
        .name(oauth2UserInfo.getName())
        .email(oauth2UserInfo.getEmail())
        .age(oauth2UserInfo.getAge())
        .password(provider.getDescription() + "_" + oauth2UserInfo.getProviderId())
        .gender(oauth2UserInfo.getGender())
        .profileImageUrl(oauth2UserInfo.getProfileUrl())
        .providerId(oauth2UserInfo.getProviderId())
        .refreshToken(refreshToken)
        .provider(provider)
        .build();
  }
}
