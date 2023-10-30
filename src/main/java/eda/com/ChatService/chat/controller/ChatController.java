package eda.com.ChatService.chat.controller;

import eda.com.ChatService.chat.dto.ChatMessage;
import eda.com.ChatService.chat.repository.ChatRoomRepository;
import eda.com.ChatService.commen.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/chat")  WebSocket 통신은 HTTP 엔드포인트가 아니므로 @RequestMapping을 필요하지 않음
public class ChatController {
  
  private final RedisPublisher redisPublisher;
  private final ChatRoomRepository chatRoomRepository;
  
  @MessageMapping("/chat/message")
  public void message(ChatMessage message) {
    
    if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
      chatRoomRepository.enterChatRoom(message.getRoomId());
      message.setMessage(message.getSender() + "님이 입장하셨습니다");
    }
    
    //Websocket에 발행된 메세지를 redis로 발행한다(publish)
    redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    
  }
}
