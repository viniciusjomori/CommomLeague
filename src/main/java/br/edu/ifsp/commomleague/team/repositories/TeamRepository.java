package br.edu.ifsp.commomleague.team.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.commomleague.team.entities.TeamEntity;

public interface TeamRepository extends JpaRepository<TeamEntity, UUID> {
    
    boolean existsByName(String name);

}
