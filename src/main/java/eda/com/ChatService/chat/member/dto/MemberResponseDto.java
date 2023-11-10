package eda.com.ChatService.chat.member.dto;

import eda.com.ChatService.chat.member.entity.Authority;
import eda.com.ChatService.chat.member.entity.Member;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
  
  
  private Long id;
  
  private String email;
  
  private String nickname;
  
  private List<Authority> roles = new ArrayList<>();
  
  private String token;
  
  public MemberResponseDto(Member member) {
    this.id = member.getId();
    this.email = member.getEmail();
    this.nickname = member.getNickname();
    this.roles = member.getRoles();
  }
  
  
}
