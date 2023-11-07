package eda.com.ChatService.commen.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
  
  /*
   * redis pub/sub 메시지를 처리하는 listener
   *  Redis와의 연결 설정과 메시지 수신
   */
  @Bean
  public RedisMessageListenerContainer redisMessageListener(
    RedisConnectionFactory connectionFactory) {
    //Redis 연결 팩토리와 연결되어 Redis pub/sub 메시지를 수신
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    return container;
  }
  
  /*
   * 애플리케이션에서 사용할 redisTemplate 설정
   *  Redis에 데이터를 저장하고 검색하기 위한 메서드
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    //데이터를 저장하고 검색하기 위한 템플릿으로, 문자열 키와 제네릭 Object 값을 사용
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    //RedisTemplate이 사용할 Redis 연결 팩토리를 설정
    redisTemplate.setConnectionFactory(connectionFactory);
    //Redis에 저장할 때 문자열 키의 직렬화 방법을 설정
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    //Redis에 저장할 때 값을 직렬화하는 방법을 설정
    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
    return redisTemplate;
  }
}
