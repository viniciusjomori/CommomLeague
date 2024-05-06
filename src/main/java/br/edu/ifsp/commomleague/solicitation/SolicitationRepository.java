package br.edu.ifsp.commomleague.solicitation;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitationRepository extends JpaRepository<SolicitationEntity, UUID>{
    
}
