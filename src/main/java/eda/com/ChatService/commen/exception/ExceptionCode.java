package eda.com.ChatService.commen.exception;

import lombok.Getter;

public enum ExceptionCode{
  
  ERROR_EXECUTING_EMBEDDED_REDIS(401, "ERROR_EXECUTING_EMBEDDED_REDIS"),
  
  MEMBER_ALREADY_EXISTS(409, "Member already exists"),
  
  MEMBER_INFO_INCORRECT(404, "Member Info Incorrect"),
  ROLE_IS_NOT_EXISTS(403, "Role is not exists");
  
  @Getter
  private int status;
  
  @Getter
  private String message;
  
  ExceptionCode(int code, String message) {
    this.status = code;
    this.message = message;
  }
}
