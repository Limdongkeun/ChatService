package eda.com.ChatService.commen;

import eda.com.ChatService.chat.member.entity.Member;
import eda.com.ChatService.chat.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  
  private final MemberRepository memberRepository;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Member member = memberRepository.findByEmail(username).orElseThrow(
      () -> new UsernameNotFoundException("유저 정보가 없습니다")
    );
    return new CustomUserDetails(member);
  }
}
