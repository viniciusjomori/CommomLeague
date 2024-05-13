package br.edu.ifsp.commomleague.user.strategy;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;

import br.edu.ifsp.commomleague.user.DTOs.UserRegisterDTO;

@Component
public class UserAgeValidation implements UserValidationStrategy {

    @Override
    public boolean validate(UserRegisterDTO e) {
        Period period = Period.between(e.birthday(), LocalDate.now());
        return period.getYears() >= 18;
    }

    @Override 
    public String getErrorMessage() {
        return "You must be 18 years old or older";
    }
    
}
