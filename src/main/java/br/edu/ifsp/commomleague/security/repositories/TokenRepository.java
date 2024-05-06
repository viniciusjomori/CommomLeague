package br.edu.ifsp.commomleague.security.repositories;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifsp.commomleague.security.entities.TokenEntity;
import br.edu.ifsp.commomleague.user.entities.UserEntity;

public interface TokenRepository extends JpaRepository<TokenEntity, UUID> {
    
    @EntityGraph(attributePaths = {"user.role.authorities"})
    public Optional<TokenEntity> findByToken(String token);
    
    @Query("SELECT t FROM TokenEntity t WHERE t.user = :user AND t.active = true")
    public Collection<TokenEntity> findActiveByUser(@Param("user") UserEntity user);
}
