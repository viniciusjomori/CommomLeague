package br.edu.ifsp.commomleague.user.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifsp.commomleague.user.UserRepository;
import br.edu.ifsp.commomleague.user.DTOs.UserRegisterDTO;

@Component
public class ExistsEmailValidation implements UserValidationStrategy {

    @Autowired
    private UserRepository repository;

    @Override
    public boolean validate(UserRegisterDTO e) {
        return !repository.existsByEmail(e.email());
    }

    @Override 
    public String getErrorMessage() {
        return "Email already registred";
    }
}
