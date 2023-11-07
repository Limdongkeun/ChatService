package eda.com.ChatService.commen.redis.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

@Profile("local")
@Configuration
public class EmbeddedRedisConfig {
  
  @Value("${spring.data.redis.port}")
  private int redisPort;
  
  @Value("${spring.data.redis.host}")
  private String redisHost;
  
  private RedisServer redisServer;
  
  @PostConstruct
  public void redisServer() {
    redisServer = RedisServer.builder()
      .port(redisPort)
      .setting("maxmemory 128M")
      .build();
    redisServer.start();
  }
  
  @PreDestroy
  public void stopRedis() {
    
    if (redisServer != null) {
      redisServer.stop();
    }
  }
}
