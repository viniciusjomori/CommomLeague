package br.edu.ifsp.commomleague.solicitation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SolicitationConflictException extends ResponseStatusException {
    
    public SolicitationConflictException() {
        super(HttpStatus.CONFLICT, "This solicitation has been defined");
    }
}
