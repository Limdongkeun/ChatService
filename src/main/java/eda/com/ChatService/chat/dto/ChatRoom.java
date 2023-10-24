package eda.com.ChatService.chat.dto;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoom {
  private String roomId;
  private String name;
  
  /*
   * pub/sub을 이용하면 구독자 관리가 알아서 되므로 웹소켓 세션 관리가 필요 없어짐
   */
  public static ChatRoom create(String name) {
    ChatRoom chatRoom = new ChatRoom();
    chatRoom.roomId = UUID.randomUUID().toString();
    chatRoom.name = name;
    return chatRoom;
  }
}
