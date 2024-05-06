package br.edu.ifsp.commomleague.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WeakPasswordException extends ResponseStatusException {

    private static HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public WeakPasswordException() {
        super(httpStatus, "Weak password!");
    }

    public WeakPasswordException(String message) {
        super(httpStatus, message);
    }
    
}
