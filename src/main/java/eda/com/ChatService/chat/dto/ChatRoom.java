package eda.com.ChatService.chat.dto;

import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoom implements Serializable {
  
  //클래스의 직렬화 버전을 관리하는 데 사용되며, 클래스가 변경될 때 해당 값을 업데이트하여 이전 버전과의 호환성을 유지하는 중요한 역할
  private static final long serialVersionUID = 6494678977089006639L;
  
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
