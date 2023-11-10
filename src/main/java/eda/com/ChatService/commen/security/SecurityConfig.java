package eda.com.ChatService.commen.security;

import eda.com.ChatService.commen.jwt.JwtAccessDeniedHandler;
import eda.com.ChatService.commen.jwt.JwtAuthenticationEntryPoint;
import eda.com.ChatService.commen.jwt.JwtAuthenticationFilter;
import eda.com.ChatService.commen.jwt.JwtProvider;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  
  private final JwtProvider jwtProvider;
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .headers(headers -> {
        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
      })
      .cors(cors -> {
        corsConfigurationSource();
      })
      .sessionManagement(sessionManagement -> {
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      })
      .formLogin(Customizer.withDefaults())
      .authorizeHttpRequests(request ->
        request
          .requestMatchers("/chat/**").hasRole("USER")
          .requestMatchers("/chat/**").hasRole("ADMIN")
          .anyRequest().permitAll()
      )
      .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
      .exceptionHandling(exceptionHandling -> {
        exceptionHandling
          .accessDeniedHandler(new JwtAccessDeniedHandler())
          .authenticationEntryPoint(new JwtAuthenticationEntryPoint());
      })
    ;
    
    return http.build();
  }
  
  
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOriginPattern("*");
    configuration.setAllowedMethods(
      Arrays.asList("*", "OPTIONS", "HEAD", "GET", "POST", "PUT", "DELETE"));
    configuration.addAllowedHeader("*");
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/ws-stomp/**", configuration);
    
    return source;
  }
/*
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
      .withUser("happydaddy")
      .password("{noop}1234")
      .roles("USER")
      .and()
      .withUser("angrydaddy")
      .password("{noop}1234")
      .roles("USER")
      .and()
      .withUser("guest")
      .password("{noop}1234")
      .roles("GUEST");
    
  }
  
 */
 
 
}

