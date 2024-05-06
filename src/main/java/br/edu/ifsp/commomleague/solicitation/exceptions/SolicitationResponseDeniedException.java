package br.edu.ifsp.commomleague.solicitation.exceptions;

import br.edu.ifsp.commomleague.app.exceptions.ForbiddenException;

public class SolicitationResponseDeniedException extends ForbiddenException {
    
    public SolicitationResponseDeniedException() {
        super("This solicitation is not for you");
    }
}
