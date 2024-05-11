package br.edu.ifsp.commomleague.team.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.commomleague.player.PlayerEntity;
import br.edu.ifsp.commomleague.player.PlayerService;
import br.edu.ifsp.commomleague.team.TeamMapper;
import br.edu.ifsp.commomleague.team.DTOs.TeamRegisterDTO;
import br.edu.ifsp.commomleague.team.entities.TeamEntity;
import br.edu.ifsp.commomleague.team.exceptions.TeamBadRequestException;
import br.edu.ifsp.commomleague.team.exceptions.TeamNotFoundException;
import br.edu.ifsp.commomleague.team.repositories.TeamRepository;

@Service
public class TeamService {
    
    @Autowired
    private TeamRepository repository;

    @Autowired
    private TeamMapper mapper;

    @Autowired
    private PlayerService playerService;

    public TeamEntity registerTeam(TeamRegisterDTO dto) {
        PlayerEntity currentPlayer = playerService.getCurrentPlayer();
        TeamEntity team = TeamEntity.builder()
            .captain(currentPlayer)
            .createdBy(currentPlayer)
            .members(new HashSet<>())
            .build();
        team.getMembers().add(currentPlayer);
        mapper.updateEntity(dto, team);
        validateTeamRegister(team);
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

    public void validateTeamRegister(TeamEntity team) {
        List<String> errors = new ArrayList<>();
        
        if(repository.existsByName(team.getName()))
            errors.add("Team name already registred");

        if(!errors.isEmpty())
            throw new TeamBadRequestException(errors);

    }

}