package eda.com.ChatService.commen.exception;

import lombok.Getter;

public enum ExceptionCode {
  
  ERROR_EXECUTING_EMBEDDED_REDIS(401, "ERROR_EXECUTING_EMBEDDED_REDIS"),
  
  MEMBER_ALREADY_EXISTS(409, "MEMBER_ALREADY_EXISTS"),
  
  MEMBER_INFO_INCORRECT(404, "MEMBER_INFO_INCORRECT"),
  ROLE_IS_NOT_EXISTS(403, "ROLE_IS_NOT_EXISTS"),
  MEMBER_NOT_FOUND(409, "MEMBER_NOT_FOUND");
  
  @Getter
  private final int status;
  
  @Getter
  private final String message;
  
  ExceptionCode(int code, String message) {
    this.status = code;
    this.message = message;
  }
}
