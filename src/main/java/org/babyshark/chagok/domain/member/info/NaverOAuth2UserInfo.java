package org.babyshark.chagok.domain.member.info;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

  public NaverOAuth2UserInfo(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getEmail() {
    return getValue("naver_account", "email");
  }

  @Override
  public String getName() {
    return "";
  }

  @Override
  public String getProviderId() {
    return "";
  }

  @Override
  public String getProfileUrl() {
    return "";
  }

  @Override
  public String getGender() {
    return "";
  }

  @Override
  public String getAge() {
    return "";
  }

  private String getValue(String... keys) {
    Map<String, Object> current = attributes;
    for (String key : keys) {
      Object value = current.get(key);
      if (value == null) {
        return null;
      }
      if (value instanceof Map) {
        current = (Map<String, Object>) value;
      } else {
        return (String) value;
      }
    }
    return current.toString();
  }
}
