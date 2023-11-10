package eda.com.ChatService.commen;

import eda.com.ChatService.chat.member.entity.Member;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
  
  private final Member member;
  
  public CustomUserDetails(Member member) {
    this.member = member;
  }
  public final Member getMember() {
    return member;
  }
  /*
   * 해당 사용자에 대한 권한을 나타내며, Spring Security가 이를 이용하여 인증과 권한 부여를 수행
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return member.getRoles().stream().map(o ->new SimpleGrantedAuthority(
      o.getName()
    )).collect(Collectors.toList());
  }
  
  @Override
  public String getPassword() {
    return member.getPassword();
  }
  
  @Override
  public String getUsername() {
    return member.getEmail();
  }
  
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  
  @Override
  public boolean isEnabled() {
    return true;
  }
}
