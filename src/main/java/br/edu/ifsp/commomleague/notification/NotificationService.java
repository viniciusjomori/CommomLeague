package br.edu.ifsp.commomleague.notification;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.commomleague.user.UserService;
import br.edu.ifsp.commomleague.user.entities.UserEntity;

@Service
public class NotificationService {
    
    @Autowired
    private NotificationRepository repository;

    @Autowired
    private NotificationMapper mapper;

    @Autowired
    private UserService userService;

    public void createNotification(UserEntity user, String message) {
        NotificationEntity notification = NotificationEntity.builder()
            .user(user)
            .message(message)
            .read(false)
            .build();
        repository.save(notification);
    }

    public Collection<NotificationResponseDTO> findMyNotifications() {
        UserEntity current = userService.getCurrentUser();
        Collection<NotificationEntity> entities = repository.findActiveByUser(current);
        return mapper.toResponseDTO(entities);
    }

    public void readNotifications(Collection<UUID> ids) {
        Collection<NotificationEntity> notifications = repository.findAllById(ids);
        notifications.forEach(n -> {
            n.setRead(true);
        });
        repository.saveAll(notifications);
    }

    public void deleteNotification(UUID id) {
        NotificationEntity notification = repository.findById(id)
            .orElseThrow();
        notification.setActive(false);
        repository.save(notification);
    }

}
