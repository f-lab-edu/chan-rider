package com.chan.rider.handler;

import com.chan.rider.common.Message;
import com.chan.rider.common.StatusEnum;
import com.chan.rider.exception.RiderFindFailException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RiderFindFailException.class)
    public ResponseEntity<Message> handleCustomerFindFailException(RiderFindFailException ex){
        return responseBadRequest(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Message> handleException(Exception ex){
        return responseBadRequest(ex.getMessage());
    }

    private ResponseEntity responseBadRequest(String messages) {
        Message message = new Message();
        message.setStatus(StatusEnum.BAD_REQUEST);
        message.setMessage(messages);
        return ResponseEntity.badRequest().body(message);
    }
}
