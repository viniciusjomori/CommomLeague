package br.edu.ifsp.commomleague.friendship.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.commomleague.friendship.entities.FriendshipSolicitationEntity;
import br.edu.ifsp.commomleague.friendship.repositories.FriendshipSolicitationRepository;
import br.edu.ifsp.commomleague.solicitation.SolicitationMapper;
import br.edu.ifsp.commomleague.solicitation.SolicitationService;
import br.edu.ifsp.commomleague.solicitation.DTOs.SolicitationResponseDTO;
import br.edu.ifsp.commomleague.solicitation.exceptions.SolicitationNotFoundException;
import br.edu.ifsp.commomleague.user.entities.UserEntity;

@Service
public class FriendshipSolicitationService {
    
    @Autowired
    private FriendshipSolicitationRepository repository;

    @Autowired
    private SolicitationService solicitationService;

    @Autowired
    private SolicitationMapper mapper;

    public SolicitationResponseDTO createSolicitation(UserEntity userTo) {
        FriendshipSolicitationEntity solicitation = FriendshipSolicitationEntity.builder()
            .userTo(userTo)
            .build();
        solicitation = (FriendshipSolicitationEntity) solicitationService.createSolicitation(solicitation);
        return mapper.toResponseDTO(solicitation);
    }

    public FriendshipSolicitationEntity findById(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new SolicitationNotFoundException());
    }

    public FriendshipSolicitationEntity accept(UUID id) {
        FriendshipSolicitationEntity solicitation = findById(id);
        solicitationService.accept(solicitation);
        return solicitation;
    }

    public FriendshipSolicitationEntity refuse(UUID id) {
        FriendshipSolicitationEntity solicitation = findById(id);
        solicitationService.refuse(solicitation);
        return solicitation;
    }
    
}
