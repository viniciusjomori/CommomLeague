package br.edu.ifsp.commomleague.user.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifsp.commomleague.security.utils.PasswordUtil;
import br.edu.ifsp.commomleague.user.DTOs.UserRegisterDTO;

@Component
public class WeakPasswordValidation implements UserValidationStrategy {
    
    @Autowired
    private PasswordUtil passwordUtil;

    @Override
    public boolean validate(UserRegisterDTO user) {
        return passwordUtil.isStrongPassword(user.password());
    }

    @Override 
    public String getErrorMessage() {
        return "Weak password";
    }

}
