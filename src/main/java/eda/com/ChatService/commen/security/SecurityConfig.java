package eda.com.ChatService.commen.security;

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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .headers(headers -> {
        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
      })
//      .cors(cors -> {
//        cors.configure();
//      })
      .formLogin(Customizer.withDefaults())
      .authorizeHttpRequests(request ->
        request
          .requestMatchers("/chat/**").hasRole("USER")
          .anyRequest().permitAll()
      );
    
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

