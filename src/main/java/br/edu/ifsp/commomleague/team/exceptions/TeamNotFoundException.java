package br.edu.ifsp.commomleague.team.exceptions;

import br.edu.ifsp.commomleague.app.exceptions.ResourceNotFoundException;

public class TeamNotFoundException extends ResourceNotFoundException {
    
    public TeamNotFoundException() {
        super("Team not found");
    }

}
