package br.edu.ifsp.commomleague.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginErrorException extends ResponseStatusException {

    public LoginErrorException() {
        this("Login error");
    }

    public LoginErrorException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
    
}
