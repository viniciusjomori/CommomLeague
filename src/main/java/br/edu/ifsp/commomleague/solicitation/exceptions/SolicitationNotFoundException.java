package br.edu.ifsp.commomleague.solicitation.exceptions;

import br.edu.ifsp.commomleague.app.exceptions.ResourceNotFoundException;

public class SolicitationNotFoundException extends ResourceNotFoundException{
    
    public SolicitationNotFoundException() {
        super("Solicitation not found");
    }
}
