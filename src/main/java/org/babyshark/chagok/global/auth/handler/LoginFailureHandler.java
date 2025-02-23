package org.babyshark.chagok.global.auth.handler;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
  private final static String CONTENT_TYPE = "text/plain";
  private final static String CHARACTER_ENCODING = "UTF-8";

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException{

    // 소셜로그인시 비밀번호가 다르게 되어있기 떄문에 소셜로그인은 소셜로그인으로 이용해야 합니다.
    if (exception != null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 401 error
      response.setContentType(CONTENT_TYPE);
      response.setCharacterEncoding(CHARACTER_ENCODING);
      response.getWriter().write("로그인을 실패하였습니다." + exception.getMessage());
    }
  }
}
