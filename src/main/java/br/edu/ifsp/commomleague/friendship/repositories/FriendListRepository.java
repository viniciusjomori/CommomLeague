package br.edu.ifsp.commomleague.friendship.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.commomleague.friendship.entities.FriendListEntity;
import br.edu.ifsp.commomleague.user.entities.UserEntity;

public interface FriendListRepository extends JpaRepository<FriendListEntity, UUID>{

    Optional<FriendListEntity> findByUser(UserEntity user);

}
