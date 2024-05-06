package br.edu.ifsp.commomleague.user.exceptions;

import java.util.List;

import br.edu.ifsp.commomleague.app.exceptions.BadRequestException;

public class UserRegisterException extends BadRequestException {

    public UserRegisterException(List<String> errors) {
        this(errors.toString());
    }

    public UserRegisterException(String message) {
        super(message);
    }
    
}
