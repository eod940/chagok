package org.babyshark.chagok.domain.member.application;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babyshark.chagok.domain.member.domain.Member;
import org.babyshark.chagok.domain.member.domain.PrincipalDetails;
import org.babyshark.chagok.domain.member.dto.OAuthAttributes;
import org.babyshark.chagok.domain.member.repository.MemberRepository;
import org.babyshark.chagok.global.auth.TokenService;
import org.babyshark.chagok.global.model.Provider;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthUserCustomService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  private final MemberRepository memberRepository;
  private final TokenService tokenService;

  /**
   * oAuth2User: userRequest에서 OAuth2User를 가져옵니다. - OAuth2User는 OAuth 서비스(ex. 카카오 관련 요소들)를 가지고 있습니다.
   * registrationID: OAuth 서비스 이름을 가져옵니다. (ex. {registrationId: "kakao"}) - 현재는 kakao만 사용해 불필요하지만,
   * 추후 다른 소셜로그인 기능 추가시 필요합니다. (추가시 주석 제거) memberAttributedName: OAuth 로그인시 키 값이 됩니다. (제공 사이트마다 다르므로
   * 따로 변수선언을 해줍니다.) attributes: OAuth 서비스의 유저 정보들을 가져옵니다.
   */
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);
    log.info("OAuth2 진입: loadUser()");
    String registrationId = userRequest.getClientRegistration()
        .getRegistrationId();
    String memberAttributedName = userRequest.getClientRegistration().getProviderDetails()
        .getUserInfoEndpoint().getUserNameAttributeName();
    Map<String, Object> attributes = oAuth2User.getAttributes();

    Provider provider = getProvider(registrationId);

    // OAuth2 로그인을 통해 가져온 OAuth2User의 attribute를 담아주는 of 메소드.
    OAuthAttributes extractAttr = OAuthAttributes.of(memberAttributedName, attributes);
    log.info("OAuth2 User Name: {}", extractAttr.getOauth2UserInfo().getName());
    log.info("OAuth2 User Email: {}", extractAttr.getOauth2UserInfo().getEmail());
    log.info("OAuth2 User Age: {}", extractAttr.getOauth2UserInfo().getAge());
    log.info("OAuth2 User ProfileUrl: {}", extractAttr.getOauth2UserInfo().getProfileUrl());
    log.info("OAuth2 User ProviderId: {}", extractAttr.getOauth2UserInfo().getProviderId());
    Member member = getMember(extractAttr, provider);

    return new PrincipalDetails(member, attributes);
  }

  private Provider getProvider(String registrationId) {
    // 다른 소셜로그인이 있다면 추가해야합니다.
    log.info(registrationId + " 로그인 진행중");

    if (registrationId.equals("kakao")) {
      return Provider.KAKAO;
    }
    if (registrationId.equals("naver")) {
      return Provider.NAVER;
    }

    return null;
  }

  /**
   * provider와 providerId로 유저를 찾습니다. 유저가 없을 경우 회원가입 후 Member를 리턴합니다.
   */
  private Member getMember(OAuthAttributes extractAttribute, Provider provider) {
    String name = extractAttribute.getOauth2UserInfo().getName();
    String profileUrl = extractAttribute.getOauth2UserInfo().getProfileUrl();
    return memberRepository.findByProviderAndProviderId(
            provider, extractAttribute.getOauth2UserInfo().getProviderId())
        .map(member -> member.update(name, profileUrl))
        .orElseGet(() -> updateAndSave(extractAttribute, provider));
  }

  private Member updateAndSave(OAuthAttributes extractAttribute, Provider provider) {
    String refreshToken = tokenService.createRefreshToken();
    Member member = extractAttribute.toEntity(provider, refreshToken,
        extractAttribute.getOauth2UserInfo());
    log.info(provider + " 소셜로그인 회원가입 완료");
    return memberRepository.save(member);
  }
}
