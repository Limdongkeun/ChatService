package eda.com.ChatService.commen.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import eda.com.ChatService.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {
  
  private final ObjectMapper objectMapper;
  private final RedisTemplate redisTemplate;
  private final SimpMessageSendingOperations messagingTemplate;
  
  
  /*
   * Redis에세 메세지가 밸행(publish) 되면 대기하고 있던 onMessage가 해당 메세지를 받아 처리
   * Redis 메시지를 수신하고 처리하
   */
  @Override
  public void onMessage(Message message, byte[] pattern) {
    try {
      //redis에서 발행된 데이터를 받아 descrialize Redis에서 받은 메시지를 문자열로 역직렬화
      String publishMessage =
        (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
      
      // ChatMessage 객채로 맵핑
      ChatMessage roomMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
      
      //WebSocket 구독자에게 채팅 메세지 Send 실시간 채팅 메시지를 클라이언트에 전달하는 역할
      messagingTemplate.convertAndSend("/sub/chat/room/" + roomMessage.getRoomId(), roomMessage);
    } catch (Exception e) {
      log.error("onMessage 에러[] = " + e.getMessage());
    }
  }
}
