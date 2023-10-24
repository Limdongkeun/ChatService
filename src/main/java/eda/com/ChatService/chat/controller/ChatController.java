package eda.com.ChatService.chat.controller;

import eda.com.ChatService.chat.ChatRoom;
import eda.com.ChatService.chat.service.ChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
  
  private final ChatService chatService;
  
  @PostMapping
  public ChatRoom createRoom(@RequestParam String name) {
    return chatService.createRoom(name);
  }
  
  @GetMapping
  public List<ChatRoom> finalAllRoom() {
    return chatService.findAllRoom();
  }
}
