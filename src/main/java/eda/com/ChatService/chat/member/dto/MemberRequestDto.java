package eda.com.ChatService.chat.member.dto;

import lombok.Getter;
import lombok.Setter;


public class MemberRequestDto {
  
  @Getter
  @Setter
  public static class SignUp {
    private Long id;
    
    private String email;
    
    private String password;
    
    private String nickname;
  }
  @Getter
  @Setter
  public static class SignIn {
    
    private String email;
    
    private String password;
    
  }
  
 
}
