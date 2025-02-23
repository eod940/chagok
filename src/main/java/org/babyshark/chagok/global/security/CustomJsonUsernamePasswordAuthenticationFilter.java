package org.babyshark.chagok.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.babyshark.chagok.domain.member.dto.LoginForm;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class CustomJsonUsernamePasswordAuthenticationFilter extends
    AbstractAuthenticationProcessingFilter {

  private static final String DEFAULT_LOGIN_URI = "/auth/login";
  private static final String HTTP_METHOD = "POST";
  private static final String CONTENT_TYPE = "application/json";
  private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
      new AntPathRequestMatcher(DEFAULT_LOGIN_URI, HTTP_METHOD);

  public CustomJsonUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper) {
    super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);  // 로그인 요청 처리
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException, IOException {
    if (request.getContentType() == null
        || !request.getContentType().equals(CONTENT_TYPE)) {
      throw new AuthenticationServiceException(
          "Content-Type이 지원되지 않는 형식입니다: " + request.getContentType());
    }

    ObjectMapper objectMapper = new ObjectMapper();
    LoginForm loginForm = objectMapper.readValue(request.getInputStream(), LoginForm.class);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        loginForm.getEmail(),
        loginForm.getPassword()
    );
    return getAuthenticationManager().authenticate(authenticationToken);
  }
}
