package br.edu.ifsp.commomleague.notification;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponseDTO(
    UUID id,
    String message,
    boolean read,
    LocalDateTime createDate
) {
    
}
