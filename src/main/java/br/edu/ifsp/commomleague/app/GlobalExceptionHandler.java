package br.edu.ifsp.commomleague.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.edu.ifsp.commomleague.app.DTOs.ResponseMessage;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ResponseMessage responseMessage;
    
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseMessage> handleResponseStatusException(ResponseStatusException ex) {
        return createResponseEntity(ex.getStatusCode(), ex.getReason());
    }

    private ResponseEntity<ResponseMessage> createResponseEntity(HttpStatusCode httpStatus, String message) {
        responseMessage.setMessage(message);
        responseMessage.setHttpStatus(httpStatus);
        return new ResponseEntity<ResponseMessage>(responseMessage, httpStatus);
    }

}