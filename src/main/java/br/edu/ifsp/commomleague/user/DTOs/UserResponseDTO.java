package br.edu.ifsp.commomleague.user.DTOs;

import java.util.UUID;

import br.edu.ifsp.commomleague.security.DTOs.RoleInfo;

public record UserResponseDTO(
    UUID id,
    String nickname,
    RoleInfo role
) {
    
}
