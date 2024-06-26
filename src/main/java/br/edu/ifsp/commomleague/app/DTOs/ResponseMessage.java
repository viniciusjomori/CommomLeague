package br.edu.ifsp.commomleague.app.DTOs;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class ResponseMessage {
    private HttpStatusCode httpStatus;
    private String message;
}
