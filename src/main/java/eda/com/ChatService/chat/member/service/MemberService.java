package eda.com.ChatService.chat.member.service;

import eda.com.ChatService.chat.member.dto.MemberRequestDto;
import eda.com.ChatService.chat.member.dto.MemberResponseDto;
import eda.com.ChatService.chat.member.entity.Authority;
import eda.com.ChatService.chat.member.entity.Member;
import eda.com.ChatService.chat.member.repository.MemberRepository;
import eda.com.ChatService.commen.commonResponseDto.MessageResponseDto;
import eda.com.ChatService.commen.exception.BusinessLogicException;
import eda.com.ChatService.commen.exception.ExceptionCode;
import eda.com.ChatService.commen.jwt.JwtProvider;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
  
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  
  public MemberResponseDto signIn(MemberRequestDto.SignIn signInDto) {
    Member member = memberRepository.findByEmail(signInDto.getEmail())
      .orElseThrow( () -> new BusinessLogicException(ExceptionCode.MEMBER_INFO_INCORRECT));
    
    if (!passwordEncoder.matches(signInDto.getPassword(), member.getPassword())) {
      throw new BusinessLogicException(ExceptionCode.MEMBER_INFO_INCORRECT);
    }
    
    return MemberResponseDto.builder()
      .id(member.getId())
      .email(member.getEmail())
      .nickname(member.getNickname())
      .roles(member.getRoles())
      .token(jwtProvider.createTokenDto(member, member.getRoles()))
      .build();
  }
  
  public MessageResponseDto signUp(MemberRequestDto.SignUp signUpDto) {
    try {
      Member member = Member.builder()
        .email(signUpDto.getEmail())
        .password(passwordEncoder.encode(signUpDto.getPassword()))
        .nickname(signUpDto.getNickname())
        .build();
      
      member.setRoles(Collections.singletonList(Authority.builder()
          .name("USER")
        .build()));
      memberRepository.save(member);
    } catch (Exception e) {
      log.error(e.getMessage() + "signUp 오류", e);
      
      throw new BusinessLogicException(ExceptionCode.MEMBER_ALREADY_EXISTS);
    }
    return new MessageResponseDto(signUpDto.getNickname() + "님 회원가입을 축하합니다");
  }
  
  public MemberResponseDto getMember(String email) {
    Member member = memberRepository.findByEmail(email)
      .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    return new MemberResponseDto(member);
  }
  
}
