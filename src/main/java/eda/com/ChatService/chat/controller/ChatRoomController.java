package eda.com.ChatService.chat.controller;

import eda.com.ChatService.chat.dto.ChatRoom;
import eda.com.ChatService.chat.repository.ChatRoomRepository;
import eda.com.ChatService.chat.user.entity.LoginInfo;
import eda.com.ChatService.commen.jwt.JwtTokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {
  private final JwtTokenProvider jwtTokenProvider;
  private final ChatRoomRepository chatRoomRepository;
  
  /*
   * 채팅방 리스트 화면
   */
  @GetMapping("/room")
  public ModelAndView rooms(Model model) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("room");
    return mav;
  }
  
  /*
   * 모든 채팅방 목록
   */
  @GetMapping("/rooms")
  @ResponseBody
  public List<ChatRoom> rooms() {
    return chatRoomRepository.findALlRoom();
  }
  
  /*
   * 채팅방 생성
   */
  @PostMapping("/room")
  @ResponseBody
  public ChatRoom createRoom(@RequestParam String name) {
    return chatRoomRepository.createChatRoom(name);
  }
  
  /*
   * 채팅방 입장 화면
   */
  @GetMapping("/room/enter/{roomId}")
  public ModelAndView roomDetails(Model model, @PathVariable String roomId) {
    ModelAndView mav = new ModelAndView();
    model.addAttribute("roomId", roomId);
    mav.addObject("roomId", roomId);
    mav.setViewName("roomDetails");
    
    return mav;
  }
  
  /*
   * 특정 채팅방 조회
   */
  @GetMapping("/room/{roomId}")
  @ResponseBody
  public ChatRoom roomInfo(@PathVariable String roomId) {
    return chatRoomRepository.findRoomById(roomId);
  }
  
  @GetMapping("/user")
  @ResponseBody
  public LoginInfo getUserInfo() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String name = auth.getName();
    return LoginInfo.builder()
      .name(name)
      .token(jwtTokenProvider.generateToken(name))
      .build();
  }
}
