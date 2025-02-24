package org.babyshark.chagok.domain.member.info;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

  public NaverOAuth2UserInfo(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getEmail() {
    return getValue("response", "email");
  }

  @Override
  public String getName() {
    return getValue("response", "name");
  }

  @Override
  public String getProviderId() {
    return getValue("response", "id");
  }

  @Override
  public String getProfileUrl() {
    return getValue("response", "profile_image");
  }

  @Override
  public String getGender() {
    return getValue("response", "gender");
  }

  @Override
  public String getAge() {
    return getValue("response", "age");
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
