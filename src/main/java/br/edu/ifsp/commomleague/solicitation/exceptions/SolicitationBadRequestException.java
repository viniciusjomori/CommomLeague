package br.edu.ifsp.commomleague.solicitation.exceptions;

import br.edu.ifsp.commomleague.app.exceptions.BadRequestException;

public class SolicitationBadRequestException extends BadRequestException {

    public SolicitationBadRequestException() {
        super("You can't send a solicitation for you");
    }
    
}
