package br.edu.ifsp.commomleague.player;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.commomleague.user.entities.UserEntity;

public interface PlayerRepository extends JpaRepository<PlayerEntity, UUID>{
    
    Optional<PlayerEntity> findByUser(UserEntity user);

}
