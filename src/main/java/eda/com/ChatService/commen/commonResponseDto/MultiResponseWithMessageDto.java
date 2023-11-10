package eda.com.ChatService.commen.commonResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class MultiResponseWithMessageDto<T> {
  private String id;
  private String message;
}
