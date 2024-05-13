package br.edu.ifsp.commomleague.app.exceptions;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestException extends ResponseStatusException {

    public BadRequestException(Collection<String> errors) {
        this(errors.toString());
    }

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
    
}
