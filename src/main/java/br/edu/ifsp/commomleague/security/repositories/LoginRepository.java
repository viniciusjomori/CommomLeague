package br.edu.ifsp.commomleague.security.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.commomleague.security.entities.LoginEntity;

public interface LoginRepository extends JpaRepository<LoginEntity, UUID> {
    
}
