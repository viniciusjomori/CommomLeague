package br.edu.ifsp.commomleague.notification;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifsp.commomleague.user.entities.UserEntity;

public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {

    @Query("""
        SELECT n FROM NotificationEntity n
        WHERE
        user = :user
        AND active = true        
    """)
    Collection<NotificationEntity> findActiveByUser(UserEntity user);
    
}
