package br.edu.ifsp.commomleague.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.commomleague.user.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByNickname(String nickname);

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}
