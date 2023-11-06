package eda.com.ChatService.commen.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

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
}

