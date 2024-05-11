package br.edu.ifsp.commomleague.team.exceptions;

import java.util.Collection;

import br.edu.ifsp.commomleague.app.exceptions.BadRequestException;

public class TeamBadRequestException extends BadRequestException {
    
    public TeamBadRequestException(Collection<String> errors) {
        super(errors.toString());
    }

}
