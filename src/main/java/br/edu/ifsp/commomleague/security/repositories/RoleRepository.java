package br.edu.ifsp.commomleague.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.commomleague.security.entities.RoleEntity;
import br.edu.ifsp.commomleague.security.enums.RoleName;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    
    Optional<RoleEntity> findById(UUID id);
    Optional<RoleEntity> findByRoleName(RoleName roleName);
}
