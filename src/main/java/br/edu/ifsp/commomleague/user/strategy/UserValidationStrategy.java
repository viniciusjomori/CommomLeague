package br.edu.ifsp.commomleague.user.strategy;

import br.edu.ifsp.commomleague.user.DTOs.UserRegisterDTO;

public interface UserValidationStrategy {
    
    boolean validate(UserRegisterDTO user);

    String getErrorMessage();

}
