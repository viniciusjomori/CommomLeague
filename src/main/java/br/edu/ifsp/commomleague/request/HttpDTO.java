package br.edu.ifsp.commomleague.request;

import org.springframework.http.HttpStatusCode;

public record HttpDTO(
    String jsonBody,
    HttpStatusCode httpStatus
) {
    
}
