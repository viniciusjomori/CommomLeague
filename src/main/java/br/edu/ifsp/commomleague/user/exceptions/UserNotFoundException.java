package br.edu.ifsp.commomleague.user.exceptions;

import br.edu.ifsp.commomleague.app.exceptions.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    
    public UserNotFoundException() {
        super("User not found");
    }
}
