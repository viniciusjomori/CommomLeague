package br.edu.ifsp.commomleague.team.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.commomleague.solicitation.SolicitationMapper;
import br.edu.ifsp.commomleague.solicitation.SolicitationService;
import br.edu.ifsp.commomleague.solicitation.DTOs.SolicitationResponseDTO;
import br.edu.ifsp.commomleague.solicitation.exceptions.SolicitationNotFoundException;
import br.edu.ifsp.commomleague.team.entities.TeamEntity;
import br.edu.ifsp.commomleague.team.entities.TeamSolicitationEntity;
import br.edu.ifsp.commomleague.team.repositories.TeamSolicitationRepository;
import br.edu.ifsp.commomleague.user.UserService;
import br.edu.ifsp.commomleague.user.entities.UserEntity;

@Service
public class TeamSolicitationService {
    
    @Autowired
    private TeamSolicitationRepository repository;

    @Autowired
    private SolicitationMapper mapper;

    @Autowired
    private SolicitationService solicitationService;

    @Autowired
    private UserService userService;

    public SolicitationResponseDTO invite(TeamEntity team, UUID userId) {
        UserEntity user = userService.findById(userId);
        TeamSolicitationEntity solicitation = TeamSolicitationEntity.builder()
            .userTo(user)
            .team(team)
            .build();
        solicitation = (TeamSolicitationEntity) solicitationService.createSolicitation(solicitation);
        return mapper.toResponseDTO(solicitation);
    }

    public TeamSolicitationEntity findById(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new SolicitationNotFoundException());
    }

    public TeamSolicitationEntity accept(UUID id) {
        TeamSolicitationEntity solicitation = findById(id);
        solicitationService.accept(solicitation);
        return solicitation;
    }

    public TeamSolicitationEntity refuse(UUID id) {
        TeamSolicitationEntity solicitation = findById(id);
        solicitationService.refuse(solicitation);
        return solicitation;
    }

}
