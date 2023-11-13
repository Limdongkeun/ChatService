package eda.com.ChatService.commen.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
  
  @ExceptionHandler(BusinessLogicException.class)
  protected ResponseEntity<Object> handleBusinessLogicException(BusinessLogicException ex, WebRequest request) {
    ExceptionCode exceptionCode = ex.getExceptionCode();
    CustomErrorResponse response = new CustomErrorResponse(exceptionCode.getStatus(), exceptionCode.getMessage());
    return handleExceptionInternal(ex, response, new HttpHeaders(),
      HttpStatusCode.valueOf(response.getStatus()), request);
  }
  
  private static class CustomErrorResponse {
    private int status;
    private String message;
    
    public CustomErrorResponse(int status, String message) {
      this.status = status;
      this.message = message;
    }
    
    public int getStatus() {
      return status;
    }
    
    public String getMessage() {
      return message;
    }
  }
}
