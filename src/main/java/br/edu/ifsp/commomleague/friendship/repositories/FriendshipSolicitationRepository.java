package br.edu.ifsp.commomleague.friendship.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.commomleague.friendship.entities.FriendshipSolicitationEntity;

public interface FriendshipSolicitationRepository extends JpaRepository<FriendshipSolicitationEntity, UUID> {
    
}
