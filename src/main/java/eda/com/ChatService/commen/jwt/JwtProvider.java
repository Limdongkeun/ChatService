package eda.com.ChatService.commen.jwt;//package eda.com.ChatService.commen.jwt;


import eda.com.ChatService.chat.member.entity.Authority;
import eda.com.ChatService.chat.member.entity.Member;
import eda.com.ChatService.commen.CustomUserDetailsService;
import eda.com.ChatService.commen.exception.BusinessLogicException;
import eda.com.ChatService.commen.exception.ExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.Base64UrlCodec;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

  public static final String GRANT_TYPE = "Bearer";
  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String REFRESH_TOKEN_KEY = "refreshToken";

  private static final Long ACCESS_TOKEN_EXPIRED_IN = Duration.ofHours(1).toMillis();    //만료시간 1시간
  private static final Long REFRESH_TOKEN_EXPIRED_IN = Duration.ofDays(1).toMillis();    //만료시간 1일

  private static final String ROLES = "roles";

  private final CustomUserDetailsService customUserDetailsService;

//  private final RedisTemplate<String, Object> redisTemplate;

  @Getter
  @Value("${spring.jwt.secret-key}")
  private String secretKey;


  @PostConstruct
  public void init() {
    secretKey = Base64UrlCodec.BASE64URL.encode(secretKey.getBytes(StandardCharsets.UTF_8));
  }

  /*
   * 토큰 생성 메서드
   * accessToekn -> header, refreshToken -> cookie 저장
   */
//  public void createTokenDto(Member member, HttpServletResponse response) {
  public String createTokenDto(Member member, List<Authority> roles) {
    Claims claims = Jwts.claims().setSubject(member.getEmail());
    claims.put(ROLES, member.getRoles());

    Date now = new Date();

    String accessToken = Jwts.builder()
      .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
      .setClaims(claims)
      .setIssuedAt(now) //JWT 발행 일자 파라미터 타입은 java.util.Date 타입
      .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRED_IN)) //JWT의 만료기한을 지정 파라미터 타입은 java.util.Date 타입
      .signWith(SignatureAlgorithm.HS256, secretKey) //서명을 위한 Key (java.security.Key) 객체를 설정
      .compact(); //JWT를 생성하고 직렬화


//    String refreshToken = Jwts.builder()
//      .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRED_IN))
//      .signWith(SignatureAlgorithm.HS256, secretKey)
//      .compact();

//    //사용자의 이메일을 키로 하고 새로 생성한 refresh token을 값으로 저장
//    redisTemplate.opsForValue().set(user.getEmail(), refreshToken, REFRESH_TOKEN_EXPIRED_IN, TimeUnit.MICROSECONDS);
//    // Redis에 저장된 refresh token을 만료 시간으로 설정
//    redisTemplate.expire(user.getEmail(), REFRESH_TOKEN_EXPIRED_IN, TimeUnit.MICROSECONDS);


//    ResponseCookie responseCookie = ResponseCookie.from(REFRESH_TOKEN_KEY, refreshToken)
//      .path("/")
//      .httpOnly(true)
//      .maxAge(REFRESH_TOKEN_EXPIRED_IN)
//      .build();
//
//    response.addHeader(AUTHORIZATION_HEADER, GRANT_TYPE + accessToken);
//    //"Set-Cookie" HTTP 헤더를 사용하여 쿠키를 클라이언트에게 전달
//    response.addHeader("Set-Cookie", responseCookie.toString());
    return accessToken;
  }

  /*
   * 토큰 이용 인증 정보 조회
   */
  public Authentication getAuthentication(String token) {
    Claims claims = parseClaims(token);

    if (claims.get(ROLES) == null) {
      throw new BusinessLogicException(ExceptionCode.ROLE_IS_NOT_EXISTS);
    } else {
      // userDetailsService를 통해 사용자의 상세 정보를 검색
      UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
      //검색한 사용자의 상세 정보(userDetails)를 기반으로 실제로 인증(Authentication) 객체를 생성
      return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
  }

  /**
   * 토큰 복호화
   */
  private Claims parseClaims(String token) {
    try {
      return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
  
  // 토큰에 담겨있는 유저 account 획득
  public String getAccount(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getAudience();
  }
  
  // Authorization Header를 통해 인증을 한다.
  public String resolveToken(HttpServletRequest request) {
    return request.getHeader("Authorization");
  }
  
  
  /**
   * 토큰 유효성 검사
   */
  public boolean validationToken(String token) {
    try {
      // Bearer 검증
      if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
        return false;
      } else {
        token = token.split(" ")[1].trim();
      }
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.error("잘못된 Jwt 서명입니다.");
    } catch (ExpiredJwtException e) {
      log.error("만료된 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      log.error("지원하지 않는 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.error("잘못된 토큰입니다.");
    }
    return false;
  }

}
