package eda.com.ChatService.chat.repository;

import eda.com.ChatService.chat.dto.ChatRoom;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class ChatRoomRepository {
  
  /*@TODO
   * 지금은 일단 간단하게 Map에 저장하지만 추후 DB 연결
   */
  private Map<String, ChatRoom> chatRoomMap;
  
  @PostConstruct
  private void init() {
    chatRoomMap = new LinkedHashMap<>();
  }
  
  public List<ChatRoom> findALlRoom() {
    List chatRooms = new ArrayList<>(chatRoomMap.values());
    Collections.reverse(chatRooms);
    return chatRooms;
  }
  
  public ChatRoom findRoomById(String id) {
    return chatRoomMap.get(id);
  }
  
  public ChatRoom createChatRoom(String name) {
    ChatRoom chatRoom = ChatRoom.create(name);
    chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
    return chatRoom;
  }
}
