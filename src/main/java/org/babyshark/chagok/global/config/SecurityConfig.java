package org.babyshark.chagok.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.babyshark.chagok.domain.member.application.MemberService;
import org.babyshark.chagok.domain.member.application.OAuthUserCustomService;
import org.babyshark.chagok.domain.member.repository.MemberRepository;
import org.babyshark.chagok.global.auth.HttpCookieOAuth2AuthorizationRequestRepository;
import org.babyshark.chagok.global.auth.TokenService;
import org.babyshark.chagok.global.auth.handler.LoginFailureHandler;
import org.babyshark.chagok.global.auth.handler.LoginSuccessHandler;
import org.babyshark.chagok.global.auth.handler.OAuth2LoginFailureHandler;
import org.babyshark.chagok.global.auth.handler.OAuth2LoginSuccessHandler;
import org.babyshark.chagok.global.security.CustomJsonUsernamePasswordAuthenticationFilter;
import org.babyshark.chagok.global.security.JwtAuthenticationProcessingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final AppConfig appConfig;
  private final AppProperties appProperties;
  private final TokenService tokenService;
  private final MemberRepository memberRepository;
  private final ObjectMapper objectMapper;
  private final MemberService customUserDetailService;
  private final OAuthUserCustomService oAuthUserCustomService;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors.configurationSource(request -> {
          CorsConfiguration config = new CorsConfiguration();
          config.setAllowCredentials(true);
          config.addAllowedOriginPattern("*");
          config.addAllowedHeader("*");
          config.addAllowedMethod("*");
          config.setAllowedOrigins(
              List.of("http://localhost:3000", "http://localhost:8080", "http://localhost:5173,",
                  "https://chagok.vercel.app", "https://chagok.shop"));
          return config;
        }))
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(
                "/h2-console/**",
                "/**"
            ).permitAll()
            .anyRequest().authenticated())
        .headers(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .logout(Customizer.withDefaults())
        .oauth2Login(oauth2 -> oauth2
            .authorizationEndpoint(endpoint -> endpoint
                .baseUri("/oauth2/authorization")
                .authorizationRequestRepository(oAuth2AuthorizationRequestRepository()))
            .redirectionEndpoint(endPoint -> endPoint
                .baseUri("/oauth2/callback/*"))
            .userInfoEndpoint(endPoint -> endPoint
                .userService(oAuthUserCustomService))
            .successHandler(oAuth2AuthenticationSuccessHandler())
            .failureHandler(oAuth2AuthenticationFailureHandler()))
        .addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class)
        .addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Primary
  @Bean
  public HttpCookieOAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository() {
    return new HttpCookieOAuth2AuthorizationRequestRepository();
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(appConfig.passwordEncoder());
    provider.setUserDetailsService(customUserDetailService);
    return new ProviderManager(provider);
  }

  @Bean
  public LoginSuccessHandler loginSuccessHandler() {
    return new LoginSuccessHandler(tokenService, memberRepository);
  }

  @Bean
  public LoginFailureHandler loginFailureHandler() {
    return new LoginFailureHandler();
  }

  @Bean
  public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter
      () {
    CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
        = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
    customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
    customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
    customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
    return customJsonUsernamePasswordLoginFilter;
  }

  @Bean
  public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
    return new JwtAuthenticationProcessingFilter(tokenService, memberRepository);
  }

  @Bean
  public OAuth2LoginSuccessHandler oAuth2AuthenticationSuccessHandler() {
    return new OAuth2LoginSuccessHandler(oAuth2AuthorizationRequestRepository(), appProperties, tokenService, memberRepository);
  }

  @Bean
  public OAuth2LoginFailureHandler oAuth2AuthenticationFailureHandler() {
    return new OAuth2LoginFailureHandler(oAuth2AuthorizationRequestRepository());
  }

}
