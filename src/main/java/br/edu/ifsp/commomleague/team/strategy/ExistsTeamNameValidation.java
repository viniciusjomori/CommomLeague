package br.edu.ifsp.commomleague.team.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifsp.commomleague.team.DTOs.TeamRegisterDTO;
import br.edu.ifsp.commomleague.team.repositories.TeamRepository;

@Component
public class ExistsTeamNameValidation implements TeamValidationStrategy {

    @Autowired
    private TeamRepository repository;

    @Override
    public boolean validate(TeamRegisterDTO team) {
        return !repository.existsByName(team.name());
    }

    @Override
    public String getErrorMessage() {
        return "Team name already registred";
    }
    
}
