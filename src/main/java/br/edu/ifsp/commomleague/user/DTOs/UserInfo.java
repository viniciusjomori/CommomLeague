package br.edu.ifsp.commomleague.user.DTOs;

import java.util.UUID;

public record UserInfo(
    UUID id,
    String nickname
) {
    
}
