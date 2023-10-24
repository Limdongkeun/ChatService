package eda.com.ChatService.chat.controller;

import eda.com.ChatService.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/chat")  WebSocket 통신은 HTTP 엔드포인트가 아니므로 @RequestMapping을 필요하지 않음
public class ChatController {
  
  private final SimpMessageSendingOperations messagingTemplate;
  
  @MessageMapping("/chat/message")
  public void message(ChatMessage message) {
    
    if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
      message.setMessage(message.getSender() + "님이 입장하셨습니다");
    }
    messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
  }
}
