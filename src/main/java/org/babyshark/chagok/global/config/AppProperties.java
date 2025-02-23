package org.babyshark.chagok.global.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

  private final OAuth2 oAuth2 = new OAuth2();

  public static class OAuth2 {
    private final List<String> authorizedRedirectUris = new ArrayList<>();
    public List<String> getAuthorizedRedirectUris() {
      return authorizedRedirectUris;
    }
  }
}
