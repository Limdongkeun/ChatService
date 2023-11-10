package eda.com.ChatService.chat.member.controller;

import eda.com.ChatService.chat.member.dto.MemberRequestDto;
import eda.com.ChatService.chat.member.dto.MemberResponseDto;
import eda.com.ChatService.chat.member.repository.MemberRepository;
import eda.com.ChatService.chat.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
  
  private final MemberService memberService;
  private final MemberRepository memberRepository;
  
  @PostMapping("/signup")
  public ResponseEntity signUp(@RequestBody MemberRequestDto.SignUp signUpDto) throws Exception {
    return new ResponseEntity<>(memberService.signUp(signUpDto), HttpStatus.CREATED);
  }
  
  @PostMapping("/signin")
  public ResponseEntity signIn(@RequestBody MemberRequestDto.SignIn signInDto) throws Exception{
    return new ResponseEntity<>(memberService.signIn(signInDto),HttpStatus.OK);
  }
  
  @GetMapping("/info/user")
  public ResponseEntity<MemberResponseDto> userInfo(@RequestParam String email) throws Exception {
    return new ResponseEntity<>(memberService.getMember(email), HttpStatus.OK);
  }
  
  @GetMapping("/info/admin")
  public ResponseEntity<MemberResponseDto> adminInfo(@RequestParam String email) throws Exception {
    return new ResponseEntity<>(memberService.getMember(email), HttpStatus.OK);
  }
}
