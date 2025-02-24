package org.babyshark.chagok.domain.member.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@AllArgsConstructor
@RequiredArgsConstructor
public class PrincipalDetails implements OAuth2User, UserDetails {

  private Member member;
  private Map<String, Object> attributes;
  private Collection<? extends GrantedAuthority> authorities;

  public PrincipalDetails(Member member) {
    this.member = member;
//    this.authorities = member.getAuthorities();
  }

  public PrincipalDetails(Member member, Map<String, Object> attributes) {
    this.member = member;
    this.attributes = attributes;
//    this.authorities = member.getAuthorities();
  }

  @Override
  public Map<String, Object> getAttributes() {
    return this.attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public String getPassword() {
    return member.getPassword();
  }

  @Override
  public String getUsername() {
    return member.getEmail();
  }

  @Override
  public String getName() {
    return member.getEmail();
  }
}
