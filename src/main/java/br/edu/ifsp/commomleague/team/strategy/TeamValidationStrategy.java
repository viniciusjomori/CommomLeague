package br.edu.ifsp.commomleague.team.strategy;

import br.edu.ifsp.commomleague.team.DTOs.TeamRegisterDTO;

public interface TeamValidationStrategy {
    
    boolean validate(TeamRegisterDTO team);

    String getErrorMessage();

}
