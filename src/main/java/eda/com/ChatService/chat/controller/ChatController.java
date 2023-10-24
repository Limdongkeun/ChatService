package eda.com.ChatService.chat.controller;

import eda.com.ChatService.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
  
  private final SimpMessageSendingOperations messagingTemplate;
  
  @MessageMapping("/message")
  public void message(ChatMessage message) {
    
    if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
      message.setMessage(message.getSender() + "님이 입장하셨습니다");
    }
    messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
  }
}
