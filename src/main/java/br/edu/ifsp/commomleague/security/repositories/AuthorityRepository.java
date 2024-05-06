package br.edu.ifsp.commomleague.security.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.commomleague.security.entities.AuthorityEntity;
import br.edu.ifsp.commomleague.security.enums.AuthorityName;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, UUID> {
    
    public Optional<AuthorityEntity> findByAuthorityName(AuthorityName authorityName);
}
