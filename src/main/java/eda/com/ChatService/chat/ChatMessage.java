package eda.com.ChatService.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
  //메세지 타입
  public enum MessageType {
    ENTER, TALK
  }
  
  private MessageType type;
  private String roomId;
  private String sender;
  private String message;
}
