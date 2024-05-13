package br.edu.ifsp.commomleague.team.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.commomleague.app.exceptions.BadRequestException;
import br.edu.ifsp.commomleague.player.PlayerEntity;
import br.edu.ifsp.commomleague.player.PlayerService;
import br.edu.ifsp.commomleague.team.TeamMapper;
import br.edu.ifsp.commomleague.team.DTOs.TeamRegisterDTO;
import br.edu.ifsp.commomleague.team.entities.TeamEntity;
import br.edu.ifsp.commomleague.team.exceptions.TeamNotFoundException;
import br.edu.ifsp.commomleague.team.repositories.TeamRepository;
import br.edu.ifsp.commomleague.team.strategy.TeamValidationStrategy;

@Service
public class TeamService {
    
    @Autowired
    private TeamRepository repository;

    @Autowired
    private TeamMapper mapper;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private List<TeamValidationStrategy> validators;

    public TeamEntity registerTeam(TeamRegisterDTO dto) {
        validateTeam(dto);
        PlayerEntity currentPlayer = playerService.getCurrentPlayer();
        TeamEntity team = TeamEntity.builder()
            .captain(currentPlayer)
            .createdBy(currentPlayer)
            .members(new HashSet<>())
            .build();
        team.getMembers().add(currentPlayer);
        mapper.updateEntity(dto, team);
        return repository.save(team);
    }

    public Collection<TeamEntity> findAll() {
        return repository.findAll();
    }

    public TeamEntity findById(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new TeamNotFoundException());
    }
    
    public void addMember(TeamEntity team, PlayerEntity player) {
        team.getMembers().add(player);
        repository.save(team);
    }

    public void leaveTeam(TeamEntity team, PlayerEntity player) {
        team.getMembers().remove(player);
        repository.save(team);
    }

    public void validateTeam(TeamRegisterDTO team) {
        List<String> errors = validators.stream()
            .filter(v -> !v.validate(team))
            .map(TeamValidationStrategy::getErrorMessage)
            .collect(Collectors.toList());

        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }

    }

}