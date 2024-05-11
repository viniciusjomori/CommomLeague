package br.edu.ifsp.commomleague.team.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.commomleague.team.entities.TeamSolicitationEntity;

public interface TeamSolicitationRepository extends JpaRepository<TeamSolicitationEntity, UUID> {
    
}
