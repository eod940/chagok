package org.babyshark.chagok.global.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import javax.crypto.SecretKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babyshark.chagok.global.error.CustomException;
import org.babyshark.chagok.global.model.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
@Getter
public class TokenService {

  private static final Long ACCESS_TOKEN_VALID_TIME = 1000L * 60L * 60L;  // 1시간
  private static final Long REFRESH_TOKEN_VALID_TIME = 1000L * 60L * 60L * 24L * 7L;  // 7일
  private static final String ACCESS_SUBJECT = "Access";
  private static final String REFRESH_SUBJECT = "Refresh";
  private static final String CLAIM_EMAIL = "email";
  private static final String CLAIM_MEMBER_ID = "memberId";
  private static final String BEARER = "Bearer ";

  @Value("${spring.jwt.secret-key}")
  private String secretKey;

  private final String accessHeader = "Authorization";
  private final String refreshHeader = "RefreshToken";

  private SecretKey key;

  @PostConstruct
  protected void init() {
    byte[] keyBytes = Base64.getDecoder().decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public String createAccessToken(String email, long memberId) {

    Claims claims = (Claims) Jwts.claims().subject(ACCESS_SUBJECT);
    claims.put(CLAIM_EMAIL, email);
    claims.put(CLAIM_MEMBER_ID, memberId);

    Date now = new Date();
    Date expiredDate = new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME);

    return Jwts.builder()
        .claims(claims)
        .issuedAt(now)
        .expiration(expiredDate)
        .signWith(key)
        .compact();
  }

  public String createRefreshToken() {
    Claims claims = (Claims) Jwts.claims().subject(REFRESH_SUBJECT);

    Date now = new Date();
    Date expiredDate = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME);

    return Jwts.builder()
        .claims(claims)
        .issuedAt(now)
        .expiration(expiredDate)
        .signWith(key)  // HMAC 기반 인증
        .compact();
  }

  public String getMemberEmail(String accessToken) {
    log.info("getMemberEmail - Authorization: {}", accessToken);
    String trimmedToken = resolveTokenOnlyAtTokenService(accessToken);
    return this.parseClaims(trimmedToken).get(CLAIM_EMAIL).toString();
  }

  public Long getMemberId(String token) {
    log.info("getMemberId - Authorization: {}", token);
    String trimmedToken = resolveTokenOnlyAtTokenService(token);
    return Long.parseLong(this.parseClaims(trimmedToken).get(CLAIM_MEMBER_ID).toString());
  }

  public String resolveTokenOnlyAtTokenService(String token) {
    if (ObjectUtils.isEmpty(token)) {
      return null;
    }
    if (!token.startsWith(BEARER)) {
      return null;
    }
    return token.replace(BEARER, "");
  }

  private Claims parseClaims(String trimmedToken) {
    try {

      return Jwts.parser().verifyWith(key).build().parseSignedClaims(trimmedToken).getPayload();

    } catch (MalformedJwtException e) {
      log.error("parseClaims: [MalFormed] 잘못된 인증 정보");
      throw new CustomException(ErrorCode.INVALID_JWT_M);
    } catch (SignatureException e) {
      log.error("parseClaims: [Signature] 잘못된 접근");
      throw new CustomException(ErrorCode.INVALID_JWT_S);
    } catch (ExpiredJwtException e) {
      log.error("parseClaims: [Expired] 만료된 접근");
      throw new CustomException(ErrorCode.INVALID_JWT_E);
    } catch (UnsupportedJwtException e) {
      log.error("parseClaims: [Unsupported] 잘못된 접근");
      throw new CustomException(ErrorCode.INVALID_JWT_U);
    } catch (IllegalArgumentException e) {
      log.error("parseClaims: [Illegal] 잘못된 인증 정보");
      throw new CustomException(ErrorCode.INVALID_JWT_I);
    }
  }

  public boolean isTokenExpired(String trimmedToken) {
    Claims claims = parseClaims(trimmedToken);
    Date expiration = claims.getExpiration();
    return expiration.before(new Date());
  }

  public String parseAccessToken(HttpServletRequest request) {
    String fullToken = request.getHeader(accessHeader);
    return resolveTokenOnlyAtTokenService(fullToken);
  }

  public Optional<String> parseRefreshToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(refreshHeader))
        .filter(refresh -> refresh.startsWith(BEARER))
        .map(refresh -> refresh.replace(BEARER, ""));
  }

  // 재발급된 Access 토큰을 헤더에 넣어서 보냅니다.
  public void sendAccessToken(HttpServletResponse response, String accessToken) {
    response.setStatus(HttpServletResponse.SC_OK);
    response.setHeader(accessHeader, accessToken);
  }

  // Access 토큰, Refresh 토큰을 모두 헤더에 넣어서 보냅니다.
  public void sendAccessTokenAndRefreshToken(HttpServletResponse response,
      String accessToken, String refreshToken) {
    response.setStatus(HttpServletResponse.SC_OK);

    setAccessTokenToHeader(response, BEARER + accessToken);
    setRefreshTokenToHeader(response, BEARER + refreshToken);
    setRefreshTokenToCookie(response, refreshToken);
  }

  public void setAccessTokenToHeader(HttpServletResponse response, String accessToken) {
    response.setHeader(accessHeader, accessToken);
  }

  public void setRefreshTokenToHeader(HttpServletResponse response, String refreshToken) {
    response.setHeader(refreshHeader, refreshToken);
  }

  public void setRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
    Cookie cookie = new Cookie(refreshHeader, refreshToken);
    cookie.setMaxAge(Math.toIntExact(REFRESH_TOKEN_VALID_TIME));  // 쿠키 만료
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    response.addCookie(cookie);
  }
}
