package eda.com.ChatService.commen.stomp;

import eda.com.ChatService.commen.jwt.JwtProvider;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
  
  private final JwtProvider jwtProvider;
  
  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    
    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
      jwtProvider.validationToken(Objects.requireNonNull(accessor.getFirstNativeHeader("Authorization")));
    }
    
    return message;
  }
}
