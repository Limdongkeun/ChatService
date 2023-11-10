package eda.com.ChatService.commen.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
  // 인증되지 않은 사용자가 보호된 엔드포인트에 액세스하려고 시도할 때 호출
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
                       AuthenticationException authException) throws IOException, ServletException {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getLocalizedMessage());
  }
  
}
