package br.edu.ifsp.commomleague.notification;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("notification")
public class NotificationController {
    
    @Autowired
    private NotificationService service;

    @GetMapping
    public ResponseEntity<Collection<NotificationResponseDTO>> findMyNotifications() {
        return ResponseEntity.ok(service.findMyNotifications());
    }

    @PostMapping
    public ResponseEntity<Void> readNotification(@RequestBody Collection<UUID> id) {
        service.readNotifications(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable UUID id) {
        service.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

}
