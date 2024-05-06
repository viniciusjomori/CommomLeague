package br.edu.ifsp.commomleague.security.DTOs;

import java.util.UUID;

import br.edu.ifsp.commomleague.security.enums.RoleName;

public record RoleInfo(
    UUID id,
    RoleName roleName
) {
    
}
